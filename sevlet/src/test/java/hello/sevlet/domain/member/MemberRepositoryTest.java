package hello.sevlet.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class MemberRepositoryTest {

    MemberRepository memberRepository = MemberRepository.getInstance();

    @AfterEach
    void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void save() {
        //given
        Member member = new Member("hellp",20);

        //when
        Member saved = memberRepository.save(member);

        //then
        Member find = memberRepository.findById(saved.getId());

        assertThat(find).isEqualTo(saved); //가져온건 같아야한다.찾은거랑
    }

    @Test
    void findAll() {
        //given
        Member member1 = new Member("member1", 20);
        Member member2 = new Member("member2", 30);
        
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> all = memberRepository.findAll();

        assertThat(all.size()).isEqualTo(2); //안에 두개 있다
        assertThat(all).contains(member1,member2); //두개는 이거다
    }
}
