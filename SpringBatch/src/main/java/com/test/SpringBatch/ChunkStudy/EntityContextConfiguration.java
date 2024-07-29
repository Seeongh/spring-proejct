package com.test.SpringBatch.ChunkStudy;

import com.test.SpringBatch.ChunkStudy.domain.History;
import com.test.SpringBatch.ChunkStudy.domain.PurchaseOrder;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.ImmutableEntityUpdateQueryHandlingMode;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@ConditionalOnProperty(name ="spring.batch.job.name", havingValue = EntityContextConfiguration.JOB_NAME)
//환경 속성이 있고 특정 값이 있는 경우에만 Bean 등록
// job.names 속성이 있고, value가 entityContextJob인경우에만 빈을 생성
public class EntityContextConfiguration {
    public static final String JOB_NAME = "entityContextJob";
    public static final String STEP_NAME = "entityContextStep";

     EntityManagerFactory entityManagerFactory;


    @Bean
    public Job entityContextJob(JobRepository jobRepository) {
        return new JobBuilder(JOB_NAME, jobRepository)
                .start(step(jobRepository))
                .build();
    }

    private Step step(JobRepository jobRepository) {

        return new StepBuilder(STEP_NAME, jobRepository)
                .<PurchaseOrder, History> chunk(100, transactionManager())
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }

    private JpaPagingItemReader<? extends PurchaseOrder> reader() {
        JpaPagingItemReader<PurchaseOrder> reader = new JpaPagingItemReader<>();
        reader.setQueryString("select o from PurchaseOrder o") ;
        reader.setEntityManagerFactory(entityManagerFactory);

        log.info("reader -> reader = {}", reader.toString());

        return reader;
    }

    private ItemProcessor<PurchaseOrder, History> processor() {
        log.info("itemProcessor");
        return item -> History.builder()
                .purchaseOrderId(item.getId())
                .productList(item.getProductList())
                .build();
    }

    private JpaItemWriter<History> writer() {
        log.info("ItemWriter");
        JpaItemWriter<History> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }
}
