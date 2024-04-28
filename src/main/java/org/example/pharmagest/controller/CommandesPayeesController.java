package org.example.pharmagest.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.example.pharmagest.dao.CaisseDAO;
import org.example.pharmagest.model.Vente;
import org.example.pharmagest.model.VenteMedicament;

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
    private TableColumn<Vente, String> nomMedicamentColumn;
    @FXML
    private TableColumn<Vente, String> quantiteColumn;
    @FXML
    private TableColumn<Vente, String> prixUnitaireColumn;
    @FXML
    private TableColumn<Vente, Double> totalColumn;
    @FXML
    private TableColumn<Vente, String> typeVenteColumn;
    @FXML
    private TableColumn<Vente, String> datePaiementColumn;
    @FXML
    private TableColumn<Vente, Void> actionColumn;

    private CaisseDAO caisseDAO;

    public CommandesPayeesController() {
        this.caisseDAO = new CaisseDAO();
    }

    @FXML
    public void initialize() {
        idVenteColumn.setCellValueFactory(new PropertyValueFactory<>("idVente"));

        nomClientColumn.setCellValueFactory(cellData -> cellData.getValue().getClient().nomClientProperty());
        prenomClientColumn.setCellValueFactory(cellData -> cellData.getValue().getClient().prenomClientProperty());
        nomMedicamentColumn.setCellValueFactory(cellData -> {
            StringBuilder medicamentsString = new StringBuilder();
            for (VenteMedicament venteMedicament : cellData.getValue().getMedicaments()) {
                medicamentsString.append(venteMedicament.getMedicament().getNomMedicament()).append("\n");
            }
            return new SimpleStringProperty(medicamentsString.toString().trim());
        });
        quantiteColumn.setCellValueFactory(cellData -> {
            StringBuilder quantitesString = new StringBuilder();
            for (VenteMedicament venteMedicament : cellData.getValue().getMedicaments()) {
                quantitesString.append(venteMedicament.getQuantite()).append("\n");
            }
            return new SimpleStringProperty(quantitesString.toString().trim());
        });
        prixUnitaireColumn.setCellValueFactory(cellData -> {
            StringBuilder prixUnitairesString = new StringBuilder();
            for (VenteMedicament venteMedicament : cellData.getValue().getMedicaments()) {
                prixUnitairesString.append(venteMedicament.getPrixUnitaire()).append("\n");
            }
            return new SimpleStringProperty(prixUnitairesString.toString().trim());
        });
        totalColumn.setCellValueFactory(cellData -> cellData.getValue().montantTotalProperty().asObject());
        typeVenteColumn.setCellValueFactory(cellData -> cellData.getValue().typeVenteProperty());
        datePaiementColumn.setCellValueFactory(cellData -> cellData.getValue().datePaiementProperty().asString());

        actionColumn.setCellFactory(new Callback<TableColumn<Vente, Void>, TableCell<Vente, Void>>() {
            @Override
            public TableCell<Vente, Void> call(TableColumn<Vente, Void> param) {
                return new TableCell<Vente, Void>() {
                    private final Button supprimerButton = new Button("Supprimer");

                    {
                        supprimerButton.setOnAction(event -> {
                            Vente vente = getTableView().getItems().get(getIndex());
                            supprimerVente(vente);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(supprimerButton);
                        }
                    }
                };
            }
        });

        ObservableList<Vente> ventesPayees = FXCollections.observableArrayList(caisseDAO.getVentesPayees());
        commandesPayeesTableView.setItems(ventesPayees);
    }

    private void supprimerVente(Vente vente) {
        caisseDAO.supprimerVente(vente);
        ObservableList<Vente> ventesPayees = FXCollections.observableArrayList(caisseDAO.getVentesPayees());
        commandesPayeesTableView.setItems(ventesPayees);
    }
}