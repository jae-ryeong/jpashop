package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
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

    @Transactional  // 위에는 readOnly이기 때문에 저장을 위해 추가
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }

    @Transactional
    public void updateItem(Long itemId, Book param){
        Item findItem = itemRepository.findOne(itemId); // id를 기반으로 영속상태에 있는 item을 찾아왔다
        findItem.setPrice(param.getPrice());
        findItem.setName(param.getName());
        findItem.setStockQuantity(param.getStockQuantity());
        /*
        이제 여기서 save가 필요가 없다
        그 이유는 이미 영속화된 id이므로 위에 set 메서드들과 @Transactional을 통해 jpa가 flush를하여 자동으로 변경해준다.
        이것이 변경감지
        pdf 86페이지에 나온게 더 나은방법
        */
    }

    @Transactional
    public Item updateItem2(Long itemId, Book param) {
        Item findItem = itemRepository.findOne(itemId); // id를 기반으로 영속상태에 있는 item을 찾아왔다
        findItem.setPrice(param.getPrice());
        findItem.setName(param.getName());
        findItem.setStockQuantity(param.getStockQuantity());
        return findItem;
        // 이게 merge방식
    }
}
