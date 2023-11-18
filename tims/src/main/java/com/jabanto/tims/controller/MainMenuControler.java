package com.jabanto.tims.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import org.springframework.stereotype.Controller;

@Controller
public class MainMenuControler {

    @FXML
    public LineChart<String,Double> chart;
}
