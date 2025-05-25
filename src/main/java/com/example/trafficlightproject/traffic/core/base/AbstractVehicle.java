package com.example.trafficlightproject.traffic.core.base;

import com.example.trafficlightproject.traffic.core.enums.Direction;
import com.example.trafficlightproject.traffic.core.interfaces.IVehicle;
import javafx.scene.shape.Rectangle;

/**
 * AbstractVehicle sınıfı, tüm araçlar için ortak olan davranışları tanımlar.
 * Bu sınıf soyut olup IVehicle arayüzünü uygular ve JavaFX Rectangle'ını genişletir.
 * Her araç görsel olarak bir dikdörtgen şeklinde temsil edilir.
 */
public abstract class AbstractVehicle extends Rectangle implements IVehicle {

    protected double speed;                   // Aracın hareket hızı (piksel/sn)
    protected Direction direction;            // Aracın gittiği yön (NORTH, SOUTH, EAST, WEST)
    protected boolean isMoving;               // Araç şu anda hareket ediyor mu?
    protected boolean passedIntersection;     // Araç kavşağı geçti mi?

    /**
     * Kurucu metot - Yön ve hız verilerek araç oluşturulur.
     * Aracın başlangıç boyutu da burada belirlenir.
     */
    public AbstractVehicle(Direction direction, double speed) {
        this.direction = direction;
        this.speed = speed;
        this.isMoving = false;                // Başlangıçta araç duruyor kabul edilir
        this.passedIntersection = false;      // Başlangıçta kavşaktan geçmedi

        this.setWidth(20);                    // Aracın görsel genişliği (piksel)
        this.setHeight(40);                   // Aracın görsel yüksekliği (piksel)
    }

    /**
     * Aracı hareket ettirmeye başlar.
     */
    @Override
    public void move() {
        isMoving = true;
    }

    /**
     * Aracı durdurur.
     */
    @Override
    public void stop() {
        isMoving = false;
    }

    /**
     * Aracın kavşaktan geçip geçmediğini döner.
     * @return true → geçti, false → henüz geçmedi
     */
    @Override
    public boolean hasPassedIntersection() {
        return passedIntersection;
    }

    /**
     * Aracın mevcut yönünü döner.
     */
    @Override
    public Direction getDirection() {
        return direction;
    }

    /**
     * Aracın yönüne göre konumunu günceller.
     * Bu metod her zaman döngüsünde çağrılır.
     */
    @Override
    public void updatePosition() {
        if (!isMoving) return; // Hareket etmiyorsa pozisyon güncellenmez

        // Yöne göre X veya Y koordinatlarını güncelle
        switch (direction) {
            case NORTH -> this.setY(this.getY() - speed);   // Yukarı hareket
            case SOUTH -> this.setY(this.getY() + speed);   // Aşağı hareket
            case EAST  -> this.setX(this.getX() + speed);   // Sağa hareket
            case WEST  -> this.setX(this.getX() - speed);   // Sola hareket
        }

        // Kavşaktan geçip geçmediğini kontrol et
        checkIntersectionPassed();
    }

    /**
     * Araç kavşaktan geçti mi, konumuna göre kontrol edilir.
     * Konum belli sınırları aştıysa "geçti" kabul edilir.
     */
    protected void checkIntersectionPassed() {
        if (!passedIntersection) {
            if ((direction == Direction.NORTH && this.getY() < 200) ||
                    (direction == Direction.SOUTH && this.getY() > 400) ||
                    (direction == Direction.EAST  && this.getX() > 400) ||
                    (direction == Direction.WEST  && this.getX() < 200)) {

                passedIntersection = true;
            }
        }
    }
}
