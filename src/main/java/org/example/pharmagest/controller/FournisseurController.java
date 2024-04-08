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
import org.example.pharmagest.dao.FournisseurDAO;
import org.example.pharmagest.model.Fournisseur;

import java.io.IOException;
import java.util.List;

public class FournisseurController {

    @FXML
    private TableView<Fournisseur> fournisseurTable;
    @FXML
    private TableColumn<Fournisseur, Integer> idFournisseurColumn;
    @FXML
    private TableColumn<Fournisseur, String> nomFournisseurColumn;
    @FXML
    private TableColumn<Fournisseur, String> emailFournisseurColumn;
    @FXML
    private TableColumn<Fournisseur, String> telFournisseurColumn;
    @FXML
    private TableColumn<Fournisseur, String> adresseFournisseurColumn;
    @FXML
    private TableColumn<Fournisseur, String> statutColumn;
    @FXML
    private TableColumn<Fournisseur, Void> actionColumn;
    @FXML
    private TextField searchField;
    private ObservableList<Fournisseur> fournisseurList;
    private FournisseurDAO fournisseurDAO;
    private boolean idFournisseurSortAscending = true;
    private boolean nomFournisseurSortAscending = true;

    @FXML
    public void initialize() {
        // Initialiser les colonnes du tableau
        idFournisseurColumn.setCellValueFactory(new PropertyValueFactory<>("idFournisseur"));
        nomFournisseurColumn.setCellValueFactory(new PropertyValueFactory<>("nomFournisseur"));
        emailFournisseurColumn.setCellValueFactory(new PropertyValueFactory<>("emailFournisseur"));
        telFournisseurColumn.setCellValueFactory(new PropertyValueFactory<>("telFournisseur"));
        adresseFournisseurColumn.setCellValueFactory(new PropertyValueFactory<>("adresseFournisseur"));
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
                    Fournisseur fournisseur = getTableView().getItems().get(getIndex());
                    modifierFournisseur(fournisseur);
                });

                supprimerButton.setOnAction(event -> {
                    Fournisseur fournisseur = getTableView().getItems().get(getIndex());
                    supprimerFournisseur(fournisseur);
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

        fournisseurDAO = new FournisseurDAO();
        refreshFournisseurList();
    }

    @FXML
    private void handleAjouterFournisseur() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/pharmagest/ajouterfournisseur.fxml"));
            Parent root = loader.load();
            AjouterFournisseurController ajouterFournisseurController = loader.getController();
            ajouterFournisseurController.setFournisseurController(this);

            Stage stage = new Stage();
            stage.setTitle("Ajouter un fournisseur");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRefreshFournisseur() {
        refreshFournisseurList();
    }

    public void refreshFournisseurList() {
        fournisseurList = FXCollections.observableArrayList(fournisseurDAO.getAllFournisseurs());
        fournisseurTable.setItems(fournisseurList);
    }

    private void modifierFournisseur(Fournisseur fournisseur) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/pharmagest/modifierfournisseur.fxml"));
            Parent root = loader.load();
            ModifierFournisseurController modifierFournisseurController = loader.getController();
            modifierFournisseurController.setFournisseur(fournisseur);
            modifierFournisseurController.setFournisseurController(this);

            Stage stage = new Stage();
            stage.setTitle("Modifier un fournisseur");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void supprimerFournisseur(Fournisseur fournisseur) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Êtes-vous sûr de vouloir supprimer ce fournisseur ?");
        alert.setContentText("Cette action est irréversible.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                fournisseurDAO.deleteFournisseur(fournisseur);
                refreshFournisseurList();
            }
        });
    }

    @FXML
    private void handleSearch() {
        String searchTerm = searchField.getText().toLowerCase();
        List<Fournisseur> searchResults = fournisseurDAO.searchFournisseurs(searchTerm);
        fournisseurList = FXCollections.observableArrayList(searchResults);
        fournisseurTable.setItems(fournisseurList);
    }

    @FXML
    private void handleShowAllFournisseurs() {
        searchField.clear();
        refreshFournisseurList();
    }

    @FXML
    private void handleIdFournisseurSort() {
        fournisseurList.sort((f1, f2) -> {
            if (idFournisseurSortAscending) {
                return Integer.compare(f1.getIdFournisseur(), f2.getIdFournisseur());
            } else {
                return Integer.compare(f2.getIdFournisseur(), f1.getIdFournisseur());
            }
        });
        idFournisseurSortAscending = !idFournisseurSortAscending;
    }

    @FXML
    private void handleNomFournisseurSort() {
        fournisseurList.sort((f1, f2) -> {
            if (nomFournisseurSortAscending) {
                return f1.getNomFournisseur().compareToIgnoreCase(f2.getNomFournisseur());
            } else {
                return f2.getNomFournisseur().compareToIgnoreCase(f1.getNomFournisseur());
            }
        });
        nomFournisseurSortAscending = !nomFournisseurSortAscending;
    }
}