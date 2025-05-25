package com.example.trafficlightproject.traffic.controller;

import com.example.trafficlightproject.traffic.controller.SimulationController;
import com.example.trafficlightproject.traffic.view.MenuView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class MainController {



    @FXML
    private BorderPane mainLayout;

    @FXML
    private RadioButton manuelRadioButton;

    @FXML
    private Button startButton;



    @FXML
    public void initialize() {
        try {
            // Simulation görünümünü yükle
            FXMLLoader simulationLoader = new FXMLLoader(getClass().getResource("/com/example/trafficlightproject/traffic/view/TrafficSimulationView.fxml"));
            AnchorPane simulationPane = simulationLoader.load();
            SimulationController simulationController = simulationLoader.getController();

            // Menü görünümünü yükle
            FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/com/example/trafficlightproject/view/MenuView.fxml"));
            VBox menuPane = menuLoader.load();
            MenuView menuView = menuLoader.getController();

            // Menüye simulationController'ı bağla
            menuView.setSimulationController(simulationController);

            // Yerleştir: sol menü, merkez simülasyon alanı
            mainLayout.setLeft(menuPane);
            mainLayout.setCenter(simulationPane);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean isManualMode;

    public static boolean getMode() {
        return isManualMode;
    }

    @FXML
    public void onStartButtonClick() {
        isManualMode = manuelRadioButton.isSelected();
        switchToSimulation();
    }

    private void switchToSimulation() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/trafficlightproject/traffic/view/TrafficSimulationView.fxml"));
            Parent simulationView = loader.load();

            Stage stage = (Stage) startButton.getScene().getWindow();
            Scene simulationScene = new Scene(simulationView, 800, 600);
            stage.setScene(simulationScene);
            stage.setTitle("Traffic Light Simulation - Running");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
