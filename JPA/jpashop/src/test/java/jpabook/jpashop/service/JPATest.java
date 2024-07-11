package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.TestMember;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JPATest {

    @Autowired
    EntityManager em;

    @Test
    @Transactional  //transaction 종료이후에 롤백
    public void MergeMain() throws Exception { //given
        em.clear();
        TestMember tmember = new TestMember();
        tmember.setId("member1");
        tmember.setName("회원");

        em.persist(tmember);
        em.close(); //준영속
        //when
        tmember.setName("변경"); //준영속상태라서 반영안되길 기대

        System.out.println("member=" + tmember.getName()); //회원1 기대
        TestMember mergedMember = em.merge(tmember);

        System.out.println("membermerge = " + mergedMember.getName());

        //then
        

     }

    private void mergeMember(TestMember member) {
        System.out.println("member=" + member.getName()); //회원1 기대
        TestMember mergedMember = em.merge(member);

        System.out.println("membermerge = " + mergedMember.getName());

    }

    private TestMember createMember(String memberA, String name) {
        TestMember member = new TestMember();
        member.setId(memberA);
        member.setName(name);

        em.persist(member);
        em.close();

        return member;
    }

    @Test
    @Transactional  //transaction 종료이후에 롤백
    public void updateTest() throws Exception {
        //given
        Long id= 1L;
        Member member = new Member();

        member.setId(id);
        member.setName("사람");

        em.persist(member);

        //when
        member.setName("변경");
        Member findMem = em.find(Member.class, id);
        System.out.println("findMem member name = "+ findMem.getName());

        //JPQL : 엔티티 객체(클래스)를 대상으로 쿼리
        TypedQuery<Member> query = em.createQuery("select m from Member m " , Member.class);
        List<Member> members = query.getResultList();

     }
}

