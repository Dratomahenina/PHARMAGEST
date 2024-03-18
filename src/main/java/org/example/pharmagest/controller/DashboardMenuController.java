package org.example.pharmagest.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.pharmagest.utils.DatabaseConnection;

public class DashboardMenuController {

    @FXML
    private Button btnLogout;

    @FXML
    private Label lblUser;

    @FXML
    private Label lblHeure;

    @FXML
    private Button btnDash;

    @FXML
    private Button btnClient;

    @FXML
    private Button btnVente;

    @FXML
    private Button btnCaisse;

    @FXML
    private Button btnMedicament;

    @FXML
    private Button btnFournisseur;

    @FXML
    private Button btnApprovisionnement;

    @FXML
    private Button btnUtilisateur;

    @FXML
    private BorderPane contentPane;

    // Méthode appelée lors du clic sur le bouton de déconnexion
    @FXML
    private void onLogoutClick() {
        try {
            // Charger le fichier FXML de la page de connexion
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/pharmagest/login.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la page de connexion
            Scene scene = new Scene(root);

            // Obtenir la référence de la fenêtre principale
            Stage stage = (Stage) btnLogout.getScene().getWindow();

            // Changer la scène pour afficher la page de connexion
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Méthodes appelées lors du clic sur les boutons du menu
    @FXML
    private void onDashClick() {
        loadInterface("dashboard");
    }

    @FXML
    private void onClientClick() {
        loadInterface("client");
    }

    @FXML
    private void onVenteClick() {
        loadInterface("vente");
    }

    @FXML
    private void onCaisseClick() {
        loadInterface("caisse");
    }

    @FXML
    private void onMedicamentClick() {
        loadInterface("medicament");
    }

    @FXML
    private void onFournisseurClick() {
        loadInterface("fournisseur");
    }

    @FXML
    private void onApprovisionnementClick() {
        loadInterface("approvisionnement");
    }

    @FXML
    private void onUtilisateurClick() {
        loadInterface("utilisateur");
    }

    private int id_utilisateur;

    public void setId_utilisateur(int id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
        loadUserInfo();
    }

    public void initialize() {
        // Créer un Timeline pour mettre à jour l'heure et la date en temps réel
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            LocalDateTime now = LocalDateTime.now();
            lblHeure.setText(now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        // System.out.println("ID de l'utilisateur dans initialize : " + id_utilisateur);

        // Récupérer les informations de l'utilisateur connecté depuis la base de données
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT nom_utilisateur FROM utilisateurs WHERE id_utilisateur = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id_utilisateur);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String nomUtilisateur = rs.getString("nom_utilisateur");
                System.out.println("Nom d'utilisateur récupéré : " + nomUtilisateur);
                lblUser.setText("Bienvenue, " + nomUtilisateur);
            } else {
                System.out.println("Aucun utilisateur trouvé avec l'ID : " + id_utilisateur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        // Charger dashboard.fxml au démarrage
        loadInterface("dashboard");
    }

    private void loadInterface(String name) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/pharmagest/" + name + ".fxml"));
            contentPane.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUserInfo() {
        System.out.println("ID de l'utilisateur dans loadUserInfo : " + id_utilisateur);

        // Récupérer les informations de l'utilisateur connecté depuis la base de données
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT nom_utilisateur FROM utilisateurs WHERE id_utilisateur = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id_utilisateur);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String nomUtilisateur = rs.getString("nom_utilisateur");
                System.out.println("Nom d'utilisateur récupéré : " + nomUtilisateur);
                lblUser.setText("Bienvenue, " + nomUtilisateur);
            } else {
                System.out.println("Aucun utilisateur trouvé avec l'ID : " + id_utilisateur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}