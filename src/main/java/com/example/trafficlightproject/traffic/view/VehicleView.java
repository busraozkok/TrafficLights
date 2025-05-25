package com.example.trafficlightproject.traffic.view;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import com.example.trafficlightproject.traffic.core.base.AbstractVehicle;
import javafx.fxml.FXML;

/**
 * Aracın ekrandaki görsel temsili.
 */
public class VehicleView {
    private final AbstractVehicle vehicle;
    private final Rectangle shape;

    public VehicleView(AbstractVehicle vehicle) {
        this.vehicle = vehicle;

        // Aracın şekli (dikdörtgen olarak)
        this.shape = new Rectangle(vehicle.getWidth(), vehicle.getHeight());
        this.shape.setFill(Color.DARKCYAN);
        this.shape.setArcWidth(10);   // köşeleri ovalleştir
        this.shape.setArcHeight(10);

        update();  // baştaki pozisyonla eşle
    }

    public Rectangle getShape() {
        return shape;
    }

    /**
     * Araç konumu güncellendiğinde çağrılır.
     */
    public void update() {
        shape.setX(vehicle.getX());
        shape.setY(vehicle.getY());
    }

    /**
     * Aracın rengi dışarıdan değiştirilebilir.
     */
    public void setColor(Color color) {
        shape.setFill(color);
    }
}
