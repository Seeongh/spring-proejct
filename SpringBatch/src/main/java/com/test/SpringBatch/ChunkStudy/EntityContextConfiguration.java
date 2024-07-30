package com.test.SpringBatch.ChunkStudy;

import com.test.SpringBatch.ChunkStudy.domain.History;
import com.test.SpringBatch.ChunkStudy.domain.PurchaseOrder;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@ConditionalOnProperty(name ="spring.batch.job.name", havingValue = EntityContextConfiguration.JOB_NAME)
//환경 속성이 있고 특정 값이 있는 경우에만 Bean 등록
// job.names 속성이 있고, value가 entityContextJob인경우에만 빈을 생성
public class EntityContextConfiguration {
    public static final String JOB_NAME = "entityContextJob";
    public static final String STEP_NAME = "entityContextStep";

    private final EntityManagerFactory entityManagerFactory;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;


    public EntityContextConfiguration(EntityManagerFactory entityManagerFactory, JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        this.entityManagerFactory = entityManagerFactory;
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    @Bean
    public Job job() {
        return new JobBuilder(JOB_NAME, jobRepository)
                .start(step())
                .build();
    }

    @Bean
    public Step step() {
        return new StepBuilder(STEP_NAME, jobRepository)
                .<PurchaseOrder, History>chunk(100, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public JpaPagingItemReader<PurchaseOrder> reader() {
        JpaPagingItemReader<PurchaseOrder> reader = new JpaPagingItemReader<>();
        reader.setQueryString("select o from PurchaseOrder o");
        reader.setEntityManagerFactory(entityManagerFactory);
        return reader;
    }

    @Bean
    public ItemProcessor<PurchaseOrder, History> processor() {
        return item -> History.builder()
                .purchaseOrderId(item.getId())
                .productList(item.getProductList())
                .build();
    }

    @Bean
    public JpaItemWriter<History> writer() {
        JpaItemWriter<History> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }
}
