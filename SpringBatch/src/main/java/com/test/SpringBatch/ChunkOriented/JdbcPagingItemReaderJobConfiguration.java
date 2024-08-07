package com.test.SpringBatch.ChunkOriented;

import com.test.SpringBatch.ChunkOriented.domain.Pay_origin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * cursor, paging 두개의 reader타입 중 paging 방식
 * 페이지 단위로 한번에 데이터를 조회해옴(설정한 페이지 단위로 connec을 맺고 끊어 부하가 적다)
 */

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JdbcPagingItemReaderJobConfiguration {

    private final PlatformTransactionManager transactionManager;

    private final DataSource dataSource; // DataSource DI
    private static final int chunkSize = 10;
    /**
     * Job Repository
     * 상태 관리 : job 상태를 저장
     *   - 오류 발생시 처리, 복구, 트랙킹, 동작수행 결정
     * 모니터링 : 실행 값 및 진행 파악
     */
    @Bean
    public Job jdbcPagingItemReaderJob(JobRepository jobRepository) throws Exception {
        return new JobBuilder("jdbcPagingItemReaderJob", jobRepository) //simpleJob 생성, 명시적으로 jobRepository 적용
                .start(jdbcPagingItemReaderStep(jobRepository, transactionManager))
                .build();
    }
    @Bean
    public Step jdbcPagingItemReaderStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {

        return new StepBuilder("jdbcPagingItemReaderStep", jobRepository) //simple step1 생성,
                .<Pay_origin, Pay_origin>chunk(chunkSize, transactionManager) //Reader에서 반환할 타입, Writer에 파라미터로 넘어올 타입
                .reader(jdbcPagingItemReader())
                .processor(jdbcPagingItemReaderProcessor())
                .writer(jdbcPagingItemWriter())
                .build();
    }

    @Bean
    public JdbcPagingItemReader<Pay_origin> jdbcPagingItemReader() throws Exception {
        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("amount", 2000);

        return new JdbcPagingItemReaderBuilder<Pay_origin>()
                .pageSize(chunkSize) //SELECT id, amount, tx_name, tx_date_time FROM pay WHERE amount >= : amount ORDER BY {id=ASCENDING} ASC FETCH NEXT 10 ROWS ONLY
                .fetchSize(chunkSize) //DataBase에서 한번에 가져올 데이터의 양
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(Pay_origin.class)) //쿼리 결과를 java객체로 매핑
                .queryProvider(createQueryProvider2())
                .parameterValues(parameterValues) //쿼리 매개변수
                .name("jdbcPagingItemReader") //sring batch  executionContext에서 저장될 이름
                .build();
    }

    private ItemWriter<Pay_origin> jdbcPagingItemWriter() {
        return list -> {
            for(Pay_origin payOrigin : list) {
                log.info("Current Pay = {}", payOrigin);
            }
        };
    }

    @StepScope
    public ItemProcessor<Pay_origin, Pay_origin> jdbcPagingItemReaderProcessor() {
        log.info("processor");
        return new ItemProcessor<Pay_origin, Pay_origin>() {
            @Override
            public Pay_origin process(Pay_origin item) throws Exception {
                log.info("processor item = {}", item.toString());
                return item;
            }
        };
    }

    @Bean
    public PagingQueryProvider createQueryProvider2() throws Exception {
        log.info("create providre datasource = {} ", dataSource.toString() );

        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
        queryProvider.setDataSource(dataSource);
        queryProvider.setSelectClause("id, amount, tx_name, tx_date_time");
        queryProvider.setFromClause("from pay_origin");
      //  queryProvider.setWhereClause("where amount >= :amount");  // 공백 제거

// 정렬 키를 Map으로 직접 설정
        Map<String, Order> sortKeys = new HashMap<>();
        sortKeys.put("id", Order.ASCENDING);
        queryProvider.setSortKeys(sortKeys);  // Map 객체를 직접 사용

        return queryProvider.getObject();
    }
}
