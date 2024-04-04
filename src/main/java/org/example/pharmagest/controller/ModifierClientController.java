package org.example.pharmagest.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.example.pharmagest.dao.ClientDAO;
import org.example.pharmagest.model.Client;

import java.time.LocalDate;

public class ModifierClientController {

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
    private Client client;

    @FXML
    public void initialize() {
        clientDAO = new ClientDAO();
        statutChoiceBox.getItems().addAll("actif", "inactif");
    }

    public void setClient(Client client) {
        this.client = client;
        populateFields();
    }

    private void populateFields() {
        nomClientField.setText(client.getNomClient());
        prenomClientField.setText(client.getPrenomClient());
        dateNaissanceClientPicker.setValue(client.getDateNaissanceClient());
        adresseClientField.setText(client.getAdresseClient());
        telephoneClientField.setText(client.getTelephoneClient());
        statutChoiceBox.setValue(client.getStatut());
    }

    @FXML
    private void handleModifierClient() {
        String nomClient = nomClientField.getText();
        String prenomClient = prenomClientField.getText();
        LocalDate dateNaissanceClient = dateNaissanceClientPicker.getValue();
        String adresseClient = adresseClientField.getText();
        String telephoneClient = telephoneClientField.getText();
        String statut = statutChoiceBox.getValue();

        client.setNomClient(nomClient);
        client.setPrenomClient(prenomClient);
        client.setDateNaissanceClient(dateNaissanceClient);
        client.setAdresseClient(adresseClient);
        client.setTelephoneClient(telephoneClient);
        client.setStatut(statut);

        clientDAO.updateClient(client);
        clientController.refreshClientList();
        closeWindow();
    }

    @FXML
    private void handleAnnulerModification() {
        closeWindow();
    }

    private void closeWindow() {
        nomClientField.getScene().getWindow().hide();
    }

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }
}