package org.example.pharmagest;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/pharmagest/login.fxml"));
        Parent root = loader.load();
        // Ajoutez cette ligne pour importer l'image
        Image icon = new Image(getClass().getResourceAsStream("assets/logo.png"));        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("PHARMAGEST - Connexion");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}