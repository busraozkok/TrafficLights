package com.example.trafficlightproject.traffic.core.interfaces;

import javafx.scene.paint.Color;

public interface ILight {
    /**
     * Işığın rengini değiştirir.
     * Örneğin, kırmızıdan yeşile veya yeşilden sarıya geçiş.
     */
    void changeColor();

    /**
     * Mevcut ışık durumunda kalan süreyi döndürür.
     *
     * @return Kalan süre (saniye cinsinden)
     */
    int getRemainingTime();

    /**
     * Işığın süresini ayarlar.
     *
     * @param duration Yeni süre (saniye cinsinden)
     */
    void setDuration(int duration);

    /**
     * Işığın mevcut rengini döndürür.
     *
     * @return Mevcut ışık rengi
     */
    Color getCurrentColor();

    /**
     * Işığın rengini ayarlar.
     *
     * @param color Yeni ışık rengi
     */
    void setCurrentColor(Color color);
}
