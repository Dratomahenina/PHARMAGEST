package org.example.pharmagest.model;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Forme {
    private IntegerProperty idForme;
    private StringProperty nomForme;
    private StringProperty statut;
    private ObjectProperty<LocalDate> dateCreation;

    @Override
    public String toString() {
        return this.getNomForme();
    }

    public Forme(int idForme, String nomForme, String statut, LocalDate dateCreation) {
        this.idForme = new SimpleIntegerProperty(idForme);
        this.nomForme = new SimpleStringProperty(nomForme);
        this.statut = new SimpleStringProperty(statut);
        this.dateCreation = new SimpleObjectProperty<>(dateCreation);
    }

    // Getters et Setters pour les propriétés

    public int getIdForme() {
        return idForme.get();
    }

    public IntegerProperty idFormeProperty() {
        return idForme;
    }

    public void setIdForme(int idForme) {
        this.idForme.set(idForme);
    }

    public String getNomForme() {
        return nomForme.get();
    }

    public StringProperty nomFormeProperty() {
        return nomForme;
    }

    public void setNomForme(String nomForme) {
        this.nomForme.set(nomForme);
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