package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Repository //component scan으로 bean 등록
public class MemberRepository {
    @PersistenceContext //SPRINGDL ENtitymanager만들어서 주입해줌 @AutoWired로 변경가능-> REquiredArgsConstruct로 변경가능
    private EntityManager em;
    //@PersistenceUnit //factory 주입받을수도있음

//    public MemberRepository(EntityManager em ) {
//        this.em = em;
//    }

    public void save(Member member) {
        em.persist(member); //db에 insert문이 나가진 않음. 특히 generatevalue전략에서는.
        //1. db transaction이 commit될때 플러시됨
        //2. jpa 영속성 컨텍스트에 있는 멤버객체가 insert문이 만들어지고
        //3. db insert쿼리가 나가야함
    }

    public Member findOne(Long id) { //단건 조회
        return em.find(Member.class, id); //type, pk
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m ", Member.class) //from의 대상이 Table이아닌 Entity
                .getResultList();
        //SQL은 테이블 대상 쿼리인데 ORM은 ENTITY객체에 대한 쿼리임
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
