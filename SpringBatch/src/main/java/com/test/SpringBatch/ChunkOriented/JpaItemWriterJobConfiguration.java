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
import org.springframework.batch.item.database.*;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
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
public class JpaItemWriterJobConfiguration {
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
    public Job jpaItemWriterJob(JobRepository jobRepository) throws Exception {
        return new JobBuilder("jpaItemWriterJob", jobRepository)
                .start(jpaItemWriterStep(jobRepository, transactionManager))
                .build();
    }
    @Bean
    public Step jpaItemWriterStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {

        return new StepBuilder("jpaItemWriterStep", jobRepository) //simple step1 생성,
                .<Pay_origin, Pay_origin>chunk(chunkSize, transactionManager) //Reader에서 반환할 타입, Writer에 파라미터로 넘어올 타입
                .reader(jpaItemWriterReader())
                .processor(jpaItemProcessor())
                .writer(jpaItemWriter())
                .build();
    }

    @Bean
    public JpaPagingItemReader<Pay_origin> jpaItemWriterReader() throws Exception {
        return new JpaPagingItemReaderBuilder<Pay_origin>()
                .name("jpaItemWriterReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(chunkSize)
                .queryString("SELECT p FROM Pay_origin p")
                .build();
    }

    @Bean
    public ItemProcessor<Pay_origin, Pay_origin> jpaItemProcessor() {
        return pay -> new Pay_origin(pay.getAmount(), pay.getId(), pay.getTxName(), pay.getTxDateTime());
    }

    /**
     * Entity class를 제네릭 타입으로 받음
     * 넘어온 item을 그대로 entityManager.metge()로 반영
     */
    private JpaItemWriter<Pay_origin> jpaItemWriter() {
        JpaItemWriter<Pay_origin> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter().setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }

}
