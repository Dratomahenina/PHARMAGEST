package org.example.pharmagest.model;

import javafx.beans.property.*;

public class CommandeClient {
    private IntegerProperty idCommande;
    private StringProperty nomClient;
    private StringProperty prenomClient;
    private StringProperty medicament;
    private IntegerProperty quantite;
    private DoubleProperty prix;
    private DoubleProperty remise;
    private DoubleProperty total;
    private StringProperty statut;
    private IntegerProperty idClient;
    private StringProperty typeVente;

    public CommandeClient(int idCommande, String nomClient, String prenomClient, String medicament, int quantite, double prix, double remise, double total, String statut, int idClient, String typeVente) {
        this.idCommande = new SimpleIntegerProperty(idCommande);
        this.nomClient = new SimpleStringProperty(nomClient);
        this.prenomClient = new SimpleStringProperty(prenomClient);
        this.medicament = new SimpleStringProperty(medicament);
        this.quantite = new SimpleIntegerProperty(quantite);
        this.prix = new SimpleDoubleProperty(prix);
        this.remise = new SimpleDoubleProperty(remise);
        this.total = new SimpleDoubleProperty(total);
        this.statut = new SimpleStringProperty(statut);
        this.idClient = new SimpleIntegerProperty(idClient);
        this.typeVente = new SimpleStringProperty(typeVente);
    }

    // Getters et Setters

    public int getIdCommande() {
        return idCommande.get();
    }

    public IntegerProperty idCommandeProperty() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande.set(idCommande);
    }

    public String getNomClient() {
        return nomClient.get();
    }

    public StringProperty nomClientProperty() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient.set(nomClient);
    }

    public String getPrenomClient() {
        return prenomClient.get();
    }

    public StringProperty prenomClientProperty() {
        return prenomClient;
    }

    public void setPrenomClient(String prenomClient) {
        this.prenomClient.set(prenomClient);
    }

    public String getMedicament() {
        return medicament.get();
    }

    public StringProperty medicamentProperty() {
        return medicament;
    }

    public void setMedicament(String medicament) {
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

    public double getPrix() {
        return prix.get();
    }

    public DoubleProperty prixProperty() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix.set(prix);
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

    public double getTotal() {
        return total.get();
    }

    public DoubleProperty totalProperty() {
        return total;
    }

    public void setTotal(double total) {
        this.total.set(total);
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

    public int getIdClient() {
        return idClient.get();
    }

    public IntegerProperty idClientProperty() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient.set(idClient);
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

}