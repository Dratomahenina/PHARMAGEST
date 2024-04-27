package org.example.pharmagest.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.pharmagest.dao.VenteDAO;
import org.example.pharmagest.model.Vente;

public class CommandesPayeesController {

    @FXML
    private TableView<Vente> commandesPayeesTableView;
    @FXML
    private TableColumn<Vente, Integer> idVenteColumn;
    @FXML
    private TableColumn<Vente, String> dateVenteColumn;
    @FXML
    private TableColumn<Vente, String> datePaiementColumn;
    @FXML
    private TableColumn<Vente, Double> montantTotalColumn;

    private VenteDAO venteDAO;

    public void setVenteDAO(VenteDAO venteDAO) {
        this.venteDAO = venteDAO;
    }

    @FXML
    public void initialize() {
        idVenteColumn.setCellValueFactory(new PropertyValueFactory<>("idVente"));
        dateVenteColumn.setCellValueFactory(cellData -> cellData.getValue().dateVenteProperty().asString());
        datePaiementColumn.setCellValueFactory(cellData -> cellData.getValue().datePaiementProperty().asString());
        montantTotalColumn.setCellValueFactory(new PropertyValueFactory<>("montantTotal"));

        ObservableList<Vente> ventesPayees = FXCollections.observableArrayList(venteDAO.getVentesPayees());
        commandesPayeesTableView.setItems(ventesPayees);
    }
}