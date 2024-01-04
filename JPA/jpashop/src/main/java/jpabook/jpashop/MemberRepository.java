package jpabook.jpashop;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {
    //엔티티 매니저가 있어야함
    @PersistenceContext
    private EntityManager em; //jpa lib등록하면서 entitymanager 자동 주입

    public Long save(Member member) {
        em.persist(member);
        return member.getId(); //command랑 query분리하려고 가급적이면 리턴값을 거의 안만듦
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }

}
