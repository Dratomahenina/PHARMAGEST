package org.example.pharmagest.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.pharmagest.dao.ClientDAO;
import org.example.pharmagest.dao.MedicamentDAO;
import org.example.pharmagest.dao.VenteDAO;
import org.example.pharmagest.model.Client;
import org.example.pharmagest.model.Medicament;
import org.example.pharmagest.model.Vente;
import org.example.pharmagest.model.VenteMedicament;

import java.time.LocalDate;
import java.util.Optional;

public class VenteController {

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
    private TableView<VenteMedicament> medicamentVenteTableView;
    @FXML
    private TableColumn<VenteMedicament, String> nomMedicamentVenteColumn;
    @FXML
    private TableColumn<VenteMedicament, Integer> quantiteMedicamentVenteColumn;
    @FXML
    private TableColumn<VenteMedicament, Double> prixUnitaireVenteColumn;
    @FXML
    private TableColumn<VenteMedicament, Double> prixTotalVenteColumn;
    @FXML
    private TableColumn<VenteMedicament, Void> actionVenteColumn;
    @FXML
    private Label prixTotalLabel;
    @FXML
    private TextField remiseTextField;
    @FXML
    private Label prixFinalLabel;
    @FXML
    private Label detailsClientLabel;
    @FXML
    private Label detailsTypeVenteLabel;

    private ObservableList<Client> clientList;
    private FilteredList<Client> filteredClientList;
    private ObservableList<Medicament> medicamentList;
    private ObservableList<VenteMedicament> venteMedicamentList;
    private FilteredList<Medicament> filteredMedicamentList;

    private ClientDAO clientDAO;
    private MedicamentDAO medicamentDAO;
    private VenteDAO venteDAO;

    private Vente venteEnCours;
    private Client clientSelectionne;
    private String typeVenteSelectionne;

    @FXML
    public void initialize() {
        clientDAO = new ClientDAO();
        medicamentDAO = new MedicamentDAO();
        venteDAO = new VenteDAO();

        clientList = FXCollections.observableArrayList(clientDAO.getAllClients());
        filteredClientList = new FilteredList<>(clientList, p -> true);
        medicamentList = FXCollections.observableArrayList(medicamentDAO.getAllMedicaments());
        venteMedicamentList = FXCollections.observableArrayList();
        filteredMedicamentList = new FilteredList<>(medicamentList, p -> true);

        nomClientColumn.setCellValueFactory(new PropertyValueFactory<>("nomClient"));
        prenomClientColumn.setCellValueFactory(new PropertyValueFactory<>("prenomClient"));

        clientTableView.setItems(filteredClientList);

        clientSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredClientList.setPredicate(client -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                return client.getNomClient().toLowerCase().contains(lowerCaseFilter) ||
                        client.getPrenomClient().toLowerCase().contains(lowerCaseFilter);
            });
        });

        nomMedicamentColumn.setCellValueFactory(new PropertyValueFactory<>("nomMedicament"));
        prixUnitaireColumn.setCellValueFactory(new PropertyValueFactory<>("prixVente"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("quantiteMedicament"));

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

        medicamentTableView.setItems(filteredMedicamentList);

        searchMedicamentField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredMedicamentList.setPredicate(medicament -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                return medicament.getNomMedicament().toLowerCase().contains(lowerCaseFilter);
            });
        });

        nomMedicamentVenteColumn.setCellValueFactory(cellData -> cellData.getValue().getMedicament().nomMedicamentProperty());
        quantiteMedicamentVenteColumn.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        prixUnitaireVenteColumn.setCellValueFactory(new PropertyValueFactory<>("prixUnitaire"));
        prixTotalVenteColumn.setCellValueFactory(cellData -> cellData.getValue().prixTotalProperty().asObject());

        actionVenteColumn.setCellFactory(param -> new TableCell<>() {
            private final Button supprimerButton = new Button("Supprimer");

            {
                supprimerButton.setOnAction(event -> {
                    VenteMedicament venteMedicament = getTableView().getItems().get(getIndex());
                    venteMedicamentList.remove(venteMedicament);
                    mettreAJourPrixTotal();
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

        medicamentVenteTableView.setItems(venteMedicamentList);

        remiseTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            mettreAJourPrixTotal();
        });
        // Initialiser venteEnCours
        venteEnCours = new Vente();

        // Ajouter des observateurs pour mettre à jour venteEnCours
        clientTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            clientSelectionne = newValue;
            venteEnCours.setClient(clientSelectionne);
            updateDetailsClientLabel();
        });

        avecOrdonnanceRadio.selectedProperty().addListener((observable, oldValue, newValue) -> {
            typeVenteSelectionne = newValue ? "Avec Ordonnance" : "Sans Ordonnance";
            venteEnCours.setTypeVente(typeVenteSelectionne);
            updateDetailsTypeVenteLabel();
        });
    }

    private void updateDetailsClientLabel() {
        if (clientSelectionne != null) {
            detailsClientLabel.setText(clientSelectionne.getNomClient() + " " + clientSelectionne.getPrenomClient());
        } else {
            detailsClientLabel.setText("Aucun client sélectionné");
        }
    }

    private void updateDetailsTypeVenteLabel() {
        if (typeVenteSelectionne != null) {
            detailsTypeVenteLabel.setText(typeVenteSelectionne);
        } else {
            detailsTypeVenteLabel.setText("Aucun type sélectionné");
        }
    }

    @FXML
    private void handleNouvelleVente() {
        venteEnCours = new Vente();
        venteEnCours.setClient(clientSelectionne);
        venteEnCours.setTypeVente(typeVenteSelectionne);
        venteEnCours.setDateVente(LocalDate.now());
        venteEnCours.setStatut("En attente");

        venteMedicamentList.clear();
        medicamentVenteTableView.refresh();
        mettreAJourPrixTotal();
        updateDetailsClientLabel();
        updateDetailsTypeVenteLabel();
    }

    private void ajouterMedicamentVente(Medicament medicament) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Ajouter un médicament");
        dialog.setHeaderText("Entrez la quantité pour le médicament :");
        dialog.setContentText(medicament.getNomMedicament());

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(quantiteString -> {
            try {
                int quantite = Integer.parseInt(quantiteString);
                if (medicament.getQuantiteMedicament() >= quantite) {
                    VenteMedicament venteMedicament = new VenteMedicament();
                    venteMedicament.setMedicament(medicament);
                    venteMedicament.setQuantite(quantite);
                    venteMedicament.setPrixUnitaire(medicament.getPrixVente());

                    venteMedicamentList.add(venteMedicament);
                    medicamentVenteTableView.refresh();
                    mettreAJourPrixTotal();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Stock insuffisant");
                    alert.setHeaderText(null);
                    alert.setContentText("La quantité demandée dépasse le stock disponible pour ce médicament.");
                    alert.showAndWait();
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Quantité invalide");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez saisir une quantité valide.");
                alert.showAndWait();
            }
        });
    }

    @FXML
    private void handleValiderVente() {
        if (venteEnCours == null || venteMedicamentList.isEmpty() || clientSelectionne == null || typeVenteSelectionne == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Vente invalide");
            alert.setHeaderText(null);
            alert.setContentText("Une vente doit contenir au moins un médicament et les informations du client et le type de vente doivent être sélectionnés.");
            alert.showAndWait();
            return;
        }

        venteEnCours.setMedicaments(venteMedicamentList);
        String prixFinalText = prixFinalLabel.getText().trim();
        if (!prixFinalText.isEmpty()) {
            try {
                double montantTotal = Double.parseDouble(prixFinalText.replace(",", "."));
                venteEnCours.setMontantTotal(montantTotal);
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Montant total invalide");
                alert.setHeaderText(null);
                alert.setContentText("Le montant total de la vente est invalide.");
                alert.showAndWait();
                return;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Montant total manquant");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez entrer le montant total de la vente.");
            alert.showAndWait();
            return;
        }

        double remise = 0;
        if (!remiseTextField.getText().isEmpty()) {
            remise = Double.parseDouble(remiseTextField.getText().replace(",", "."));
        }
        venteEnCours.setRemise(remise);

        venteDAO.addVente(venteEnCours);

        for (VenteMedicament venteMedicament : venteMedicamentList) {
            Medicament medicament = venteMedicament.getMedicament();
            int quantiteVendue = venteMedicament.getQuantite();
            medicament.setQuantiteMedicament(medicament.getQuantiteMedicament() - quantiteVendue);
            medicamentDAO.updateMedicament(medicament);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Vente validée");
        alert.setHeaderText(null);
        alert.setContentText("La vente a été validée avec succès.");
        alert.showAndWait();

        // Réinitialiser les champs après validation de la vente
        handleNouvelleVente();
    }


    private void mettreAJourPrixTotal() {
        double prixTotal = venteMedicamentList.stream()
                .mapToDouble(vm -> vm.getPrixUnitaire() * vm.getQuantite())
                .sum();
        prixTotalLabel.setText(String.format("%.2f", prixTotal));

        double remise = 0;
        if (!remiseTextField.getText().isEmpty()) {
            remise = Double.parseDouble(remiseTextField.getText().replace(",", "."));
        }
        double prixFinal = prixTotal - remise;
        prixFinalLabel.setText(String.format("%.2f", prixFinal));
    }
}