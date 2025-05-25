package com.example.trafficlightproject.traffic.model;

import com.example.trafficlightproject.traffic.core.enums.Direction;





public class Vehicle {
    private String id;             // Araç kimliği
    private double xPosition;      // Araç x eksenindeki konumu
    private double yPosition;      // Araç y eksenindeki konumu
    private String direction;      // Araç yönü (örneğin: "NORTH", "SOUTH", "EAST", "WEST")
    private boolean isMoving;      // Araç hareket ediyor mu?
    private double speed;          // Araç hızı (birim/saniye)

    /**
     * Constructor: Bir araç nesnesi oluşturur.
     *
     * @param id        Araç kimliği
     * @param xPosition Başlangıç x konumu
     * @param yPosition Başlangıç y konumu
     * @param direction Araç yönü
     * @param speed     Başlangıç hızı
     */
    public Vehicle(String id, double xPosition, double yPosition, String direction, double speed) {
        this.id = id;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.direction = direction;
        this.speed = speed;
        this.isMoving = speed > 0; // Eğer hız pozitifse araç hareket ediyor
    }

    /**
     * Araç kimliğini döndürür.
     *
     * @return Araç kimliği
     */
    public String getId() {
        return id;
    }

    /**
     * X eksenindeki konumu döndürür.
     *
     * @return x konumu
     */
    public double getXPosition() {
        return xPosition;
    }

    /**
     * Y eksenindeki konumu döndürür.
     *
     * @return y konumu
     */
    public double getYPosition() {
        return yPosition;
    }

    /**
     * Araç yönünü döndürür.
     *
     * @return Yön
     */
    public String getDirection() {
        return direction;
    }

    /**
     * Araç hızını döndürür.
     *
     * @return Hız
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Araç hareket ediyor mu?
     *
     * @return true: Araç hareket ediyor, false: Araç duruyor
     */
    public boolean isMoving() {
        return isMoving;
    }

    /**
     * Araç yönünü ayarlar.
     *
     * @param direction Yeni yön
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * Araç hızını ayarlar ve hareket durumunu günceller.
     *
     * @param speed Yeni hız
     */
    public void setSpeed(double speed) {
        this.speed = speed;
        this.isMoving = speed > 0; // Hız sıfırdan büyükse araç hareket ediyor
    }

    /**
     * Araç konumunu günceller (hız ve yönüne bağlı olarak).
     */
    public void updatePosition() {
        if (isMoving) {
            switch (direction) {
                case "NORTH":
                    yPosition -= speed; // Kuzeye doğru hareket
                    break;
                case "SOUTH":
                    yPosition += speed; // Güneye doğru hareket
                    break;
                case "EAST":
                    xPosition += speed; // Doğuya doğru hareket
                    break;
                case "WEST":
                    xPosition -= speed; // Batıya doğru hareket
                    break;
            }
        }
    }

    /**
     * Araç durdurulur (hız sıfırlanır).
     */
    public void stop() {
        this.speed = 0;
        this.isMoving = false;
    }

    /**
     * Araç bilgilerini String formatında döndürür.
     *
     * @return Araç bilgileri
     */
    @Override
    public String toString() {
        return "Araç ID: " + id +
                ", Konum: (" + xPosition + ", " + yPosition + ")" +
                ", Yön: " + direction +
                ", Hız: " + speed +
                ", Hareket Durumu: " + (isMoving ? "Hareket Ediyor" : "Duruyor");
    }

    public void accelerate() {
        this.speed += 2; // İstersen hız artışını değiştirebilirsin
        this.isMoving = true;
    }

    public void decelerate() {
        this.speed -= 2;
        if (this.speed < 0) {
            this.speed = 0;
            this.isMoving = false;
        }
    }

}
