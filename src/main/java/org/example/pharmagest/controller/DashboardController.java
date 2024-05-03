package org.example.pharmagest.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.pharmagest.dao.VenteDAO;
import org.example.pharmagest.dao.MedicamentDAO;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DashboardController {

    @FXML
    private Label dateLabel;

    @FXML
    private Label dailySalesLabel;

    @FXML
    private Label dailyProfitLabel;

    @FXML
    private Label stockAlertLabel;

    @FXML
    private LineChart<String, Number> salesChart;

    @FXML
    private BarChart<String, Number> profitChart;

    private VenteDAO venteDAO;
    private MedicamentDAO medicamentDAO;

    public void initialize() {
        venteDAO = new VenteDAO();
        medicamentDAO = new MedicamentDAO();

        // Afficher la date actuelle
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = currentDate.format(formatter);
        dateLabel.setText("Date: " + formattedDate);

        // Afficher les ventes journalières
        double dailySales = venteDAO.getDailySales();
        dailySalesLabel.setText(String.format("%.2f Rs", dailySales));

        // Afficher les bénéfices journaliers
        double dailyProfit = venteDAO.getDailyProfit();
        dailyProfitLabel.setText(String.format("%.2f Rs", dailyProfit));

        // Afficher le nombre d'alertes d'approvisionnement
        int stockAlerts = medicamentDAO.getStockAlerts(30);
        stockAlertLabel.setText(String.valueOf(stockAlerts));

        // Afficher le graphique de progression des ventes
        XYChart.Series<String, Number> salesSeries = new XYChart.Series<>();
        salesSeries.setName("Ventes");
        // Ajouter les données des ventes par jour, semaine, mois ou année
        salesSeries.getData().add(new XYChart.Data<>("01/05/2024", 5000));
        salesSeries.getData().add(new XYChart.Data<>("02/05/2024", 7500));
        salesSeries.getData().add(new XYChart.Data<>("03/05/2024", 6000));
        salesChart.getData().add(salesSeries);

        // Afficher le graphique de progression des bénéfices
        XYChart.Series<String, Number> profitSeries = new XYChart.Series<>();
        profitSeries.setName("Bénéfices");
        // Ajouter les données des bénéfices par jour, semaine, mois ou année
        profitSeries.getData().add(new XYChart.Data<>("01/05/2024", 2000));
        profitSeries.getData().add(new XYChart.Data<>("02/05/2024", 3500));
        profitSeries.getData().add(new XYChart.Data<>("03/05/2024", 2800));
        profitChart.getData().add(profitSeries);
    }

    @FXML
    private void showSalesDetails() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/pharmagest/salesdetails.fxml"));
            Parent root = loader.load();
            SalesDetailsController salesDetailsController = loader.getController();
            salesDetailsController.setVenteDAO(venteDAO);

            Stage stage = new Stage();
            stage.setTitle("Détails des Ventes");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showProfitDetails() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/pharmagest/profitdetails.fxml"));
            Parent root = loader.load();
            ProfitDetailsController profitDetailsController = loader.getController();
            profitDetailsController.setVenteDAO(venteDAO);

            Stage stage = new Stage();
            stage.setTitle("Détails des Bénéfices");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showSupplyAlerts() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/pharmagest/approvisionnement.fxml"));
            Parent root = loader.load();
            ApprovisionnementController approvisionnementController = loader.getController();
            approvisionnementController.initialize(); // Appel à la méthode initialize() d'ApprovisionnementController

            Stage stage = new Stage();
            stage.setTitle("Approvisionnement");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}