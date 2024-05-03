package org.example.pharmagest.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import org.example.pharmagest.dao.VenteDAO;
import org.example.pharmagest.model.LigneVente;
import org.example.pharmagest.model.Vente;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class CommandesPayeesController {

    @FXML
    private TableView<Vente> commandesPayeesTableView;
    @FXML
    private TableColumn<Vente, Integer> idVenteColumn;
    @FXML
    private TableColumn<Vente, String> nomClientColumn;
    @FXML
    private TableColumn<Vente, String> prenomClientColumn;
    @FXML
    private TableColumn<Vente, String> typeVenteColumn;
    @FXML
    private TableColumn<Vente, Double> montantTotalColumn;
    @FXML
    private TableColumn<Vente, String> medicamentsColumn;
    @FXML
    private TableColumn<Vente, LocalDate> datePaiementColumn;
    @FXML
    private TableColumn<Vente, Void> actionColumn;

    private VenteDAO venteDAO;

    public void initialize() {
        venteDAO = new VenteDAO();

        idVenteColumn.setCellValueFactory(new PropertyValueFactory<>("idVente"));
        nomClientColumn.setCellValueFactory(cellData -> cellData.getValue().getClient() != null ? cellData.getValue().getClient().nomClientProperty() : null);
        prenomClientColumn.setCellValueFactory(cellData -> cellData.getValue().getClient() != null ? cellData.getValue().getClient().prenomClientProperty() : null);
        typeVenteColumn.setCellValueFactory(new PropertyValueFactory<>("typeVente"));
        montantTotalColumn.setCellValueFactory(new PropertyValueFactory<>("montantTotal"));
        medicamentsColumn.setCellValueFactory(cellData -> {
            StringBuilder medicaments = new StringBuilder();
            for (LigneVente ligneVente : cellData.getValue().getLignesVente()) {
                medicaments.append(ligneVente.getMedicament().getNomMedicament()).append(", ");
            }
            return new SimpleStringProperty(medicaments.toString().trim().replaceAll(", $", ""));
        });
        datePaiementColumn.setCellValueFactory(new PropertyValueFactory<>("datePaiement"));

        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button ouvrirFactureButton = new Button("Ouvrir Facture");
            private final Button supprimerButton = new Button("Supprimer");

            {
                ouvrirFactureButton.setOnAction(event -> {
                    Vente vente = getTableView().getItems().get(getIndex());
                    ouvrirFacture(vente);
                });

                supprimerButton.setOnAction(event -> {
                    Vente vente = getTableView().getItems().get(getIndex());
                    handleSupprimerVente(vente);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new HBox(ouvrirFactureButton, supprimerButton));
                }
            }
        });

        refreshCommandesPayeesTableView();
    }

    private void refreshCommandesPayeesTableView() {
        ObservableList<Vente> ventesPayees = FXCollections.observableArrayList(venteDAO.getVentesPayees());
        commandesPayeesTableView.setItems(ventesPayees);
    }

    private void handleSupprimerVente(Vente vente) {
        venteDAO.deleteVentePayee(vente);
        refreshCommandesPayeesTableView();
    }

    private void ouvrirFacture(Vente vente) {
        String filePath = "Factures/facture_" + vente.getIdVente() + ".pdf";
        try {
            Desktop.getDesktop().open(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}