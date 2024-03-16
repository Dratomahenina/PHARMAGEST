module org.example.pharmagest {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;


    opens org.example.pharmagest to javafx.fxml;
    exports org.example.pharmagest;
}