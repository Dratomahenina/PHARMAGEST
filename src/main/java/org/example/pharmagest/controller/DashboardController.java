package org.example.pharmagest.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;
import org.example.pharmagest.dao.VenteDAO;
import org.example.pharmagest.dao.MedicamentDAO;
import org.example.pharmagest.model.Vente;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

        // Récupérer les données des ventes et des profits par période
        List<Vente> ventesJournalieres = venteDAO.getVentesByPeriod("Journalier");
        List<Vente> ventesHebdomadaires = venteDAO.getVentesByPeriod("Hebdomadaire");
        List<Vente> ventesMensuelles = venteDAO.getVentesByPeriod("Mensuel");
        List<Vente> ventesAnnuelles = venteDAO.getVentesByPeriod("Annuel");

        List<Vente> profitsJournaliers = venteDAO.getProfitsByPeriod("Journalier");
        List<Vente> profitsHebdomadaires = venteDAO.getProfitsByPeriod("Hebdomadaire");
        List<Vente> profitsMensuels = venteDAO.getProfitsByPeriod("Mensuel");
        List<Vente> profitsAnnuels = venteDAO.getProfitsByPeriod("Annuel");

        // Mettre à jour les diagrammes avec les données récupérées
        updateSalesChart(ventesJournalieres, ventesHebdomadaires, ventesMensuelles, ventesAnnuelles);
        updateProfitChart(profitsJournaliers, profitsHebdomadaires, profitsMensuels, profitsAnnuels);

        // Mettre à jour les diagrammes régulièrement (toutes les minutes)
        Timeline timeline = new Timeline(new KeyFrame(Duration.minutes(1), event -> {
            // Récupérer les données des ventes et des profits par période
            List<Vente> ventesJournalieresUpdated = venteDAO.getVentesByPeriod("Journalier");
            List<Vente> ventesHebdomadairesUpdated = venteDAO.getVentesByPeriod("Hebdomadaire");
            List<Vente> ventesMensuellesUpdated = venteDAO.getVentesByPeriod("Mensuel");
            List<Vente> ventesAnnuellesUpdated = venteDAO.getVentesByPeriod("Annuel");

            List<Vente> profitsJournaliersUpdated = venteDAO.getProfitsByPeriod("Journalier");
            List<Vente> profitsHebdomadairesUpdated = venteDAO.getProfitsByPeriod("Hebdomadaire");
            List<Vente> profitsMensuelsUpdated = venteDAO.getProfitsByPeriod("Mensuel");
            List<Vente> profitsAnnuelsUpdated = venteDAO.getProfitsByPeriod("Annuel");

            // Mettre à jour les diagrammes avec les données récupérées
            updateSalesChart(ventesJournalieresUpdated, ventesHebdomadairesUpdated, ventesMensuellesUpdated, ventesAnnuellesUpdated);
            updateProfitChart(profitsJournaliersUpdated, profitsHebdomadairesUpdated, profitsMensuelsUpdated, profitsAnnuelsUpdated);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void updateSalesChart(List<Vente> ventesJournalieres, List<Vente> ventesHebdomadaires,
                                  List<Vente> ventesMensuelles, List<Vente> ventesAnnuelles) {
        // Effacer les données existantes du diagramme des ventes
        salesChart.getData().clear();

        // Ajouter les données des ventes pour chaque période au diagramme
        XYChart.Series<String, Number> seriesJournalier = new XYChart.Series<>();
        seriesJournalier.setName("Journalier");
        for (Vente vente : ventesJournalieres) {
            seriesJournalier.getData().add(new XYChart.Data<>(vente.getDateVente().toString(), vente.getMontantTotal()));
        }
        salesChart.getData().add(seriesJournalier);

        XYChart.Series<String, Number> seriesHebdomadaire = new XYChart.Series<>();
        seriesHebdomadaire.setName("Hebdomadaire");
        for (Vente vente : ventesHebdomadaires) {
            seriesHebdomadaire.getData().add(new XYChart.Data<>(vente.getDateVente().toString(), vente.getMontantTotal()));
        }
        salesChart.getData().add(seriesHebdomadaire);

        XYChart.Series<String, Number> seriesMensuel = new XYChart.Series<>();
        seriesMensuel.setName("Mensuel");
        for (Vente vente : ventesMensuelles) {
            seriesMensuel.getData().add(new XYChart.Data<>(vente.getDateVente().toString(), vente.getMontantTotal()));
        }
        salesChart.getData().add(seriesMensuel);

        XYChart.Series<String, Number> seriesAnnuel = new XYChart.Series<>();
        seriesAnnuel.setName("Annuel");
        for (Vente vente : ventesAnnuelles) {
            seriesAnnuel.getData().add(new XYChart.Data<>(vente.getDateVente().toString(), vente.getMontantTotal()));
        }
        salesChart.getData().add(seriesAnnuel);
    }

    private void updateProfitChart(List<Vente> profitsJournaliers, List<Vente> profitsHebdomadaires,
                                   List<Vente> profitsMensuels, List<Vente> profitsAnnuels) {
        // Effacer les données existantes du diagramme des bénéfices
        profitChart.getData().clear();

        // Ajouter les données des bénéfices pour chaque période au diagramme
        XYChart.Series<String, Number> seriesJournalier = new XYChart.Series<>();
        seriesJournalier.setName("Journalier");
        for (Vente profit : profitsJournaliers) {
            seriesJournalier.getData().add(new XYChart.Data<>(profit.getDateVente().toString(), profit.getProfit()));
        }
        profitChart.getData().add(seriesJournalier);

        XYChart.Series<String, Number> seriesHebdomadaire = new XYChart.Series<>();
        seriesHebdomadaire.setName("Hebdomadaire");
        for (Vente profit : profitsHebdomadaires) {
            seriesHebdomadaire.getData().add(new XYChart.Data<>(profit.getDateVente().toString(), profit.getProfit()));
        }
        profitChart.getData().add(seriesHebdomadaire);

        XYChart.Series<String, Number> seriesMensuel = new XYChart.Series<>();
        seriesMensuel.setName("Mensuel");
        for (Vente profit : profitsMensuels) {
            seriesMensuel.getData().add(new XYChart.Data<>(profit.getDateVente().toString(), profit.getProfit()));
        }
        profitChart.getData().add(seriesMensuel);

        XYChart.Series<String, Number> seriesAnnuel = new XYChart.Series<>();
        seriesAnnuel.setName("Annuel");
        for (Vente profit : profitsAnnuels) {
            seriesAnnuel.getData().add(new XYChart.Data<>(profit.getDateVente().toString(), profit.getProfit()));
        }
        profitChart.getData().add(seriesAnnuel);
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