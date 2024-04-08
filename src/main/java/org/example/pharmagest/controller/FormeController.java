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
import org.example.pharmagest.dao.FormeDAO;
import org.example.pharmagest.model.Forme;

import java.io.IOException;
import java.util.List;

public class FormeController {

    @FXML
    private TableView<Forme> formeTable;
    @FXML
    private TableColumn<Forme, Integer> idFormeColumn;
    @FXML
    private TableColumn<Forme, String> nomFormeColumn;
    @FXML
    private TableColumn<Forme, String> statutColumn;
    @FXML
    private TableColumn<Forme, Void> actionColumn;
    @FXML
    private TextField searchField;
    private ObservableList<Forme> formeList;
    private FormeDAO formeDAO;
    private boolean idFormeSortAscending = true;
    private boolean nomFormeSortAscending = true;

    @FXML
    public void initialize() {
        // Initialiser les colonnes du tableau
        idFormeColumn.setCellValueFactory(new PropertyValueFactory<>("idForme"));
        nomFormeColumn.setCellValueFactory(new PropertyValueFactory<>("nomForme"));
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
                    Forme forme = getTableView().getItems().get(getIndex());
                    modifierForme(forme);
                });

                supprimerButton.setOnAction(event -> {
                    Forme forme = getTableView().getItems().get(getIndex());
                    supprimerForme(forme);
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

        formeDAO = new FormeDAO();
        refreshFormeList();
    }

    @FXML
    private void handleAjouterForme() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/pharmagest/ajouterforme.fxml"));
            Parent root = loader.load();
            AjouterFormeController ajouterFormeController = loader.getController();
            ajouterFormeController.setFormeController(this);

            Stage stage = new Stage();
            stage.setTitle("Ajouter une forme");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRefreshForme() {
        refreshFormeList();
    }

    public void refreshFormeList() {
        formeList = FXCollections.observableArrayList(formeDAO.getAllFormes());
        formeTable.setItems(formeList);
    }

    private void modifierForme(Forme forme) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/pharmagest/modifierforme.fxml"));
            Parent root = loader.load();
            ModifierFormeController modifierFormeController = loader.getController();
            modifierFormeController.setForme(forme);
            modifierFormeController.setFormeController(this);

            Stage stage = new Stage();
            stage.setTitle("Modifier une forme");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void supprimerForme(Forme forme) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Êtes-vous sûr de vouloir supprimer cette forme ?");
        alert.setContentText("Cette action est irréversible.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                formeDAO.deleteForme(forme);
                refreshFormeList();
            }
        });
    }

    @FXML
    private void handleSearch() {
        String searchTerm = searchField.getText().toLowerCase();
        List<Forme> searchResults = formeDAO.searchFormes(searchTerm);
        formeList = FXCollections.observableArrayList(searchResults);
        formeTable.setItems(formeList);
    }

    @FXML
    private void handleShowAllFormes() {
        searchField.clear();
        refreshFormeList();
    }

    @FXML
    private void handleIdFormeSort() {
        formeList.sort((f1, f2) -> {
            if (idFormeSortAscending) {
                return Integer.compare(f1.getIdForme(), f2.getIdForme());
            } else {
                return Integer.compare(f2.getIdForme(), f1.getIdForme());
            }
        });
        idFormeSortAscending = !idFormeSortAscending;
    }

    @FXML
    private void handleNomFormeSort() {
        formeList.sort((f1, f2) -> {
            if (nomFormeSortAscending) {
                return f1.getNomForme().compareToIgnoreCase(f2.getNomForme());
            } else {
                return f2.getNomForme().compareToIgnoreCase(f1.getNomForme());
            }
        });
        nomFormeSortAscending = !nomFormeSortAscending;
    }
}