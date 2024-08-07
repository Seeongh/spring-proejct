//package com.test.SpringBatch.ChunkOriented;
//
//import com.test.SpringBatch.ChunkOriented.domain.Pay_origin;
//import jakarta.persistence.EntityManagerFactory;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.database.JpaPagingItemReader;
//import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//
///**
// * PagingItemReader 주의사항
// *  : 정렬이 포함되어있어야함 -> 정렬기준이 정해져있지 않으면 쿼리마다 본인들만의 정렬기준을 만들어 실행
// * 앞에서 조회했던 데이터가 다음 조회 쿼리의 정렬기준에 포함되기도 하고, 빠지기도 했던 것입니다.
// *
// *
// */
//@Slf4j
//@RequiredArgsConstructor
//@Configuration
//public class JpaPagingItemReaderJobConfiguration {
//    private final PlatformTransactionManager transactionManager;
//
//    private final EntityManagerFactory entityManagerFactory;
//    private static final int chunkSize = 10;
//    /**
//     * Job Repository
//     * 상태 관리 : job 상태를 저장
//     *   - 오류 발생시 처리, 복구, 트랙킹, 동작수행 결정
//     * 모니터링 : 실행 값 및 진행 파악
//     */
//    @Bean
//    public Job jpaPagingItemReaderJob(JobRepository jobRepository) throws Exception {
//        return new JobBuilder("jpaPagingItemReaderJob", jobRepository) //simpleJob 생성, 명시적으로 jobRepository 적용
//                .start(jpaPagingItemReaderStep(jobRepository, transactionManager))
//                .build();
//    }
//    @Bean
//    public Step jpaPagingItemReaderStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {
//
//        return new StepBuilder("jpaPagingItemReaderStep", jobRepository) //simple step1 생성,
//                .<Pay_origin, Pay_origin>chunk(chunkSize, transactionManager) //Reader에서 반환할 타입, Writer에 파라미터로 넘어올 타입
//                .reader(jpaPagingItemReader())
//                .writer(jpaPagingItemWriter())
//                .build();
//    }
//
//    @Bean
//    public JpaPagingItemReader<Pay_origin> jpaPagingItemReader() throws Exception {
//
//        return new JpaPagingItemReaderBuilder<Pay_origin>()
//                .name("jpaPagingItemReader")
//                .entityManagerFactory(entityManagerFactory)
//                .pageSize(chunkSize)
//                .queryString("SELECT p FROM Pay p WHERE amount >= 2000")
//                .build();
//    }
//
//    private ItemWriter<Pay_origin> jpaPagingItemWriter() {
//        return list -> {
//            for(Pay_origin payOrigin : list) {
//                log.info("Current Pay = {}", payOrigin);
//            }
//        };
//    }
//
//
//}
