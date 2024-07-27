package com.test.SpringBatch.Tasklet.condition;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * BatchStatus / Exit Status
 * BatchStatus : job, step의 실행결과를 Spring에서 기록
 * ExitStatus: Step의 실행 후 상태
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class StepNextConditionalJobConfiguration {
    @Autowired
    PlatformTransactionManager transactionManager;

    @Autowired
    JobRepository jobRepository;
    /**
     * Job Repository
     * 상태 관리 : job 상태를 저장
     *   - 오류 발생시 처리, 복구, 트랙킹, 동작수행 결정
     * 모니터링 : 실행 값 및 진행 파악
     */
    @Bean
    public Job stepNextConditionalJob(JobRepository jobRepository) {
        return new JobBuilder("stepNextConditionalJob", jobRepository) //simpleJob 생성, 명시적으로 jobRepository 적용
                .start(conditionalJobStep1())
                    .on("FAILED") //step1이라서 에러 발생
                    .to(conditionalJobStep3()) //step3이동
                    .on("*")// 결과 관계없이
//                .end() //flow종료'
//                    .from(conditionalJobStep1())
//                    .on("COMPLETED WITH SKIPS") //StepExecutionListener를 상속 받아 ExitStatus를 구현할 수 있음.
//                    .to(errorPrint1())
                .end()
                    .from(conditionalJobStep1()) //step1로부터
                        .on("*") //failed외 경우에
                        .to(conditionalJobStep2()) //step2이동
                        .next(conditionalJobStep3())// step2끝나면 step3이동
                        .on("*")// 결과 상관없이
                    .end()//flow종료
                .end()//job 종료
                .build();
    }
    @Bean
    public Step conditionalJobStep1() {

        return new StepBuilder("conditionalJobStep1", jobRepository) //simple step1 생성,
                .tasklet((contribution, chunkContext) -> { //step안에 수행될 기능
                    log.info(">>>>>>> This is conditionalJobStep1");

                    /**
                     * Exitstatus는 failed!
                     * 이 status를 보고 flow진행
                     */
                    //contribution.setExitStatus(ExitStatus.FAILED);
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }

    @Bean
    public Step conditionalJobStep2() {

        return new StepBuilder("conditionalJobStep2", jobRepository) //simple step1 생성,
                .tasklet((contribution, chunkContext) -> { //step안에 수행될 기능
                    log.info(">>>>>>> This is conditionalJobStep2");
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }

    @Bean
    public Step conditionalJobStep3() {

        return new StepBuilder("conditionalJobStep3", jobRepository) //simple step1 생성,
                .tasklet((contribution, chunkContext) -> { //step안에 수행될 기능
                    log.info(">>>>>>> This is conditionalJobStep3");
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }
}
