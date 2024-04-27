package org.example.pharmagest.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.pharmagest.dao.VenteDAO;
import org.example.pharmagest.dao.VenteMedicamentDAO;
import org.example.pharmagest.model.Vente;
import org.example.pharmagest.model.VenteMedicament;
import org.example.pharmagest.utils.FacturePDF;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class CaisseController {

    @FXML
    private TableView<VenteMedicament> venteTableView;
    @FXML
    private TableColumn<VenteMedicament, Integer> idVenteColumn;
    @FXML
    private TableColumn<VenteMedicament, String> medicamentColumn;
    @FXML
    private TableColumn<VenteMedicament, Integer> quantiteColumn;
    @FXML
    private TableColumn<VenteMedicament, Double> prixUnitaireColumn;
    @FXML
    private TableColumn<VenteMedicament, Double> prixTotalColumn;
    @FXML
    private TableColumn<VenteMedicament, LocalDateTime> dateVenteColumn;
    @FXML
    private Label detailsVenteLabel;
    @FXML
    private Label montantTotalLabel;
    @FXML
    private Label remiseLabel;
    @FXML
    private Label montantFinalLabel;

    private ObservableList<VenteMedicament> venteList;
    private VenteMedicamentDAO venteMedicamentDAO;
    private VenteDAO venteDAO;
    private VenteMedicament venteSelectionne;

    @FXML
    private TextField montantDonneTextField;

    @FXML
    private Label montantRenduLabel;

    @FXML
    public void initialize() {
        venteMedicamentDAO = new VenteMedicamentDAO();
        venteDAO = new VenteDAO();
        venteList = FXCollections.observableArrayList(venteMedicamentDAO.getAllVenteMedicamentsNonPayes());

        idVenteColumn.setCellValueFactory(new PropertyValueFactory<>("idVente"));
        medicamentColumn.setCellValueFactory(cellData -> cellData.getValue().getMedicament().nomMedicamentProperty());
        quantiteColumn.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        prixUnitaireColumn.setCellValueFactory(new PropertyValueFactory<>("prixUnitaire"));
        prixTotalColumn.setCellValueFactory(cellData -> cellData.getValue().prixTotalProperty().asObject());
        dateVenteColumn.setCellValueFactory(new PropertyValueFactory<>("dateVente"));

        venteTableView.setItems(venteList);

        venteTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                venteSelectionne = venteTableView.getSelectionModel().getSelectedItem();
                Vente vente = venteDAO.getVenteByIdVente(venteSelectionne.getIdVente());
                detailsVenteLabel.setText("Détails de la vente : " + vente.getIdVente() + " - Client : " + vente.getClient().getNomClient() + " " + vente.getClient().getPrenomClient() + " - Type de Vente : " + vente.getTypeVente());
                montantTotalLabel.setText(String.format("%.2f", vente.getMontantTotal()));
                remiseLabel.setText(String.format("%.2f", vente.getRemise()));
                montantFinalLabel.setText(String.format("%.2f", vente.getMontantTotal() - vente.getRemise()));
            }
        });
    }

    @FXML
    private void handleValiderPaiement() {
        if (venteSelectionne != null) {
            double montantDonne = Double.parseDouble(montantDonneTextField.getText().replace(",", "."));
            Vente vente = venteDAO.getVenteByIdVente(venteSelectionne.getIdVente());
            double montantFinal = vente.getMontantTotal() - vente.getRemise();

            if (montantDonne >= montantFinal) {
                double montantRendu = montantDonne - montantFinal;
                montantRenduLabel.setText(String.format("%.2f", montantRendu));

                vente.setStatut("Payée");
                vente.setDatePaiement(LocalDateTime.now());
                venteDAO.updateVente(vente);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Paiement validé");
                alert.setHeaderText(null);
                alert.setContentText("Le paiement a été validé avec succès.");
                alert.showAndWait();

                // Rafraîchir la liste des ventes
                venteList = FXCollections.observableArrayList(venteMedicamentDAO.getAllVenteMedicamentsNonPayes());
                venteTableView.setItems(venteList);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Montant insuffisant");
                alert.setHeaderText(null);
                alert.setContentText("Le montant donné est insuffisant pour régler la vente.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void handleImprimerFacture() {
        if (venteSelectionne != null) {
            Vente vente = venteDAO.getVenteByIdVente(venteSelectionne.getIdVente());
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
                alert.setTitle("Vente non sélectionnée");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez sélectionner une vente payée dans la liste pour imprimer la facture.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void handleAfficherCommandesPayees() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/pharmagest/commandespayees.fxml"));
            Parent root = loader.load();
            CommandesPayeesController commandesPayeesController = loader.getController();
            commandesPayeesController.setVenteDAO(venteDAO);

            Stage stage = new Stage();
            stage.setTitle("Commandes Payées");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}