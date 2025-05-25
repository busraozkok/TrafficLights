package com.example.trafficlightproject.traffic.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.example.trafficlightproject.traffic.core.enums.Direction;
import com.example.trafficlightproject.traffic.model.Car;
import com.example.trafficlightproject.traffic.model.Truck;
import com.example.trafficlightproject.traffic.core.base.AbstractVehicle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * TrafficSimulationView, trafik simülasyonunun GUI tarafındaki görüntü ve yönetim sınıfıdır.
 * Kullanıcıya yolları, araçları ve ışıkları göstermekle birlikte,
 * arka plandaki controller sınıflarıyla bağlantı kurar.
 *
 * Bu sınıf aynı zamanda araçların başlangıç konumlarını,
 * hareketlerini ve simülasyonun başlatılmasını yönetir.
 */
public class TrafficSimulationView {

    @FXML
    private AnchorPane root; // FXML'deki ana kapsayıcı pane

    @FXML
    private Group roadGroup; // Yolların görsel olarak gruplanması için (örnek kullanım)

    @FXML
    private Pane vehicleLayer; // Araçların ekleneceği katman

    private final List<AbstractVehicle> vehicles = new ArrayList<>(); // Simülasyondaki araçlar

    private final Random random = new Random(); // Araç türü seçimi için rastgele

    private Stage primaryStage; // Sahne referansı, dışarıdan set edilmesi gerekebilir

    // Simülasyonun kontrolcülerini buraya inject edip kullanabilirsiniz.
    // Örneğin zaman, araç, ışık yönetimi controllerları burada tutulabilir
    // private SimulationController simulationController;
    // private VehicleController vehicleController;
    // private TimeController timeController;

    /**
     * FXML yüklemesi tamamlandığında otomatik çağrılır.
     * Burada başlangıç ayarları yapılabilir.
     */
    @FXML
    public void initialize() {
        System.out.println("TrafficSimulationView initialize called");

        // Örnek araçlar eklenebilir ya da initSimulation ile başlanabilir.
        // Aşağıda örnek 3 araç oluşturuluyor ve görünüme ekleniyor:
        for (int i = 0; i < 3; i++) {
            AbstractVehicle vehicle = random.nextBoolean()
                    ? new Car(Direction.SOUTH)
                    : new Truck(Direction.SOUTH);

            vehicle.setX(100 + i * 60);
            vehicle.setY(600);

            vehicles.add(vehicle);
            vehicleLayer.getChildren().add(vehicle);

            // Eğer hareket metodu varsa çalıştır
            vehicle.move();
        }
    }

    /**
     * Dışarıdan gelen yoğunluk haritasına göre araçları oluşturur ve sahneye ekler.
     * @param densities Yön ve o yöne ait araç sayısı
     */
    public void initSimulation(Map<Direction, Integer> densities) {
        System.out.println("Yoğunluk bilgileri alındı: " + densities);

        // Araçları oluşturup başlangıç pozisyonlarına yerleştiriyoruz
        for (Map.Entry<Direction, Integer> entry : densities.entrySet()) {
            Direction dir = entry.getKey();
            int count = entry.getValue();

            for (int i = 0; i < count; i++) {
                AbstractVehicle vehicle = (random.nextBoolean())
                        ? new Car(dir)
                        : new Truck(dir);

                // Yönlere göre başlangıç pozisyonları
                double startX = getStartX(dir);
                double startY = getStartY(dir);

                // Aynı yöne gelen araçları biraz kaydırarak yan yana dizelim
                if (dir == Direction.NORTH || dir == Direction.SOUTH) {
                    vehicle.setX(startX + i * 30);
                    vehicle.setY(startY);
                } else { // EAST ve WEST
                    vehicle.setX(startX);
                    vehicle.setY(startY + i * 30);
                }

                vehicles.add(vehicle);
                vehicleLayer.getChildren().add(vehicle);

                // Hareket başlat (isteğe bağlı)
                vehicle.move();
            }
        }
    }

    /**
     * Yönlere göre araçların başlangıç X koordinatlarını döner.
     * @param dir Araç yönü
     * @return Başlangıç X koordinatı
     */
    private double getStartX(Direction dir) {
        return switch (dir) {
            case NORTH, SOUTH -> 300;
            case EAST -> 0;
            case WEST -> 600;
        };
    }

    /**
     * Yönlere göre araçların başlangıç Y koordinatlarını döner.
     * @param dir Araç yönü
     * @return Başlangıç Y koordinatı
     */
    private double getStartY(Direction dir) {
        return switch (dir) {
            case EAST, WEST -> 300;
            case NORTH -> 600;
            case SOUTH -> 0;
        };
    }

    /**
     * Simülasyonun her adımında çağrılacak güncelleme metodu.
     * Örnek olarak araçların pozisyonu güncellenir.
     * Bu metodu zamanlayıcı veya animation timer ile periyodik çağırabilirsiniz.
     */
    public void updateSimulation() {
        // Araçların hareketi burada kontrol edilir
        for (AbstractVehicle vehicle : vehicles) {
            vehicle.move();
            // İsterseniz araçların hızlarını, konumlarını buradan da güncelleyebilirsiniz.
        }
    }

    /**
     * Simülasyon sıfırlama metodu, tüm araçları başlangıç pozisyonuna döndürür.
     */
    public void resetSimulation() {
        for (AbstractVehicle vehicle : vehicles) {
            Direction dir = vehicle.getDirection();

            // Başlangıç koordinatlarını tekrar ata
            vehicle.setX(getStartX(dir));
            vehicle.setY(getStartY(dir));

            // Hareketi durdurabilir veya resetleyebilirsiniz
            vehicle.stop();
        }
    }

    /**
     * Dışarıdan sahne (Stage) referansı alır.
     * @param stage JavaFX Stage nesnesi
     */
    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    /**
     * Simülasyon görünümünü yükler ve gösterir.
     *
     * @param densities Yönlere göre araç sayıları
     * @param primaryStage JavaFX Stage nesnesi
     */
    public void loadSimulationView(Map<Direction, Integer> densities, Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/trafficlight/view/TrafficSimulationView.fxml"));
            Parent root = loader.load();

            TrafficSimulationView simulationController = loader.getController();

            // Stage ve simülasyon başlatma
            simulationController.setPrimaryStage(primaryStage);
            simulationController.initSimulation(densities);

            Scene scene = new Scene(root);
            primaryStage.setTitle("Trafik Simülasyonu");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * NOT:
     * 1) AbstractVehicle, Car, Truck sınıflarınızın JavaFX Node veya Parent'dan türemesi gerekir.
     * 2) FXML dosyanızda 'root', 'vehicleLayer', 'roadGroup' gibi elementlerin tanımlı olması zorunludur.
     * 3) Araç hareket mantığını move() ve stop() metodlarında yönetmelisiniz.
     * 4) Simülasyonun gerçek zamanlı çalışması için Timeline veya AnimationTimer kullanarak updateSimulation() metodunu çağırabilirsiniz.
     * 5) Bu sınıf aynı zamanda simülasyonun giriş noktası olarak düşünülebilir, sahne yükleme ve araç yaratma işlemlerini içerir.
     */

}
