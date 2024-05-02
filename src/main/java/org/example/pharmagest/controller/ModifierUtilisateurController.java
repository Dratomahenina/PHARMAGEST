package org.example.pharmagest.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.pharmagest.dao.UtilisateurDAO;
import org.example.pharmagest.model.Utilisateur;

public class ModifierUtilisateurController {

    @FXML
    private TextField nomUtilisateurField;

    @FXML
    private PasswordField motDePasseField;

    @FXML
    private ChoiceBox<String> roleChoiceBox;

    private UtilisateurDAO utilisateurDAO;
    private UtilisateurController utilisateurController;
    private Utilisateur utilisateur;

    @FXML
    public void initialize() {
        utilisateurDAO = new UtilisateurDAO();
        roleChoiceBox.getItems().addAll("admin", "vendeur", "caissier");
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        populateFields();
    }

    private void populateFields() {
        nomUtilisateurField.setText(utilisateur.getNomUtilisateur());
        motDePasseField.setText(utilisateur.getMotDePasse());
        roleChoiceBox.setValue(utilisateur.getRole());
    }

    @FXML
    private void handleModifierUtilisateur() {
        String nomUtilisateur = nomUtilisateurField.getText();
        String motDePasse = motDePasseField.getText();
        String role = roleChoiceBox.getValue();

        utilisateur.setNomUtilisateur(nomUtilisateur);
        utilisateur.setMotDePasse(motDePasse);
        utilisateur.setRole(role);

        utilisateurDAO.updateUtilisateur(utilisateur);
        utilisateurController.refreshUtilisateurList();
        closeWindow();
    }

    @FXML
    private void handleAnnulerModification() {
        closeWindow();
    }

    private void closeWindow() {
        nomUtilisateurField.getScene().getWindow().hide();
    }

    public void setUtilisateurController(UtilisateurController utilisateurController) {
        this.utilisateurController = utilisateurController;
    }
}