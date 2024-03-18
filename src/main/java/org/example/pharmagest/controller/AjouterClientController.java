package org.example.pharmagest.controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.example.pharmagest.dao.ClientDAO;
import org.example.pharmagest.model.Client;

import java.time.LocalDate;

public class AjouterClientController {

    @FXML
    private TextField nomClientTextField;
    @FXML
    private TextField prenomClientTextField;
    @FXML
    private DatePicker dateNaissanceClientPicker;
    @FXML
    private TextField adresseClientTextField;
    @FXML
    private TextField telephoneClientTextField;
    @FXML
    private TextField nomMedecinTextField;

    private ClientDAO clientDAO;

    public void initialize() {
        clientDAO = new ClientDAO();
    }

    @FXML
    private void handleAjouterClient() {
        String nomClient = nomClientTextField.getText();
        String prenomClient = prenomClientTextField.getText();
        LocalDate dateNaissanceClient = dateNaissanceClientPicker.getValue();
        String adresseClient = adresseClientTextField.getText();
        String telephoneClient = telephoneClientTextField.getText();
        String nomMedecin = nomMedecinTextField.getText();

        // Créer un nouvel objet Client avec les données saisies
        Client nouveauClient = new Client(0, nomClient, prenomClient, dateNaissanceClient, adresseClient, telephoneClient, nomMedecin, LocalDate.now());

        // Enregistrer le nouveau client dans la base de données
        clientDAO.addClient(nouveauClient);

        // Fermer la fenêtre d'ajout de client
        nomClientTextField.getScene().getWindow().hide();
    }
}