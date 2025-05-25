package com.example.trafficlightproject.traffic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import com.example.trafficlightproject.traffic.controller.SimulationController;
import com.example.trafficlightproject.traffic.view.MenuView;
import com.example.trafficlightproject.traffic.view.ControlPanel;
import javafx.scene.layout.VBox;

public class MainApp extends Application {
    private static Stage primaryStage;

    // Açılış ekranı (Yoğunluk seçme ekranı)
    VBox menuView = new MenuView();

    // Kontrol paneli (Başlat, duraklat, reset butonları)
    VBox controlPanel = new ControlPanel();

    private SimulationController simulationController;


    @Override
    public void start(Stage stage) {
        try {
            primaryStage = stage;

            // FXML'den Menü Görünümünü Yükle
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/trafficlightproject/view/MenuView.fxml"));
            Parent menuView = loader.load();

            Scene menuScene = new Scene(menuView, 600, 400);
            stage.setTitle("Traffic Light Simulation - Menu");
            stage.setScene(menuScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        launch(args); // JavaFX uygulamasını başlat
    }


    public static void setRoot(String fxmlPath) throws Exception {
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/com/example/trafficlightproject/traffic/view/" + fxmlPath));
        Parent newRoot = loader.load();

        Scene newScene = new Scene(newRoot);
        primaryStage.setScene(newScene);
        primaryStage.setTitle("Trafik Simülasyonu - Çalışıyor");
    }





}