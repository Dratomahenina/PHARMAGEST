package org.example.pharmagest.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
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
    private ChoiceBox<String> statutChoiceBox;

    private ClientDAO clientDAO;
    private ClientController clientController;
    private VenteController venteController;


    @FXML
    public void initialize() {
        clientDAO = new ClientDAO();
        statutChoiceBox.getItems().addAll("actif", "inactif");
        statutChoiceBox.setValue("actif"); // Valeur par défaut
    }

    @FXML
    private void handleAjouterClient() {
        String nomClient = nomClientField.getText();
        String prenomClient = prenomClientField.getText();
        LocalDate dateNaissanceClient = dateNaissanceClientPicker.getValue();
        String adresseClient = adresseClientField.getText();
        String telephoneClient = telephoneClientField.getText();
        String statut = statutChoiceBox.getValue();

        Client client = new Client(0, nomClient, prenomClient, dateNaissanceClient, adresseClient, telephoneClient, statut, LocalDate.now());
        clientDAO.addClient(client);

        // Fermer la fenêtre d'ajout de client
        nomClientField.getScene().getWindow().hide();
    }

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    public void setClientController(VenteController venteController) {
        this.venteController = venteController;
    }
}