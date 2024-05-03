package org.example.pharmagest.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.pharmagest.dao.VenteDAO;
import org.example.pharmagest.model.Vente;

import java.time.LocalDate;

public class ProfitDetailsController {

    @FXML
    private ChoiceBox<String> periodChoiceBox;

    @FXML
    private TableView<Vente> profitTable;

    @FXML
    private TableColumn<Vente, LocalDate> dateColumn;

    @FXML
    private TableColumn<Vente, Double> profitColumn;

    private VenteDAO venteDAO;

    public void initialize() {
        periodChoiceBox.setItems(FXCollections.observableArrayList("Journalier", "Hebdomadaire", "Mensuel", "Annuel", "Total"));
        periodChoiceBox.setValue("Journalier");

        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateVente"));
        profitColumn.setCellValueFactory(new PropertyValueFactory<>("profit"));

        periodChoiceBox.setOnAction(event -> updateProfitTable());
    }

    public void setVenteDAO(VenteDAO venteDAO) {
        this.venteDAO = venteDAO;
        updateProfitTable();
    }

    private void updateProfitTable() {
        String selectedPeriod = periodChoiceBox.getValue();
        profitTable.setItems(FXCollections.observableArrayList(venteDAO.getProfitsByPeriod(selectedPeriod)));
    }
}