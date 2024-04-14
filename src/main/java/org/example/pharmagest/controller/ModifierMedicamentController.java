package org.example.pharmagest.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.example.pharmagest.dao.FamilleDAO;
import org.example.pharmagest.dao.FormeDAO;
import org.example.pharmagest.dao.FournisseurDAO;
import org.example.pharmagest.dao.MedicamentDAO;
import org.example.pharmagest.model.Famille;
import org.example.pharmagest.model.Forme;
import org.example.pharmagest.model.Fournisseur;
import org.example.pharmagest.model.Medicament;

public class ModifierMedicamentController {

    @FXML
    private TextField nomMedicamentField;

    @FXML
    private TextField descriptionMedicamentField;

    @FXML
    private ChoiceBox<Fournisseur> fournisseurChoiceBox;

    @FXML
    private ChoiceBox<Famille> familleChoiceBox;

    @FXML
    private ChoiceBox<Forme> formeChoiceBox;

    @FXML
    private TextField quantiteMedicamentField;

    @FXML
    private TextField prixVenteField;

    @FXML
    private ChoiceBox<String> statutChoiceBox;

    private MedicamentDAO medicamentDAO;
    private FournisseurDAO fournisseurDAO;
    private FamilleDAO familleDAO;
    private FormeDAO formeDAO;
    private MedicamentController medicamentController;
    private Medicament medicament;

    @FXML
    public void initialize() {
        medicamentDAO = new MedicamentDAO();
        fournisseurDAO = new FournisseurDAO();
        familleDAO = new FamilleDAO();
        formeDAO = new FormeDAO();

        // Remplir les ChoiceBox avec les données des fournisseurs, familles et formes
        fournisseurChoiceBox.getItems().addAll(fournisseurDAO.getAllFournisseurs());
        familleChoiceBox.getItems().addAll(familleDAO.getAllFamilles());
        formeChoiceBox.getItems().addAll(formeDAO.getAllFormes());

        // Remplir le ChoiceBox du statut avec les valeurs possibles
        statutChoiceBox.getItems().addAll("actif", "inactif");
    }

    public void setMedicament(Medicament medicament) {
        this.medicament = medicament;
        populateFields();
    }

    private void populateFields() {
        nomMedicamentField.setText(medicament.getNomMedicament());
        descriptionMedicamentField.setText(medicament.getDescriptionMedicament());
        fournisseurChoiceBox.setValue(medicament.getFournisseur());
        familleChoiceBox.setValue(medicament.getFamille());
        formeChoiceBox.setValue(medicament.getForme());
        quantiteMedicamentField.setText(String.valueOf(medicament.getQuantiteMedicament()));
        prixVenteField.setText(String.valueOf(medicament.getPrixVente()));
        statutChoiceBox.setValue(medicament.getStatut());
    }

    @FXML
    private void handleModifierMedicament() {
        String nomMedicament = nomMedicamentField.getText();
        String descriptionMedicament = descriptionMedicamentField.getText();
        Fournisseur fournisseur = fournisseurChoiceBox.getValue();
        Famille famille = familleChoiceBox.getValue();
        Forme forme = formeChoiceBox.getValue();
        int quantiteMedicament = Integer.parseInt(quantiteMedicamentField.getText());
        double prixVente = Double.parseDouble(prixVenteField.getText());
        String statut = statutChoiceBox.getValue();

        medicament.setNomMedicament(nomMedicament);
        medicament.setDescriptionMedicament(descriptionMedicament);
        medicament.setFournisseur(fournisseur);
        medicament.setFamille(famille);
        medicament.setForme(forme);
        medicament.setQuantiteMedicament(quantiteMedicament);
        medicament.setPrixVente(prixVente);
        medicament.setStatut(statut);

        medicamentDAO.updateMedicament(medicament);
        medicamentController.refreshMedicamentList();
        closeWindow();
    }

    @FXML
    private void handleAnnulerModification() {
        closeWindow();
    }

    private void closeWindow() {
        nomMedicamentField.getScene().getWindow().hide();
    }

    public void setMedicamentController(MedicamentController medicamentController) {
        this.medicamentController = medicamentController;
    }
}