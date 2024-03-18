package org.example.pharmagest.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.pharmagest.dao.ClientDAO;
import org.example.pharmagest.model.Client;

import java.io.IOException;
import java.time.LocalDate;

public class ClientController {

    @FXML
    private TableView<Client> clientTable;
    @FXML
    private TableColumn<Client, Integer> idClientColumn;
    @FXML
    private TableColumn<Client, String> nomClientColumn;
    @FXML
    private TableColumn<Client, String> prenomClientColumn;
    @FXML
    private TableColumn<Client, LocalDate> dateNaissanceClientColumn;
    @FXML
    private TableColumn<Client, String> adresseClientColumn;
    @FXML
    private TableColumn<Client, String> telephoneClientColumn;
    @FXML
    private TableColumn<Client, String> nomMedecinColumn;
    @FXML
    private TableColumn<Client, LocalDate> dateCreationColumn;

    private ObservableList<Client> clientList;
    private ClientDAO clientDAO;

    @FXML
    public void initialize() {
        // Initialiser les colonnes du tableau
        idClientColumn.setCellValueFactory(new PropertyValueFactory<>("idClient"));
        nomClientColumn.setCellValueFactory(new PropertyValueFactory<>("nomClient"));
        prenomClientColumn.setCellValueFactory(new PropertyValueFactory<>("prenomClient"));
        dateNaissanceClientColumn.setCellValueFactory(new PropertyValueFactory<>("dateNaissanceClient"));
        adresseClientColumn.setCellValueFactory(new PropertyValueFactory<>("adresseClient"));
        telephoneClientColumn.setCellValueFactory(new PropertyValueFactory<>("telephoneClient"));
        nomMedecinColumn.setCellValueFactory(new PropertyValueFactory<>("nomMedecin"));
        dateCreationColumn.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));

        clientDAO = new ClientDAO();
        clientList = FXCollections.observableArrayList(clientDAO.getAllClients());

        // Affecter les données au tableau
        clientTable.setItems(clientList);
    }

    @FXML
    private void handleAjouterClient() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/pharmagest/ajouterclient.fxml"));
            Parent root = loader.load();
            AjouterClientController ajouterClientController = loader.getController();
            ajouterClientController.setClientController(this);

            Stage stage = new Stage();
            stage.setTitle("Ajouter un client");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshClientList() {
        clientList.clear();
        clientList.addAll(clientDAO.getAllClients());
    }
}