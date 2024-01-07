package jpabook.jpashop;

import com.fasterxml.classmate.MemberResolver;
import org.assertj.core.api.Assertions;
import org.hibernate.boot.TempTableDdlTransactionHandling;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;


@SpringBootTest
@RunWith(SpringRunner.class)
public class MemberRepositoryTest {
   // @Autowired MemberRepository memberRepository;

    @Test
    @Transactional //테스트 후 바로 롤백함
    @Rollback(false)
    public void testMember() throws Exception {
        //given
     //   Member member = new Member();
    //    member.setUsername("userA");

        //when
     //   Long savedId = memberRepository.save(member);
      //  Member findmember = memberRepository.find(savedId);
        //then
     //   Assertions.assertThat(findmember.getId()).isEqualTo(member.getId());

    }
}