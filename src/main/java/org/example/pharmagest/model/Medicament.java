package org.example.pharmagest.model;

import javafx.beans.property.*;

public class Medicament {
    private IntegerProperty idMedicament;
    private StringProperty nomMedicament;
    private StringProperty categorieMedicament;
    private StringProperty formeMedicament;
    private StringProperty statut;

    public Medicament(int idMedicament, String nomMedicament, String categorieMedicament, String formeMedicament, String statut) {
        this.idMedicament = new SimpleIntegerProperty(idMedicament);
        this.nomMedicament = new SimpleStringProperty(nomMedicament);
        this.categorieMedicament = new SimpleStringProperty(categorieMedicament);
        this.formeMedicament = new SimpleStringProperty(formeMedicament);
        this.statut = new SimpleStringProperty(statut);
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

    public int getIdMedicament() {
        return idMedicament.get();
    }

    public IntegerProperty idMedicamentProperty() {
        return idMedicament;
    }

    public void setIdMedicament(int idMedicament) {
        this.idMedicament.set(idMedicament);
    }

    public String getNomMedicament() {
        return nomMedicament.get();
    }

    public StringProperty nomMedicamentProperty() {
        return nomMedicament;
    }

    public void setNomMedicament(String nomMedicament) {
        this.nomMedicament.set(nomMedicament);
    }

    public String getCategorieMedicament() {
        return categorieMedicament.get();
    }

    public StringProperty categorieMedicamentProperty() {
        return categorieMedicament;
    }

    public void setCategorieMedicament(String categorieMedicament) {
        this.categorieMedicament.set(categorieMedicament);
    }

    public String getFormeMedicament() {
        return formeMedicament.get();
    }

    public StringProperty formeMedicamentProperty() {
        return formeMedicament;
    }

    public void setFormeMedicament(String formeMedicament) {
        this.formeMedicament.set(formeMedicament);
    }
}