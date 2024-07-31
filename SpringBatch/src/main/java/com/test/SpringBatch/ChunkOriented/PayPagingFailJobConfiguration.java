package com.test.SpringBatch.ChunkOriented;

import com.test.SpringBatch.ChunkOriented.domain.Pay;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@RequiredArgsConstructor
@Configuration
@ConditionalOnProperty(name = "sprinb.batch.job.names", havingValue = PayPagingFailJobConfiguration.JOB_NAME)
public class PayPagingFailJobConfiguration {

    public static final String JOB_NAME = "payPagingFailJob";

    private final EntityManagerFactory entityManagerFactory;

    private final PlatformTransactionManager transactionManager;

    private final int chunkSize = 10;

    @Bean
    public Job payPagingJob(JobRepository jobRepository) throws Exception {
        return new JobBuilder(JOB_NAME, jobRepository)
                .start(payPagingStep(jobRepository, transactionManager))
                .build();
    }

    @Bean
    @JobScope
    public Step payPagingStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("payPagingStep", jobRepository)
                .<Pay, Pay>chunk(chunkSize, transactionManager)
                .reader(payPagingReader())
                .processor(payPagingProcessor())
                .writer(writer())
                .build();
    }

    /**
     * paging 은 offset과 limit을 이용하여 매 조회마다 다음 페이지를 읽어오는 방식.
     * 페이징은 offset을 offset+ limit로 이루어지는데,
     * 데이터를 처리할 떄마다 조건에 걸리는 데이터의 순번이  달라,
     * 다음 offset으로 넘어갈경우 누락되는 데이터가 생김
     *
     * [해결 방법]
     * 1. cursor사용
     * 2. pgingreader override -> page번호를 0번째로 고정
     *
     * @return
     */
    @Bean
    @StepScope
    public JpaPagingItemReader<Pay> payPagingReader() {
        JpaPagingItemReader<Pay> reader = new JpaPagingItemReader<Pay>() {
            @Override
            public int getPage() {
                return 0 ;
            }
        };

        reader.setQueryString("SELECT p FROM Pay p WHERE p.successStatus=false");
        reader.setPageSize(chunkSize);
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setName("payPagingReader");

        return reader;
    }

    @Bean
    @StepScope
    public ItemProcessor<Pay, Pay> payPagingProcessor() {
        return item -> {
            log.info("processor -> {}", item.getId());
            item.success();
            return item;
        };
    }


    /**
     * Wirter는 3가지
     * JdbcBatchItemWrtier : 쿼리를 모아서 한번에 db로 보냄
     * HibernateItemWrtier
     * JpaItemWriter
     * @return
     */
    @Bean
    @StepScope
    public JpaItemWriter<Pay> writer() {
        JpaItemWriter<Pay> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }
}

