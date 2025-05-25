package com.example.trafficlightproject.traffic.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import com.example.trafficlightproject.traffic.controller.SimulationController;
import javafx.scene.layout.VBox;


public class ControlPanel extends VBox {

    @FXML private Button startButton;
    @FXML private Button pauseButton;
    @FXML private Button resetButton;

    private SimulationController simulationController; // Simülasyonu kontrol etmek için bağlantı

    @FXML
    public void initialize() {
        simulationController = new SimulationController(); // Simülasyon nesnesini oluştur
        startButton.setOnAction(e -> simulationController.startSimulation());
        pauseButton.setOnAction(e -> simulationController.stopSimulation());
        resetButton.setOnAction(e -> simulationController.resetSimulation());
    }
}
