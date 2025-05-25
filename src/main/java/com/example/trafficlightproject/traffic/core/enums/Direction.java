package com.example.trafficlightproject.traffic.core.enums;

public enum Direction {
    NORTH,
    SOUTH,
    EAST,
    WEST;

    /**
     * Bu metod, mevcut yönün tam zıt yönünü (karşı yönü) döndürür.
     * Örneğin NORTH için SOUTH, EAST için WEST döner.
     *
     * Bu özellik genellikle iki yönlü trafik kontrolü, karşıdan gelen araçları kontrol etme
     * veya yön tabanlı geçiş kuralları için kullanılır.
     *
     * @return Zıt yön (karşı yön)
     */
    public Direction getOpposite() {
        switch (this) {
            case NORTH: return SOUTH;
            case SOUTH: return NORTH;
            case EAST:  return WEST;
            case WEST:  return EAST;
            default:    throw new IllegalArgumentException("Unknown direction: " + this);
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case NORTH: return "North";
            case SOUTH: return "South";
            case EAST:  return "East";
            case WEST:  return "West";
            default:    return super.toString();
        }
    }
}
