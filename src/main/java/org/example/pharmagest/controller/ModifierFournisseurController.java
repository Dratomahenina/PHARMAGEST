package org.example.pharmagest.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.example.pharmagest.dao.FournisseurDAO;
import org.example.pharmagest.model.Fournisseur;

public class ModifierFournisseurController {

    @FXML
    private TextField nomFournisseurField;
    @FXML
    private TextField emailFournisseurField;
    @FXML
    private TextField telFournisseurField;
    @FXML
    private TextField adresseFournisseurField;
    @FXML
    private ChoiceBox<String> statutChoiceBox;

    private FournisseurDAO fournisseurDAO;
    private FournisseurController fournisseurController;
    private Fournisseur fournisseur;

    @FXML
    public void initialize() {
        fournisseurDAO = new FournisseurDAO();
        statutChoiceBox.getItems().addAll("actif", "inactif");
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
        populateFields();
    }

    private void populateFields() {
        nomFournisseurField.setText(fournisseur.getNomFournisseur());
        emailFournisseurField.setText(fournisseur.getEmailFournisseur());
        telFournisseurField.setText(fournisseur.getTelFournisseur());
        adresseFournisseurField.setText(fournisseur.getAdresseFournisseur());
        statutChoiceBox.setValue(fournisseur.getStatut());
    }

    @FXML
    private void handleModifierFournisseur() {
        String nomFournisseur = nomFournisseurField.getText();
        String emailFournisseur = emailFournisseurField.getText();
        String telFournisseur = telFournisseurField.getText();
        String adresseFournisseur = adresseFournisseurField.getText();
        String statut = statutChoiceBox.getValue();

        fournisseur.setNomFournisseur(nomFournisseur);
        fournisseur.setEmailFournisseur(emailFournisseur);
        fournisseur.setTelFournisseur(telFournisseur);
        fournisseur.setAdresseFournisseur(adresseFournisseur);
        fournisseur.setStatut(statut);

        fournisseurDAO.updateFournisseur(fournisseur);
        fournisseurController.refreshFournisseurList();
        closeWindow();
    }

    @FXML
    private void handleAnnulerModification() {
        closeWindow();
    }

    private void closeWindow() {
        nomFournisseurField.getScene().getWindow().hide();
    }

    public void setFournisseurController(FournisseurController fournisseurController) {
        this.fournisseurController = fournisseurController;
    }
}