package hello.jdbc.repository;

import com.zaxxer.hikari.HikariDataSource;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

import static hello.jdbc.connection.ConnectionConst.*;

import java.util.NoSuchElementException;

@Slf4j
public class MemberRepositoryV1Test {

    MemberRepositoryV1 repository;

    @BeforeEach //각 테스트 실행 직전에 한번씩 호출됨
    void beforeEach(){
        //기본 DriverManger = 항상 새로운 커넥션 획득
        //DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);

        // Connection Pool = 커넥션풀 할당하여 거기서 커넥션 획득
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        //DATASOURCE로 해주면 setJdbcURL이 없어서 hikariDataSOurce로함


        repository = new MemberRepositoryV1(dataSource);
    }

    @Test
    public void crud () throws Exception {
        //given
        Member member = new Member("memberVO8", 30000);

        repository.save(member);
        //when
       // Member byId = repository.findById("memberVO");
       // log.info("findMember={}", byId);
        //then
       // Assertions.assertThat(byId),isEqual(member);
        //instance는 다르지만

        //update
        repository.update("memberVO8", 20000);
        Member updatedMember = repository.findById("memberVO8");
        Assertions.assertThat(updatedMember.getMoney()).isEqualTo(20000);

        //delete
        repository.delete("memberVO8");
        //exception 검증
        Assertions.assertThatThrownBy(()-> repository.findById("memberVO8"))
                .isInstanceOf(NoSuchElementException.class); //지웠는데 찾아서 exception\
        //마지막 삭제하는건 비추 : 같은테스트 돌리다가 뻑나면 삭제가 안되고 insert도안되기때문

        Thread.sleep(1000);
     }
}
