package org.example.pharmagest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Charger le fichier FXML depuis le répertoire resources
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/pharmagest/login.fxml"));
        primaryStage.setTitle("PHARMAGEST - Connexion");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
