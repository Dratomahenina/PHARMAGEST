package org.example.pharmagest.controller;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.pharmagest.utils.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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

        // Ajoutez les gestionnaires d'événements pour les champs de saisie
        usernameField.setOnKeyPressed(this::handleKeyPressed);
        passwordField.setOnKeyPressed(this::handleKeyPressed);
    }

    @FXML
    private Label errorLabel;

    private void showErrorMessage(String message) {
        errorLabel.setText(message);
    }

    @FXML
    private StackPane loadingPane;

    @FXML
    private VBox loginContent;

    @FXML
    private ProgressIndicator progressIndicator;

    private void showLoadingIndicator() {
        loginContent.setEffect(new GaussianBlur(10));
        loadingPane.setVisible(true);
        progressIndicator.setProgress(-1);
    }

    private void hideLoadingIndicator() {
        loginContent.setEffect(null);
        loadingPane.setVisible(false);
        progressIndicator.setProgress(0);
    }

    @FXML
    private void onLoginButtonClick() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs manquants");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }

        showLoadingIndicator();

        Task<Integer> loginTask = new Task<>() {
            @Override
            protected Integer call() throws Exception {
                int id_utilisateur = authenticateUser(username, password);
                return id_utilisateur;
            }
        };

        loginTask.setOnSucceeded(event -> {
            int id_utilisateur = loginTask.getValue();
            System.out.println("ID de l'utilisateur après authentification : " + id_utilisateur);
            if (id_utilisateur > 0) {
                String userRole = getUserRole(id_utilisateur);
                logUserLogin(id_utilisateur, username, userRole);
                openDashboard(id_utilisateur, userRole);
            } else {
                hideLoadingIndicator();
                System.out.println("Nom d'utilisateur ou mot de passe incorrect.");
            }
        });

        loginTask.setOnFailed(event -> {
            hideLoadingIndicator();
            System.out.println("Erreur lors de l'authentification.");
        });

        Thread loginThread = new Thread(loginTask);
        loginThread.start();
    }

    private void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            onLoginButtonClick();
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

            // Masquer le loading une fois le tableau de bord chargé
            stage.setOnShown(e -> hideLoadingIndicator());

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

    private String getUserRole(int id_utilisateur) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT role FROM utilisateurs WHERE id_utilisateur = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id_utilisateur);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String userRole = rs.getString("role");
                System.out.println("Rôle de l'utilisateur récupéré : " + userRole);
                return userRole;
            } else {
                System.out.println("Aucun utilisateur trouvé avec l'ID : " + id_utilisateur);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private void logUserLogin(int userId, String username, String role) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO login_historique (id_utilisateur, nom_utilisateur, role, date_heure) " +
                    "VALUES (?, ?, ?, CURRENT_TIMESTAMP) " +
                    "ON CONFLICT (id_utilisateur) DO UPDATE SET date_heure = CURRENT_TIMESTAMP";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            stmt.setString(2, username);
            stmt.setString(3, role);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}