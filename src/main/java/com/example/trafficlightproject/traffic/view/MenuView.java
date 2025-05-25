package com.example.trafficlightproject.traffic.view;

import com.example.trafficlightproject.traffic.MainApp;
import com.example.trafficlightproject.traffic.controller.SimulationController;
import com.example.trafficlightproject.traffic.core.enums.Direction;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MenuView extends VBox {

    @FXML private RadioButton manuelRadioButton;
    @FXML private RadioButton randomRadioButton;
    @FXML private TextField northField;
    @FXML private TextField southField;
    @FXML private TextField eastField;
    @FXML private TextField westField;
    @FXML private Button startButton;

    private ToggleGroup modeToggleGroup;

   private SimulationController simulationController;

    public void setSimulationController(SimulationController simulationController) {
        this.simulationController = simulationController;
    }

    @FXML
    public void initialize() {
        modeToggleGroup = new ToggleGroup();
        manuelRadioButton.setToggleGroup(modeToggleGroup);
        randomRadioButton.setToggleGroup(modeToggleGroup);
        manuelRadioButton.setSelected(true);

        startButton.setOnAction(event -> handleStart());
    }

    @FXML
    private void handleStart() {
        Map<Direction, Integer> densities = new HashMap<>();

        if (manuelRadioButton.isSelected()) {
            try {
                int north = Integer.parseInt(northField.getText());
                int south = Integer.parseInt(southField.getText());
                int east = Integer.parseInt(eastField.getText());
                int west = Integer.parseInt(westField.getText());

                if (!isValidDensity(north, south, east, west)) {
                    System.out.println("Yoğunluklar 0 ile 100 arasında olmalı.");
                    return;
                }

//                densities.put(Direction.NORTH, north);
//                densities.put(Direction.SOUTH, south);
//                densities.put(Direction.EAST, east);
//                densities.put(Direction.WEST, west);

                System.out.println("Manuel Yoğunluklar:");
                System.out.println("North: " + north + ", South: " + south + ", East: " + east + ", West: " + west);

            } catch (NumberFormatException e) {
                System.out.println("Lütfen tüm yönler için geçerli bir sayı girin.");
                return;
            }

        } else if (randomRadioButton.isSelected()) {
            Random random = new Random();
            int north = random.nextInt(101);
            int south = random.nextInt(101);
            int east = random.nextInt(101);
            int west = random.nextInt(101);

            northField.setText(String.valueOf(north));
            southField.setText(String.valueOf(south));
            eastField.setText(String.valueOf(east));
            westField.setText(String.valueOf(west));

//            densities.put(Direction.NORTH, north);
//            densities.put(Direction.SOUTH, south);
//            densities.put(Direction.EAST, east);
//            densities.put(Direction.WEST, west);

            System.out.println("Rastgele Yoğunluklar:");
            System.out.println("North: " + north + ", South: " + south + ", East: " + east + ", West: " + west);

        } else {
            System.out.println("Lütfen bir yoğunluk tipi seçin.");
            return;
        }

//        if (simulationController != null) {
//            simulationController.setTrafficDensity(densities);
//        }

        // Simülasyon ekranına geç
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/trafficlightproject/view/TrafficSimulationView.fxml"));
            Parent simulationView = loader.load();

            Stage stage = (Stage) startButton.getScene().getWindow();
            Scene simulationScene = new Scene(simulationView, 1137, 951);
            stage.setScene(simulationScene);
            stage.setTitle("Traffic Light Simulation - Main View");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isValidDensity(int... values) {
        for (int value : values) {
            if (value < 0 || value > 100) return false;
        }
        return true;
    }

    public void setPrimaryStage(Stage primaryStage) {
        // Opsiyonel: İleride sahne yönetimi için kullanılabilir
    }
//    @FXML
//    private void applyManualDensity() {
//        try {
//            int north = Integer.parseInt(txtNorth.getText());
//            int south = Integer.parseInt(txtSouth.getText());
//            int east = Integer.parseInt(txtEast.getText());
//            int west = Integer.parseInt(txtWest.getText());
//
//            simulationController.setTrafficDensityManual(north, south, east, west);
//        } catch (NumberFormatException e) {
//            System.out.println("Lütfen tüm yoğunluk alanlarına sayısal değerler girin.");
//        }
//    }

}
