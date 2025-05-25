package com.example.trafficlightproject.traffic.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class LightController {

    @FXML private Circle redCircle;
    @FXML private Circle yellowCircle;
    @FXML private Circle greenCircle;

    @FXML private Label redLabel;
    @FXML private Label yellowLabel;
    @FXML private Label greenLabel;

    private Timeline countdownTimeline;

    /**
     * Başlatmak istediğin ışık için bu fonksiyonu çağır.
     * @param color Renk: "RED", "YELLOW", "GREEN"
     * @param seconds Geri sayım süresi
     */
    public void startCountdown(String color, int seconds) {
        stopCountdown(); // Önceki zamanlayıcıyı durdur

        // Aktif renk ve etiket seçimi
        Circle activeCircle = switch (color.toUpperCase()) {
            case "RED" -> redCircle;
            case "YELLOW" -> yellowCircle;
            case "GREEN" -> greenCircle;
            default -> null;
        };

        Label activeLabel = switch (color.toUpperCase()) {
            case "RED" -> redLabel;
            case "YELLOW" -> yellowLabel;
            case "GREEN" -> greenLabel;
            default -> null;
        };

        if (activeCircle == null || activeLabel == null) return;

        // Diğer ışıkları "kapalı" hale getir
        setCircleColors(color);

        // Sayacı başlat
        countdownTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            int current = Integer.parseInt(activeLabel.getText());
            if (current > 0) {
                activeLabel.setText(String.valueOf(current - 1));
            } else {
                stopCountdown(); // Süre bitince durdur
            }
        }));
        countdownTimeline.setCycleCount(seconds);
        activeLabel.setText(String.valueOf(seconds));
        countdownTimeline.play();
    }

    /**
     * Işıkları varsayılan rengine döndürür, yalnızca seçilen rengi "parlak" yapar.
     */
    private void setCircleColors(String activeColor) {
        redCircle.setFill(activeColor.equals("RED") ? Color.RED : Color.DARKRED);
        yellowCircle.setFill(activeColor.equals("YELLOW") ? Color.YELLOW : Color.GOLDENROD);
        greenCircle.setFill(activeColor.equals("GREEN") ? Color.LIMEGREEN : Color.DARKGREEN);
    }

    /**
     * Zamanlayıcıyı durdurur.
     */
    public void stopCountdown() {
        if (countdownTimeline != null) {
            countdownTimeline.stop();
        }
    }
}
