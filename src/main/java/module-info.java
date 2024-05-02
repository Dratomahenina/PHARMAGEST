module org.example.pharmagest {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires itextpdf;
    requires javafx.web;
    requires java.desktop;

    opens org.example.pharmagest to javafx.fxml;
    exports org.example.pharmagest;

    exports org.example.pharmagest.controller;
    opens org.example.pharmagest.controller to javafx.fxml;

    exports org.example.pharmagest.utils;
    opens org.example.pharmagest.utils to javafx.fxml;

    exports org.example.pharmagest.model;
    opens org.example.pharmagest.model to javafx.base;
}