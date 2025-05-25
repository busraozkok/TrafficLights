package com.example.trafficlightproject.traffic.core.enums;

public enum LightColor {
    RED,
    YELLOW,
    GREEN;
    /**
     * Trafik ışığının bir sonraki renge geçişini temsil eder.
     * Geçiş sırası: GREEN -> YELLOW -> RED -> GREEN ...
     *
     * Bu metod simülasyon döngüsünde trafik ışıklarının sıralı olarak ilerlemesini sağlar.
     *
     * @return Işık renginin bir sonraki durumu
     */
    public LightColor next() {
        switch (this) {
            case GREEN: return YELLOW;
            case YELLOW: return RED;
            case RED: return GREEN;
            default: throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case RED: return "Red";
            case YELLOW: return "Yellow";
            case GREEN: return "Green";
            default: return super.toString();
        }
    }
}
