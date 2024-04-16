package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

@Slf4j
public class MemberRepositoryV0Test {

    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    public void crud () throws Exception {
        //given
        Member member = new Member("memberVO3", 30000);

        repository.save(member);
        //when
       // Member byId = repository.findById("memberVO");
       // log.info("findMember={}", byId);
        //then
       // Assertions.assertThat(byId),isEqual(member);
        //instance는 다르지만

        //update
        repository.update("memberVO3", 20000);
        Member updatedMember = repository.findById("memberVO");
        Assertions.assertThat(updatedMember.getMoney()).isEqualTo(20000);

        //delete
        repository.delete("memberVO3");
        //exception 검증
        Assertions.assertThatThrownBy(()-> repository.findById("memberV3"))
                .isInstanceOf(NoSuchElementException.class); //지웠는데 찾아서 exception\
        //마지막 삭제하는건 비추 : 같은테스트 돌리다가 뻑나면 삭제가 안되고 insert도안되기때문

     }
}
