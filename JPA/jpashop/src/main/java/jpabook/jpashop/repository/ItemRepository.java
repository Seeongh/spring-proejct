package jpabook.jpashop.repository;

import jpabook.jpashop.item.Book;
import jpabook.jpashop.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) { //item은 jpa저장전까지 id가없음 = 영속성 context등록이 안되어있음
        if(item.getId() == null) {
            em.persist(item);
        }
        else{ //수정했떠니 merge로 왔네? - 실무에서는 잘안씀
            em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id) ;
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }

}
