package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class) //spring과 함께 실행
@SpringBootTest //springboot띄운상태로 테스트(spring container안에서 실행)
@Transactional  //transaction 종료이후에 롤백
public class MemberServiceTest {
    /**
     * 회원 가입 : 1. 가입, 2.중복시 exception 반환
     * 회원 조회
     */

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    //@Rollback(false)   //spring transition은 rollback을 해버림 -> test때 rollback막아야 결과 볼 수 있음
    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("testA");

        //when
        Long savedId = memberService.join(member);

        //then
        em.flush(); //영속성 컨텍스트에 member가 들어가면 database에 반영함
        assertEquals(member, memberRepository.findOne(savedId)); //찾아온멤버랑 저장한거랑 같아야함
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim1");

        Member member2 = new Member();
        member1.setName("kim1");

        //when
        memberService.join(member1);
//        try {
            memberService.join(member2); //exception 발생 여기서 함수를 나감
//        }catch (IllegalStateException e) {
//             return;
//        }
        //then
         //fail("exception"); //코드가 돌다 여기 오면 잘못된거임. 들어갔다는거니까
        Assert.fail("실패");
    }

}