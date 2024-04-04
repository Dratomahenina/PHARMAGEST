package org.example.pharmagest.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.example.pharmagest.dao.FamilleDAO;
import org.example.pharmagest.model.Famille;

public class ModifierFamilleController {

    @FXML
    private TextField nomFamilleField;
    @FXML
    private ChoiceBox<String> statutChoiceBox;

    private FamilleDAO familleDAO;
    private FamilleController familleController;
    private Famille famille;

    @FXML
    public void initialize() {
        familleDAO = new FamilleDAO();
        statutChoiceBox.getItems().addAll("actif", "inactif");
    }

    public void setFamille(Famille famille) {
        this.famille = famille;
        populateFields();
    }

    private void populateFields() {
        nomFamilleField.setText(famille.getNomFamille());
        statutChoiceBox.setValue(famille.getStatut());
    }

    @FXML
    private void handleModifierFamille() {
        String nomFamille = nomFamilleField.getText();
        String statut = statutChoiceBox.getValue();

        famille.setNomFamille(nomFamille);
        famille.setStatut(statut);

        familleDAO.updateFamille(famille);
        familleController.refreshFamilleList();
        closeWindow();
    }

    @FXML
    private void handleAnnulerModification() {
        closeWindow();
    }

    private void closeWindow() {
        nomFamilleField.getScene().getWindow().hide();
    }

    public void setFamilleController(FamilleController familleController) {
        this.familleController = familleController;
    }
}