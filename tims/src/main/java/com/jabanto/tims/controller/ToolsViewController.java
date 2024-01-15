package com.jabanto.tims.controller;

import com.jabanto.tims.dao.models.Assignment;
import com.jabanto.tims.dao.models.Item;
import com.jabanto.tims.dao.models.ItemType;
import com.jabanto.tims.service.generic.*;
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

import static javafx.scene.control.Alert.AlertType.*;

@Component
public class ToolsViewController {


    @FXML
    public TableColumn assignmentIdColumn;
    public TableColumn nameColumn;
    public TableColumn assignColumn;
    public TableColumn checkOutDateColumn;
    public TableColumn dispenseColumn;
    public TableColumn checkInDateColumn;
    public TableView toolsTable;
    public Button cu_checkOut;
    public DatePicker cu_checkOutDate;
    public ComboBox<String> cu_receiver;
    public ComboBox cu_toolName;
    public ComboBox cu_category;
    public Button ci_checkin;
    public DatePicker ci_dateIn;
    public TextField ci_toolName;
    public TextField ci_category;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private ItemStatusService itemStatusService;

    private List<String> receivers = new ArrayList<>();
    private static final String STATUS_CHECKUP_NAME = "Check Out";
    private static final String STATUS_AVAILABLE_NAME = "Avaible";

    private int checkoutItemID;
    private int checkInAssignmentID;

    @FXML
    public void initialize(){

        receivers = userService.getReceiverNames();
        loadToolsTable();
        configureTableSelection();
        loadComboBox();
        loadComboBoxSelection();

    }



    private void loadComboBoxSelection() {

        // Agregar un ChangeListener al ComboBox
        cu_category.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Aquí se ejecuta tu método cuando se selecciona un nuevo valor
                if (newValue != null){
                    List<String> filteredItems = itemService.getItemsNamesByCategory(newValue);
                    if (filteredItems.size()>0){
                        cu_toolName.getItems().clear();
                        cu_toolName.getItems().addAll(filteredItems);
                    }else {
                        cu_toolName.getItems().clear();
                    }
                }
            }
        });

        // Agregar un ChangeListener al ComboBox
        cu_toolName.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Aquí se ejecuta tu método cuando se selecciona un nuevo valor
                if (newValue != null){
                    Item currentItem = itemService.getItemByName(newValue);
                    checkoutItemID = currentItem.getId();
                }else {
                    checkoutItemID = 0;
                }
            }
        });
    }

    private  void createAutoCompleteComboBox(ComboBox comboBox, List<String> collection){
        // Configurar el listener para el TextField
        AutoCompletionBinding<String> autoCompletionBinding = TextFields.bindAutoCompletion(comboBox.getEditor(), collection);
        // Configurar el evento de selección para actualizar el valor del ComboBox
        autoCompletionBinding.setOnAutoCompleted(event -> {
            comboBox.getSelectionModel().select(event.getCompletion());
            comboBox.setValue(event.getCompletion());
        });

    }


    private void loadComboBox() {

        List<String> categoriesNames = categoryService.getCategoriesNames();
        cu_category.getItems().addAll(categoriesNames);
        createAutoCompleteComboBox(cu_category,categoriesNames);

        List<String> toolsNames = itemService.getItemsNamesByGroup(ItemType.TOOLS);
        cu_toolName.getItems().addAll(toolsNames);
        createAutoCompleteComboBox(cu_toolName,toolsNames);

        cu_receiver.getItems().addAll(receivers);
        createAutoCompleteComboBox(cu_receiver,receivers);

    }

    private void configureTableSelection() {
        // Configurar el listener para la selección de la tabla
        toolsTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Assignment>() {
            @Override
            public void changed(ObservableValue<? extends Assignment> observable, Assignment oldValue, Assignment newValue) {

                if (newValue != null ) {
                    // Actualizar los datos en el TextField y ComboBox
                    checkInAssignmentID = newValue.getId();
                    ci_toolName.setText(newValue.getItem().getItemName());
                    ci_category.setText(newValue.getItem().getCategory().getName());
                } else {
                    // Limpiar los datos si no hay ninguna fila seleccionada
                    checkInAssignmentID = 0;
                    ci_toolName.clear();
                }
            }
        });

    }

    private void loadToolsTable() {

        List<Assignment> assignments = assignmentService.readAssignments();
        List<Assignment> filteredAssignmentsByItemType = new ArrayList<>();

        for (Assignment assignment : assignments) {

        boolean isToolItem = assignment.getItem().getItemType() == ItemType.TOOLS;
            boolean isToolAvailable = assignment.getItem().getStatus().getName().equals(STATUS_CHECKUP_NAME);
            boolean isNotCheckIn = assignment.getCheckInDate()==null;

            if (assignment.getItem() != null &&  isToolItem && isNotCheckIn && isToolAvailable) {
                filteredAssignmentsByItemType.add(assignment);
            }
        }

        toolsTable.getItems().addAll(filteredAssignmentsByItemType);

        for (Assignment item: filteredAssignmentsByItemType) {

            assignmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
            nameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Assignment, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Assignment, String> assingmentStringCellData) {
                    return Bindings.selectString(assingmentStringCellData.getValue().getItem(), "itemName");
                }
            });
            assignColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Assignment, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Assignment, String> assingmentStringCellData) {
                    return Bindings.selectString(assingmentStringCellData.getValue().getReceiverId(), "email");
                }
            });
            dispenseColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Assignment, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Assignment, String> assingmentStringCellData) {
                    return Bindings.selectString(assingmentStringCellData.getValue().getGiverId(), "email");
                }
            });
            checkOutDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
            if (item.getCheckInDate()!=null){
                checkInDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
            }
        }
    }


    @FXML
    public void checkInTool(ActionEvent actionEvent) {

        Assignment checkInAssignment = new Assignment();

        if (actionEvent.getSource().equals(ci_checkin)&& ci_dateIn.getValue() != null) {

            checkInAssignment = assignmentService.getAssignmentById(checkInAssignmentID);

            LocalDate localDate = ci_dateIn.getValue();
            LocalTime localTime = LocalTime.now();
            LocalDateTime localDateTime = LocalDateTime.of(localDate,localTime);
            // Convierte LocalDateTime a java.util.Date
            Date checkInDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            checkInAssignment.setCheckInDate(checkInDate);
            Item updateItem = checkInAssignment.getItem();
            updateItem.setStatus(itemStatusService.getStatus(STATUS_AVAILABLE_NAME).get());
            itemService.updateItem(updateItem,checkInAssignment.getItem());
            int result =  assignmentService.checkInItem(checkInAssignment);
            if (result == 1) {
                ViewControllerUtils.generateAlert(AssignmentService.ASSIGNMENT_CHECKIN_SUCCESS, INFORMATION);
                toolsTable.getItems().clear();
                ci_dateIn.getEditor().clear();
                loadToolsTable();
            }else if (result == 2) {
                ViewControllerUtils.generateAlert(AssignmentService.ASSIGNMENT_CHECKUP_ERROR, ERROR);
            }

        }else {
            ViewControllerUtils.generateAlert("Select a Check-in Date.", WARNING);
        }
    }

    @FXML
    public void checkOutTool(ActionEvent actionEvent) {

        Assignment checkOutAssignment = new Assignment();

        if (actionEvent.getSource().equals(cu_checkOut)&&cu_checkOutDate.getValue() != null) {

            LocalDate localDate = cu_checkOutDate.getValue();
            LocalTime localTime = LocalTime.now();
            LocalDateTime localDateTime = LocalDateTime.of(localDate,localTime);
            // Convierte LocalDateTime a java.util.Date
            Date checkOutDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            checkOutAssignment.setCheckOutDate(checkOutDate);
            checkOutAssignment.setGiverId(userService.loadUserByEmail(MainMenuController.USERLOGGED_NAME).get());
            checkOutAssignment.setReceiverId(userService.loadUserByEmail(cu_receiver.getValue()).get());
            checkOutAssignment.setItem(itemService.getItemById(checkoutItemID).get());
            Item updateItem = checkOutAssignment.getItem();
            updateItem.setStatus(itemStatusService.getStatus(STATUS_CHECKUP_NAME).get());
            itemService.updateItem(updateItem,checkOutAssignment.getItem());

            int result = assignmentService.checkOutItem(checkOutAssignment);
            if (result == 1) {
                toolsTable.getItems().clear();
                loadToolsTable();
                cu_toolName.getItems().clear();
                cu_receiver.getItems().clear();
                loadComboBox();
                ViewControllerUtils.generateAlert(AssignmentService.ASSIGNMENT_CREATED, INFORMATION);
            }else if (result == 2) {
                ViewControllerUtils.generateAlert(AssignmentService.ASSIGMENT_DATABASE_ERROR, ERROR);
            }
        }else {
            ViewControllerUtils.generateAlert("Select a Check-Out Date.", WARNING);
        }

    }
}
