package org.example.pharmagest.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.pharmagest.model.User;
import org.example.pharmagest.utils.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private void initialize() {
        loginButton.setOnAction(e -> onLoginButtonClick());
    }

    @FXML
    private void onLoginButtonClick() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = authenticateUser(username, password);
        if (user != null) {
            // Insérer manuellement un enregistrement dans la table "utilisateurs"
            try (Connection conn = DatabaseConnection.getConnection()) {
                String query = "INSERT INTO utilisateurs (nom_utilisateur, mot_de_passe, role) VALUES (?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, user.getUsername());
                stmt.setString(2, password);
                stmt.setString(3, user.getRole());
                stmt.executeUpdate();
                System.out.println("Enregistrement inséré dans la table utilisateurs");
            } catch (Exception e) {
                e.printStackTrace();
            }

            openDashboard(user.getId(), user.getRole());
        } else {
            System.out.println("Nom d'utilisateur ou mot de passe incorrect.");
        }
    }

    private void openDashboard(int id_utilisateur, String userRole) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/pharmagest/dashboard_menu.fxml"));
            Parent root = fxmlLoader.load();

            DashboardMenuController dashboardController = fxmlLoader.getController();
            dashboardController.setId_utilisateur(id_utilisateur);
            dashboardController.setUserRole(userRole);

            Scene scene = new Scene(root);
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Tableau de bord");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private User authenticateUser(String username, String password) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT id_utilisateur, nom_utilisateur, role FROM utilisateurs WHERE nom_utilisateur = ? AND mot_de_passe = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("id_utilisateur");
                String nomUtilisateur = rs.getString("nom_utilisateur");
                String userRole = rs.getString("role");
                System.out.println("Utilisateur authentifié : ID = " + userId + ", Nom d'utilisateur = " + nomUtilisateur + ", Rôle = " + userRole);
                return new User(userId, nomUtilisateur, userRole);
            } else {
                System.out.println("Aucun utilisateur trouvé avec les informations d'identification fournies.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}