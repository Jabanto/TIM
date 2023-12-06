package com.jabanto.tims.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class MainMenuController {

    @FXML
    public Button loginButton;
    public BorderPane mainPanel;
    public StackPane users_btn;
    public StackPane tools_btn;
    public StackPane items_btn;
    public StackPane keys_btn;
    public Button reports_btn;
    public Button backup_btn;
    public Label current_date_label;

    @Value("${spring.application.ui.user-btn-description}")
    private String user_btn_description;
    @Value("${spring.application.ui.tools-btn-description}")
    private String tools_btn_description;
    @Value("${spring.application.ui.items-btn-description}")
    private String items_btn_description;
    @Value("${spring.application.ui.keys-btn-description}")
    private String keys_btn_description;
    @Value("${spring.application.ui.reports-btn-description}")
    private String reports_btn_description;
    @Value("${spring.application.ui.backup-btn-description}")
    private String backup_btn_description;

    /**
     * In this method i can call all me define elements on my fxml file
     * and set properties as well cann i create new elements and set these
     * to my fxml file , but normally i set data from another services
     * into the elements that i have already create or defined on my
     * fxml file
     */
    @FXML
    public void initialize(){
        setButtonsDescriptions();
        setLocalTime_Date();
    }

    private void setLocalTime_Date() {
        // Hole das aktuelle Datum und die aktuelle Uhrzeit
        LocalDateTime now = LocalDateTime.now();

        // Formatiere das Datum und die Uhrzeit im gewünschten Format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yy");
        String formattedDateTime = now.format(formatter);

        // Setze den Text des Labels
        //current_date_label.getText();

    }

    private void setButtonsDescriptions(){
        // Define all text for Tooltips from  buttons to the list
        Tooltip tooltipUsers = new Tooltip(user_btn_description);
        Tooltip tooltipItems = new Tooltip(items_btn_description);
        Tooltip tooltipKeys = new Tooltip(keys_btn_description);
        Tooltip tooltipTools = new Tooltip(tools_btn_description);
        Tooltip tooltipBackup = new Tooltip(backup_btn_description);
        Tooltip tooltipReports = new Tooltip(reports_btn_description);
        // create a tooltip and assing to the button
        Tooltip.install(users_btn,tooltipUsers);
        Tooltip.install(items_btn,tooltipItems);
        Tooltip.install(keys_btn,tooltipKeys);
        Tooltip.install(tools_btn,tooltipTools);
        Tooltip.install(backup_btn,tooltipBackup);
        Tooltip.install(reports_btn,tooltipReports);


    }

}