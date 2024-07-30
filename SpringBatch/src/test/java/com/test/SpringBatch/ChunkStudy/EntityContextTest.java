package com.test.SpringBatch.ChunkStudy;

import com.test.SpringBatch.ChunkStudy.domain.Product;
import com.test.SpringBatch.ChunkStudy.domain.PurchaseOrder;
import com.test.SpringBatch.ChunkStudy.repository.PurchaseOrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@SpringJUnitConfig(EntityContextConfiguration.class)
@TestPropertySource(properties = {"spring.batch.job.name=" + EntityContextConfiguration.JOB_NAME})
public class EntityContextTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;


    @Autowired
    private Job job;
    @Before
    public void setUp() {
        purchaseOrderRepository.deleteAll();
        this.jobLauncherTestUtils.setJob(job);
    }

    @Test
    public void testJobExecution() throws Exception {
        // given
        for (int i = 0; i < 100; i++) {
            purchaseOrderRepository.save(PurchaseOrder.builder()
                    .memo("도착할때 전화주세요.")
                    .productList(Arrays.asList(
                            Product.builder().name("마우스").amount(10000L).build(),
                            Product.builder().name("키보드").amount(30000L).build()
                    ))
                    .build());
        }

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        // then
        assertThat(purchaseOrderRepository.findAll().size()).isEqualTo(100);
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }
//
//    @Bean
//    public JobLauncherTestUtils getJobLauncherTestUtils(){
//
//        return new JobLauncherTestUtils() {
//            @Override
//            @Autowired
//            public void setJob(@Qualifier("entityContextJob") Job job) {
//                super.setJob(job);
//            }
//        };
//    }
}