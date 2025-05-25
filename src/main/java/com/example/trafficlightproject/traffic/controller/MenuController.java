package com.example.trafficlightproject.traffic.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import com.example.trafficlightproject.traffic.controller.SimulationController;
import com.example.trafficlightproject.traffic.core.enums.Direction;

import javafx.event.ActionEvent;

import java.net.URL;
import java.util.Map;
import java.util.EnumMap;
import java.util.Random;

import java.io.IOException;

public class MenuController {
    @FXML
    private RadioButton manuelRadioButton;

    @FXML
    private RadioButton randomRadioButton;

    @FXML
    private TextField northField;

    @FXML
    private TextField southField;

    @FXML
    private TextField eastField;

    @FXML
    private TextField westField;

    @FXML
    private Button startButton;

    private Stage primaryStage;

    private ToggleGroup toggleGroup;

    // Stage setter (Main'den atanacak)
    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    @FXML
    public void initialize() {
        // RadioButton'ları ToggleGroup'a ekle
        toggleGroup = new ToggleGroup();
        manuelRadioButton.setToggleGroup(toggleGroup);
        randomRadioButton.setToggleGroup(toggleGroup);

        // Manuel seçili olsun varsayılan
        manuelRadioButton.setSelected(true);

        // Start button action
        startButton.setOnAction(event -> {
            try {
                handleStart();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void handleRandomSelection(ActionEvent event) {
        Random random = new Random();

        int north = random.nextInt(101); // 0–100 arası
        int south = random.nextInt(101);
        int east = random.nextInt(101);
        int west = random.nextInt(101);

        northField.setText(String.valueOf(north));
        southField.setText(String.valueOf(south));
        eastField.setText(String.valueOf(east));
        westField.setText(String.valueOf(west));

        System.out.println("Rastgele değerler üretildi."); // Kontrol için log
    }
//    private void startSimulation() throws IOException {
//        // Yoğunluk değerlerini oku
//        String north = northField.getText();
//        String south = southField.getText();
//        String east = eastField.getText();
//        String west = westField.getText();
//
//        // Manuel mi yoksa random mu seçilmiş?
//        boolean isManual = manuelRadioButton.isSelected();
//
//        // Burada istersen input validation yapabilirsin
//        if (isManual) {
//            if (north.isEmpty() || south.isEmpty() || east.isEmpty() || west.isEmpty()) {
//                showAlert("Please fill in all fields for manual input.");
//                return;
//            }
//            // İstersen sayı formatı kontrolü ekle
//            try {
//                Integer.parseInt(north);
//                Integer.parseInt(south);
//                Integer.parseInt(east);
//                Integer.parseInt(west);
//            } catch (NumberFormatException e) {
//                showAlert("Please enter valid numbers in the fields.");
//                return;
//            }
//        }
//
//        // SimulationView yükleniyor
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/trafficlightproject/view/TrafficSimulationView.fxml"));
//        Parent simulationRoot = loader.load();
//
//        // Controller'ı al
//        SimulationController simulationController = loader.getController();
//        URL fxmlLocation = getClass().getResource("/com/example/trafficlightproject/view/TrafficSimulationView.fxml");
//        System.out.println("FXML Location: " + fxmlLocation);
//
//        // Yoğunluk bilgilerini gönder
//        if (isManual) {
//            simulationController.setTrafficDensityManual(
//                    Integer.parseInt(north),
//                    Integer.parseInt(south),
//                    Integer.parseInt(east),
//                    Integer.parseInt(west)
//            );
//        } else {
//            simulationController.setTrafficDensityRandom();
//        }

//        // Scene değiştir
//        Scene simulationScene = new Scene(simulationRoot);
//        primaryStage.setScene(simulationScene);
//        primaryStage.show();
//
//        // Simülasyonu başlat
//        simulationController.startSimulation();
//    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Input Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void handleStart() throws IOException {
        Map<Direction, Integer> densities = new EnumMap<>(Direction.class);
        Random random = new Random();

        if (manuelRadioButton.isSelected()) {
            try {
                densities.put(Direction.NORTH, Integer.parseInt(northField.getText()));
                densities.put(Direction.SOUTH, Integer.parseInt(southField.getText()));
                densities.put(Direction.EAST, Integer.parseInt(eastField.getText()));
                densities.put(Direction.WEST, Integer.parseInt(westField.getText()));
            } catch (NumberFormatException e) {
                showAlert("Lütfen tüm yönler için geçerli sayılar girin.");
                return;
            }
        } else if (randomRadioButton.isSelected()) {
            densities.put(Direction.NORTH, Integer.parseInt(northField.getText()));
            densities.put(Direction.SOUTH, Integer.parseInt(southField.getText()));
            densities.put(Direction.EAST, Integer.parseInt(eastField.getText()));
            densities.put(Direction.WEST, Integer.parseInt(westField.getText()));
        } else {
            showAlert("Lütfen bir yoğunluk tipi seçin.");
            return;
        }

        loadSimulationView(densities);
    }

    private void loadSimulationView(Map<Direction, Integer> densities) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/trafficlightproject/view/TrafficSimulationView.fxml"));
        Parent root = loader.load();

        // Controller'ı al
        SimulationController simulationController = loader.getController();

        // Yoğunlukları controller'a gönder
        simulationController.setTrafficDensityManual(
                densities.get(Direction.NORTH),
                densities.get(Direction.SOUTH),
                densities.get(Direction.EAST),
                densities.get(Direction.WEST)
        );

        // Simülasyonu başlat
        simulationController.startSimulation();

        // Sahneyi değiştir
        Scene scene = new Scene(root);
        primaryStage.setTitle("Trafik Simülasyonu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

//    @FXML
//    private void handleRandomSelection(ActionEvent event) {
//        generateRandomTrafficDensity();
//    }

    private void generateRandomTrafficDensity() {
        Random rand = new Random();

        int north = rand.nextInt(100);
        int south = rand.nextInt(100);
        int east = rand.nextInt(100);
        int west = rand.nextInt(100);

        northField.setText(String.valueOf(north));
        southField.setText(String.valueOf(south));
        eastField.setText(String.valueOf(east));
        westField.setText(String.valueOf(west));
    }


}
