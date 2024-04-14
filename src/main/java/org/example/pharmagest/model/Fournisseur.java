package org.example.pharmagest.model;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Fournisseur {
    private IntegerProperty idFournisseur;
    private StringProperty nomFournisseur;
    private StringProperty emailFournisseur;
    private StringProperty telFournisseur;
    private StringProperty adresseFournisseur;
    private StringProperty statut;
    private ObjectProperty<LocalDate> dateCreation;

    @Override
    public String toString() {
        return this.getNomFournisseur();
    }

    public Fournisseur(int idFournisseur, String nomFournisseur, String emailFournisseur, String telFournisseur, String adresseFournisseur, String statut, LocalDate dateCreation) {
        this.idFournisseur = new SimpleIntegerProperty(idFournisseur);
        this.nomFournisseur = new SimpleStringProperty(nomFournisseur);
        this.emailFournisseur = new SimpleStringProperty(emailFournisseur);
        this.telFournisseur = new SimpleStringProperty(telFournisseur);
        this.adresseFournisseur = new SimpleStringProperty(adresseFournisseur);
        this.statut = new SimpleStringProperty(statut);
        this.dateCreation = new SimpleObjectProperty<>(dateCreation);
    }

    // Getters et Setters pour les propriétés

    public int getIdFournisseur() {
        return idFournisseur.get();
    }

    public IntegerProperty idFournisseurProperty() {
        return idFournisseur;
    }

    public void setIdFournisseur(int idFournisseur) {
        this.idFournisseur.set(idFournisseur);
    }

    public String getNomFournisseur() {
        return nomFournisseur.get();
    }

    public StringProperty nomFournisseurProperty() {
        return nomFournisseur;
    }

    public void setNomFournisseur(String nomFournisseur) {
        this.nomFournisseur.set(nomFournisseur);
    }

    public String getEmailFournisseur() {
        return emailFournisseur.get();
    }

    public StringProperty emailFournisseurProperty() {
        return emailFournisseur;
    }

    public void setEmailFournisseur(String emailFournisseur) {
        this.emailFournisseur.set(emailFournisseur);
    }

    public String getTelFournisseur() {
        return telFournisseur.get();
    }

    public StringProperty telFournisseurProperty() {
        return telFournisseur;
    }

    public void setTelFournisseur(String telFournisseur) {
        this.telFournisseur.set(telFournisseur);
    }

    public String getAdresseFournisseur() {
        return adresseFournisseur.get();
    }

    public StringProperty adresseFournisseurProperty() {
        return adresseFournisseur;
    }

    public void setAdresseFournisseur(String adresseFournisseur) {
        this.adresseFournisseur.set(adresseFournisseur);
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
