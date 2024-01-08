package jpabook.jpashop.service;

import jpabook.jpashop.item.Book;
import jpabook.jpashop.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }


    //변경감지에 의한 데이터 변경법
    @Transactional
    public void updateItem(Long itemId, Book bookParam) {
        Item findItem = itemRepository.findOne(itemId);
        findItem.setPrice(bookParam.getPrice());
        findItem.setStockQuantity(bookParam.getStockQuantity());
        //영속상태인 객체를 찾아서 SET해주었으므로 persist등 처리가 필요없음. 변경감지를 해서 transition commit해서 flush할거니까
    }
}
