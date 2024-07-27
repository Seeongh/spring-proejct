package com.test.SpringBatch.ChunkStudy;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringBatchTest
@SpringJUnitConfig(BatchTestConfig.class)
public class BatchTestConfig {


//    @Bean
//    public JobLauncherTestUtils jobLauncherTestUtils() {
//        return new JobLauncherTestUtils();
//    }

    @Bean
    public JobLauncherTestUtils jobLauncherTestUtils(Job job) {
        JobLauncherTestUtils jobLauncherTestUtils = new JobLauncherTestUtils();
        jobLauncherTestUtils.setJob(job);
        return jobLauncherTestUtils;
    }
}
