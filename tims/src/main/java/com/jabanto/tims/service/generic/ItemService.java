package com.jabanto.tims.service.generic;
import com.jabanto.tims.dao.models.Item;
import com.jabanto.tims.dao.repositories.ItemsRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ItemService  {

        @Autowired
        private ItemsRepository itemRepository;

        public String createItem(Item item){
            try {
                if (!itemRepository.existsById(item.getId())){
                    item.setId((null == itemRepository.findMaxId()? 0 : itemRepository.findMaxId() + 1));
                    itemRepository.save(item);
                    return "Item record created successfully.";
                }else {
                    return "Item already exists in the database.";
                }
            }catch (Exception e){
                throw e;
            }
        }

        public List<Item> readItems(){
            return itemRepository.findAll();
        }
}
