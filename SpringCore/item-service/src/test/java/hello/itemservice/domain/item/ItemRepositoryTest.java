package hello.itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class ItemRepositoryTest {
    ItemRepository itemRepository = new ItemRepository();

    /**
     * AfterEach
     * 각 테스트 하나하나 끝날때마다 실행됨
     */
    @AfterEach
    void afterEach() {
        itemRepository.clearStore();
   }

   @Test
    void save() { //jnit5에서는 public없어도됨.

        // given 테스트 조건
       Item item = new Item("itemA", 10000,10);

       //when 테스트 목적
       Item savedItem = itemRepository.save(item);

       //then 테스트 결과 확인
       Item findItem = itemRepository.findById(savedItem.getId());
       assertThat(findItem).isEqualTo(savedItem);
   }

    @Test
    void findAll() { //jnit5에서는 public없어도됨.
        //given
        Item item1 = new Item("itemA", 10000,10);
        Item item2 = new Item("itemB", 20000,30);
        
        itemRepository.save(item1);
        itemRepository.save(item2);
        
        //when
        List<Item>  result = itemRepository.findAll();
        
        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(item1,item2); //item1과 item2를 가지고있는가
    }

    @Test
    void updateItem() { //jnit5에서는 public없어도됨.
        //given
        Item item = new Item("item1",10000,10);

        Item savedItem = itemRepository.save(item);
        Long itemId = savedItem.getId();

        //when
        Item updateParam = new Item("item2", 20000, 30);
        itemRepository.update(itemId,updateParam);

        //then
        Item findItem = itemRepository.findById(itemId);

        assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());

    }
}
