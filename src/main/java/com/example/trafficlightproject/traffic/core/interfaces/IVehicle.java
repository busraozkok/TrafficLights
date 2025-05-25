package com.example.trafficlightproject.traffic.core.interfaces;

import com.example.trafficlightproject.traffic.core.enums.Direction;

/**
 * IVehicle arayüzü, trafik simülasyonunda kullanılacak araç nesnelerinin sahip olması gereken temel davranışları tanımlar.
 * Araçların konumu, hareketi ve kavşakla olan ilişkileri bu arayüz üzerinden yönetilir.
 */
public interface IVehicle {
    double getX();                        //Aracın X eksenindeki konumunu döndürür.
    double getY();                        //Aracın Y eksenindeki konumunu döndürür.
    void setX(double x);                  // Aracın X koordinatını günceller.
    void setY(double y);                  //Aracın Y koordinatını günceller.
    void move();                          // Araba hareket ediyor mu, Aracı ileri doğru hareket ettirir. Trafik ışığı yeşilse veya durma zorunluluğu yoksa çağrılır.
    void stop();                          // Araba durmalı mı, Aracı durdurur. Genellikle kırmızı ışıkta veya öndeki araç duruyorsa kullanılır.
    void updatePosition();                // Konum güncelle. Aracın mevcut konumunu günceller. Genellikle her simülasyon döngüsünde çağrılır.
    boolean hasPassedIntersection();      // Kavşaktan geçti mi, Aracın kavşağı geçip geçmediğini kontrol eder. Bu bilgi, araç simülasyondan çıkarılacak mı veya durmaya devam edecek mi kararlarında kullanılır
    Direction getDirection();             // Gidiş yönü,Aracın yönünü döndürür. NORTH, SOUTH, EAST veya WEST olabilir.
}
