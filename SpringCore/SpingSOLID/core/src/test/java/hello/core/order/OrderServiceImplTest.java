package hello.core.order;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {
    //순수한 자바로만 텐스트

    @Test
    public void createOrder() {
        OrderServiceImpl orderService = new OrderServiceImpl();
        orderService.createOrder(1L, "itemA", 10000); //수정자에선 null point exception
    }

}