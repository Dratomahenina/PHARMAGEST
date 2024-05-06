package org.example.pharmagest.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.pharmagest.dao.ClientDAO;
import org.example.pharmagest.dao.MedicamentDAO;
import org.example.pharmagest.dao.VenteDAO;
import org.example.pharmagest.model.Client;
import org.example.pharmagest.model.LigneVente;
import org.example.pharmagest.model.Medicament;
import org.example.pharmagest.model.Vente;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public class VenteController {

    @FXML
    private VBox clientSelectionVBox;
    @FXML
    private TableView<Client> clientTableView;
    @FXML
    private TableColumn<Client, String> nomClientColumn;
    @FXML
    private TableColumn<Client, String> prenomClientColumn;
    @FXML
    private TextField clientSearchField;
    @FXML
    private RadioButton avecOrdonnanceRadio;
    @FXML
    private RadioButton sansOrdonnanceRadio;
    @FXML
    private TableView<Medicament> medicamentTableView;
    @FXML
    private TableColumn<Medicament, String> nomMedicamentColumn;
    @FXML
    private TableColumn<Medicament, Double> prixUnitaireColumn;
    @FXML
    private TableColumn<Medicament, Integer> stockColumn;
    @FXML
    private TableColumn<Medicament, Void> actionColumn;
    @FXML
    private TextField searchMedicamentField;
    @FXML
    private TableView<LigneVente> medicamentVenteTableView;
    @FXML
    private TableColumn<LigneVente, String> nomMedicamentVenteColumn;
    @FXML
    private TableColumn<LigneVente, Integer> quantiteMedicamentVenteColumn;
    @FXML
    private TableColumn<LigneVente, Double> prixUnitaireVenteColumn;
    @FXML
    private TableColumn<LigneVente, Double> prixTotalVenteColumn;
    @FXML
    private TableColumn<LigneVente, Void> actionVenteColumn;
    @FXML
    private Label prixTotalLabel;
    @FXML
    private Label prixFinalLabel;
    @FXML
    private Label detailsClientLabel;
    @FXML
    private Label detailsTypeVenteLabel;

    private ClientDAO clientDAO;
    private MedicamentDAO medicamentDAO;
    private VenteDAO venteDAO;
    private ObservableList<LigneVente> lignesVente = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        clientDAO = new ClientDAO();
        medicamentDAO = new MedicamentDAO();
        venteDAO = new VenteDAO();

        nomClientColumn.setCellValueFactory(new PropertyValueFactory<>("nomClient"));
        prenomClientColumn.setCellValueFactory(new PropertyValueFactory<>("prenomClient"));
        nomMedicamentColumn.setCellValueFactory(new PropertyValueFactory<>("nomMedicament"));
        prixUnitaireColumn.setCellValueFactory(new PropertyValueFactory<>("prixVente"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("quantiteMedicament"));
        nomMedicamentVenteColumn.setCellValueFactory(cellData -> cellData.getValue().getMedicament().nomMedicamentProperty());
        quantiteMedicamentVenteColumn.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        prixUnitaireVenteColumn.setCellValueFactory(new PropertyValueFactory<>("prixUnitaire"));
        prixTotalVenteColumn.setCellValueFactory(new PropertyValueFactory<>("prixTotal"));

        sansOrdonnanceRadio.setSelected(true);
        detailsTypeVenteLabel.setText("Sans Ordonnance");
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button ajouterButton = new Button("Ajouter");

            {
                ajouterButton.setOnAction(event -> {
                    Medicament medicament = getTableView().getItems().get(getIndex());
                    ajouterMedicamentVente(medicament);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(ajouterButton);
                }
            }
        });

        actionVenteColumn.setCellFactory(param -> new TableCell<>() {
            private final Button effacerButton = new Button("Effacer");

            {
                effacerButton.setOnAction(event -> {
                    LigneVente ligneVente = getTableView().getItems().get(getIndex());
                    supprimerMedicamentVente(ligneVente);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(effacerButton);
                }
            }
        });

        avecOrdonnanceRadio.setOnAction(event -> {
            clientSelectionVBox.setVisible(true);
            refreshClientTableView();
            updateTypeVenteLabel();
        });

        sansOrdonnanceRadio.setOnAction(event -> {
            clientSelectionVBox.setVisible(false);
            clientTableView.getSelectionModel().clearSelection();
            detailsClientLabel.setText("");
            updateTypeVenteLabel();
        });

        clientTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                detailsClientLabel.setText(newValue.getNomClient() + " " + newValue.getPrenomClient());
            }
        });

        sansOrdonnanceRadio.setSelected(true);
        clientSelectionVBox.setVisible(false);

        refreshMedicamentTableView();
    }

    @FXML
    private void handleNouvelleVente() {
        lignesVente.clear();
        refreshMedicamentVenteTableView();
        updatePrixTotal();
        clientTableView.getSelectionModel().clearSelection();
        sansOrdonnanceRadio.setSelected(true);
        clientSelectionVBox.setVisible(false);
        detailsClientLabel.setText("");
        detailsTypeVenteLabel.setText("Sans Ordonnance");
        searchMedicamentField.clear();
        medicamentTableView.getSelectionModel().clearSelection();
    }

    @FXML
    private void updateTypeVenteLabel() {
        if (avecOrdonnanceRadio.isSelected()) {
            detailsTypeVenteLabel.setText("Avec Ordonnance");
        } else {
            detailsTypeVenteLabel.setText("Sans Ordonnance");
        }
    }

    @FXML
    private void handleClientSearch() {
        String searchTerm = clientSearchField.getText();
        ObservableList<Client> searchResults = FXCollections.observableArrayList(clientDAO.searchClients(searchTerm));
        clientTableView.setItems(searchResults);
    }

    @FXML
    private void handleMedicamentSearch() {
        String searchTerm = searchMedicamentField.getText();
        ObservableList<Medicament> searchResults = FXCollections.observableArrayList(medicamentDAO.searchMedicaments(searchTerm));
        medicamentTableView.setItems(searchResults);
    }

    private void ajouterMedicamentVente(Medicament medicament) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Quantité");
        dialog.setHeaderText("Saisir la quantité");
        dialog.setContentText("Quantité :");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(quantiteString -> {
            try {
                int quantite = Integer.parseInt(quantiteString);
                double prixUnitaire = medicament.getPrixVente();
                double prixTotal = quantite * prixUnitaire;

                LigneVente ligneVente = new LigneVente(0, medicament, quantite, prixUnitaire, prixTotal);
                lignesVente.add(ligneVente);
                refreshMedicamentVenteTableView();
                updatePrixTotal();
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Quantité invalide");
                alert.setContentText("Veuillez saisir une quantité valide.");
                alert.showAndWait();
            }
        });
    }

    private void supprimerMedicamentVente(LigneVente ligneVente) {
        lignesVente.remove(ligneVente);
        refreshMedicamentVenteTableView();
        updatePrixTotal();
    }

    @FXML
    private void handleValiderVente() {
        Client selectedClient = null;
        if (avecOrdonnanceRadio.isSelected()) {
            selectedClient = clientTableView.getSelectionModel().getSelectedItem();
            if (selectedClient == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Avertissement");
                alert.setHeaderText("Aucun client sélectionné");
                alert.setContentText("Veuillez sélectionner un client pour une vente avec ordonnance.");
                alert.showAndWait();
                return;
            }
        }

        if (!lignesVente.isEmpty()) {
            String typeVente = avecOrdonnanceRadio.isSelected() ? "Avec Ordonnance" : "Sans Ordonnance";
            String prixTotalString = prixTotalLabel.getText().replace(",", ".");
            double montantTotal = Double.parseDouble(prixTotalString);

            Vente vente;
            if (typeVente.equals("Sans Ordonnance")) {
                vente = new Vente(0, null, typeVente, montantTotal, LocalDate.now(), "En attente", lignesVente, null);
            } else {
                vente = new Vente(0, selectedClient, typeVente, montantTotal, LocalDate.now(), "En attente", lignesVente, null);
            }
            int idVente = venteDAO.addVente(vente);


            for (LigneVente ligneVente : lignesVente) {
                ligneVente.setIdVente(idVente);
                venteDAO.addLigneVente(ligneVente);
                Medicament medicament = ligneVente.getMedicament();
                medicament.setQuantiteMedicament(medicament.getQuantiteMedicament() - ligneVente.getQuantite());
                medicamentDAO.updateMedicament(medicament);
            }

            lignesVente.clear();
            refreshMedicamentVenteTableView();
            updatePrixTotal();
            clientTableView.getSelectionModel().clearSelection();
            sansOrdonnanceRadio.setSelected(true);
            clientSelectionVBox.setVisible(false);
            detailsClientLabel.setText("");
            detailsTypeVenteLabel.setText("Sans Ordonnance");
        }
    }

    @FXML
    private void handleAjouterClient() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/pharmagest/ajouterclient.fxml"));
            Parent root = loader.load();
            AjouterClientController ajouterClientController = loader.getController();

            ClientController clientController = new ClientController();
            ajouterClientController.setClientController(clientController);

            Stage stage = new Stage();
            stage.setTitle("Ajouter un client");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            refreshClientTableView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void refreshClientTableView() {
        ObservableList<Client> clients = FXCollections.observableArrayList(clientDAO.getClientsActifs());
        clientTableView.setItems(clients);
    }

    private void refreshMedicamentTableView() {
        ObservableList<Medicament> medicaments = FXCollections.observableArrayList(medicamentDAO.getMedicamentsActifs());
        medicamentTableView.setItems(medicaments);
    }

    private void refreshMedicamentVenteTableView() {
        medicamentVenteTableView.setItems(lignesVente);
    }

    private void updatePrixTotal() {
        double prixTotal = lignesVente.stream().mapToDouble(LigneVente::getPrixTotal).sum();
        prixTotalLabel.setText(String.format("%.2f", prixTotal));
        prixFinalLabel.setText(String.format("%.2f", prixTotal));
    }
}