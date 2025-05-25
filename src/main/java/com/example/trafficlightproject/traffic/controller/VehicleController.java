package com.example.trafficlightproject.traffic.controller;

import com.example.trafficlightproject.traffic.model.Vehicle;

import java.util.List;

public class VehicleController {
    private List<Vehicle> vehicles; // Yöneteceğimiz araçların listesi
    private double safeDistance;   // Çarpışmayı önlemek için araçlar arasındaki güvenli mesafe

    public VehicleController(List<Vehicle> vehicles, double safeDistance) {
        this.vehicles = vehicles; // Araç listesini al
        this.safeDistance = safeDistance; // Güvenli mesafeyi ayarla
    }

    /**
     * Araçların hızlarını ve pozisyonlarını günceller.
     */
    public void updateVehiclePositions() {
        for (Vehicle vehicle : vehicles) {
            // Araç hareket ediyorsa hızına göre pozisyonunu güncelle
            if (vehicle.isMoving()) {
                System.out.println("Araç " + vehicle.getId() + " hareket ediyor. Hız: " + vehicle.getSpeed());
            } else {
                System.out.println("Araç " + vehicle.getId() + " bekliyor.");
            }
        }
    }

    /**
     * Araçların çarpışma durumlarını kontrol eder.
     */
    public void checkCollisions() {
        for (int i = 0; i < vehicles.size(); i++) {
            Vehicle currentVehicle = vehicles.get(i);

            for (int j = i + 1; j < vehicles.size(); j++) {
                Vehicle otherVehicle = vehicles.get(j);

                // Eğer araçlar aynı yöndeyse ve mesafeleri güvenli mesafeden azsa, çarpışmayı önleyin
                if (currentVehicle.getDirection().equals(otherVehicle.getDirection())) {
                    double distance = calculateDistance(currentVehicle, otherVehicle);
                    if (distance < safeDistance) {
                        System.out.println("Çarpışma önlendi! Araçlar: " + currentVehicle.getId() + " ve " + otherVehicle.getId());
                        currentVehicle.stop();
                        otherVehicle.stop();
                    }
                }
            }
        }
    }

    /**
     * Araçların çarpışmasını önlemek için hızlarını kontrol eder.
     */
    public void manageSpeeds() {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.isMoving()) {
                // Araç sarı ışıkta ise yavaşla
                if (vehicle.getSpeed() > 10) {
                    vehicle.decelerate();
                }
                // Araç kırmızı ışıkta duruyorsa, kalkış ivmesi uygula
                else if (vehicle.getSpeed() == 0) {
                    vehicle.accelerate();
                }
            }
        }
    }

    /**
     * İki araç arasındaki mesafeyi hesaplar (örnek için basitleştirilmiş bir hesaplama).
     *
     * @param v1 Birinci araç
     * @param v2 İkinci araç
     * @return Araçlar arasındaki mesafe
     */
    private double calculateDistance(Vehicle v1, Vehicle v2) {
        return Math.sqrt(Math.pow(v1.getXPosition() - v2.getXPosition(), 2) + Math.pow(v1.getYPosition() - v2.getYPosition(), 2));
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}
