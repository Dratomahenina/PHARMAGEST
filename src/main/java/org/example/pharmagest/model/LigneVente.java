package org.example.pharmagest.model;

import javafx.beans.property.*;

public class LigneVente {
    private IntegerProperty idLigneVente;
    private IntegerProperty idVente;
    private ObjectProperty<Medicament> medicament;
    private IntegerProperty quantite;
    private DoubleProperty prixUnitaire;
    private DoubleProperty prixTotal;

    public LigneVente(int idLigneVente, Medicament medicament, int quantite, double prixUnitaire, double prixTotal) {
        this.idLigneVente = new SimpleIntegerProperty(idLigneVente);
        this.idVente = new SimpleIntegerProperty();
        this.medicament = new SimpleObjectProperty<>(medicament);
        this.quantite = new SimpleIntegerProperty(quantite);
        this.prixUnitaire = new SimpleDoubleProperty(prixUnitaire);
        this.prixTotal = new SimpleDoubleProperty(prixTotal);
    }

    // Getters et setters

    public int getIdLigneVente() {
        return idLigneVente.get();
    }

    public IntegerProperty idLigneVenteProperty() {
        return idLigneVente;
    }

    public void setIdLigneVente(int idLigneVente) {
        this.idLigneVente.set(idLigneVente);
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
}

