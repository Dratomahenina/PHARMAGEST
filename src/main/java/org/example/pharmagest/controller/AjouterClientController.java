package org.example.pharmagest.controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.example.pharmagest.dao.ClientDAO;
import org.example.pharmagest.model.Client;

import java.time.LocalDate;

public class AjouterClientController {

    @FXML
    private TextField nomClientField;
    @FXML
    private TextField prenomClientField;
    @FXML
    private DatePicker dateNaissanceClientPicker;
    @FXML
    private TextField adresseClientField;
    @FXML
    private TextField telephoneClientField;
    @FXML
    private TextField nomMedecinField;

    private ClientDAO clientDAO;
    private ClientController clientController;

    @FXML
    public void initialize() {
        clientDAO = new ClientDAO();
    }

    @FXML
    private void handleAjouterClient() {
        String nomClient = nomClientField.getText();
        String prenomClient = prenomClientField.getText();
        LocalDate dateNaissanceClient = dateNaissanceClientPicker.getValue();
        String adresseClient = adresseClientField.getText();
        String telephoneClient = telephoneClientField.getText();
        String nomMedecin = nomMedecinField.getText();

        Client client = new Client(0, nomClient, prenomClient, dateNaissanceClient, adresseClient, telephoneClient, nomMedecin, LocalDate.now());
        clientDAO.addClient(client);

        if (clientController != null) {
            clientController.refreshClientList();
        }

        // Fermer la fenêtre d'ajout de client
        nomClientField.getScene().getWindow().hide();
    }

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }
}