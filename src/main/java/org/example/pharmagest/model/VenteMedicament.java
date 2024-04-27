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
    private ObjectProperty<Vente> vente;
    private IntegerProperty idClient;
    private StringProperty typeVente;
    private ObjectProperty<Client> client;

    public VenteMedicament() {
        this.idVenteMedicament = new SimpleIntegerProperty();
        this.idVente = new SimpleIntegerProperty();
        this.medicament = new SimpleObjectProperty<>();
        this.quantite = new SimpleIntegerProperty();
        this.prixUnitaire = new SimpleDoubleProperty();
        this.prixTotal = new SimpleDoubleProperty();
        this.dateVente = new SimpleObjectProperty<>();
        this.vente = new SimpleObjectProperty<>();
        this.idClient = new SimpleIntegerProperty();
        this.typeVente = new SimpleStringProperty();
        this.client = new SimpleObjectProperty<>();
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

    public Vente getVente() {
        return vente.get();
    }

    public ObjectProperty<Vente> venteProperty() {
        return vente;
    }

    public void setVente(Vente vente) {
        this.vente.set(vente);
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

    public Client getClient() {
        return client.get();
    }

    public ObjectProperty<Client> clientProperty() {
        if (client == null) {
            client = new SimpleObjectProperty<>();
        }
        return client;
    }

    public void setClient(Client client) {
        clientProperty().set(client);
    }

}