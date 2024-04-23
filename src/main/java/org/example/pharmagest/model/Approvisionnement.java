package org.example.pharmagest.model;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Approvisionnement {
    private IntegerProperty idApprovisionnement;
    private ObjectProperty<Medicament> medicament;
    private ObjectProperty<Fournisseur> fournisseur;
    private IntegerProperty quantiteCommandee;
    private ObjectProperty<LocalDate> dateApprovisionnement;
    private StringProperty statut;
    private StringProperty commentaire;

    private DoubleProperty prixFournisseur;

    public double getPrixFournisseur() {
        return prixFournisseur.get();
    }

    public DoubleProperty prixFournisseurProperty() {
        return prixFournisseur;
    }

    public void setPrixFournisseur(double prixFournisseur) {
        this.prixFournisseur.set(prixFournisseur);
    }

    public Approvisionnement(int idApprovisionnement, Medicament medicament, Fournisseur fournisseur, int quantiteCommandee, LocalDate dateApprovisionnement, String statut, Double prixFournisseur, int quantiteRecue, String commentaire) {
        this.idApprovisionnement = new SimpleIntegerProperty(idApprovisionnement);
        this.medicament = new SimpleObjectProperty<>(medicament);
        this.fournisseur = new SimpleObjectProperty<>(fournisseur);
        this.quantiteCommandee = new SimpleIntegerProperty(quantiteCommandee);
        this.dateApprovisionnement = new SimpleObjectProperty<>(dateApprovisionnement);
        this.prixFournisseur = new SimpleDoubleProperty(prixFournisseur);
        this.statut = new SimpleStringProperty(statut);
        this.commentaire = new SimpleStringProperty(commentaire);
    }

    // Getters et Setters

    public int getIdApprovisionnement() {
        return idApprovisionnement.get();
    }

    public IntegerProperty idApprovisionnementProperty() {
        return idApprovisionnement;
    }

    public void setIdApprovisionnement(int idApprovisionnement) {
        this.idApprovisionnement.set(idApprovisionnement);
    }

    public Medicament getMedicament() {
        return medicament.get();
    }

    public ObjectProperty<Medicament> medicamentProperty() {
        return medicament;
    }

    public void setMedicament(Medicament medicament) {
        this.medicament.set(medicament);
    }

    public Fournisseur getFournisseur() {
        return fournisseur.get();
    }

    public ObjectProperty<Fournisseur> fournisseurProperty() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur.set(fournisseur);
    }

    public int getQuantiteCommandee() {
        return quantiteCommandee.get();
    }

    public IntegerProperty quantiteCommandeeProperty() {
        return quantiteCommandee;
    }

    public void setQuantiteCommandee(int quantiteCommandee) {
        this.quantiteCommandee.set(quantiteCommandee);
    }

    public LocalDate getDateApprovisionnement() {
        return dateApprovisionnement.get();
    }

    public ObjectProperty<LocalDate> dateApprovisionnementProperty() {
        return dateApprovisionnement;
    }

    public void setDateApprovisionnement(LocalDate dateApprovisionnement) {
        this.dateApprovisionnement.set(dateApprovisionnement);
    }

    public String getStatut() {
        return statut.get();
    }

    public StringProperty statutProperty() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut.set(statut);
    }

    public String getCommentaire() {
        return commentaire.get();
    }

    public StringProperty commentaireProperty() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire.set(commentaire);
    }
}