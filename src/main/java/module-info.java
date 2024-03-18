module org.example.pharmagest {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;


    opens org.example.pharmagest to javafx.fxml;
    exports org.example.pharmagest;
    exports org.example.pharmagest.controller;
    opens org.example.pharmagest.controller to javafx.fxml;
    exports org.example.pharmagest.utils;
    opens org.example.pharmagest.utils to javafx.fxml;
}