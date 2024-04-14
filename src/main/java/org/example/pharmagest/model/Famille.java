package org.example.pharmagest.model;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Famille {
    private IntegerProperty idFamille;
    private StringProperty nomFamille;
    private StringProperty statut;
    private ObjectProperty<LocalDate> dateCreation;

    @Override
    public String toString() {
        return this.getNomFamille();
    }

    public Famille(int idFamille, String nomFamille, String statut, LocalDate dateCreation) {
        this.idFamille = new SimpleIntegerProperty(idFamille);
        this.nomFamille = new SimpleStringProperty(nomFamille);
        this.statut = new SimpleStringProperty(statut);
        this.dateCreation = new SimpleObjectProperty<>(dateCreation);
    }

    // Getters et Setters pour les propriétés

    public int getIdFamille() {
        return idFamille.get();
    }

    public IntegerProperty idFamilleProperty() {
        return idFamille;
    }

    public void setIdFamille(int idFamille) {
        this.idFamille.set(idFamille);
    }

    public String getNomFamille() {
        return nomFamille.get();
    }

    public StringProperty nomFamilleProperty() {
        return nomFamille;
    }

    public void setNomFamille(String nomFamille) {
        this.nomFamille.set(nomFamille);
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

    public LocalDate getDateCreation() {
        return dateCreation.get();
    }

    public ObjectProperty<LocalDate> dateCreationProperty() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation.set(dateCreation);
    }
}