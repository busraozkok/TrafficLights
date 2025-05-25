package com.example.trafficlightproject.traffic.model;

import com.example.trafficlightproject.traffic.core.enums.Direction;
import java.util.Map;

/**
 * TrafficCycle sınıfı, trafik ışıklarının yoğunluğa göre çalışma süresini ve yön değiştirme döngüsünü yönetir.
 * Bu sınıf 120 saniyelik sabit bir çevrimde her yönün yeşil ışık süresini hesaplar ve sırayla ışıkları değiştirir.
 */
public class TrafficCycle {
    // Sabit değerler (tüm yönlerin toplam çevrim süresi 120 saniyedir)
    public static final int TOTAL_CYCLE_TIME = 120;
    public static final int YELLOW_TIME = 3;           // Sarı ışık süresi sabit
    public static final int MIN_GREEN_TIME = 10;       // En az yeşil ışık süresi
    public static final int MAX_GREEN_TIME = 60;       // En fazla yeşil ışık süresi

    // Her yön için yeşil ışık süresi (saniye cinsinden)
    private int greenTimeNorth;
    private int greenTimeSouth;
    private int greenTimeEast;
    private int greenTimeWest;

    // Şu anda yeşil ışık yanan yön
    private Direction currentGreenDirection;

    // Geçen toplam zaman (saniye cinsinden)
    private int elapsedTime;

    /**
     * Varsayılan yapılandırıcı - başlangıçta her yön için sabit yeşil süre atanır.
     */


    public boolean isGreen(Direction direction) {
        // Mevcut döngü durumuna göre true/false döndür
        // Örnek basit bir mantık:
        return currentGreenDirection == direction;
    }

    public TrafficCycle() {
        greenTimeNorth = 30;
        greenTimeSouth = 30;
        greenTimeEast = 30;
        greenTimeWest = 30;

        currentGreenDirection = Direction.NORTH;
        elapsedTime = 0;
    }

    /**
     * Her yön için trafik yoğunluğuna göre yeşil ışık süresi hesaplar.
     * Süreler minimum ve maksimum değerlerle sınırlandırılır.
     * @param densities Her yön için yoğunluk oranı (0–100)
     */
    public void calculateGreenTimes(Map<Direction, Integer> densities) {
        int totalDensity = densities.values().stream().mapToInt(Integer::intValue).sum();
        if (totalDensity == 0) totalDensity = 1;  // Sıfıra bölünmeyi önlemek için koruma

        greenTimeNorth = calculateTimeForDensity(densities.getOrDefault(Direction.NORTH, 0), totalDensity);
        greenTimeSouth = calculateTimeForDensity(densities.getOrDefault(Direction.SOUTH, 0), totalDensity);
        greenTimeEast  = calculateTimeForDensity(densities.getOrDefault(Direction.EAST, 0), totalDensity);
        greenTimeWest  = calculateTimeForDensity(densities.getOrDefault(Direction.WEST, 0), totalDensity);
    }

    /**
     * Belirli bir yön için yoğunluğa göre yeşil ışık süresi hesaplar.
     * @param density O yönün yoğunluğu
     * @param totalDensity Tüm yönlerin toplam yoğunluğu
     * @return Yoğunluk oranına göre hesaplanan ve sınırlanan yeşil ışık süresi
     */
    private int calculateTimeForDensity(int density, int totalDensity) {
        double proportion = (double) density / totalDensity;
        int availableTime = TOTAL_CYCLE_TIME - 4 * YELLOW_TIME; // Sarı süreler çıkarılır
        int time = (int) (proportion * availableTime);

        // Süreyi minimum ve maksimum aralıkta sınırla
        if (time < MIN_GREEN_TIME) return MIN_GREEN_TIME;
        if (time > MAX_GREEN_TIME) return MAX_GREEN_TIME;
        return time;
    }

    /**
     * Geçen süreyi artırır ve yeşil ışığın hangi yönde olacağını hesaplar.
     * Bu metot her saniye TimeController tarafından çağrılmalıdır.
     * @param deltaTime Kaç saniye ilerletileceği
     */
    public void update(int deltaTime) {
        elapsedTime += deltaTime;

        int cyclePosition = elapsedTime % TOTAL_CYCLE_TIME;

        // Her yön için yeşil + sarı sürelerin bitiş zamanı belirlenir
        int northEnd = greenTimeNorth + YELLOW_TIME;
        int southEnd = northEnd + greenTimeSouth + YELLOW_TIME;
        int eastEnd  = southEnd + greenTimeEast + YELLOW_TIME;
        int westEnd  = eastEnd + greenTimeWest + YELLOW_TIME;

        // O anki süre hangi aralıktaysa, o yön aktif yeşil olarak atanır
        if (cyclePosition < northEnd) {
            currentGreenDirection = Direction.NORTH;
        } else if (cyclePosition < southEnd) {
            currentGreenDirection = Direction.SOUTH;
        } else if (cyclePosition < eastEnd) {
            currentGreenDirection = Direction.EAST;
        } else {
            currentGreenDirection = Direction.WEST;
        }
    }

    /**
     * Şu anda yeşil ışıkta olan yönü verir.
     */
    public Direction getCurrentGreenDirection() {
        return currentGreenDirection;
    }

    /**
     * O anki yeşil ışığın kalan süresini hesaplar.
     * @return Kalan süre (saniye)
     */
    public int getRemainingTime() {
        int cyclePosition = elapsedTime % TOTAL_CYCLE_TIME;

        switch (currentGreenDirection) {
            case NORTH:
                return greenTimeNorth + YELLOW_TIME - cyclePosition;
            case SOUTH:
                return greenTimeSouth + YELLOW_TIME -
                        (cyclePosition - (greenTimeNorth + YELLOW_TIME));
            case EAST:
                return greenTimeEast + YELLOW_TIME -
                        (cyclePosition - (greenTimeNorth + greenTimeSouth + 2 * YELLOW_TIME));
            case WEST:
                return greenTimeWest + YELLOW_TIME -
                        (cyclePosition - (greenTimeNorth + greenTimeSouth + greenTimeEast + 3 * YELLOW_TIME));
            default:
                return 0;
        }
    }

    // Getter metodları (istersen ileride set metodları da ekleyebilirsin)
    public int getElapsedTime() {
        return elapsedTime;
    }

    public int getGreenTime(Direction direction) {
        return switch (direction) {
            case NORTH -> greenTimeNorth;
            case SOUTH -> greenTimeSouth;
            case EAST  -> greenTimeEast;
            case WEST  -> greenTimeWest;
        };
    }
}
