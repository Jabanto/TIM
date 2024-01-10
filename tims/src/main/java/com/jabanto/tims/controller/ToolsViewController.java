package com.jabanto.tims.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.springframework.stereotype.Component;

@Component
public class ToolsViewController {

    @FXML
    public TableColumn tooldIdColumn;
    public TableColumn nameColumn;
    public TableColumn assignColumn;
    public TableColumn checkOutDateColumn;
    public TableColumn dispenseColumn;
    public TableColumn checkInDateColumn;
    public TableView toolsTable;

    @FXML
    public void initialized(){

        loadToolsTable();

    }

    private void loadToolsTable() {



    }


}
