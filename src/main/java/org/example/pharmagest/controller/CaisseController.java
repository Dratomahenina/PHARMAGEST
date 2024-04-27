package org.example.pharmagest.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.pharmagest.dao.CaisseDAO;
import org.example.pharmagest.model.VenteMedicament;
import org.example.pharmagest.model.Vente;
import org.example.pharmagest.model.Client;
import org.example.pharmagest.utils.FacturePDF;

import java.time.LocalDateTime;
import javafx.beans.property.SimpleStringProperty;

public class CaisseController {

    @FXML
    private TableView<VenteMedicament> venteMedicamentTableView;
    @FXML
    private TableColumn<VenteMedicament, Integer> idVenteMedicamentColumn;
    @FXML
    private TableColumn<VenteMedicament, String> nomClientColumn;
    @FXML
    private TableColumn<VenteMedicament, String> prenomClientColumn;
    @FXML
    private TableColumn<VenteMedicament, String> nomMedicamentColumn;
    @FXML
    private TableColumn<VenteMedicament, Integer> quantiteColumn;
    @FXML
    private TableColumn<VenteMedicament, Double> prixUnitaireColumn;
    @FXML
    private TableColumn<VenteMedicament, Double> totalColumn;
    @FXML
    private TableColumn<VenteMedicament, String> typeVenteColumn;
    @FXML
    private TableColumn<VenteMedicament, String> statutColumn;
    @FXML
    private Label detailsCommandeLabel;
    @FXML
    private Label montantTotalLabel;
    @FXML
    private Label remiseLabel;
    @FXML
    private Label montantFinalLabel;
    @FXML
    private TextField montantDonneTextField;
    @FXML
    private Label montantRenduLabel;

    private ObservableList<VenteMedicament> venteMedicamentList;
    private CaisseDAO caisseDAO;
    private VenteMedicament venteMedicamentSelectionne;

    @FXML
    public void initialize() {
        caisseDAO = new CaisseDAO();
        venteMedicamentList = FXCollections.observableArrayList(caisseDAO.getAllVenteMedicaments());

        idVenteMedicamentColumn.setCellValueFactory(new PropertyValueFactory<>("idVenteMedicament"));
        nomClientColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getClient() != null) {
                return cellData.getValue().getClient().nomClientProperty();
            } else {
                return new SimpleStringProperty("");
            }
        });
        prenomClientColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getClient() != null) {
                return cellData.getValue().getClient().prenomClientProperty();
            } else {
                return new SimpleStringProperty("");
            }
        });
        nomMedicamentColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getMedicament() != null) {
                return cellData.getValue().getMedicament().nomMedicamentProperty();
            } else {
                return new SimpleStringProperty("");
            }
        });
        quantiteColumn.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        prixUnitaireColumn.setCellValueFactory(new PropertyValueFactory<>("prixUnitaire"));
        totalColumn.setCellValueFactory(cellData -> cellData.getValue().prixTotalProperty().asObject());
        typeVenteColumn.setCellValueFactory(new PropertyValueFactory<>("typeVente"));
        statutColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getVente() != null) {
                return cellData.getValue().getVente().statutProperty();
            } else {
                return new SimpleStringProperty("");
            }
        });

        venteMedicamentTableView.setItems(venteMedicamentList);

        venteMedicamentTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                venteMedicamentSelectionne = venteMedicamentTableView.getSelectionModel().getSelectedItem();
                Vente vente = venteMedicamentSelectionne.getVente();
                Client client = vente.getClient();
                if (client != null) {
                    detailsCommandeLabel.setText("Détails de la commande : " + vente.getIdVente() +
                            " - Client : " + client.getNomClient() + " " + client.getPrenomClient());
                } else {
                    detailsCommandeLabel.setText("Détails de la commande : " + vente.getIdVente() +
                            " - Client : Client non disponible");
                }
                montantTotalLabel.setText(String.format("%.2f", vente.getMontantTotal()));
                remiseLabel.setText(String.format("%.2f", vente.getRemise()));
                montantFinalLabel.setText(String.format("%.2f", vente.getMontantTotal() - vente.getRemise()));
            }
        });
    }

    @FXML
    private void handleValiderPaiement() {
        if (venteMedicamentSelectionne != null) {
            Vente vente = venteMedicamentSelectionne.getVente();
            double montantDonne = Double.parseDouble(montantDonneTextField.getText().replace(",", "."));
            double montantFinal = vente.getMontantTotal() - vente.getRemise();

            if (montantDonne >= montantFinal) {
                double montantRendu = montantDonne - montantFinal;
                montantRenduLabel.setText(String.format("%.2f", montantRendu));

                caisseDAO.updateVenteStatut(vente.getIdVente(), "Payée");
                caisseDAO.updateVenteDatePaiement(vente.getIdVente(), LocalDateTime.now());

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Paiement validé");
                alert.setHeaderText(null);
                alert.setContentText("Le paiement a été validé avec succès.");
                alert.showAndWait();

                venteMedicamentList = FXCollections.observableArrayList(caisseDAO.getAllVenteMedicaments());
                venteMedicamentTableView.setItems(venteMedicamentList);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Montant insuffisant");
                alert.setHeaderText(null);
                alert.setContentText("Le montant donné est insuffisant pour régler la commande.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void handleImprimerFacture() {
        if (venteMedicamentSelectionne != null) {
            Vente vente = venteMedicamentSelectionne.getVente();
            if (vente.getStatut().equals("Payée")) {
                try {
                    FacturePDF.genererFacturePDF(vente);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Impression de facture");
                    alert.setHeaderText(null);
                    alert.setContentText("La facture a été générée avec succès.");
                    alert.showAndWait();
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur d'impression");
                    alert.setHeaderText(null);
                    alert.setContentText("Une erreur s'est produite lors de l'impression de la facture.");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Commande non payée");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez sélectionner une commande payée pour imprimer la facture.");
                alert.showAndWait();
            }
        }
    }
}