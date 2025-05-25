package com.example.trafficlightproject.traffic.model;

import com.example.trafficlightproject.traffic.core.base.AbstractVehicle;
import com.example.trafficlightproject.traffic.core.enums.Direction;

import java.util.Random;

/**
 * Car, AbstractVehicle soyut sınıfını genişleten somut bir araç türüdür.
 * Rasgele hızla başlar ve sağa/sola dönebilir.
 */
public class Car extends AbstractVehicle {

    private final Random random = new Random();

    public Car(Direction direction) {
        super(direction, generateRandomSpeed());
    }

    /**
     * 1.0 ile 3.5 arasında rasgele hız üretir (piksel/frame)
     */
    private static double generateRandomSpeed() {
        return 1.0 + new Random().nextDouble() * 2.5;
    }

    /**
     * Sağa döner (örneğin NORTH ise EAST olur)
     */
    public void turnRight() {
        switch (this.direction) {
            case NORTH -> this.direction = Direction.EAST;
            case EAST  -> this.direction = Direction.SOUTH;
            case SOUTH -> this.direction = Direction.WEST;
            case WEST  -> this.direction = Direction.NORTH;
        }
    }

    /**
     * Sola döner (örneğin NORTH ise WEST olur)
     */
    public void turnLeft() {
        switch (this.direction) {
            case NORTH -> this.direction = Direction.WEST;
            case WEST  -> this.direction = Direction.SOUTH;
            case SOUTH -> this.direction = Direction.EAST;
            case EAST  -> this.direction = Direction.NORTH;
        }
    }
}

