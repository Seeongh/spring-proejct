package hello.itemservice.itemService.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor //final이 붙은애로 생성자를 만들어줌
@Slf4j
public class BsicItemController {

    private final ItemRepository itemRepository;

    //@Autowired //생성자가 하나이면 생략 가능하다.
//    public BsicItemController(ItemRepository itemRepository) { //SPRING BEAN으로 등록된 REPOSITORY가 들어감
//        this.itemRepository = itemRepository;
//    }

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items",items);

        return "basic/items";
    }

    @PostConstruct
    public void init() {
        //test용 미리넣어둑
        itemRepository.save(new Item("itemA", 10000,10));
        itemRepository.save(new Item("itemB", 20000,20));
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {

        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);

        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

  //  @PostMapping("/add")
    public String additemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model) {
        Item item =new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        Item savedItem = itemRepository.save(item);
        model.addAttribute("item",item);
        return "basic/item";
    }


    //@PostMapping("/add")
    //public String save(Item item, Model model) {
    public String addItemv2(@ModelAttribute("item") Item item) {
        //modelattribbute는 "item"으로 선언한걸 다시 model에 넣어준다.
        itemRepository.save(item);
        //model.addAttribute("item",item); //자동추가되어 생략가능
        return "basic/item";
    }


    //@PostMapping("/add")
    //public String save(Item item, Model model) {
    public String addItemv3(@ModelAttribute Item item) {
        //default로 class명을 첫글자만 소문자로 바꿔서 modelattribute에 담긴다
        itemRepository.save(item);
        //model.addAttribute("item",item); //자동추가되어 생략가능
        return "basic/item";
    }

    //@PostMapping("/add")
    public String addItemv4(Item item) {
        //default로 class명을 첫글자만 소문자로 바꿔서 modelattribute에 담긴다
        itemRepository.save(item);
        //model.addAttribute("item",item); //자동추가되어 생략가능
        return "basic/item";
    }

    @PostMapping("/add")
    public String addItemv5(Item item) {
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId(); //PRG 방식으로 이슈 개선
    }
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId,@ModelAttribute Item item) {
        itemRepository.update(itemId,item);
        return "redirect:/basic/items/{itemId}"; //pathvariable에있는거 쓸 수 있음
    }
}
