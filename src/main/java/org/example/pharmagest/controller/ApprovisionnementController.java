package org.example.pharmagest.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import org.example.pharmagest.dao.ApprovisionnementDAO;
import org.example.pharmagest.dao.MedicamentDAO;
import org.example.pharmagest.model.Approvisionnement;
import org.example.pharmagest.model.Medicament;
import org.example.pharmagest.model.Fournisseur;
import org.example.pharmagest.model.Famille;
import org.example.pharmagest.model.Forme;

import java.time.LocalDate;

public class ApprovisionnementController {

    @FXML
    private HBox tableContainer;
    @FXML
    private TableView<Medicament> medicamentTable;
    @FXML
    private TableColumn<Medicament, String> nomMedicamentColumn;
    @FXML
    private TableColumn<Medicament, Integer> quantiteMedicamentColumn;
    @FXML
    private TableColumn<Medicament, Double> prixVenteColumn;
    @FXML
    private TableColumn<Medicament, Double> prixFournisseurColumn;
    @FXML
    private TextField seuilField;
    @FXML
    private Label nomMedicamentLabel;
    @FXML
    private Label quantiteActuelleLabel;
    @FXML
    private TextField quantiteCommanderField;
    @FXML
    private TextField prixFournisseurField;
    @FXML
    private DatePicker dateApprovisionementField;
    @FXML
    private TextArea commentaireField;
    @FXML
    private Button validerButton;
    @FXML
    private TableView<Approvisionnement> approvisionnementTable;
    @FXML
    private TableColumn<Approvisionnement, Integer> idApprovisionnementColumn;
    @FXML
    private TableColumn<Approvisionnement, String> medicamentApprovisionnementColumn;
    @FXML
    private TableColumn<Approvisionnement, Integer> quantiteApprovisionnementColumn;
    @FXML
    private TableColumn<Approvisionnement, LocalDate> dateApprovisionnementColumn;
    @FXML
    private TableColumn<Approvisionnement, String> statutApprovisionnementColumn;

    private MedicamentDAO medicamentDAO;
    private ApprovisionnementDAO approvisionnementDAO;
    private ObservableList<Medicament> medicamentList;
    private ObservableList<Approvisionnement> approvisionnementList;
    private int seuil = 30;

    @FXML
    public void initialize() {
        medicamentDAO = new MedicamentDAO();
        approvisionnementDAO = new ApprovisionnementDAO();

        nomMedicamentColumn.setCellValueFactory(new PropertyValueFactory<>("nomMedicament"));
        quantiteMedicamentColumn.setCellValueFactory(new PropertyValueFactory<>("quantiteMedicament"));
        prixVenteColumn.setCellValueFactory(new PropertyValueFactory<>("prixVente"));
        prixFournisseurColumn.setCellValueFactory(new PropertyValueFactory<>("prixFournisseur"));

        idApprovisionnementColumn.setCellValueFactory(new PropertyValueFactory<>("idApprovisionnement"));
        medicamentApprovisionnementColumn.setCellValueFactory(cellData -> cellData.getValue().getMedicament().nomMedicamentProperty());
        quantiteApprovisionnementColumn.setCellValueFactory(new PropertyValueFactory<>("quantiteCommandee"));
        dateApprovisionnementColumn.setCellValueFactory(new PropertyValueFactory<>("dateApprovisionnement"));
        statutApprovisionnementColumn.setCellValueFactory(new PropertyValueFactory<>("statut"));

        refreshMedicamentList();
        refreshApprovisionnementList();

        medicamentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Medicament selectedMedicament = medicamentTable.getSelectionModel().getSelectedItem();
                nomMedicamentLabel.setText(selectedMedicament.getNomMedicament());
                quantiteActuelleLabel.setText(String.valueOf(selectedMedicament.getQuantiteMedicament()));
            }
        });
    }

    @FXML
    private void handleDefinirSeuil() {
        String seuilText = seuilField.getText();
        if (!seuilText.isEmpty()) {
            seuil = Integer.parseInt(seuilText);
            refreshMedicamentList();
        }
    }

    @FXML
    private void handleValiderApprovisionnement() {
        Medicament selectedMedicament = medicamentTable.getSelectionModel().getSelectedItem();
        if (selectedMedicament != null) {
            int quantiteCommander = Integer.parseInt(quantiteCommanderField.getText());
            LocalDate dateApprovisionnement = dateApprovisionementField.getValue();
            String commentaire = commentaireField.getText();
            double prixFournisseur = Double.parseDouble(prixFournisseurField.getText());

            Approvisionnement approvisionnement = new Approvisionnement(0, selectedMedicament, selectedMedicament.getFournisseur(), quantiteCommander, dateApprovisionnement, "En cours", prixFournisseur, 0, commentaire);
            approvisionnementDAO.addApprovisionnement(approvisionnement);

            refreshMedicamentList();
            refreshApprovisionnementList();
            clearFields();
        }
    }

    @FXML
    private void handleValiderReceptionApprovisionnement() {
        Approvisionnement selectedApprovisionnement = approvisionnementTable.getSelectionModel().getSelectedItem();
        if (selectedApprovisionnement != null && selectedApprovisionnement.getStatut().equals("En cours")) {
            Medicament medicament = selectedApprovisionnement.getMedicament();
            int quantiteRecue = selectedApprovisionnement.getQuantiteCommandee();
            medicament.setQuantiteMedicament(medicament.getQuantiteMedicament() + quantiteRecue);

            medicamentDAO.updateMedicament(medicament);

            selectedApprovisionnement.setStatut("Reçu");
            approvisionnementDAO.updateApprovisionnement(selectedApprovisionnement);

            refreshMedicamentList();
            refreshApprovisionnementList();
        }
    }


    @FXML
    private void handleSupprimerApprovisionnement() {
        Approvisionnement selectedApprovisionnement = approvisionnementTable.getSelectionModel().getSelectedItem();
        if (selectedApprovisionnement != null) {
            approvisionnementDAO.deleteApprovisionnement(selectedApprovisionnement);
            refreshApprovisionnementList();
        }
    }

    private void refreshMedicamentList() {
        medicamentList = FXCollections.observableArrayList(medicamentDAO.getMedicamentsEnDessousDuSeuil(seuil));
        medicamentTable.setItems(medicamentList);
    }

    private void refreshApprovisionnementList() {
        approvisionnementList = FXCollections.observableArrayList(approvisionnementDAO.getAllApprovisionnements());
        approvisionnementTable.setItems(approvisionnementList);
    }

    private void clearFields() {
        nomMedicamentLabel.setText("");
        quantiteActuelleLabel.setText("");
        quantiteCommanderField.clear();
        dateApprovisionementField.setValue(null);
        commentaireField.clear();
        prixFournisseurField.clear();
    }

    @FXML
    private void handleRefresh() {
        refreshMedicamentList();
        refreshApprovisionnementList();
        clearFields();
    }
}