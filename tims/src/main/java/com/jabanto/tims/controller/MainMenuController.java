package com.jabanto.tims.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class MainMenuController {

    @FXML
    public Button loginButton;
    public BorderPane mainPanel;

    // Help elements to manage the elemetnos on the fxml files
    private List<Button>  buttons = new ArrayList<>();

    /**
     * In this method i can call all me define elements on my fxml file
     * and set properties as well cann i create new elements and set these
     * to my fxml file , but normally i set data from another services
     * into the elements that i have already create or defined on my
     * fxml file
     */
    @FXML
    public void initialize()
    {
    }

    private void setButtonsDimensions(){


        // Define all buttons to the list
        // buttons.add(topButton);
        // define the height and width
        for(int i = 0; i < buttons.size(); i++){
            Button button = buttons.get(i);
            button.setMaxHeight(Double.MAX_VALUE);
            button.setMaxWidth(Double.MAX_VALUE);
        }
    }


}
