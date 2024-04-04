package org.example.pharmagest.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.example.pharmagest.dao.FormeDAO;
import org.example.pharmagest.model.Forme;

public class ModifierFormeController {

    @FXML
    private TextField nomFormeField;
    @FXML
    private ChoiceBox<String> statutChoiceBox;

    private FormeDAO formeDAO;
    private FormeController formeController;
    private Forme forme;

    @FXML
    public void initialize() {
        formeDAO = new FormeDAO();
        statutChoiceBox.getItems().addAll("actif", "inactif");
    }

    public void setForme(Forme forme) {
        this.forme = forme;
        populateFields();
    }

    private void populateFields() {
        nomFormeField.setText(forme.getNomForme());
        statutChoiceBox.setValue(forme.getStatut());
    }

    @FXML
    private void handleModifierForme() {
        String nomForme = nomFormeField.getText();
        String statut = statutChoiceBox.getValue();

        forme.setNomForme(nomForme);
        forme.setStatut(statut);

        formeDAO.updateForme(forme);
        formeController.refreshFormeList();
        closeWindow();
    }

    @FXML
    private void handleAnnulerModification() {
        closeWindow();
    }

    private void closeWindow() {
        nomFormeField.getScene().getWindow().hide();
    }

    public void setFormeController(FormeController formeController) {
        this.formeController = formeController;
    }
}