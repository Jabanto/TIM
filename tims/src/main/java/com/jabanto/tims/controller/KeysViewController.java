package com.jabanto.tims.controller;

import com.jabanto.tims.dao.models.Assignment;
import com.jabanto.tims.dao.models.Item;
import com.jabanto.tims.dao.models.ItemType;
import com.jabanto.tims.service.generic.AssignmentService;
import com.jabanto.tims.service.generic.ItemService;
import com.jabanto.tims.service.generic.ItemStatusService;
import com.jabanto.tims.service.generic.UserService;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static javafx.scene.control.Alert.AlertType.*;

@Component
public class KeysViewController {

    @FXML
    public TextField cu_Id;
    public ComboBox cu_keyNane;
    public ComboBox<String> cu_receiverUser;
    public DatePicker cu_dateOut;
    public Button cu_reset;
    public Button cu_checkOut;
    public TableView keysTable;
    public TableColumn keysIdColumn;
    public TableColumn nameColumn;
    public TableColumn assingToColumn;
    public TableColumn dispenseFromColumn;
    public TableColumn outDateColumn;
    public TableColumn inDateColumn;
    public Button ci_checkIn;
    public TextField ci_keyName;
    public DatePicker ci_dateInt;

    private List<String> receivers =  new ArrayList<>();
    private int checkoutItemID;
    private int checkInAssigmentID;
    private static final int STATUS_AVAIBLE_ID = 7 ;
    private static final String STATUS_AVAIBLE_NAME = "Avaible";
    private static final String STATUS_CHECKUP_NAME = "Check Out";

    @Autowired
    private ItemService itemService;

    @Autowired
    private AssignmentService assingmentService;

    @Autowired
    private ItemStatusService itemStatusService;

    @Autowired
    private UserService userService;

    @FXML
    public void initialize(){

        receivers = userService.getReceiverNames();
        loadKeysTable();
        loadComboBoxes();
        configureAutoComplete();
        configureTableSelection();
        configureComboBoxSelection();

    }

    private void configureComboBoxSelection() {

        // Add ChangeListener to ComboBox
        cu_keyNane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //Set up the listener for new combo box selection
                if (newValue != null){
                    Item currentItem = itemService.getItemByName(newValue);
                    checkoutItemID = currentItem.getId();
                }else {
                    checkoutItemID = 0;
                }
            }
        });

    }

    private void configureTableSelection() {
        // Set up the listener for table selection
        keysTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Assignment>() {
            @Override
            public void changed(ObservableValue<? extends Assignment> observable, Assignment oldValue, Assignment newValue) {

                if (newValue != null ) {
                    // Update fields intro TextField y ComboBox
                    checkInAssigmentID = newValue.getId();
                    ci_keyName.setText(newValue.getItem().getItemName());
                } else {
                    // Reset the value if it is not an item selected
                    checkInAssigmentID = 0;
                    ci_keyName.clear();
                }
            }
        });
    }

    private void configureAutoComplete() {

        // Set up the listener for TextField
        AutoCompletionBinding<String> autoCompletionBinding = TextFields.bindAutoCompletion(cu_receiverUser.getEditor(), receivers);
        // Set up the selection event to update the ComboBox value
        autoCompletionBinding.setOnAutoCompleted(event -> {
            cu_receiverUser.getSelectionModel().select(event.getCompletion());
            cu_receiverUser.setValue(event.getCompletion());
        });

    }

    private List<String> filterSuggestions(List<String> nombres, String input) {
        // Filter the list of names based on user input
        return nombres.stream()
                .filter(nombre -> nombre.toLowerCase().startsWith(input.toLowerCase()))
                .collect(Collectors.toList());
    }

    private void loadComboBoxes() {

        List<String> keysNames = itemService.getItemsNamesByGroup(ItemType.KEYS);
        cu_receiverUser.getItems().addAll(receivers);
        cu_keyNane.getItems().addAll(keysNames);

    }

    private void loadKeysTable() {

        List<Assignment> assignments = assingmentService.readAssignments();
        List<Assignment> filteredAssigmentsByKey = new ArrayList<>();

        for (Assignment assignment : assignments) {

            boolean isKeyItem = assignment.getItem().getItemType() == ItemType.KEYS;
            boolean isKeyAvaible = assignment.getItem().getStatus().getName().equals(STATUS_CHECKUP_NAME);
            boolean isNotCheckIn = assignment.getCheckInDate()==null;

            if (assignment.getItem() != null &&  isKeyItem && isNotCheckIn && isKeyAvaible) {
                filteredAssigmentsByKey.add(assignment);
            }
        }

        keysTable.getItems().addAll(filteredAssigmentsByKey);

        for (Assignment item: filteredAssigmentsByKey) {

            keysIdColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
            nameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Assignment, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Assignment, String> assingmentStringCellData) {
                    return Bindings.selectString(assingmentStringCellData.getValue().getItem(), "itemName");
                }
            });
            assingToColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Assignment, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Assignment, String> assingmentStringCellData) {
                    return Bindings.selectString(assingmentStringCellData.getValue().getReceiverId(), "email");
                }
            });
            dispenseFromColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Assignment, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Assignment, String> assingmentStringCellData) {
                    return Bindings.selectString(assingmentStringCellData.getValue().getGiverId(), "email");
                }
            });
            outDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
            if (item.getCheckInDate()!=null){
                inDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
            }
        }
    }

    @FXML
    public void checkOutKey(ActionEvent actionEvent) {
        Assignment checkOutAssigment = new Assignment();

        if (actionEvent.getSource().equals(cu_checkOut)) {

            LocalDate localDate = cu_dateOut.getValue();
            LocalTime localTime = LocalTime.now();
            LocalDateTime localDateTime = LocalDateTime.of(localDate,localTime);
            // convert or cast LocalDateTime to java.util.Date
            Date checkOutDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            checkOutAssigment.setCheckOutDate(checkOutDate);
            checkOutAssigment.setGiverId(userService.loadUserByEmail(MainMenuController.USERLOGGED_NAME).get());
            checkOutAssigment.setReceiverId(userService.loadUserByEmail(cu_receiverUser.getValue()).get());
            checkOutAssigment.setItem(itemService.getItemById(checkoutItemID).get());
                Item updateItem = checkOutAssigment.getItem();
                updateItem.setStatus(itemStatusService.getStatus(STATUS_CHECKUP_NAME).get());
            itemService.updateItem(updateItem,checkOutAssigment.getItem());

            int result = assingmentService.checkOutItem(checkOutAssigment);
            if (result == 1) {
                keysTable.getItems().clear();
                loadKeysTable();
                cu_keyNane.getItems().clear();
                cu_receiverUser.getItems().clear();
                loadComboBoxes();
                ViewControllerUtils.generateAlert(AssignmentService.ASSIGNMENT_CREATED, INFORMATION);
            }else if (result == 2) {
                ViewControllerUtils.generateAlert(AssignmentService.ASSIGMENT_DATABASE_ERROR, ERROR);
            }
            keysTable.getItems().clear();
            loadKeysTable();

        }
    }

    @FXML
    public void checkInKey(ActionEvent actionEvent) {

        Assignment checkInAssigment = new Assignment();

        if (actionEvent.getSource().equals(ci_checkIn)&& ci_dateInt.getValue() != null) {

            checkInAssigment = assingmentService.getAssignmentById(checkInAssigmentID);

            LocalDate localDate = ci_dateInt.getValue();
            LocalTime localTime = LocalTime.now();
            LocalDateTime localDateTime = LocalDateTime.of(localDate,localTime);
            // convert or cast LocalDateTime to java.util.Date
            Date checkInDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            checkInAssigment.setCheckInDate(checkInDate);
                Item updateItem = checkInAssigment.getItem();
                updateItem.setStatus(itemStatusService.getStatus(STATUS_AVAIBLE_NAME).get());
                itemService.updateItem(updateItem,checkInAssigment.getItem());
            int result =  assingmentService.checkInItem(checkInAssigment);
            if (result == 1) {
                ViewControllerUtils.generateAlert(AssignmentService.ASSIGNMENT_CHECKIN_SUCCESS, INFORMATION);
                keysTable.getItems().clear();
                ci_dateInt.getEditor().clear();
                loadKeysTable();
            }else if (result == 2) {
                ViewControllerUtils.generateAlert(AssignmentService.ASSIGNMENT_CHECKUP_ERROR, ERROR);
            }

        }else {
            ViewControllerUtils.generateAlert("Select a Check-in Date.", WARNING);
        }
    }
}
