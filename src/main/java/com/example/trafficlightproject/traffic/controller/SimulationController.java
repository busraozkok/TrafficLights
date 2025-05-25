package com.example.trafficlightproject.traffic.controller;

import com.example.trafficlightproject.traffic.core.base.AbstractVehicle;
import com.example.trafficlightproject.traffic.core.enums.Direction;
import com.example.trafficlightproject.traffic.model.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.*;

public class SimulationController {

    @FXML private Pane vehicleLayer;
    @FXML private Pane trafficLightLayer;

    private final List<AbstractVehicle> vehicles = new ArrayList<>();
    private final Random random = new Random();

    private final TrafficCycle trafficCycle = new TrafficCycle(); // Trafik döngüsü
    private final Intersection intersection = new Intersection(trafficCycle); // Kavşak kontrolü

    private Timeline simulationTimeline;
    private boolean isRunning;


    public SimulationController() {
        // BU SATIR YOKSA, layer null olur:
    }

    public void setTrafficDensity(Map<Direction, Integer> densities) {
        trafficCycle.calculateGreenTimes(densities);
        System.out.println("Trafik yoğunluklarına göre yeşil ışık süreleri güncellendi:");
        for (Direction dir : Direction.values()) {
            System.out.println(dir + " -> " + trafficCycle.getGreenTime(dir) + " saniye");
        }
    }


    @FXML
    public void initialize() {
        generateVehicles();  // Başlangıçta birkaç araç üret
        simulationTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateSimulation()));
        simulationTimeline.setCycleCount(Timeline.INDEFINITE);
    }

    /**
     * Simülasyon döngüsünü günceller
     */
    private void updateSimulation() {
        // 1 saniyelik süre geçişi (deltaTime = 1)
        intersection.update(1);

        // Her araç için pozisyon güncelle ve görseli taşı
        for (AbstractVehicle vehicle : vehicles) {
            if (intersection.isGreenLight(vehicle.getDirection())) {
                vehicle.move();
            }
            vehicle.updatePosition();
        }
    }

    public void startSimulation() {
        if (!isRunning) {
            isRunning = true;
            simulationTimeline.play();
            System.out.println("Simülasyon başlatıldı.");
        }
    }

    public void stopSimulation() {
        if (isRunning) {
            isRunning = false;
            simulationTimeline.pause();
            System.out.println("Simülasyon durduruldu.");
        }
    }

    public void resetSimulation() {
        stopSimulation();
        vehicleLayer.getChildren().clear();
        vehicles.clear();
        System.out.println("Simülasyon sıfırlandı.");
    }


    public HBox createControlButtons() {
        Button startButton = new Button("Başlat");
        startButton.setOnAction(e -> startSimulation());

        Button stopButton = new Button("Durdur");
        stopButton.setOnAction(e -> stopSimulation());

        Button resetButton = new Button("Sıfırla");
        resetButton.setOnAction(e -> resetSimulation());

        HBox controlButtons = new HBox(10, startButton, stopButton, resetButton);
        controlButtons.setStyle("-fx-padding: 10; -fx-alignment: center; -fx-spacing: 10;");

        return controlButtons;
    }

    /**
     * Araç üretir, katmana çizer ve intersection'a ekler
     */
    private void generateVehicles() {
        for (int i = 0; i < 5; i++) {
            Direction direction = Direction.SOUTH; // Şimdilik tek yönden
            AbstractVehicle vehicle = random.nextBoolean()
                    ? new Car(direction)
                    : new Truck(direction);

            // Başlangıç konumları örnek
            vehicle.setX(280);
            vehicle.setY(-i * 60); // Araçları aralıklı yerleştir

            vehicleLayer.getChildren().add(vehicle); // Görsel olarak ekle
            vehicles.add(vehicle);                   // Listeye ekle
            intersection.addVehicle(direction, vehicle); // Intersection kuyruğuna ekle
        }
    }

    public Pane getVehicleLayer() {
        return vehicleLayer;
    }

    public void setTrafficDensityManual(int north, int south, int east, int west) {
        Map<Direction, Integer> densities = new EnumMap<>(Direction.class);
        densities.put(Direction.NORTH, north);
        densities.put(Direction.SOUTH, south);
        densities.put(Direction.EAST, east);
        densities.put(Direction.WEST, west);

        setTrafficDensity(densities);

        System.out.println("Manuel trafik yoğunlukları uygulandı:");
        densities.forEach((dir, value) -> System.out.println(dir + " -> " + value));
    }

    public void setTrafficDensityRandom() {
        Map<Direction, Integer> densities = new EnumMap<>(Direction.class);
        for (Direction direction : Direction.values()) {
            int randomDensity = random.nextInt(101); // 0–100
            densities.put(direction, randomDensity);
        }

        setTrafficDensity(densities);

        System.out.println("Rastgele trafik yoğunlukları uygulandı:");
        densities.forEach((dir, value) -> System.out.println(dir + " -> " + value));
    }

}
