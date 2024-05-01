package hello.sevlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * [기존] 서블릿 코드를 클래스 파일로 빌드해서 톰캣같은 웹서버에 배포
 * [Spring Boot] 톰캣 서버를 내장하고 있어, 톰캣 서버 설치 없이 편리하게 실행
 * @ServletComponentScan 을 통해 서블릿을 직접 등록해서 사용함.
 */
@ServletComponentScan //서블릿 자동등록
@SpringBootApplication
public class SevletApplication {

	public static void main(String[] args) {
		SpringApplication.run(SevletApplication.class, args);
	}

}
