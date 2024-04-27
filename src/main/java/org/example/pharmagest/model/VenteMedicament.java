package org.example.pharmagest.model;

import javafx.beans.property.*;

import java.time.LocalDateTime;

public class VenteMedicament {
    private IntegerProperty idVenteMedicament;
    private IntegerProperty idVente;
    private ObjectProperty<Medicament> medicament;
    private IntegerProperty quantite;
    private DoubleProperty prixUnitaire;
    private DoubleProperty prixTotal;
    private ObjectProperty<LocalDateTime> dateVente;

    public VenteMedicament() {
        this.idVenteMedicament = new SimpleIntegerProperty();
        this.idVente = new SimpleIntegerProperty();
        this.medicament = new SimpleObjectProperty<>();
        this.quantite = new SimpleIntegerProperty();
        this.prixUnitaire = new SimpleDoubleProperty();
        this.prixTotal = new SimpleDoubleProperty();
        this.dateVente = new SimpleObjectProperty<>();
    }

    // Getters et setters

    public int getIdVenteMedicament() {
        return idVenteMedicament.get();
    }

    public IntegerProperty idVenteMedicamentProperty() {
        return idVenteMedicament;
    }

    public void setIdVenteMedicament(int idVenteMedicament) {
        this.idVenteMedicament.set(idVenteMedicament);
    }

    public int getIdVente() {
        return idVente.get();
    }

    public IntegerProperty idVenteProperty() {
        return idVente;
    }

    public void setIdVente(int idVente) {
        this.idVente.set(idVente);
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

    public int getQuantite() {
        return quantite.get();
    }

    public IntegerProperty quantiteProperty() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite.set(quantite);
    }

    public double getPrixUnitaire() {
        return prixUnitaire.get();
    }

    public DoubleProperty prixUnitaireProperty() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire.set(prixUnitaire);
    }

    public double getPrixTotal() {
        return prixTotal.get();
    }

    public DoubleProperty prixTotalProperty() {
        return prixTotal;
    }

    public void setPrixTotal(double prixTotal) {
        this.prixTotal.set(prixTotal);
    }

    public LocalDateTime getDateVente() {
        return dateVente.get();
    }

    public ObjectProperty<LocalDateTime> dateVenteProperty() {
        return dateVente;
    }

    public void setDateVente(LocalDateTime dateVente) {
        this.dateVente.set(dateVente);
    }
}