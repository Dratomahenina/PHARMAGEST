package org.example.pharmagest.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.pharmagest.dao.VenteDAO;
import org.example.pharmagest.model.LigneVente;
import org.example.pharmagest.model.Vente;
import org.example.pharmagest.utils.FactureGenerator;

import java.io.IOException;

public class CaisseController {

    @FXML
    private TableView<Vente> venteTableView;
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
    private TableColumn<Vente, String> statutColumn;
    @FXML
    private TableColumn<Vente, Void> actionColumn;
    @FXML
    private TableView<LigneVente> detailsCommandeTableView;
    @FXML
    private TableColumn<LigneVente, String> medicamentColumn;
    @FXML
    private TableColumn<LigneVente, Integer> quantiteColumn;
    @FXML
    private TableColumn<LigneVente, Double> prixUnitaireColumn;
    @FXML
    private Label montantTotalLabel;
    @FXML
    private TextField montantDonneTextField;
    @FXML
    private Label montantRenduLabel;

    private VenteDAO venteDAO;

    @FXML
    private void handleRefresh() {
        refreshVenteTableView();
    }

    @FXML
    public void initialize() {
        venteDAO = new VenteDAO();

        idVenteColumn.setCellValueFactory(new PropertyValueFactory<>("idVente"));
        nomClientColumn.setCellValueFactory(cellData -> cellData.getValue().getClient() != null ? cellData.getValue().getClient().nomClientProperty() : null);
        prenomClientColumn.setCellValueFactory(cellData -> cellData.getValue().getClient() != null ? cellData.getValue().getClient().prenomClientProperty() : null);
        typeVenteColumn.setCellValueFactory(new PropertyValueFactory<>("typeVente"));
        montantTotalColumn.setCellValueFactory(new PropertyValueFactory<>("montantTotal"));
        statutColumn.setCellValueFactory(new PropertyValueFactory<>("statut"));
        medicamentColumn.setCellValueFactory(cellData -> cellData.getValue().getMedicament().nomMedicamentProperty());
        quantiteColumn.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        prixUnitaireColumn.setCellValueFactory(new PropertyValueFactory<>("prixUnitaire"));

        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button supprimerButton = new Button("Supprimer");

            {
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
                    setGraphic(supprimerButton);
                }
            }
        });

        refreshVenteTableView();

        venteTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                refreshVenteDetails(newValue);
            }
        });
    }

    private void refreshVenteTableView() {
        ObservableList<Vente> ventes = FXCollections.observableArrayList(venteDAO.getAllVentesEnAttente());
        venteTableView.setItems(ventes);
    }

    private void refreshVenteDetails(Vente vente) {
        montantTotalLabel.setText(String.format("%.2f", vente.getMontantTotal()));
        detailsCommandeTableView.setItems(FXCollections.observableArrayList(vente.getLignesVente()));
    }

    @FXML
    private void handleValiderPaiement() {
        Vente selectedVente = venteTableView.getSelectionModel().getSelectedItem();
        if (selectedVente != null && selectedVente.getStatut().equals("En attente")) {
            String montantDonneString = montantDonneTextField.getText();
            if (!montantDonneString.isEmpty()) {
                double montantDonne = Double.parseDouble(montantDonneString);
                double montantRendu = montantDonne - selectedVente.getMontantTotal();
                montantRenduLabel.setText(String.format("%.2f", montantRendu));

                selectedVente.setStatut("Payée");
                venteDAO.updateVente(selectedVente);
                venteDAO.enregistrerVentePayee(selectedVente);

                // Génération de la facture
                String filePath = "Factures/facture_" + selectedVente.getIdVente() + ".pdf";
                FactureGenerator.generateFacture(selectedVente, filePath);

                refreshVenteTableView();
                montantDonneTextField.clear();
            } else {
                // Afficher un message d'erreur ou une alerte
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Montant invalide");
                alert.setContentText("Veuillez saisir un montant valide.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void handleCommandesPayees() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/pharmagest/commandespayees.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Commandes Payées");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSupprimerVente(Vente vente) {
        if (vente != null && vente.getStatut().equals("En attente")) {
            venteDAO.deleteVente(vente);
            refreshVenteTableView();
        }
    }
}