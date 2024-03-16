package org.example.pharmagest;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class MainController {
    private static final String DB_URL = "jdbc:postgresql://localhost:5433/pharmagest";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "Pazerty11";

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    protected void onLoginButtonClick() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        int id_utilisateur = authenticateUser(username, password);
        if (id_utilisateur > 0) {
            // Authentification réussie, ouvrir la fenêtre suivante (dashboard par exemple)
            openDashboard(id_utilisateur);
        } else {
            // Afficher un message d'erreur en cas d'échec de l'authentification
            System.out.println("Nom d'utilisateur ou mot de passe incorrect.");
        }
    }

    private void openDashboard(int id_utilisateur) {
        try {
            // Charger le fichier FXML du tableau de bord
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dashboard_menu.fxml"));
            Parent root = fxmlLoader.load();

            DashboardMenuController dashboardController = fxmlLoader.getController();
            dashboardController.setId_utilisateur(id_utilisateur);

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Obtenir la fenêtre actuelle
            Stage stage = (Stage) loginButton.getScene().getWindow();

            // Afficher la nouvelle scène dans une nouvelle fenêtre
            stage.setScene(scene);
            stage.setTitle("Tableau de bord");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int authenticateUser(String username, String password) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT id_utilisateur FROM utilisateurs WHERE nom_utilisateur = ? AND mot_de_passe = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_utilisateur");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}