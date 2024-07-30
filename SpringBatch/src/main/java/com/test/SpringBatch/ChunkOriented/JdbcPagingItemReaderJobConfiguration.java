package com.test.SpringBatch.ChunkOriented;

import com.test.SpringBatch.ChunkOriented.domain.Pay;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static javax.swing.SortOrder.ASCENDING;

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
                .<Pay, Pay>chunk(chunkSize, transactionManager) //Reader에서 반환할 타입, Writer에 파라미터로 넘어올 타입
                .reader(jdbcPagingItemReader())
                .writer(jdbcPagingItemWriter())
                .build();
    }

    @Bean
    public JdbcPagingItemReader<Pay> jdbcPagingItemReader() throws Exception {
        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("amount", 2000);

        return new JdbcPagingItemReaderBuilder<Pay>()
                .pageSize(chunkSize) //SELECT id, amount, tx_name, tx_date_time FROM pay WHERE amount >= : amount ORDER BY {id=ASCENDING} ASC FETCH NEXT 10 ROWS ONLY
                .fetchSize(chunkSize) //DataBase에서 한번에 가져올 데이터의 양
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(Pay.class)) //쿼리 결과를 java객체로 매핑
                .queryProvider(createQueryProvider())
                .parameterValues(parameterValues) //쿼리 매개변수
                .name("jdbcPagingItemReader") //sring batch  executionContext에서 저장될 이름
                .build();
    }

    private ItemWriter<Pay> jdbcPagingItemWriter() {
        return list -> {
            for(Pay pay: list) {
                log.info("Current Pay = {}", pay);
            }
        };
    }

    @Bean
    public PagingQueryProvider createQueryProvider() throws Exception {
        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
        queryProvider.setDataSource(dataSource);
        queryProvider.setSelectClause("id, amount, tx_name, tx_date_time");
        queryProvider.setFromClause("from pay");
        queryProvider.setWhereClause("where amount >= : amount");

        Map<String, Order> sortKeys= new HashMap<>(1);
        sortKeys.put("id", Order.ASCENDING);

        queryProvider.setSortKey(sortKeys.toString());

        return queryProvider.getObject();
    }
}
