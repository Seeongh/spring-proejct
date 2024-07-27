package com.test.SpringBatch.Tasklet.job;

import com.test.SpringBatch.ChunkStudy.domain.History;
import com.test.SpringBatch.ChunkStudy.domain.PurchaseOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@RequiredArgsConstructor
@Configuration // Spring batch job은 configuration으로 등록되어야하낟.
public class SimpleJobConfiguration {


    @Autowired
    PlatformTransactionManager transactionManager;
    /**
     * Job Repository
     * 상태 관리 : job 상태를 저장
     *   - 오류 발생시 처리, 복구, 트랙킹, 동작수행 결정
     * 모니터링 : 실행 값 및 진행 파악
     */
    @Bean
    public Job simpleJob(JobRepository jobRepository) {
        return new JobBuilder("simpleJob", jobRepository) //simpleJob 생성, 명시적으로 jobRepository 적용
                .start(simpleStep1(jobRepository, transactionManager))
                .build();
    }
    @Bean
    public Step simpleStep1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {

        return new StepBuilder("simpleStep1", jobRepository) //simple step1 생성,
                .tasklet((contribution, chunkContext) -> { //step안에 수행될 기능
                    log.info(">>>>>>> This is step1");
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }
}
