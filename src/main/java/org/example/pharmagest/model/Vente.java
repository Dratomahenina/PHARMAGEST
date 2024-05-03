package org.example.pharmagest.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class Vente {
    private IntegerProperty idVente;
    private ObjectProperty<Client> client;
    private StringProperty typeVente;
    private DoubleProperty montantTotal;
    private ObjectProperty<LocalDate> dateVente;
    private StringProperty statut;
    private ObservableList<LigneVente> lignesVente;
    private ObjectProperty<LocalDate> datePaiement;
    private DoubleProperty profit;

    public Vente(int idVente, Client client, String typeVente, double montantTotal, LocalDate dateVente, String statut, ObservableList<LigneVente> lignesVente, LocalDate datePaiement) {
        this.idVente = new SimpleIntegerProperty(idVente);
        this.client = new SimpleObjectProperty<>(client);
        this.typeVente = new SimpleStringProperty(typeVente);
        this.montantTotal = new SimpleDoubleProperty(montantTotal);
        this.dateVente = new SimpleObjectProperty<>(dateVente);
        this.statut = new SimpleStringProperty(statut);
        this.lignesVente = lignesVente != null ? FXCollections.observableArrayList(lignesVente) : FXCollections.observableArrayList();
        this.datePaiement = new SimpleObjectProperty<>(datePaiement);
        this.profit = new SimpleDoubleProperty(0);
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

    public String getTypeVente() {
        return typeVente.get();
    }

    public StringProperty typeVenteProperty() {
        return typeVente;
    }

    public void setTypeVente(String typeVente) {
        this.typeVente.set(typeVente);
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

    public LocalDate getDateVente() {
        return dateVente.get();
    }

    public ObjectProperty<LocalDate> dateVenteProperty() {
        return dateVente;
    }

    public void setDateVente(LocalDate dateVente) {
        this.dateVente.set(dateVente);
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

    public ObservableList<LigneVente> getLignesVente() {
        return lignesVente;
    }

    public void setLignesVente(ObservableList<LigneVente> lignesVente) {
        this.lignesVente = FXCollections.observableArrayList(lignesVente);
    }

    public LocalDate getDatePaiement() {
        return datePaiement.get();
    }

    public ObjectProperty<LocalDate> datePaiementProperty() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement.set(datePaiement);
    }

    public double getProfit() {
        return profit.get();
    }

    public DoubleProperty profitProperty() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit.set(profit);
    }
}