package org.example.pharmagest.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.example.pharmagest.dao.MedicamentDAO;
import org.example.pharmagest.model.Medicament;

public class ModifierMedicamentController {

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
    private Medicament medicament;

    @FXML
    public void initialize() {
        medicamentDAO = new MedicamentDAO();
        categorieChoiceBox.getItems().addAll("Antibiotique", "Antidouleur", "Antihistaminique", "Allergologie", "Anesthésie réanimation", "Antalgiques", "Anti-inflammatoires", "Cancérologie et hématologie", "Cardiologie et angéiologie", "Contraception et interruption de grossesse", "Dermatologie", "Endocrinologie", "Gastro-Entéro-Hépatologie", "Gynécologie", "Hémostase et sang", "Immunologie", "Infectiologie - Parasitologie", "Métabolisme et nutrition", "Neurologie-psychiatrie", "Ophtalmologie", "Oto-rhino-laryngologie", "Pneumologie", "Produits diagnostiques ou autres produits thérapeutiqu...", "Rhumatologie", "Sang et dérivés", "Souches Homéopathiques", "Stomatologie", "Toxicologie", "Urologie néphrologie");
        formeChoiceBox.getItems().addAll("Comprimé", "Gélule", "Sirop", "Suppositoire", "Bain de bouche", "Gomme", "Capsule", "Lotion", "Collyre", "Pansement", "Shampooing", "Crème");
        statutChoiceBox.getItems().addAll("actif", "inactif");
    }

    public void setMedicament(Medicament medicament) {
        this.medicament = medicament;
        populateFields();
    }

    private void populateFields() {
        nomMedicamentField.setText(medicament.getNomMedicament());
        categorieChoiceBox.setValue(medicament.getCategorieMedicament());
        formeChoiceBox.setValue(medicament.getFormeMedicament());
        statutChoiceBox.setValue(medicament.getStatut());
    }

    @FXML
    private void handleModifierMedicament() {
        String nomMedicament = nomMedicamentField.getText();
        String categorie = categorieChoiceBox.getValue();
        String forme = formeChoiceBox.getValue();
        String statut = statutChoiceBox.getValue();
        medicament.setStatut(statut);

        medicament.setNomMedicament(nomMedicament);
        medicament.setCategorieMedicament(categorie);
        medicament.setFormeMedicament(forme);

        medicamentDAO.updateMedicament(medicament);
        medicamentController.refreshMedicamentList();
        closeWindow();
    }

    @FXML
    private void handleAnnulerModification() {
        closeWindow();
    }

    private void closeWindow() {
        nomMedicamentField.getScene().getWindow().hide();
    }

    public void setMedicamentController(MedicamentController medicamentController) {
        this.medicamentController = medicamentController;
    }
}