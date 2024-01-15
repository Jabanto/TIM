package com.jabanto.tims.service.generic;
import com.jabanto.tims.dao.models.Item;
import com.jabanto.tims.dao.models.ItemType;
import com.jabanto.tims.dao.models.User;
import com.jabanto.tims.dao.repositories.ItemsRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ItemService  {

    @Autowired
    private ItemsRepository itemRepository;

    public static final int ITEM_CREATED_SUCCESS = 1;
    public static final int ITEM_ALREADY_EXISTS = 2;
    public static final int DATABASE_ERROR =3;

    private static final int AVAIBLE = 7;
    private static final int ITEM_UPDATE_SUCCESS = 1;

    public final static String ITEM_EXITS = "User with name already exits.";
    public final static String ITEM_NAME_INVALID = "Item with name format not valid.";
    public final static String ITEM_CREATED = "New item created.";
    public final static String ITEM_DATABASE_ERROR = "Error creating new item.";

    public int createItem(Item item){

        try {
            item.setId((null == itemRepository.findMaxId()? 0 : itemRepository.findMaxId() + 1));
            if (!itemRepository.existsByItemName(item.getItemName())){
                itemRepository.save(item);
                return ITEM_CREATED_SUCCESS;
            }else {
                return ITEM_ALREADY_EXISTS;
            }
        }catch (Exception e){
            e.printStackTrace();
            return DATABASE_ERROR;
        }
    }


    public List<Item> readItems(){
            return itemRepository.findAll();
        }

    public Optional<Item> getItemById(int updateItemId) {
        Optional<Item> item = itemRepository.findById(updateItemId);
        return item;
    }

    public int updateItem(Item updateItem, Item existingItem) {
        try {
            existingItem.setItemName(updateItem.getItemName());
            existingItem.setItemType(updateItem.getItemType());
            existingItem.setCategory(updateItem.getCategory());
            existingItem.setStatus(updateItem.getStatus());
            existingItem.setRemark(updateItem.getRemark());
            itemRepository.save(existingItem);
            return ITEM_UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return  DATABASE_ERROR;
        }

    }

    public List<String> getItemsNamesByGroup(ItemType groupName) {

        List<Item> keys = itemRepository.findByItemGroup(groupName);
        List<Item> filteredItems = new ArrayList<>();

        filteredItems = keys.stream()
                    .filter(item -> item.getStatus().getId() == AVAIBLE)
                    .collect(Collectors.toList());
         keys = filteredItems;

        List<String> itemNames = keys.stream()
                .map(Item::getItemName)
                .collect(Collectors.toList());

        return itemNames;

    }

    public List<String> getItemsNamesByCategory(String categoryName) {

        List<Item> keys = itemRepository.findByItemGroup(ItemType.TOOLS);
        List<Item> filteredItems = new ArrayList<>();

        filteredItems = keys.stream()
                .filter(item -> item.getStatus().getId() == AVAIBLE && item.getCategory().getName().equals(categoryName))
                .collect(Collectors.toList());
        keys = filteredItems;

        List<String> itemNames = keys.stream()
                .map(Item::getItemName)
                .collect(Collectors.toList());

        return itemNames;

    }



    public Item getItemByName(String name){
        Item item = itemRepository.findByItemName(name);
        return item;
    }
}
