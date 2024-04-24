package org.example.pharmagest.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.example.pharmagest.dao.UtilisateurDAO;
import org.example.pharmagest.model.Utilisateur;

public class AjouterUtilisateurController {

    @FXML
    private TextField nomUtilisateurField;
    @FXML
    private TextField motDePasseField;
    @FXML
    private ChoiceBox<String> roleChoiceBox;

    private UtilisateurDAO utilisateurDAO;
    private UtilisateurController utilisateurController;

    @FXML
    public void initialize() {
        utilisateurDAO = new UtilisateurDAO();
        roleChoiceBox.getItems().addAll("admin", "caissier", "vendeur");
    }

    @FXML
    private void handleAjouterUtilisateur() {
        String nomUtilisateur = nomUtilisateurField.getText();
        String motDePasse = motDePasseField.getText();
        String role = roleChoiceBox.getValue();

        Utilisateur utilisateur = new Utilisateur(0, nomUtilisateur, motDePasse, role, null);
        utilisateurDAO.addUtilisateur(utilisateur);

        if (utilisateurController != null) {
            utilisateurController.refreshUtilisateurList();
        }

        closeWindow();
    }

    private void closeWindow() {
        nomUtilisateurField.getScene().getWindow().hide();
    }

    public void setUtilisateurController(UtilisateurController utilisateurController) {
        this.utilisateurController = utilisateurController;
    }
}