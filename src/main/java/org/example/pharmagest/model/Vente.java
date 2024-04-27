package org.example.pharmagest.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Vente {
    private IntegerProperty idVente;
    private ObjectProperty<Client> client;
    private ObjectProperty<LocalDate> dateVente;
    private StringProperty typeVente;
    private StringProperty statut;
    private DoubleProperty montantTotal;
    private DoubleProperty remise;
    private ObservableList<VenteMedicament> medicaments;
    private ObjectProperty<LocalDateTime> datePaiement;

    public Vente() {
        this.idVente = new SimpleIntegerProperty();
        this.client = new SimpleObjectProperty<>();
        this.dateVente = new SimpleObjectProperty<>();
        this.typeVente = new SimpleStringProperty();
        this.statut = new SimpleStringProperty();
        this.montantTotal = new SimpleDoubleProperty();
        this.remise = new SimpleDoubleProperty();
        this.medicaments = FXCollections.observableArrayList();
        this.datePaiement = new SimpleObjectProperty<>();
    }

    // Getters et setters

    public int getIdVente() {
        return idVente.get();
    }

    public IntegerProperty idVenteProperty() {
        return idVente;
    }

    public void setIdVente(int idVente) {
        this.idVente.set(idVente);
    }

    public Client getClient() {
        return client.get();
    }

    public ObjectProperty<Client> clientProperty() {
        return client;
    }

    public void setClient(Client client) {
        this.client.set(client);
    }

    public LocalDate getDateVente() {
        return dateVente.get();
    }

    public ObjectProperty<LocalDate> dateVenteProperty() {
        return dateVente;
    }

    public void setDateVente(LocalDate dateVente) {
        this.dateVente.set(dateVente);
    }

    public String getTypeVente() {
        return typeVente.get();
    }

    public StringProperty typeVenteProperty() {
        return typeVente;
    }

    public void setTypeVente(String typeVente) {
        this.typeVente.set(typeVente);
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

    public double getMontantTotal() {
        return montantTotal.get();
    }

    public DoubleProperty montantTotalProperty() {
        return montantTotal;
    }

    public void setMontantTotal(double montantTotal) {
        this.montantTotal.set(montantTotal);
    }

    public double getRemise() {
        return remise.get();
    }

    public DoubleProperty remiseProperty() {
        return remise;
    }

    public void setRemise(double remise) {
        this.remise.set(remise);
    }

    public ObservableList<VenteMedicament> getMedicaments() {
        return medicaments;
    }

    public void setMedicaments(ObservableList<VenteMedicament> medicaments) {
        this.medicaments = medicaments;
    }

    public LocalDateTime getDatePaiement() {
        return datePaiement.get();
    }

    public ObjectProperty<LocalDateTime> datePaiementProperty() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDateTime datePaiement) {
        this.datePaiement.set(datePaiement);
    }
}