package org.example.pharmagest.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.example.pharmagest.dao.MedicamentDAO;
import org.example.pharmagest.model.Medicament;

public class AjouterMedicamentController {

    @FXML
    private TextField nomMedicamentField;
    @FXML
    private ChoiceBox<String> categorieChoiceBox;
    @FXML
    private ChoiceBox<String> formeChoiceBox;
    @FXML
    private ChoiceBox<String> statutChoiceBox;

    private MedicamentDAO medicamentDAO;
    private MedicamentController medicamentController;

    @FXML
    public void initialize() {
        medicamentDAO = new MedicamentDAO();
        categorieChoiceBox.getItems().addAll("Antibiotique", "Antidouleur", "Antihistaminique", "Allergologie", "Anesthésie réanimation", "Antalgiques", "Anti-inflammatoires", "Cancérologie et hématologie", "Cardiologie et angéiologie", "Contraception et interruption de grossesse", "Dermatologie", "Endocrinologie", "Gastro-Entéro-Hépatologie", "Gynécologie", "Hémostase et sang", "Immunologie", "Infectiologie - Parasitologie", "Métabolisme et nutrition", "Neurologie-psychiatrie", "Ophtalmologie", "Oto-rhino-laryngologie", "Pneumologie", "Produits diagnostiques ou autres produits thérapeutiqu...", "Rhumatologie", "Sang et dérivés", "Souches Homéopathiques", "Stomatologie", "Toxicologie", "Urologie néphrologie");
        formeChoiceBox.getItems().addAll("Comprimé", "Gélule", "Sirop", "Suppositoire", "Bain de bouche", "Gomme", "Capsule", "Lotion", "Collyre", "Pansement", "Shampooing", "Crème");
        statutChoiceBox.getItems().addAll("actif", "inactif");
    }

    @FXML
    private void handleAjouterMedicament() {
        String nomMedicament = nomMedicamentField.getText();
        String categorie = categorieChoiceBox.getValue();
        String forme = formeChoiceBox.getValue();
        String statut = statutChoiceBox.getValue();

        Medicament medicament = new Medicament(0, nomMedicament, categorie, forme, statut);
        medicamentDAO.addMedicament(medicament);

        if (medicamentController != null) {
            medicamentController.refreshMedicamentList();
        }

        // Fermer la fenêtre d'ajout de médicament
        nomMedicamentField.getScene().getWindow().hide();
    }

    public void setMedicamentController(MedicamentController medicamentController) {
        this.medicamentController = medicamentController;
    }
}