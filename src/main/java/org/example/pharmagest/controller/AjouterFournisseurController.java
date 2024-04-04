package org.example.pharmagest.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.example.pharmagest.dao.FournisseurDAO;
import org.example.pharmagest.model.Fournisseur;

import java.time.LocalDate;

public class AjouterFournisseurController {

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

    @FXML
    public void initialize() {
        fournisseurDAO = new FournisseurDAO();
        statutChoiceBox.getItems().addAll("actif", "inactif");
        statutChoiceBox.setValue("actif"); // Valeur par défaut
    }

    @FXML
    private void handleAjouterFournisseur() {
        String nomFournisseur = nomFournisseurField.getText();
        String emailFournisseur = emailFournisseurField.getText();
        String telFournisseur = telFournisseurField.getText();
        String adresseFournisseur = adresseFournisseurField.getText();
        String statut = statutChoiceBox.getValue();

        Fournisseur fournisseur = new Fournisseur(0, nomFournisseur, emailFournisseur, telFournisseur, adresseFournisseur, statut, LocalDate.now());
        fournisseurDAO.addFournisseur(fournisseur);

        if (fournisseurController != null) {
            fournisseurController.refreshFournisseurList();
        }

        // Fermer la fenêtre d'ajout de fournisseur
        nomFournisseurField.getScene().getWindow().hide();
    }

    public void setFournisseurController(FournisseurController fournisseurController) {
        this.fournisseurController = fournisseurController;
    }
}