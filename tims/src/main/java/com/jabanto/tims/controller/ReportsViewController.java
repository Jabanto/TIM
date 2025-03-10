package com.jabanto.tims.controller;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.jabanto.tims.dao.models.Assignment;
import com.jabanto.tims.dao.models.Item;
import com.jabanto.tims.service.generic.AssignmentService;
import com.jabanto.tims.service.generic.ItemService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javafx.scene.control.Alert.AlertType.*;

@Component
public class ReportsViewController {
    @FXML
    public ComboBox<String> reportsSelection;
    public TextField directoryPath;
    public TextArea reportDescription;
    public Button reports_btn;


    @Value("${spring.application.ui.report-01-Name}")
    private String reportType01;
    @Value("${spring.application.ui.report-02-Name}")
    private String reportType02;
    @Value("${spring.application.ui.report-03-Name}")
    private String reportType03;

    @Value("${spring.application.ui.report-01-Des}")
    private String reportDescription01;
    @Value("${spring.application.ui.report-02-Des}")
    private String reportDescription02;
    @Value("${spring.application.ui.report-03-Des}")
    private String reportDescription03;

    private  Map<String, String> reportsWithDescription = new HashMap<>();
    private static final int PDF_CREATION_SUCCESS = 1;
    private static final int PDF_CREATION_ERROR = 2;
    private static final int PDF_CREATION_EMPTY = 3;
    private static final String REPORT_TYPE_01_NAME ="Current Assignments Report" ;
    private static final String REPORT_TYPE_02_NAME = " Historical Assignments Report" ;
    private static final String REPORT_TYPE_03_NAME = "Availability Tools Report" ;

    private static final int AVAILABLE = 7;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private ItemService itemService;

    @FXML
    public void initialize(){

        loadComboBox();
        loadComboSelection();

    }

    private void loadComboSelection() {

        // Agregar un ChangeListener al ComboBox
        reportsSelection.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Aquí se ejecuta tu método cuando se selecciona un nuevo valor
                if (newValue != null){
                    reportDescription.setText(reportsWithDescription.get(newValue));
                }else {
                    reportDescription.setText("");
                }
            }
        });

    }

    private void loadComboBox() {

        reportsWithDescription.put(reportType01,reportDescription01);
        reportsWithDescription.put(reportType02,reportDescription02);
        reportsWithDescription.put(reportType03,reportDescription03);

        List<String> reportTypeNames = new ArrayList<>();
        reportTypeNames.add(reportType01);
        reportTypeNames.add(reportType02);
        reportTypeNames.add(reportType03);

        reportsSelection.getItems().addAll(reportTypeNames);
    }


    @FXML
    public void chooseDirectory(MouseEvent mouseEvent) {

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select destination folder");

        //Show Dialog and assign destination Path
        File selectedDirectory = directoryChooser.showDialog(null);

        // Update Textfile  with selected Path
        if (selectedDirectory != null) {
            directoryPath.setText(selectedDirectory.getAbsolutePath());
        }
    }

    @FXML
    public void createReport(ActionEvent actionEvent) {

        List<Assignment> assignments = assignmentService.readAssignments();
        if (actionEvent.getSource().equals(reports_btn)&& !directoryPath.getText().isEmpty()) {
            if (assignments != null) {
                int result;
                switch (reportsSelection.getValue()) {
                    case REPORT_TYPE_01_NAME :
                        result = generatePdf_Type01(assignments, directoryPath.getText());
                        break;
                    case REPORT_TYPE_02_NAME:
                        result = generatePdf_Type02(assignments, directoryPath.getText());
                        break;
                    case REPORT_TYPE_03_NAME:
                        List<Item> items = itemService.readItems();
                        result = generatePdf_Type03(items, directoryPath.getText());
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + reportsSelection.getSelectionModel().getSelectedItem());
                }
                if (result==1){
                    ViewControllerUtils.generateAlert("Report creation Success. Report is located in: "+ directoryPath.getText() , INFORMATION);
                }else{
                    ViewControllerUtils.generateAlert("An error occurred during the report creation process. Please check the logs for details", ERROR);
                }
            }else {
                ViewControllerUtils.generateAlert("The backup couldn't be performed. No Assignments found.",WARNING);
            }
        }else {
            ViewControllerUtils.generateAlert("Select a folder destination.", WARNING);
        }
    }
    
    public int generatePdf_Type01(List<Assignment> assignments, String outputPath) {

        try {

            PdfWriter writer = new PdfWriter(outputPath+"/reportType01.pdf");
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            // Create table
            Paragraph documentTitle = setTitle(reportType01);
            document.add(documentTitle);

            Table table = new Table(4); // 4 columns for userName, toolName, assignmentDate, returnDate, dispense from

            // add columns headers
            table.addHeaderCell("Tool Name");
            table.addHeaderCell("Assign to");
            table.addHeaderCell("Dispense from");
            table.addHeaderCell("Check-out Date");

            for (Assignment assignment : assignments) {

                if (assignment.getCheckInDate() == null){
                    table.addCell(new Cell().add(new Paragraph(assignment.getItem().getItemName())));
                    table.addCell(new Cell().add(new Paragraph(assignment.getReceiverId().getEmail())));
                    table.addCell(new Cell().add(new Paragraph(assignment.getGiverId().getEmail())));
                    table.addCell(new Cell().add(new Paragraph(assignment.getCheckOutDate() != null ? assignment.getCheckOutDate().toString() : "")));
                }
            }
            // Add table to the document
            document.add(table);
            // Close document
            document.close();
            return PDF_CREATION_SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
            return PDF_CREATION_ERROR;
        }
    }
    
    public int generatePdf_Type02(List<Assignment> assignments, String outputPath) {

        try {

            PdfWriter writer = new PdfWriter(outputPath+"/reportType02.pdf");
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            // Create table
            Paragraph documentTitle = setTitle(reportType02);
            document.add(documentTitle);

            Table table = new Table(4); // 4 columns for userName, toolName, assignmentDate, returnDate, dispense from

            // add columns headers
            table.addHeaderCell("Tool Name");
            table.addHeaderCell("Assign to");
            table.addHeaderCell("Dispense from");
            table.addHeaderCell("Check-out Date");
            table.addHeaderCell("Check-in Date");

            for (Assignment assignment : assignments) {

                if (assignment.getCheckInDate() != null){
                    table.addCell(new Cell().add(new Paragraph(assignment.getItem().getItemName())));
                    table.addCell(new Cell().add(new Paragraph(assignment.getReceiverId().getEmail())));
                    table.addCell(new Cell().add(new Paragraph(assignment.getGiverId().getEmail())));
                    table.addCell(new Cell().add(new Paragraph(assignment.getCheckOutDate() != null ? assignment.getCheckOutDate().toString() : "")));
                    table.addCell(new Cell().add(new Paragraph(assignment.getCheckInDate() != null ? assignment.getCheckInDate().toString() : "")));
                }
            }
            // Add table to the document
            document.add(table);
            // Close document
            document.close();
            return PDF_CREATION_SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
            return PDF_CREATION_ERROR;
        }
    }


    public int generatePdf_Type03(List<Item> items, String outputPath) {

        if (items.isEmpty() || items == null){
            ViewControllerUtils.generateAlert("No Items available found", WARNING);
            return PDF_CREATION_EMPTY;
        }
        else {
            try {

                PdfWriter writer = new PdfWriter(outputPath + "/reportType03.pdf");
                PdfDocument pdf = new PdfDocument(writer);
                Document document = new Document(pdf);
                // Create table
                Paragraph documentTitle = setTitle(reportType03);
                document.add(documentTitle);

                Table table = new Table(6);

                // add columns headers
                table.addHeaderCell("Tool Id");
                table.addHeaderCell("Tool Name");
                table.addHeaderCell("Tool Type");
                table.addHeaderCell("Category");
                table.addHeaderCell("Status");
                table.addHeaderCell("Remark");

                for (Item item : items) {

                    if (item.getStatus().getId() == AVAILABLE) {
                        table.addCell(new Cell().add(new Paragraph(String.valueOf(item.getId()))));
                        table.addCell(new Cell().add(new Paragraph(item.getItemName())));
                        table.addCell(new Cell().add(new Paragraph(item.getItemType().name())));
                        table.addCell(new Cell().add(new Paragraph(item.getCategory().getName())));
                        table.addCell(new Cell().add(new Paragraph(item.getStatus().getName())));
                        table.addCell(new Cell().add(new Paragraph(item.getRemark())));
                    }
                }
                // Add table to the document
                document.add(table);
                // Close document
                document.close();
                return PDF_CREATION_SUCCESS;
            } catch (IOException e) {
                e.printStackTrace();
                return PDF_CREATION_ERROR;
            }
        }
    }




    private Paragraph setTitle(String reportTypeName) throws IOException {

        PdfFont code = PdfFontFactory.createFont(StandardFonts.COURIER);
        Style style = new Style()
                .setFont(code)
                .setFontSize(14)
                .setFontColor(ColorConstants.BLACK)
                .setBackgroundColor(ColorConstants.LIGHT_GRAY);

        Paragraph paragraph = new Paragraph()
                .add("Report of type: ")
                .add(new Text(reportTypeName).addStyle(style))
                .add(" Description: " + reportsWithDescription.get(reportTypeName))
                .add(".");
        return paragraph;
    }
}
