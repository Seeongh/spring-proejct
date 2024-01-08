package jpabook.jpashop.service;

import jpabook.jpashop.item.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemUpdateTest {

    @Autowired
    EntityManager em;
    @Test
    public void updateTest() throws Exception {
        //기본 변경 메커니즘
        Book book = em.find(Book.class, 1L);
        //tx
        book.setName("asdfad");
        //flush 시점에 dirtychecking 변경감지를 통해 업데이트 함


        //준영속 Entity일때 , updateItem시 로직이 날라오면서 Bookform을 통해 데이터가 넘어옴
        //jpa에 한번 들어갔다 나온애 -> 준영속 엔티티
        //jpa에 갔다온 id는 있는데 ...영속되어있지는 않음


     }
}
