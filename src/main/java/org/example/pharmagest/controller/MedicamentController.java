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
import org.example.pharmagest.dao.MedicamentDAO;
import org.example.pharmagest.model.Medicament;

import java.io.IOException;
import java.util.List;

public class MedicamentController {

    @FXML
    private TableView<Medicament> medicamentTable;

    @FXML
    private TableColumn<Medicament, Integer> idMedicamentColumn;

    @FXML
    private TableColumn<Medicament, String> nomMedicamentColumn;

    @FXML
    private TableColumn<Medicament, String> descriptionMedicamentColumn;

    @FXML
    private TableColumn<Medicament, String> fournisseurColumn;

    @FXML
    private TableColumn<Medicament, String> familleColumn;

    @FXML
    private TableColumn<Medicament, String> formeColumn;

    @FXML
    private TableColumn<Medicament, Integer> quantiteMedicamentColumn;

    @FXML
    private TableColumn<Medicament, Double> prixVenteColumn;

    @FXML
    private TableColumn<Medicament, String> statutColumn;

    @FXML
    private TableColumn<Medicament, Void> actionColumn;

    @FXML
    private TextField searchField;

    private ObservableList<Medicament> medicamentList;

    private MedicamentDAO medicamentDAO;

    private boolean idMedicamentSortAscending = true;
    private boolean nomMedicamentSortAscending = true;

    @FXML
    public void initialize() {
        // Initialiser les colonnes du tableau
        idMedicamentColumn.setCellValueFactory(new PropertyValueFactory<>("idMedicament"));
        nomMedicamentColumn.setCellValueFactory(new PropertyValueFactory<>("nomMedicament"));
        descriptionMedicamentColumn.setCellValueFactory(new PropertyValueFactory<>("descriptionMedicament"));
        fournisseurColumn.setCellValueFactory(cellData -> cellData.getValue().getFournisseur().nomFournisseurProperty());
        familleColumn.setCellValueFactory(cellData -> cellData.getValue().getFamille().nomFamilleProperty());
        formeColumn.setCellValueFactory(cellData -> cellData.getValue().getForme().nomFormeProperty());
        quantiteMedicamentColumn.setCellValueFactory(new PropertyValueFactory<>("quantiteMedicament"));
        prixVenteColumn.setCellValueFactory(new PropertyValueFactory<>("prixVente"));
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
                    Medicament medicament = getTableView().getItems().get(getIndex());
                    modifierMedicament(medicament);
                });

                supprimerButton.setOnAction(event -> {
                    Medicament medicament = getTableView().getItems().get(getIndex());
                    supprimerMedicament(medicament);
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

        medicamentDAO = new MedicamentDAO();
        refreshMedicamentList();
    }

    @FXML
    private void handleAjouterMedicament() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/pharmagest/ajoutermedicament.fxml"));
            Parent root = loader.load();
            AjouterMedicamentController ajouterMedicamentController = loader.getController();
            ajouterMedicamentController.setMedicamentController(this);

            Stage stage = new Stage();
            stage.setTitle("Ajouter un médicament");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRefreshMedicament() {
        refreshMedicamentList();
    }

    public void refreshMedicamentList() {
        medicamentList = FXCollections.observableArrayList(medicamentDAO.getAllMedicaments());
        medicamentTable.setItems(medicamentList);
    }

    private void modifierMedicament(Medicament medicament) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/pharmagest/modifiermedicament.fxml"));
            Parent root = loader.load();
            ModifierMedicamentController modifierMedicamentController = loader.getController();
            modifierMedicamentController.setMedicament(medicament);
            modifierMedicamentController.setMedicamentController(this);

            Stage stage = new Stage();
            stage.setTitle("Modifier un médicament");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void supprimerMedicament(Medicament medicament) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Êtes-vous sûr de vouloir supprimer ce médicament ?");
        alert.setContentText("Cette action est irréversible.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                medicamentDAO.deleteMedicament(medicament);
                refreshMedicamentList();
            }
        });
    }

    @FXML
    private void handleSearch() {
        String searchTerm = searchField.getText().toLowerCase();
        List<Medicament> searchResults = medicamentDAO.searchMedicaments(searchTerm);
        medicamentList = FXCollections.observableArrayList(searchResults);
        medicamentTable.setItems(medicamentList);
    }

    @FXML
    private void handleShowAllMedicaments() {
        searchField.clear();
        refreshMedicamentList();
    }

    @FXML
    private void handleIdMedicamentSort() {
        medicamentList.sort((m1, m2) -> {
            if (idMedicamentSortAscending) {
                return Integer.compare(m1.getIdMedicament(), m2.getIdMedicament());
            } else {
                return Integer.compare(m2.getIdMedicament(), m1.getIdMedicament());
            }
        });
        idMedicamentSortAscending = !idMedicamentSortAscending;
    }

    @FXML
    private void handleNomMedicamentSort() {
        medicamentList.sort((m1, m2) -> {
            if (nomMedicamentSortAscending) {
                return m1.getNomMedicament().compareToIgnoreCase(m2.getNomMedicament());
            } else {
                return m2.getNomMedicament().compareToIgnoreCase(m1.getNomMedicament());
            }
        });
        nomMedicamentSortAscending = !nomMedicamentSortAscending;
    }
}