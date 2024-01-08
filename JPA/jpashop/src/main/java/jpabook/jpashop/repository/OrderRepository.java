package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }


    //주문 검색
//    public List<Order> findAllByString(OrderSearch orderSearch) {
//
//        //jpql로 조건을 걸어 해결하는법
//        /*
//        em.createQuery("select o from Order o join o.member m" +
//                        "where o.status= :status" +
//                        "and m.name like :name", Order.class)
//                .setParameter("status", orderSearch.getOrderStatus())
//                .setParameter("name", orderSearch.getMemberName())
//                .getResultList();
//         */
//
//        //jpql로 그냥 조건없이 가져오는법
//        /*
//         em.createQuery("select o from Order o join o.member m)
//                .getResultList();
//         */
//
//        //jpql로 가져와서 동적으로 생성하기 - 잘안씀
//        /*
//        String jpql = "select 0 from Order o join o.member m" ;
//        boolean isFirstCondition = true;
//
//        if(orderSearch.getOrderStatus() != null) {
//            if(isFirstCondition) {
//                jpql += "where";
//                isFirstCondition = false;
//            }
//            else {
//                jpql += "and";
//            }
//            jpql += "o.status :status";
//        }
//
//          TypedQuery<Order> query = em.createQuery(jpql, Order.class)
//                .setMaxResults(1000);
//
//                // set parameter까지 진행해주어야함.
//                // return query.getResultList();
//
//         */
//        //jpql을 문자로 다루는건 버그 발생 위험이 높음
//
//    }
//    //검색 방법 2 - criteria
//    public List<Order> findAllByCriteria(OrderSearch orderSearch) {
//        //jpql을 작성할때 jpa에서 표준으로 제공하는것
//
//    }
}
