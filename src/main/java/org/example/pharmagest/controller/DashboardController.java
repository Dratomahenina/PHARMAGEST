package org.example.pharmagest.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class DashboardController {
    @FXML
    private Label lblBeneficeTotal;

    @FXML
    private Label lblNombreSeuil;

    @FXML
    private Button btnSeuil;

    @FXML
    private Button btnRefresh;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblVenteTotal;

    @FXML
    public void initialize() {
        // Initialiser les données du tableau de bord
        loadDashboardData();
    }

    @FXML
    private void btnSeuilOnAction() {
        // TODO: Implémenter l'action du bouton "Afficher" pour l'approvisionnement
    }

    @FXML
    private void btnRefreshOnAction() {
        // Rafraîchir les données du tableau de bord
        loadDashboardData();
    }

    private void loadDashboardData() {
        // TODO: Charger les données réelles depuis la base de données ou les services

        // Exemple de données fictives
        double beneficeTotal = 1000.0;
        int nombreSeuil = 5;
        String date = "14/03/2024";
        int venteTotal = 10;

        // Mettre à jour les labels avec les données
        lblBeneficeTotal.setText(String.format("%.2f Rs", beneficeTotal));
        lblNombreSeuil.setText(String.valueOf(nombreSeuil));
        lblDate.setText(date);
        lblVenteTotal.setText(String.valueOf(venteTotal));
    }
}