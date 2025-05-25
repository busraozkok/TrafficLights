package com.example.trafficlightproject.traffic.core.base;

import javafx.scene.paint.Color;

public abstract class AbstractLight {
    private Color color;        // Işığın mevcut rengi
    private int duration;       // Işığın mevcut durumunda kalacağı süre (saniye)
    private int remainingTime;  // Kalan süre (saniye)

    /**
     * AbstractLight constructor.
     *
     * @param initialColor Başlangıç rengi
     * @param duration     Başlangıç süresi (saniye)
     */
    public AbstractLight(Color initialColor, int duration) {
        this.color = initialColor;
        this.duration = duration;
        this.remainingTime = duration; // Başlangıçta kalan süre, verilen süreyle aynı
    }

    /**
     * Işığın rengini alır.
     *
     * @return Işığın mevcut rengi
     */
    public Color getColor() {
        return color;
    }

    /**
     * Işığın rengini ayarlar.
     *
     * @param color Yeni renk
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Işığın süresini alır.
     *
     * @return Süre (saniye cinsinden)
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Işığın süresini ayarlar.
     *
     * @param duration Yeni süre (saniye cinsinden)
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Kalan süreyi alır.
     *
     * @return Kalan süre (saniye cinsinden)
     */
    public int getRemainingTime() {
        return remainingTime;
    }

    /**
     * Kalan süreyi sıfırlar.
     */
    public void resetRemainingTime() {
        this.remainingTime = this.duration;
    }

    /**
     * Kalan süreyi bir azaltır.
     * Süre sıfıra ulaştığında, bir sonraki duruma geçiş yapılmalıdır.
     */
    public void countdown() {
        if (remainingTime > 0) {
            remainingTime--;
        }
    }

    /**
     * Işığın bir sonraki duruma geçişini sağlayan soyut metot.
     * İlgili alt sınıflar bu metodu implement etmelidir.
     */
    public abstract void changeState();
}
