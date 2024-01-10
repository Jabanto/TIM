package com.jabanto.tims.controller;

import com.jabanto.tims.dao.models.Assignment;
import com.jabanto.tims.dao.models.ItemType;
import com.jabanto.tims.service.generic.AssignmentService;
import com.jabanto.tims.service.generic.ItemService;
import com.jabanto.tims.service.generic.UserService;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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

@Component
public class KeysViewController {

    @FXML
    public TextField cu_Id;
    public ComboBox cu_keyNane;
    public ComboBox cu_receiverUser;
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

    @Autowired
    private ItemService itemService;

    @Autowired
    private AssignmentService assingmentService;

    @Autowired
    private UserService userService;

    private List<String> receivers =  new ArrayList<>();

    @FXML
    public void initialize(){

        receivers = userService.getReceiverNames();
        loadKeysTable();
        loadComboBoxes();
        configureComboBox();

    }

    private void configureComboBox() {

        // Configurar el listener para el TextField
        AutoCompletionBinding<String> autoCompletionBinding = TextFields.bindAutoCompletion(cu_receiverUser.getEditor(), receivers);

        // Configurar el evento de selección para actualizar el valor del ComboBox
        autoCompletionBinding.setOnAutoCompleted(event -> {
            cu_receiverUser.getSelectionModel().select(event.getCompletion());
            cu_receiverUser.setValue(event.getCompletion());
        });

        /*

        cu_receiverUser.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            List<String> suggestions = filterSuggestions(receivers, newValue);
            cu_receiverUser.setItems(FXCollections.observableArrayList(suggestions));
        });
        */
    }

    private List<String> filterSuggestions(List<String> nombres, String input) {
        // Filtrar la lista de nombres según la entrada del usuario
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
            if (assignment.getItem() != null && assignment.getItem().getItemType() == ItemType.KEYS) {
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
            //outDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
            //inDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        }
    }

    @FXML
    public void checkOutKey(ActionEvent actionEvent) {
        Assignment checkOutAssigment = new Assignment();

        if (actionEvent.getSource().equals(cu_checkOut)) {

            LocalDate localDate = cu_dateOut.getValue();
            LocalTime localTime = LocalTime.now();
            LocalDateTime localDateTime = LocalDateTime.of(localDate,localTime);
            // Convierte LocalDateTime a java.util.Date
            Date checkOutDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            checkOutAssigment.setCheckOutDate(checkOutDate);

        }
    }
}
