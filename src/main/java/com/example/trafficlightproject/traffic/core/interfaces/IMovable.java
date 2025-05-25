package com.example.trafficlightproject.traffic.core.interfaces;

public interface IMovable {//Büşra
    /**
     * Nesneyi hareket ettirir.
     * Örneğin bir araç yeşil ışıkta bu metod ile ilerler.
     */
    void move();

    /**
     * Nesnenin durmasını sağlar.
     * Genellikle kırmızı ışıkta veya bir engelle karşılaşıldığında çağrılır.
     */
    void stop();
}
