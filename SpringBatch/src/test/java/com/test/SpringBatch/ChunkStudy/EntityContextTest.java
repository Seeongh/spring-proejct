package com.test.SpringBatch.ChunkStudy;

import com.test.SpringBatch.ChunkStudy.domain.Product;
import com.test.SpringBatch.ChunkStudy.domain.PurchaseOrder;
import com.test.SpringBatch.ChunkStudy.repository.PurchaseOrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
@SpringBatchTest
@SpringBootTest(classes = {BatchTestConfig.class})
@TestPropertySource(properties = {"job.name=" + EntityContextConfiguration.JOB_NAME})
public class EntityContextTest {


    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;


    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Test
    public void read에서_processor로_entity넘기면_영속성이_유지되여야함() throws Exception {

        //given
        for(int i =0 ; i<100; i++)  {
            purchaseOrderRepository.save(
                    PurchaseOrder.builder()
                            .memo("도착할때 전화주세요")
                            .productList(
                                    Arrays.asList(
                                          Product.builder().name("마우스").amount(10000L).build(),
                                            Product.builder().name("키보드").amount(30000L).build()
                                    )
                            )
                            .build()
            );
        }
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        //when

        //then
        Assertions.assertThat(purchaseOrderRepository.findAll().size()).isEqualTo(100);
        Assertions.assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);

     }
}
