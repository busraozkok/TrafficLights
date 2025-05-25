package com.example.trafficlightproject.traffic.model;

import com.example.trafficlightproject.traffic.core.enums.Direction;
import com.example.trafficlightproject.traffic.core.enums.LightColor;
import com.example.trafficlightproject.traffic.core.interfaces.IVehicle;

import java.util.HashMap;
import java.util.Map;

public class Intersection {

    private final Map<Direction, TrafficQueue> directionQueues; // Yön başına araç kuyruğu
    private final Map<Direction, TrafficLight> trafficLights;   // Yön başına trafik ışığı
    private final TrafficCycle trafficCycle;                    // Dinamik ışık döngüsü

    public boolean isGreenLight(Direction direction) {
        // TrafficCycle üzerinden veya kendi iç durumunuza göre kontrol edin
        return trafficCycle.isGreen(direction);
    }

    public Intersection(TrafficCycle trafficCycle) {
        this.trafficCycle = trafficCycle;
        this.directionQueues = new HashMap<>();
        this.trafficLights = new HashMap<>();

        // Her yön için boş kuyruk ve trafik ışığı başlat
        for (Direction dir : Direction.values()) {
            directionQueues.put(dir, new TrafficQueue());
            trafficLights.put(dir, new TrafficLight(dir, 10, 3, 15)); // Başlangıçta sabit süreler
        }
    }

    /**
     * Belirli bir yönden aracı kavşağa alır (kuyruğa ekler).
     */
    public void addVehicle(Direction direction, IVehicle vehicle) {
        directionQueues.get(direction).addVehicle(vehicle);
    }

    /**
     * Her döngü adımında ışıkları ve kuyrukları günceller.
     */
    public void update(int deltaTime) {
        trafficCycle.update(deltaTime); // Süre ilerlet
        Direction activeDirection = trafficCycle.getCurrentGreenDirection();

        for (Direction dir : Direction.values()) {
            trafficLights.get(dir).tick(); // Işık süresini azalt

            if (dir == activeDirection &&
                    trafficLights.get(dir).getCurrentColor() == LightColor.GREEN) {

                IVehicle frontVehicle = directionQueues.get(dir).peekVehicle();

                if (frontVehicle != null && !frontVehicle.hasPassedIntersection()) {
                    frontVehicle.move(); // Aracı harekete geçir
                }

                // Araç kavşağı geçtiyse kuyruktan çıkar
                if (frontVehicle != null && frontVehicle.hasPassedIntersection()) {
                    directionQueues.get(dir).pollVehicle();
                }
            } else {
                // Diğer yönlerde araçları durdur
                IVehicle front = directionQueues.get(dir).peekVehicle();
                if (front != null) front.stop();
            }
        }
    }

    /**
     * Kavşakta aktif olan yönü döndürür.
     */
    public Direction getActiveDirection() {
        return trafficCycle.getCurrentGreenDirection();
    }

    /**
     * Her yönün ışık ve kuyruk bilgilerini loglamak için (opsiyonel).
     */
    public void printStatus() {
        System.out.println("Kavşak Durumu:");
        for (Direction dir : Direction.values()) {
            System.out.println("Yön: " + dir +
                    ", Işık: " + trafficLights.get(dir).getCurrentColor() +
                    ", Kuyruk: " + directionQueues.get(dir).getSize());
        }
    }

}
