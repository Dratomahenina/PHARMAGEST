package org.example.pharmagest.dao;

import org.example.pharmagest.model.Famille;
import org.example.pharmagest.model.Forme;
import org.example.pharmagest.model.Fournisseur;
import org.example.pharmagest.model.Medicament;
import org.example.pharmagest.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MedicamentDAO {

    public void addMedicament(Medicament medicament) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO medicament (nom_medicament, description_medicament, id_fournisseur, id_famille, id_forme, statut, quantite_medicament, prix_vente, prix_fournisseur) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, medicament.getNomMedicament());
            stmt.setString(2, medicament.getDescriptionMedicament());
            stmt.setInt(3, medicament.getFournisseur().getIdFournisseur());
            stmt.setInt(4, medicament.getFamille().getIdFamille());
            stmt.setInt(5, medicament.getForme().getIdForme());
            stmt.setString(6, medicament.getStatut());
            stmt.setInt(7, medicament.getQuantiteMedicament());
            stmt.setDouble(8, medicament.getPrixVente());
            stmt.setDouble(9, medicament.getPrixFournisseur());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Medicament> getAllMedicaments() {
        List<Medicament> medicaments = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT m.*, f.nom_fournisseur, fa.nom_famille, fo.nom_forme " +
                    "FROM medicament m " +
                    "JOIN fournisseur f ON m.id_fournisseur = f.id_fournisseur " +
                    "JOIN famille fa ON m.id_famille = fa.id_famille " +
                    "JOIN forme fo ON m.id_forme = fo.id_forme";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Medicament medicament = new Medicament(
                        rs.getInt("id_medicament"),
                        rs.getString("nom_medicament"),
                        rs.getString("description_medicament"),
                        new Fournisseur(rs.getInt("id_fournisseur"), rs.getString("nom_fournisseur"), null, null, null, null, null),
                        new Famille(rs.getInt("id_famille"), rs.getString("nom_famille"), null, null),
                        new Forme(rs.getInt("id_forme"), rs.getString("nom_forme"), null, null),
                        rs.getInt("quantite_medicament"),
                        rs.getDouble("prix_vente"),
                        rs.getDouble("prix_fournisseur"),
                        rs.getString("statut")
                );
                medicaments.add(medicament);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medicaments;
    }

    public Medicament getMedicamentById(int medicamentId) {
        Medicament medicament = null;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM medicament WHERE id_medicament = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, medicamentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int fournisseurId = rs.getInt("id_fournisseur");
                Fournisseur fournisseur = fournisseurId != 0 ? new FournisseurDAO().getFournisseurById(fournisseurId) : null;

                int familleId = rs.getInt("id_famille");
                Famille famille = familleId != 0 ? new FamilleDAO().getFamilleById(familleId) : null;

                int formeId = rs.getInt("id_forme");
                Forme forme = formeId != 0 ? new FormeDAO().getFormeById(formeId) : null;

                medicament = new Medicament(
                        rs.getInt("id_medicament"),
                        rs.getString("nom_medicament"),
                        rs.getString("description_medicament"),
                        fournisseur,
                        famille,
                        forme,
                        rs.getInt("quantite_medicament"),
                        rs.getDouble("prix_vente"),
                        rs.getDouble("prix_fournisseur"),
                        rs.getString("statut")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicament;
    }

    public void updateMedicament(Medicament medicament) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE medicament SET nom_medicament = ?, description_medicament = ?, " +
                    "id_fournisseur = ?, id_famille = ?, id_forme = ?, statut = ?, quantite_medicament = ?, prix_vente = ?, prix_fournisseur = ? WHERE id_medicament = ?";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, medicament.getNomMedicament());
            stmt.setString(2, medicament.getDescriptionMedicament());
            stmt.setInt(3, medicament.getFournisseur().getIdFournisseur());
            stmt.setInt(4, medicament.getFamille().getIdFamille());
            stmt.setInt(5, medicament.getForme().getIdForme());
            stmt.setString(6, medicament.getStatut());
            stmt.setInt(7, medicament.getQuantiteMedicament());
            stmt.setDouble(8, medicament.getPrixVente());
            stmt.setDouble(9, medicament.getPrixFournisseur());
            stmt.setInt(10, medicament.getIdMedicament());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteMedicament(Medicament medicament) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM medicament WHERE id_medicament = ?";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, medicament.getIdMedicament());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Medicament> searchMedicaments(String searchTerm) {
        List<Medicament> searchResults = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT m.*, f.nom_fournisseur, fa.nom_famille, fo.nom_forme " +
                    "FROM medicament m " +
                    "JOIN fournisseur f ON m.id_fournisseur = f.id_fournisseur " +
                    "JOIN famille fa ON m.id_famille = fa.id_famille " +
                    "JOIN forme fo ON m.id_forme = fo.id_forme " +
                    "WHERE LOWER(m.nom_medicament) LIKE ? OR LOWER(m.description_medicament) LIKE ?";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, "%" + searchTerm.toLowerCase() + "%");
            stmt.setString(2, "%" + searchTerm.toLowerCase() + "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Medicament medicament = new Medicament(
                        rs.getInt("id_medicament"),
                        rs.getString("nom_medicament"),
                        rs.getString("description_medicament"),
                        new Fournisseur(rs.getInt("id_fournisseur"), rs.getString("nom_fournisseur"), null, null, null, null, null),
                        new Famille(rs.getInt("id_famille"), rs.getString("nom_famille"), null, null),
                        new Forme(rs.getInt("id_forme"), rs.getString("nom_forme"), null, null),
                        rs.getInt("quantite_medicament"),
                        rs.getDouble("prix_vente"),
                        rs.getDouble("prix_fournisseur"),
                        rs.getString("statut")
                );
                searchResults.add(medicament);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return searchResults;
    }

    public List<Medicament> getMedicamentsEnDessousDuSeuil(int seuil) {
        List<Medicament> medicaments = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT m.*, f.nom_fournisseur, fa.nom_famille, fo.nom_forme " +
                    "FROM medicament m " +
                    "JOIN fournisseur f ON m.id_fournisseur = f.id_fournisseur " +
                    "JOIN famille fa ON m.id_famille = fa.id_famille " +
                    "JOIN forme fo ON m.id_forme = fo.id_forme " +
                    "WHERE m.quantite_medicament <= ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, seuil);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Medicament medicament = new Medicament(
                        rs.getInt("id_medicament"),
                        rs.getString("nom_medicament"),
                        rs.getString("description_medicament"),
                        new Fournisseur(rs.getInt("id_fournisseur"), rs.getString("nom_fournisseur"), null, null, null, null, null),
                        new Famille(rs.getInt("id_famille"), rs.getString("nom_famille"), null, null),
                        new Forme(rs.getInt("id_forme"), rs.getString("nom_forme"), null, null),
                        rs.getInt("quantite_medicament"),
                        rs.getDouble("prix_vente"),
                        rs.getDouble("prix_fournisseur"),
                        rs.getString("statut")
                );
                medicaments.add(medicament);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicaments;
    }

    public int getStockAlerts(int threshold) {
        int stockAlerts = 0;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT COUNT(*) AS stock_alerts FROM medicament WHERE quantite_medicament < ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, threshold);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                stockAlerts = rs.getInt("stock_alerts");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stockAlerts;
    }

    public List<Medicament> getMedicamentsActifs() {
        List<Medicament> medicaments = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT m.*, f.nom_fournisseur, fa.nom_famille, fo.nom_forme " +
                    "FROM medicament m " +
                    "JOIN fournisseur f ON m.id_fournisseur = f.id_fournisseur " +
                    "JOIN famille fa ON m.id_famille = fa.id_famille " +
                    "JOIN forme fo ON m.id_forme = fo.id_forme " +
                    "WHERE m.statut = 'actif'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Medicament medicament = new Medicament(
                        rs.getInt("id_medicament"),
                        rs.getString("nom_medicament"),
                        rs.getString("description_medicament"),
                        new Fournisseur(rs.getInt("id_fournisseur"), rs.getString("nom_fournisseur"), null, null, null, null, null),
                        new Famille(rs.getInt("id_famille"), rs.getString("nom_famille"), null, null),
                        new Forme(rs.getInt("id_forme"), rs.getString("nom_forme"), null, null),
                        rs.getInt("quantite_medicament"),
                        rs.getDouble("prix_vente"),
                        rs.getDouble("prix_fournisseur"),
                        rs.getString("statut")
                );
                medicaments.add(medicament);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicaments;
    }

}