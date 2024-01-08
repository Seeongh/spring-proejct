package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.item.Album;
import jpabook.jpashop.item.Item;
import jpabook.jpashop.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    OrderService orderService;
    @Autowired
    EntityManager em;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        //given
        Member member = new Member();
        member.setName("test1");
        member.setAddress(new Address("서울","경기","123-123"));
        em.persist(member);

        Item item = new Album();
        item.setName("iu album");
        item.setPrice(10000);
        item.setStockQuantity(3);
        em.persist(item);

        int orderCount = 2;
        //when
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);//주문넣음

        //then
        Order getOrder = orderRepository.findOne(orderId);

        Assert.assertEquals("상품 주문 시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        Assert.assertEquals("주문한 상품 종류 수가 정확해야한다.",1,getOrder.getOrderItems().size());
        Assert.assertEquals("주문가격은 가격 * 수량이다", 10000 * orderCount, getOrder.getTotalPrice());
        Assert.assertEquals("주문 수량 만큼 재고가 줄어야한다" , 1, item.getStockQuantity());
    }

     @Test
     public void 주문취소() throws Exception {
         //given
         Member member = new Member();
         member.setName("test1");
         member.setAddress(new Address("서울","경기","123-123"));
         em.persist(member);

         Item item = new Album();
         item.setName("iu album");
         item.setPrice(10000);
         item.setStockQuantity(3);
         em.persist(item);

         int orderCount = 2;
         Long orderId = orderService.order(member.getId(), item.getId(), orderCount);//주문넣음

         //when
         orderService.cancelOrder(orderId);

         //then

         Order getOrder = orderRepository.findOne(orderId);
       //  Assert.assertEquals("주문한 상품 종류 수는 없다.", 0 ,getOrder.getOrderItems().size()); //item만 지우고 order는살아있어서 걸림
         Assert.assertEquals("주문상태는 cancel이다", OrderStatus.CANCEL, getOrder.getStatus());
         Assert.assertEquals("주문 취소상품은 재고가 증가해야함", 3, item.getStockQuantity());

      }

      @Test(expected = NotEnoughStockException.class)
      public void 상품재고수량초과() throws Exception {
          //given
          Member member = new Member();
          member.setName("test1");
          member.setAddress(new Address("서울","경기","123-123"));
          em.persist(member);

          Item item = new Album();
          item.setName("iu album");
          item.setPrice(10000);
          item.setStockQuantity(3);
          em.persist(item);

          int orderCount = 4;

          //when
          Long orderId = orderService.order(member.getId(), item.getId(), orderCount);//주문넣음
          //여기서 order.createOrder -> orderItem.CreateOrderItem에서 -> item의 stock을 까주는데,
          //removeitem에서 exception발생

          //then
            fail("예외 발생해야댐");
       }
}