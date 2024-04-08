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
import org.example.pharmagest.dao.FamilleDAO;
import org.example.pharmagest.model.Famille;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class FamilleController {

    @FXML
    private TableView<Famille> familleTable;
    @FXML
    private TableColumn<Famille, Integer> idFamilleColumn;
    @FXML
    private TableColumn<Famille, String> nomFamilleColumn;
    @FXML
    private TableColumn<Famille, String> statutColumn;
    @FXML
    private TableColumn<Famille, Void> actionColumn;
    @FXML
    private TextField searchField;
    private ObservableList<Famille> familleList;
    private FamilleDAO familleDAO;
    private boolean idFamilleSortAscending = true;
    private boolean nomFamilleSortAscending = true;

    @FXML
    public void initialize() {
        // Initialiser les colonnes du tableau
        idFamilleColumn.setCellValueFactory(new PropertyValueFactory<>("idFamille"));
        nomFamilleColumn.setCellValueFactory(new PropertyValueFactory<>("nomFamille"));
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
                    Famille famille = getTableView().getItems().get(getIndex());
                    modifierFamille(famille);
                });

                supprimerButton.setOnAction(event -> {
                    Famille famille = getTableView().getItems().get(getIndex());
                    supprimerFamille(famille);
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

        familleDAO = new FamilleDAO();
        refreshFamilleList();
    }

    @FXML
    private void handleAjouterFamille() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/pharmagest/ajouterfamille.fxml"));
            Parent root = loader.load();
            AjouterFamilleController ajouterFamilleController = loader.getController();
            ajouterFamilleController.setFamilleController(this);

            Stage stage = new Stage();
            stage.setTitle("Ajouter une famille");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRefreshFamille() {
        refreshFamilleList();
    }

    public void refreshFamilleList() {
        familleList = FXCollections.observableArrayList(familleDAO.getAllFamilles());
        familleTable.setItems(familleList);
    }

    private void modifierFamille(Famille famille) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/pharmagest/modifierfamille.fxml"));
            Parent root = loader.load();
            ModifierFamilleController modifierFamilleController = loader.getController();
            modifierFamilleController.setFamille(famille);
            modifierFamilleController.setFamilleController(this);

            Stage stage = new Stage();
            stage.setTitle("Modifier une famille");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void supprimerFamille(Famille famille) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Êtes-vous sûr de vouloir supprimer cette famille ?");
        alert.setContentText("Cette action est irréversible.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                familleDAO.deleteFamille(famille);
                refreshFamilleList();
            }
        });
    }

    @FXML
    private void handleSearch() {
        String searchTerm = searchField.getText().toLowerCase();
        List<Famille> searchResults = familleDAO.searchFamilles(searchTerm);
        familleList = FXCollections.observableArrayList(searchResults);
        familleTable.setItems(familleList);
    }

    @FXML
    private void handleShowAllFamilles() {
        searchField.clear();
        refreshFamilleList();
    }

    @FXML
    private void handleIdFamilleSort() {
        familleList.sort((f1, f2) -> {
            if (idFamilleSortAscending) {
                return Integer.compare(f1.getIdFamille(), f2.getIdFamille());
            } else {
                return Integer.compare(f2.getIdFamille(), f1.getIdFamille());
            }
        });
        idFamilleSortAscending = !idFamilleSortAscending;
    }

    @FXML
    private void handleNomFamilleSort() {
        familleList.sort((f1, f2) -> {
            if (nomFamilleSortAscending) {
                return f1.getNomFamille().compareToIgnoreCase(f2.getNomFamille());
            } else {
                return f2.getNomFamille().compareToIgnoreCase(f1.getNomFamille());
            }
        });
        nomFamilleSortAscending = !nomFamilleSortAscending;
    }
}