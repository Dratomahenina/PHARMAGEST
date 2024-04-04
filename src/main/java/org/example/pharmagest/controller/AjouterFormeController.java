package org.example.pharmagest.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.example.pharmagest.dao.FormeDAO;
import org.example.pharmagest.model.Forme;

public class AjouterFormeController {

    @FXML
    private TextField nomFormeField;
    @FXML
    private ChoiceBox<String> statutChoiceBox;

    private FormeDAO formeDAO;
    private FormeController formeController;

    @FXML
    public void initialize() {
        formeDAO = new FormeDAO();
        statutChoiceBox.getItems().addAll("actif", "inactif");
        statutChoiceBox.setValue("actif"); // Valeur par défaut
    }

    @FXML
    private void handleAjouterForme() {
        String nomForme = nomFormeField.getText();
        String statut = statutChoiceBox.getValue();

        Forme forme = new Forme(0, nomForme, statut, null); // La date de création est gérée par la base de données
        formeDAO.addForme(forme);

        if (formeController != null) {
            formeController.refreshFormeList();
        }

        // Fermer la fenêtre d'ajout de forme
        nomFormeField.getScene().getWindow().hide();
    }

    public void setFormeController(FormeController formeController) {
        this.formeController = formeController;
    }
}