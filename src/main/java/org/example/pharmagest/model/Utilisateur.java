package org.example.pharmagest.model;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Utilisateur {
    private IntegerProperty idUtilisateur;
    private StringProperty nomUtilisateur;
    private StringProperty motDePasse;
    private StringProperty role;
    private ObjectProperty<LocalDate> dateCreation;

    public Utilisateur(int idUtilisateur, String nomUtilisateur, String motDePasse, String role, LocalDate dateCreation) {
        this.idUtilisateur = new SimpleIntegerProperty(idUtilisateur);
        this.nomUtilisateur = new SimpleStringProperty(nomUtilisateur);
        this.motDePasse = new SimpleStringProperty(motDePasse);
        this.role = new SimpleStringProperty(role);
        this.dateCreation = new SimpleObjectProperty<>(dateCreation);
    }

    public int getIdUtilisateur() {
        return idUtilisateur.get();
    }

    public IntegerProperty idUtilisateurProperty() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur.set(idUtilisateur);
    }

    public String getNomUtilisateur() {
        return nomUtilisateur.get();
    }

    public StringProperty nomUtilisateurProperty() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur.set(nomUtilisateur);
    }

    public String getMotDePasse() {
        return motDePasse.get();
    }

    public StringProperty motDePasseProperty() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse.set(motDePasse);
    }

    public String getRole() {
        return role.get();
    }

    public StringProperty roleProperty() {
        return role;
    }

    public void setRole(String role) {
        this.role.set(role);
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