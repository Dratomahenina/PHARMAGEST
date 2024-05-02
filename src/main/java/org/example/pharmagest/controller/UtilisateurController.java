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
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.pharmagest.dao.UtilisateurDAO;
import org.example.pharmagest.model.Utilisateur;

import java.io.IOException;
import java.util.List;

public class UtilisateurController {

    @FXML
    private TableView<Utilisateur> utilisateurTable;
    @FXML
    private TableColumn<Utilisateur, Integer> idUtilisateurColumn;
    @FXML
    private TableColumn<Utilisateur, String> nomUtilisateurColumn;
    @FXML
    private TableColumn<Utilisateur, String> motDePasseColumn;
    @FXML
    private TableColumn<Utilisateur, String> roleColumn;
    @FXML
    private TableColumn<Utilisateur, Void> actionColumn;
    @FXML
    private TextField searchField;
    private ObservableList<Utilisateur> utilisateurList;
    private UtilisateurDAO utilisateurDAO;
    private boolean idUtilisateurSortAscending = true;
    private boolean nomUtilisateurSortAscending = true;

    @FXML
    public void initialize() {
        // Initialiser les colonnes du tableau
        idUtilisateurColumn.setCellValueFactory(new PropertyValueFactory<>("idUtilisateur"));
        nomUtilisateurColumn.setCellValueFactory(new PropertyValueFactory<>("nomUtilisateur"));
        motDePasseColumn.setCellValueFactory(new PropertyValueFactory<>("motDePasse"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

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
                    Utilisateur utilisateur = getTableView().getItems().get(getIndex());
                    modifierUtilisateur(utilisateur);
                });

                supprimerButton.setOnAction(event -> {
                    Utilisateur utilisateur = getTableView().getItems().get(getIndex());
                    supprimerUtilisateur(utilisateur);
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

        utilisateurDAO = new UtilisateurDAO();
        refreshUtilisateurList();
    }

    @FXML
    private void handleAjouterUtilisateur() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/pharmagest/ajouterutilisateur.fxml"));
            Parent root = loader.load();
            AjouterUtilisateurController ajouterUtilisateurController = loader.getController();
            ajouterUtilisateurController.setUtilisateurController(this);

            Stage stage = new Stage();
            stage.setTitle("Ajouter un utilisateur");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRefreshUtilisateur() {
        refreshUtilisateurList();
    }

    public void refreshUtilisateurList() {
        utilisateurList = FXCollections.observableArrayList(utilisateurDAO.getAllUtilisateurs());
        utilisateurTable.setItems(utilisateurList);
    }

    private void modifierUtilisateur(Utilisateur utilisateur) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/pharmagest/modifierutilisateur.fxml"));
            Parent root = loader.load();
            ModifierUtilisateurController modifierUtilisateurController = loader.getController();
            modifierUtilisateurController.setUtilisateur(utilisateur);
            modifierUtilisateurController.setUtilisateurController(this);

            Stage stage = new Stage();
            stage.setTitle("Modifier un utilisateur");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void supprimerUtilisateur(Utilisateur utilisateur) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Êtes-vous sûr de vouloir supprimer cet utilisateur ?");
        alert.setContentText("Cette action est irréversible.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                utilisateurDAO.deleteUtilisateur(utilisateur);
                refreshUtilisateurList();
            }
        });
    }

    @FXML
    private void handleSearch() {
        String searchTerm = searchField.getText().toLowerCase();
        List<Utilisateur> searchResults = utilisateurDAO.searchUtilisateurs(searchTerm);
        utilisateurList = FXCollections.observableArrayList(searchResults);
        utilisateurTable.setItems(utilisateurList);
    }

    @FXML
    private void handleShowAllUtilisateurs() {
        searchField.clear();
        refreshUtilisateurList();
    }

    @FXML
    private void handleIdUtilisateurSort() {
        utilisateurList.sort((u1, u2) -> {
            if (idUtilisateurSortAscending) {
                return Integer.compare(u1.getIdUtilisateur(), u2.getIdUtilisateur());
            } else {
                return Integer.compare(u2.getIdUtilisateur(), u1.getIdUtilisateur());
            }
        });
        idUtilisateurSortAscending = !idUtilisateurSortAscending;
    }

    @FXML
    private void handleNomUtilisateurSort() {
        utilisateurList.sort((u1, u2) -> {
            if (nomUtilisateurSortAscending) {
                return u1.getNomUtilisateur().compareToIgnoreCase(u2.getNomUtilisateur());
            } else {
                return u2.getNomUtilisateur().compareToIgnoreCase(u1.getNomUtilisateur());
            }
        });
        nomUtilisateurSortAscending = !nomUtilisateurSortAscending;
    }
}