package com.example.trafficlightproject.traffic.model;

import com.example.trafficlightproject.traffic.core.base.AbstractVehicle;
import com.example.trafficlightproject.traffic.core.enums.Direction;

import java.util.Random;

/**
 * Tractor, AbstractVehicle'den türeyen daha yavaş ve büyük bir araç türüdür.
 */
public class Truck extends AbstractVehicle {

    private final Random random = new Random();

    public Truck(Direction direction) {
        super(direction, generateRandomSpeed());

        // Traktörler daha geniş ve uzun olabilir
        this.setWidth(30);
        this.setHeight(50);
    }

    /**
     * 0.5 ile 1.5 arasında rastgele bir hız üretir
     */
    private static double generateRandomSpeed() {
        return 0.5 + new Random().nextDouble();
    }

    /**
     * Sağa dönme
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
     * Sola dönme
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

