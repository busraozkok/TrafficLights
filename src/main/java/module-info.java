module com.example.trafficlightproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens com.example.trafficlightproject.traffic.controller to javafx.fxml;
    opens com.example.trafficlightproject.traffic to javafx.graphics;
    opens com.example.trafficlightproject to javafx.fxml;
    opens com.example.trafficlightproject.traffic.view to javafx.fxml;


    exports com.example.trafficlightproject.traffic;
    exports com.example.trafficlightproject;
    exports com.example.trafficlightproject.traffic.controller;
    exports com.example.trafficlightproject.traffic.view;
}