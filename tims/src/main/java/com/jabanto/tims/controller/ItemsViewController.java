package com.jabanto.tims.controller;

import com.jabanto.tims.dao.models.ItemType;
import com.jabanto.tims.service.generic.CategoryService;
import com.jabanto.tims.service.generic.ItemService;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemsViewController {

    @FXML
    public ComboBox new_group;
    public ComboBox new_category;
    public TextField new_itemName;

    @Autowired
    private ItemService itemService;

    @Autowired
    private CategoryService categoryService;

    @FXML
    public void initialize(){

        loadItems();
        loadComboBox();
    }

    private void loadComboBox() {

        List<String> itemTypes = Arrays.stream(ItemType.values())
                .map(ItemType::name)
                .collect(Collectors.toList());

        List<String> categoriesNames = categoryService.getCategoriesNames();

        new_group.getItems().addAll(itemTypes);
        new_category.getItems().addAll(categoriesNames);



    }

    private void loadItems() {



    }
}
