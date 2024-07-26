package com.test.SpringBatch.ChunkStudy;

import com.test.SpringBatch.ChunkStudy.domain.History;
import com.test.SpringBatch.ChunkStudy.domain.PurchaseOrder;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.ImmutableEntityUpdateQueryHandlingMode;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@ConditionalOnProperty(name =" job.name", havingValue = EntityContextConfiguration.JOB_NAME)
public class EntityContextConfiguration {
    public static final String JOB_NAME = "entityContextJob";
    private static final String STEP_NAME = "entityContextStep";


    private EntityManagerFactory entityManagerFactory;

    @Bean
    public Job job(JobRepository jobRepository, Step step) {
        return new JobBuilder(STEP_NAME, jobRepository)
                .start(step)
                .build();
    }
    @Bean
    private Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {

        return new StepBuilder(STEP_NAME, jobRepository)
                .<PurchaseOrder, History> chunk(100, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    private JpaPagingItemReader<? extends PurchaseOrder> reader() {
        JpaPagingItemReader<PurchaseOrder> reader = new JpaPagingItemReader<>();
        reader.setQueryString("select o from PurchaseOrder o") ;
        reader.setEntityManagerFactory(entityManagerFactory);

        log.info("reader -> reader = {}", reader.toString());

        return reader;
    }

    private ItemProcessor<PurchaseOrder, History> processor() {
        return item -> History.builder()
                .purchaseOrderId(item.getId())
                .productList(item.getProductList())
                .build();
    }

    private JpaItemWriter<History> writer() {
        JpaItemWriter<History> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }
}
