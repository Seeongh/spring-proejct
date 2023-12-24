package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * db상태에 맞게 바꿔줄 수도 있음
 * @component로 스캔의 대상이됨
 * 핵심 도메인 로직
 */
@Repository
public class ItemRepository {

    /**
     * multi thread환경에서 여러개가 map에 접근하는 환경이면 Hashmap쓰면 안됨
     * repository는 싱글톤이고, static으로 선언되어있기때문에
     * 쓰려면 CuncurrentHashmap써야함
     */
    private static final Map<Long,Item>  store = new HashMap<>(); //static, ID와 데이터에 맞춘형태

    /**
     *  multi thread환경에서 여러개가 map에 접근하는 환경이면 Hashmap쓰면 안됨
     * 동시에 접근시 값이 꼬여서
     * atomic long등 다른걸 사용험
     */
    private static long sequence = 0L; //static

    public Item save(Item item) { //상품 등록
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    /**
     * 상품 수정
     * id를 가지고 데이터를 재등록
     * @param item
     * @return
     */
    public void update(Long itemId, Item updateParam) {
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
        //원래는 updateParam이아닌 별도의 객체를 만드는게 맞음 id를 사용하지 않기때문, 나중에 혼란스러워짐
        //중복과 명확성중 고민할땐 명확성을 따라야함.
    }

    /**
     * 상품 찾기(하나만)
     * @param id
     * @return
     */
    public Item findById(Long id){
        return store.get(id);
    }

    /**
     * 상품찾기 리스트
     * @return
     */
    public List<Item> findAll() {
//        List<Item> Items = new ArrayList<>();
//        store.forEach((key,item)->Items.add(item));
//        return Items;

        return new ArrayList<>(store.values());
        /**
         * 반환하기 전에 collection 타입을 맞춰주는 이유
         * arraylist에 값이 막 담겨도 실제 store값이 달라지지않음
         * type바꿔하는 문제도생김 나중에.
         */
    }

    public void delete(Long itemId){
        store.remove(itemId);
    }

    public void clearStore() {
        store.clear();
    }
}
