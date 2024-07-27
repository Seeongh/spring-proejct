package com.test.SpringBatch.Tasklet.condition;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Random;

/**
 * step이 flow만 담당하도록 다양한 분기 처리를 할 수 있도록
 * step분기만 담당하는 타입(Decider)
 */

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DeciderJobConfiguration {

    @Autowired
    PlatformTransactionManager transactionManager;

    @Autowired
    JobRepository jobRepository;

    @Bean
    public Job deciderJob(JobRepository jobRepository) {
        return new JobBuilder("deciderJob", jobRepository) //simpleJob 생성, 명시적으로 jobRepository 적용
                .start(startStep())
                .next(decider()) // 홀짝 구분
                .from(decider()) //decider 상태가
                    .on("ODD")  //ODD면, STEP으로 처리하는게 아니여서 Exitstatus가 아닌 flowExceutionStatus로 상태를 관리한다.
                    .to(oddStep()) //oddStep으로
                .from(decider())
                    .on("EVEN") //EVEN이라면
                    .to(evenStep()) //evenStep으로
                .end()
                .build();
    }
    @Bean
    public Step startStep() {

        return new StepBuilder("startStep", jobRepository) //simple step1 생성,
                .tasklet((contribution, chunkContext) -> { //step안에 수행될 기능
                    log.info(">>>>>>> This is startStep");
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }

    @Bean
    public Step evenStep() {

        return new StepBuilder("evenStep", jobRepository) //simple step1 생성,
                .tasklet((contribution, chunkContext) -> { //step안에 수행될 기능
                    log.info(">>>>> 짝수입니다.");
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }

    @Bean
    public Step oddStep() {

        return new StepBuilder("oddStep", jobRepository) //simple step1 생성,
                .tasklet((contribution, chunkContext) -> { //step안에 수행될 기능
                    log.info(">>>>>>> 홀수입니다.");
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }

    @Bean
    public JobExecutionDecider decider() {
        return new OddDecider();
    }

    public static class OddDecider implements JobExecutionDecider {

        @Override
        public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
            Random rand = new Random();

            int randomNumber = rand.nextInt(50)+1;
            log.info("랜덤 숫자 : {}" , randomNumber);

            if(randomNumber % 2 == 0 )  {
                return new FlowExecutionStatus("EVEN");
            } else {
                return new FlowExecutionStatus("ODD");
            }
        }
    }
}
