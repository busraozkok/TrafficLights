package com.example.trafficlightproject.traffic.model;

import com.example.trafficlightproject.traffic.core.enums.Direction;
import com.example.trafficlightproject.traffic.core.enums.LightColor;
import com.example.trafficlightproject.traffic.core.interfaces.IVehicle;




public class TrafficLight {

    // Işığın hangi yöne ait olduğunu tutar (örneğin: NORTH, SOUTH)
    private Direction direction;

    // Şu anki ışık rengi
    private LightColor currentColor;

    // O anki ışık renginin bitmesine kalan süre
    private int remainingTime;

    // Her ışık renginin ne kadar süreyle yanacağı (saniye cinsinden)
    private int redDuration;
    private int yellowDuration;
    private int greenDuration;

    // Yapıcı metot: ışığın yönü ve her rengin süresi dışarıdan verilir.
    public TrafficLight(Direction direction, int redDuration, int yellowDuration, int greenDuration) {
        this.direction = direction;
        this.redDuration = redDuration;
        this.yellowDuration = yellowDuration;
        this.greenDuration = greenDuration;

        // Başlangıçta ışık kırmızıdan başlasın
        this.currentColor = LightColor.RED;
        this.remainingTime = redDuration;
    }

    // Işık rengini bir sonrakine geçiren metot
    public void switchToNextColor() {
        switch (currentColor) {
            case RED -> {
                currentColor = LightColor.GREEN;
                remainingTime = greenDuration;
            }
            case GREEN -> {
                currentColor = LightColor.YELLOW;
                remainingTime = yellowDuration;
            }
            case YELLOW -> {
                currentColor = LightColor.RED;
                remainingTime = redDuration;
            }
        }
    }

    // Zamanlayıcı: her saniye çağrıldığında süreyi 1 azaltır, süre biterse rengi değiştirir
    public void tick() {
        if (remainingTime > 0) {
            remainingTime--;
        } else {
            switchToNextColor();
        }
    }

    // Şu anki ışık rengini döndürür
    public LightColor getCurrentColor() {
        return currentColor;
    }

    // Işığın bu renkte kalacağı kalan süreyi döndürür
    public int getRemainingTime() {
        return remainingTime;
    }

    // Bu ışığın yönünü döndürür
    public Direction getDirection() {
        return direction;
    }

    public void updateLightStateBasedOnVehicles(TrafficQueue queue) {
        IVehicle vehicleAtFront = queue.peekVehicle(); // Kuyruktaki ilk aracı al

        if (vehicleAtFront != null) {
            if (vehicleAtFront.hasPassedIntersection()) {
                switchToNextColor(); // Araç kavşaktan geçtiyse ışık değiştir
            }
        }
    }




}
