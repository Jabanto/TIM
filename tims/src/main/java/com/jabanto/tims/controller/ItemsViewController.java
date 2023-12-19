package com.jabanto.tims.controller;

import com.jabanto.tims.service.generic.ItemService;
import javafx.fxml.FXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemsViewController {

    @Autowired
    private ItemService itemService;


    @FXML
    public void initialize(){

        loadItems();
    }

    private void loadItems() {



    }
}
