package org.example.pharmagest.model;

import javafx.beans.property.*;

import java.time.LocalDateTime;

public class Caisse {
    private IntegerProperty idCaisse;
    private ObjectProperty<Vente> vente;
    private DoubleProperty montantDonne;
    private DoubleProperty montantRendu;
    private ObjectProperty<LocalDateTime> datePaiement;

    public Caisse() {
        this.idCaisse = new SimpleIntegerProperty();
        this.vente = new SimpleObjectProperty<>();
        this.montantDonne = new SimpleDoubleProperty();
        this.montantRendu = new SimpleDoubleProperty();
        this.datePaiement = new SimpleObjectProperty<>();
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

    public Vente getVente() {
        return vente.get();
    }

    public ObjectProperty<Vente> venteProperty() {
        return vente;
    }

    public void setVente(Vente vente) {
        this.vente.set(vente);
    }

    public double getMontantDonne() {
        return montantDonne.get();
    }

    public DoubleProperty montantDonneProperty() {
        return montantDonne;
    }

    public void setMontantDonne(double montantDonne) {
        this.montantDonne.set(montantDonne);
    }

    public double getMontantRendu() {
        return montantRendu.get();
    }

    public DoubleProperty montantRenduProperty() {
        return montantRendu;
    }

    public void setMontantRendu(double montantRendu) {
        this.montantRendu.set(montantRendu);
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