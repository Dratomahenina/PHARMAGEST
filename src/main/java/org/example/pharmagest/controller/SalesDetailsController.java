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

public class SalesDetailsController {

    @FXML
    private ChoiceBox<String> periodChoiceBox;

    @FXML
    private TableView<Vente> salesTable;

    @FXML
    private TableColumn<Vente, LocalDate> dateColumn;

    @FXML
    private TableColumn<Vente, Double> totalAmountColumn;

    private VenteDAO venteDAO;

    public void initialize() {
        periodChoiceBox.setItems(FXCollections.observableArrayList("Journalier", "Hebdomadaire", "Mensuel", "Annuel", "Total"));
        periodChoiceBox.setValue("Journalier");

        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateVente"));
        totalAmountColumn.setCellValueFactory(new PropertyValueFactory<>("montantTotal"));

        periodChoiceBox.setOnAction(event -> updateSalesTable());
    }

    public void setVenteDAO(VenteDAO venteDAO) {
        this.venteDAO = venteDAO;
        updateSalesTable();
    }

    private void updateSalesTable() {
        String selectedPeriod = periodChoiceBox.getValue();
        salesTable.setItems(FXCollections.observableArrayList(venteDAO.getVentesByPeriod(selectedPeriod)));
    }
}