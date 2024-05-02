package org.example.pharmagest.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.pharmagest.model.*;
import org.example.pharmagest.utils.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CaisseDAO {

    public void addCaisse(Caisse caisse) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO caisse (date_caisse, montant_initial, total_ventes, total_retraits, montant_final) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setDate(1, Date.valueOf(caisse.getDateCaisse()));
            stmt.setDouble(2, caisse.getMontantInitial());
            stmt.setDouble(3, caisse.getTotalVentes());
            stmt.setDouble(4, caisse.getTotalRetraits());
            stmt.setDouble(5, caisse.getMontantFinal());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCaisse(Caisse caisse) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE caisse SET montant_initial = ?, total_ventes = ?, total_retraits = ?, montant_final = ? WHERE id_caisse = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setDouble(1, caisse.getMontantInitial());
            stmt.setDouble(2, caisse.getTotalVentes());
            stmt.setDouble(3, caisse.getTotalRetraits());
            stmt.setDouble(4, caisse.getMontantFinal());
            stmt.setInt(5, caisse.getIdCaisse());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Caisse getCaisseByDate(LocalDate date) {
        Caisse caisse = null;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM caisse WHERE date_caisse = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setDate(1, Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int idCaisse = rs.getInt("id_caisse");
                LocalDate dateCaisse = rs.getDate("date_caisse").toLocalDate();
                double montantInitial = rs.getDouble("montant_initial");
                double totalVentes = rs.getDouble("total_ventes");
                double totalRetraits = rs.getDouble("total_retraits");
                double montantFinal = rs.getDouble("montant_final");

                List<Vente> ventes = getVentesByCaisse(idCaisse);

                caisse = new Caisse(idCaisse, dateCaisse, montantInitial, totalVentes, totalRetraits, montantFinal, FXCollections.observableArrayList(ventes));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return caisse;
    }

    private List<Vente> getVentesByCaisse(int idCaisse) {
        List<Vente> ventes = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT v.*, c.nom_client, c.prenom_client FROM vente v JOIN client c ON v.id_client = c.id_client JOIN vente_caisse vc ON v.id_vente = vc.id_vente WHERE vc.id_caisse = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, idCaisse);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int idVente = rs.getInt("id_vente");
                int idClient = rs.getInt("id_client");
                String typeVente = rs.getString("type_vente");
                double montantTotal = rs.getDouble("montant_total");
                LocalDate dateVente = rs.getDate("date_vente").toLocalDate();
                String statut = rs.getString("statut");

                Client client = getClientById(idClient);

                ObservableList<LigneVente> lignesVente = FXCollections.observableArrayList(getLignesVenteByIdVente(idVente));

                Vente vente = new Vente(idVente, client, typeVente, montantTotal, dateVente, statut, lignesVente, null);                ventes.add(vente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ventes;
    }

    private Client getClientById(int idClient) {
        Client client = null;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM client WHERE id_client = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, idClient);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String nomClient = rs.getString("nom_client");
                String prenomClient = rs.getString("prenom_client");
                LocalDate dateNaissanceClient = rs.getDate("date_naissance_client").toLocalDate();
                String adresseClient = rs.getString("adresse_client");
                String telephoneClient = rs.getString("telephone_client");
                String statut = rs.getString("statut");
                LocalDate dateCreation = rs.getDate("date_creation").toLocalDate();
                client = new Client(idClient, nomClient, prenomClient, dateNaissanceClient, adresseClient, telephoneClient, statut, dateCreation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    private List<LigneVente> getLignesVenteByIdVente(int idVente) {
        List<LigneVente> lignesVente = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT lv.*, m.nom_medicament, m.prix_vente FROM ligne_vente lv JOIN medicament m ON lv.id_medicament = m.id_medicament WHERE lv.id_vente = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, idVente);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                int idLigneVente = rs.getInt("id_ligne_vente");
                int idMedicament = rs.getInt("id_medicament");
                String nomMedicament = rs.getString("nom_medicament");
                double prixVente = rs.getDouble("prix_vente");
                Medicament medicament = new Medicament(idMedicament, nomMedicament, null, null, null, null, 0, prixVente, 0, null);
                int quantite = rs.getInt("quantite");
                double prixUnitaire = rs.getDouble("prix_unitaire");
                double prixTotal = rs.getDouble("prix_total");
                LigneVente ligneVente = new LigneVente(idLigneVente, medicament, quantite, prixUnitaire, prixTotal);
                lignesVente.add(ligneVente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lignesVente;
    }
}