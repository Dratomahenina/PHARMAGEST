package org.example.pharmagest.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.pharmagest.dao.ClientDAO;
import org.example.pharmagest.model.Client;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

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
    private TableColumn<Client, String> statutColumn;
    @FXML
    private TableColumn<Client, Void> actionColumn;
    @FXML
    private TextField searchField;
    private ObservableList<Client> clientList;
    private ClientDAO clientDAO;
    private boolean idClientSortAscending = true;
    private boolean nomClientSortAscending = true;
    private boolean prenomClientSortAscending = true;

    @FXML
    public void initialize() {
        // Initialiser les colonnes du tableau
        clientDAO = new ClientDAO();
        idClientColumn.setCellValueFactory(new PropertyValueFactory<>("idClient"));
        nomClientColumn.setCellValueFactory(new PropertyValueFactory<>("nomClient"));
        prenomClientColumn.setCellValueFactory(new PropertyValueFactory<>("prenomClient"));
        dateNaissanceClientColumn.setCellValueFactory(new PropertyValueFactory<>("dateNaissanceClient"));
        adresseClientColumn.setCellValueFactory(new PropertyValueFactory<>("adresseClient"));
        telephoneClientColumn.setCellValueFactory(new PropertyValueFactory<>("telephoneClient"));
        statutColumn.setCellValueFactory(new PropertyValueFactory<>("statut"));

        // Configurer la colonne "Action"
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button modifierButton = new Button();
            private final Button supprimerButton = new Button();

            {
                // Charger les images pour les icônes des boutons
                ImageView editIcon = new ImageView(new Image(getClass().getResourceAsStream("/org/example/pharmagest/assets/edit.png")));
                ImageView deleteIcon = new ImageView(new Image(getClass().getResourceAsStream("/org/example/pharmagest/assets/delete.png")));

                // Redimensionner les icônes à une taille appropriée
                editIcon.setFitWidth(18);
                editIcon.setFitHeight(18);
                deleteIcon.setFitWidth(18);
                deleteIcon.setFitHeight(18);

                // Définir les icônes pour les boutons
                modifierButton.setGraphic(editIcon);
                supprimerButton.setGraphic(deleteIcon);

                modifierButton.setOnAction(event -> {
                    Client client = getTableView().getItems().get(getIndex());
                    modifierClient(client);
                });

                supprimerButton.setOnAction(event -> {
                    Client client = getTableView().getItems().get(getIndex());
                    supprimerClient(client);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new HBox(5, modifierButton, supprimerButton));
                }
            }
        });

        clientDAO = new ClientDAO();
        refreshClientList();
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

    @FXML
    private void handleRefreshClient() {
        refreshClientList();
    }

    public void refreshClientList() {
        clientList = FXCollections.observableArrayList(clientDAO.getAllClients());
        clientTable.setItems(clientList);
    }

    private void modifierClient(Client client) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/pharmagest/modifierclient.fxml"));
            Parent root = loader.load();
            ModifierClientController modifierClientController = loader.getController();
            modifierClientController.setClient(client);
            modifierClientController.setClientController(this);

            Stage stage = new Stage();
            stage.setTitle("Modifier un client");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void supprimerClient(Client client) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Êtes-vous sûr de vouloir supprimer ce client ?");
        alert.setContentText("Cette action est irréversible.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                clientDAO.deleteClient(client);
                refreshClientList();
            }
        });
    }

    @FXML
    private void handleSearch() {
        String searchTerm = searchField.getText().toLowerCase();
        List<Client> searchResults = clientDAO.searchClients(searchTerm);
        clientList = FXCollections.observableArrayList(searchResults);
        clientTable.setItems(clientList);
    }

    @FXML
    private void handleShowAllClients() {
        searchField.clear();
        refreshClientList();
    }

    @FXML
    private void handleIdClientSort(MouseEvent event) {
        clientList.sort((c1, c2) -> {
            if (idClientSortAscending) {
                return Integer.compare(c1.getIdClient(), c2.getIdClient());
            } else {
                return Integer.compare(c2.getIdClient(), c1.getIdClient());
            }
        });
        idClientSortAscending = !idClientSortAscending;
    }

    @FXML
    private void handleNomClientSort(MouseEvent event) {
        clientList.sort((c1, c2) -> {
            if (nomClientSortAscending) {
                return c1.getNomClient().compareToIgnoreCase(c2.getNomClient());
            } else {
                return c2.getNomClient().compareToIgnoreCase(c1.getNomClient());
            }
        });
        nomClientSortAscending = !nomClientSortAscending;
    }

    @FXML
    private void handlePrenomClientSort(MouseEvent event) {
        clientList.sort((c1, c2) -> {
            if (prenomClientSortAscending) {
                return c1.getPrenomClient().compareToIgnoreCase(c2.getPrenomClient());
            } else {
                return c2.getPrenomClient().compareToIgnoreCase(c1.getPrenomClient());
            }
        });
        prenomClientSortAscending = !prenomClientSortAscending;
    }
}