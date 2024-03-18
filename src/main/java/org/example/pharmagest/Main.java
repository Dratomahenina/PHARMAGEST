package org.example.pharmagest;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.pharmagest.controller.DashboardMenuController;
import org.example.pharmagest.utils.DatabaseConnection;

import java.io.IOException;
import java.sql.*;

public class Main extends Application {
    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/pharmagest/login.fxml"));
        Parent root = loader.load();
        usernameField = (TextField) loader.getNamespace().get("usernameField");
        passwordField = (PasswordField) loader.getNamespace().get("passwordField");
        loginButton = (Button) loader.getNamespace().get("loginButton");
        loginButton.setOnAction(e -> onLoginButtonClick());

        primaryStage.setTitle("PHARMAGEST - Connexion");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @FXML
    private void onLoginButtonClick() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        int id_utilisateur = authenticateUser(username, password);
        System.out.println("ID de l'utilisateur après authentification : " + id_utilisateur);
        if (id_utilisateur > 0) {
            openDashboard(id_utilisateur);
        } else {
            System.out.println("Nom d'utilisateur ou mot de passe incorrect.");
        }
    }

    private void openDashboard(int id_utilisateur) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/pharmagest/dashboard_menu.fxml"));
            Parent root = fxmlLoader.load();

            DashboardMenuController dashboardController = fxmlLoader.getController();
            dashboardController.setId_utilisateur(id_utilisateur);

            Scene scene = new Scene(root);
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Tableau de bord");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int authenticateUser(String username, String password) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT id_utilisateur FROM utilisateurs WHERE nom_utilisateur = ? AND mot_de_passe = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id_utilisateur = rs.getInt("id_utilisateur");
                System.out.println("ID de l'utilisateur récupéré : " + id_utilisateur);
                return id_utilisateur;
            } else {
                System.out.println("Aucun utilisateur trouvé avec les informations d'identification fournies.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void main(String[] args) {
        launch(args);
    }
}