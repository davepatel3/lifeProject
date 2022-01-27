module com.example.life {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.life to javafx.fxml;
    exports com.example.life;
}