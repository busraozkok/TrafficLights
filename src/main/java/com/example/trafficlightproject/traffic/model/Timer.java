package com.example.trafficlightproject.traffic.model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Duration;

public class Timer {
    private final IntegerProperty remainingTime; // Kalan süre (saniye)
    private final Timeline timeline;            // Animasyonlu zamanlayıcı
    private final Runnable onTimerFinished;     // Geri sayım bittiğinde çalışacak işlev

    /**
     * Constructor: Timer nesnesi oluşturur.
     *
     * @param initialTime     Başlangıç süresi (saniye cinsinden)
     * @param onTimerFinished Geri sayım sona erdiğinde çağrılacak işlev
     */
    public Timer(int initialTime, Runnable onTimerFinished) {
        this.remainingTime = new SimpleIntegerProperty(initialTime);
        this.onTimerFinished = onTimerFinished;

        // Timeline ile geri sayım oluştur
        this.timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            if (remainingTime.get() > 0) {
                remainingTime.set(remainingTime.get() - 1); // Süreyi azalt
            } else {
                stop(); // Geri sayımı durdur
                if (onTimerFinished != null) {
                    onTimerFinished.run(); // Geri sayım tamamlandığında çalıştır
                }
            }
        }));
        this.timeline.setCycleCount(Timeline.INDEFINITE); // Sonsuz döngü
    }

    /**
     * Geri sayımı başlatır.
     */
    public void start() {
        if (remainingTime.get() > 0 && timeline.getStatus() != Timeline.Status.RUNNING) {
            timeline.play();
        }
    }

    /**
     * Geri sayımı durdurur.
     */
    public void stop() {
        if (timeline.getStatus() == Timeline.Status.RUNNING) {
            timeline.pause();
        }
    }

    /**
     * Geri sayımı sıfırlar.
     *
     * @param initialTime Yeni başlangıç süresi
     */
    public void reset(int initialTime) {
        stop();
        remainingTime.set(initialTime);
    }

    /**
     * Kalan süreyi döndürür.
     *
     * @return Kalan süre (saniye cinsinden)
     */
    public int getRemainingTime() {
        return remainingTime.get();
    }

    /**
     * Kalan süreyi JavaFX özelliği olarak döndürür (bağlama için).
     *
     * @return Kalan süreyi temsil eden IntegerProperty
     */
    public IntegerProperty remainingTimeProperty() {
        return remainingTime;
    }
}
