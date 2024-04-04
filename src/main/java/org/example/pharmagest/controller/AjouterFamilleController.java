package org.example.pharmagest.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.example.pharmagest.dao.FamilleDAO;
import org.example.pharmagest.model.Famille;

public class AjouterFamilleController {

    @FXML
    private TextField nomFamilleField;
    @FXML
    private ChoiceBox<String> statutChoiceBox;

    private FamilleDAO familleDAO;
    private FamilleController familleController;

    @FXML
    public void initialize() {
        familleDAO = new FamilleDAO();
        statutChoiceBox.getItems().addAll("actif", "inactif");
        statutChoiceBox.setValue("actif"); // Valeur par défaut
    }

    @FXML
    private void handleAjouterFamille() {
        String nomFamille = nomFamilleField.getText();
        String statut = statutChoiceBox.getValue();

        Famille famille = new Famille(0, nomFamille, statut, null); // La date de création est gérée par la base de données
        familleDAO.addFamille(famille);

        if (familleController != null) {
            familleController.refreshFamilleList();
        }

        // Fermer la fenêtre d'ajout de famille
        nomFamilleField.getScene().getWindow().hide();
    }

    public void setFamilleController(FamilleController familleController) {
        this.familleController = familleController;
    }
}