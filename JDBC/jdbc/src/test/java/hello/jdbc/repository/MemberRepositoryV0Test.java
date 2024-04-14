package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import org.junit.jupiter.api.Test;

public class MemberRepositoryV0Test {

    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    public void crud () throws Exception {
        //given
        Member member = new Member("memberVO", 10000);

        repository.save(member);
        //when

        //then

     }
}
