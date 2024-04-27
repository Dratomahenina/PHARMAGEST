package org.example.pharmagest.model;

import javafx.beans.property.*;

public class Medicament {
    private IntegerProperty idMedicament;
    private StringProperty nomMedicament;
    private StringProperty descriptionMedicament;
    private ObjectProperty<Fournisseur> fournisseur;
    private ObjectProperty<Famille> famille;
    private ObjectProperty<Forme> forme;
    private IntegerProperty quantiteMedicament;
    private DoubleProperty prixVente;
    private DoubleProperty prixFournisseur;
    private StringProperty statut;

    @Override
    public String toString() {
        return nomMedicament.get();
    }

    public Medicament(int idMedicament, String nomMedicament, String descriptionMedicament,
                      Fournisseur fournisseur, Famille famille, Forme forme, int quantiteMedicament, double prixVente, double prixFournisseur, String statut) {
        this.idMedicament = new SimpleIntegerProperty(idMedicament);
        this.nomMedicament = new SimpleStringProperty(nomMedicament);
        this.descriptionMedicament = new SimpleStringProperty(descriptionMedicament);
        this.fournisseur = new SimpleObjectProperty<>(fournisseur);
        this.famille = new SimpleObjectProperty<>(famille);
        this.forme = new SimpleObjectProperty<>(forme);
        this.quantiteMedicament = new SimpleIntegerProperty(quantiteMedicament);
        this.prixVente = new SimpleDoubleProperty(prixVente);
        this.prixFournisseur = new SimpleDoubleProperty(prixFournisseur);
        this.statut = new SimpleStringProperty(statut);
    }

    // Getters et setters

    public int getIdMedicament() {
        return idMedicament.get();
    }

    public IntegerProperty idMedicamentProperty() {
        return idMedicament;
    }

    public void setIdMedicament(int idMedicament) {
        this.idMedicament.set(idMedicament);
    }

    public String getNomMedicament() {
        return nomMedicament.get();
    }

    public StringProperty nomMedicamentProperty() {
        return nomMedicament;
    }

    public void setNomMedicament(String nomMedicament) {
        this.nomMedicament.set(nomMedicament);
    }

    public String getDescriptionMedicament() {
        return descriptionMedicament.get();
    }

    public StringProperty descriptionMedicamentProperty() {
        return descriptionMedicament;
    }

    public void setDescriptionMedicament(String descriptionMedicament) {
        this.descriptionMedicament.set(descriptionMedicament);
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

    public Famille getFamille() {
        return famille.get();
    }

    public ObjectProperty<Famille> familleProperty() {
        return famille;
    }

    public void setFamille(Famille famille) {
        this.famille.set(famille);
    }

    public Forme getForme() {
        return forme.get();
    }

    public ObjectProperty<Forme> formeProperty() {
        return forme;
    }

    public void setForme(Forme forme) {
        this.forme.set(forme);
    }

    public int getQuantiteMedicament() {
        return quantiteMedicament.get();
    }

    public IntegerProperty quantiteMedicamentProperty() {
        return quantiteMedicament;
    }

    public void setQuantiteMedicament(int quantiteMedicament) {
        this.quantiteMedicament.set(quantiteMedicament);
    }

    public double getPrixVente() {
        return prixVente.get();
    }

    public DoubleProperty prixVenteProperty() {
        return prixVente;
    }

    public void setPrixVente(double prixVente) {
        this.prixVente.set(prixVente);
    }

    public double getPrixFournisseur() {
        return prixFournisseur.get();
    }

    public DoubleProperty prixFournisseurProperty() {
        return prixFournisseur;
    }

    public void setPrixFournisseur(double prixFournisseur) {
        this.prixFournisseur.set(prixFournisseur);
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
}