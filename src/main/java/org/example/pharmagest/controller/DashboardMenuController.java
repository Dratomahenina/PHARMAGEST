package org.example.pharmagest.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
    private Button btnFamille;

    @FXML
    private Button btnForme;

    @FXML
    private BorderPane contentPane;

    private int id_utilisateur;
    private String userRole;

    public void setId_utilisateur(int id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
        loadUserInfo();
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
        updateMenuVisibility();
    }

    @FXML
    private void onLogoutClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/pharmagest/login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) btnLogout.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
    private void onFamilleClick() {
        loadInterface("famille");
    }

    @FXML
    private void onFormeClick() {
        loadInterface("forme");
    }

    @FXML
    private void onApprovisionnementClick() {
        loadInterface("approvisionnement");
    }

    @FXML
    private void onUtilisateurClick() {
        loadInterface("utilisateur");
    }

    public void initialize() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            LocalDateTime now = LocalDateTime.now();
            lblHeure.setText(now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        loadInterface("dashboard");
    }

    private void loadUserInfo() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT nom_utilisateur, role FROM utilisateurs WHERE id_utilisateur = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id_utilisateur);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String nomUtilisateur = rs.getString("nom_utilisateur");
                String role = rs.getString("role");
                System.out.println("Nom d'utilisateur récupéré : " + nomUtilisateur);
                System.out.println("Rôle de l'utilisateur : " + role);
                lblUser.setText("Bienvenue " + nomUtilisateur + ", " +role);
                setUserRole(role);
            } else {
                System.out.println("Aucun utilisateur trouvé avec l'ID : " + id_utilisateur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateMenuVisibility() {
        if (userRole != null) {
            if (userRole.equals("admin")) {
                btnDash.setVisible(true);
                btnClient.setVisible(true);
                btnVente.setVisible(true);
                btnCaisse.setVisible(true);
                btnMedicament.setVisible(true);
                btnFournisseur.setVisible(true);
                btnApprovisionnement.setVisible(true);
                btnUtilisateur.setVisible(true);
                btnFamille.setVisible(true);
                btnForme.setVisible(true);
            } else if (userRole.equals("vendeur")) {
                btnDash.setVisible(true);
                btnClient.setVisible(false);
                btnVente.setVisible(true);
                btnCaisse.setVisible(false);
                btnMedicament.setVisible(false);
                btnFournisseur.setVisible(false);
                btnApprovisionnement.setVisible(false);
                btnUtilisateur.setVisible(false);
                btnFamille.setVisible(false);
                btnForme.setVisible(false);
            } else if (userRole.equals("caissier")) {
                btnDash.setVisible(true);
                btnClient.setVisible(false);
                btnVente.setVisible(false);
                btnCaisse.setVisible(true);
                btnMedicament.setVisible(false);
                btnFournisseur.setVisible(false);
                btnApprovisionnement.setVisible(false);
                btnUtilisateur.setVisible(false);
                btnFamille.setVisible(false);
                btnForme.setVisible(false);
            }
        }
    }

    private void loadInterface(String name) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/pharmagest/" + name + ".fxml"));
            contentPane.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showCalendar(MouseEvent event) {
        DatePicker datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());

        Popup popup = new Popup();
        popup.setAutoHide(true);
        popup.setAnchorLocation(PopupWindow.AnchorLocation.CONTENT_TOP_LEFT);

        StackPane content = new StackPane(datePicker);
        content.setPrefSize(200, 200);
        popup.getContent().add(content);

        popup.show(lblHeure, event.getScreenX(), event.getScreenY());
    }
}