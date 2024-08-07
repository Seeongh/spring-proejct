package com.test.SpringBatch.ChunkOriented;

import com.test.SpringBatch.ChunkOriented.domain.Pay_origin;
import jakarta.persistence.EntityManager;
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
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class CustomItemWriterJobConfiguration {
    private final PlatformTransactionManager transactionManager;

    private final DataSource dataSource; // DataSource DI
    private static final int chunkSize = 10;
    private final EntityManagerFactory em;

    @Bean
    public Job CustomItemWriterJob(JobRepository jobRepository) throws Exception {
        return new JobBuilder("CustomItemWriterJob", jobRepository)
                .start(customItemWriterStep(jobRepository, transactionManager))
                .build();
    }

    @JobScope
    public Step customItemWriterStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("customItemWriterStep", jobRepository)
                .<Pay_origin, Pay_origin> chunk(chunkSize, transactionManager)
                .reader(customItemWriterReader())
                .processor(customItemWriterProcessor())
                .writer(customItemWriter())
                .build();
    }

    @StepScope
    public JpaPagingItemReader<Pay_origin> customItemWriterReader() {
        return new JpaPagingItemReaderBuilder<Pay_origin>()
                .name("customItemWriterReader")
                .entityManagerFactory(em)
                .pageSize(chunkSize)
                .queryString("SELECT p FROM Pay_origin p")
                .build();
    }

    @StepScope
    public ItemProcessor<Pay_origin, Pay_origin> customItemWriterProcessor() {
        return Pay_origin -> new Pay_origin(Pay_origin.getAmount(), Pay_origin.getTxName(), Pay_origin.getTxDateTime());
    }

    @StepScope
    public ItemWriter<Pay_origin> customItemWriter() {
        return new ItemWriter<Pay_origin>() {

            @Override
            public void write(Chunk<? extends Pay_origin> chunk) throws Exception {
                for(Pay_origin item : chunk) {
                    System.out.println(chunk.toString());
                }
            }
        };
    }
}
