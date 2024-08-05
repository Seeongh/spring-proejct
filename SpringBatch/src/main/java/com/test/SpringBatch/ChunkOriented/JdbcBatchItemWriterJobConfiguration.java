package com.test.SpringBatch.ChunkOriented;

import com.test.SpringBatch.ChunkOriented.domain.Pay_origin;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JdbcBatchItemWriterJobConfiguration {
    private final PlatformTransactionManager transactionManager;

    private final DataSource dataSource; // DataSource DI
    private final EntityManagerFactory entityManagerFactory;
    private static final int chunkSize = 10;
    /**
     * Job Repository
     * 상태 관리 : job 상태를 저장
     *   - 오류 발생시 처리, 복구, 트랙킹, 동작수행 결정
     * 모니터링 : 실행 값 및 진행 파악
     */
    @Bean
    public Job jdbcBatchItemWriterJob(JobRepository jobRepository) throws Exception {
        return new JobBuilder("jdbcBatchItemWriterJob", jobRepository)
                .start(jdbcBatchItemWriterStep(jobRepository, transactionManager))
                .build();
    }
    @Bean
    public Step jdbcBatchItemWriterStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {

        return new StepBuilder("jdbcBatchItemWriterStep", jobRepository) //simple step1 생성,
                .<Pay_origin, Pay_origin>chunk(chunkSize, transactionManager) //Reader에서 반환할 타입, Writer에 파라미터로 넘어올 타입
                .reader(jdbcBatchItemWriterReader())
                .processor(jpaItemProcessor())
                .writer(jdbcBatchItemWriter())
                .build();
    }

    @Bean
    public JdbcPagingItemReader<Pay_origin> jdbcBatchItemWriterReader() throws Exception {
        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("amount", 2000);

        return new JdbcPagingItemReaderBuilder<Pay_origin>()
                .pageSize(chunkSize) //SELECT id, amount, tx_name, tx_date_time FROM pay WHERE amount >= : amount ORDER BY {id=ASCENDING} ASC FETCH NEXT 10 ROWS ONLY
                .fetchSize(chunkSize) //DataBase에서 한번에 가져올 데이터의 양
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(Pay_origin.class)) //쿼리 결과를 java객체로 매핑
                .queryProvider(createQueryProvider())
                .parameterValues(parameterValues) //쿼리 매개변수
                .name("jdbcBatchItemWriter") //sring batch  executionContext에서 저장될 이름
                .build();
    }

    @Bean
    public ItemProcessor<Pay_origin, Pay_origin> jpaItemProcessor() {
        return pay -> new Pay_origin(pay.getAmount(), pay.getId(), pay.getTxName(), pay.getTxDateTime());
    }

    /**
     * 제네릭 타입은 Reader에서 반환하는 값
     * 모든 Item 처리 후 트랜잭션 커밋
     * Query를 모아두고 한번에 전송한다(chunksize만큼)
     * @return
     */
    private ItemWriter<Pay_origin> jdbcBatchItemWriter() {
        return new JdbcBatchItemWriterBuilder<Pay_origin>()
                .dataSource(dataSource)
                .sql("insert into pay(amount, tx_name, tx_date_time) values (:amount, :txName, :txDateTime") // values(:field) dto의 getter, map의 key에 매핑됨
                .beanMapped() //value 매핑 return type 객체, columnmapped는 Map
                .build();
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

