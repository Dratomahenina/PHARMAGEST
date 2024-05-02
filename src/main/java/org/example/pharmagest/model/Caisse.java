package org.example.pharmagest.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class Caisse {
    private IntegerProperty idCaisse;
    private ObjectProperty<LocalDate> dateCaisse;
    private DoubleProperty montantInitial;
    private DoubleProperty totalVentes;
    private DoubleProperty totalRetraits;
    private DoubleProperty montantFinal;
    private ObservableList<Vente> ventes;

    public Caisse(int idCaisse, LocalDate dateCaisse, double montantInitial, double totalVentes, double totalRetraits, double montantFinal, ObservableList<Vente> ventes) {
        this.idCaisse = new SimpleIntegerProperty(idCaisse);
        this.dateCaisse = new SimpleObjectProperty<>(dateCaisse);
        this.montantInitial = new SimpleDoubleProperty(montantInitial);
        this.totalVentes = new SimpleDoubleProperty(totalVentes);
        this.totalRetraits = new SimpleDoubleProperty(totalRetraits);
        this.montantFinal = new SimpleDoubleProperty(montantFinal);
        this.ventes = FXCollections.observableArrayList(ventes);
    }

    // Getters et setters

    public int getIdCaisse() {
        return idCaisse.get();
    }

    public IntegerProperty idCaisseProperty() {
        return idCaisse;
    }

    public void setIdCaisse(int idCaisse) {
        this.idCaisse.set(idCaisse);
    }

    public LocalDate getDateCaisse() {
        return dateCaisse.get();
    }

    public ObjectProperty<LocalDate> dateCaisseProperty() {
        return dateCaisse;
    }

    public void setDateCaisse(LocalDate dateCaisse) {
        this.dateCaisse.set(dateCaisse);
    }

    public double getMontantInitial() {
        return montantInitial.get();
    }

    public DoubleProperty montantInitialProperty() {
        return montantInitial;
    }

    public void setMontantInitial(double montantInitial) {
        this.montantInitial.set(montantInitial);
    }

    public double getTotalVentes() {
        return totalVentes.get();
    }

    public DoubleProperty totalVentesProperty() {
        return totalVentes;
    }

    public void setTotalVentes(double totalVentes) {
        this.totalVentes.set(totalVentes);
    }

    public double getTotalRetraits() {
        return totalRetraits.get();
    }

    public DoubleProperty totalRetraitsProperty() {
        return totalRetraits;
    }

    public void setTotalRetraits(double totalRetraits) {
        this.totalRetraits.set(totalRetraits);
    }

    public double getMontantFinal() {
        return montantFinal.get();
    }

    public DoubleProperty montantFinalProperty() {
        return montantFinal;
    }

    public void setMontantFinal(double montantFinal) {
        this.montantFinal.set(montantFinal);
    }

    public ObservableList<Vente> getVentes() {
        return ventes;
    }

    public void setVentes(ObservableList<Vente> ventes) {
        this.ventes = FXCollections.observableArrayList(ventes);
    }
}