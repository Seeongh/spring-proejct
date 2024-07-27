package com.test.SpringBatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableBatchProcessing //배치 기능 활성화 -> auto 실행시 없어야됨.
// spring batch 5에서는 DefaultBatchConfiguration 라는걸 이용해서 자동 구성을 해주고있음
//BatchAutoConfiguration 클래스에서 자동으로 구성해줌으로 @EnableBatchProcession이나 DefaultBatchConfiguration 은 없어야 자동실행댐
@SpringBootApplication
public class SpringBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchApplication.class, args);
	}

}
