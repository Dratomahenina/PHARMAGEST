package org.example.pharmagest.dao;

import org.example.pharmagest.model.Famille;
import org.example.pharmagest.model.Forme;
import org.example.pharmagest.model.Fournisseur;
import org.example.pharmagest.model.Medicament;
import org.example.pharmagest.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicamentDAO {

    public void addMedicament(Medicament medicament) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO medicament (nom_medicament, description_medicament, id_fournisseur, id_famille, id_forme, statut) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, medicament.getNomMedicament());
            stmt.setString(2, medicament.getDescriptionMedicament());
            stmt.setInt(3, medicament.getFournisseur().getIdFournisseur());
            stmt.setInt(4, medicament.getFamille().getIdFamille());
            stmt.setInt(5, medicament.getForme().getIdForme());
            stmt.setString(6, medicament.getStatut());

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
                        rs.getString("statut")
                );
                medicaments.add(medicament);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medicaments;
    }

    public void updateMedicament(Medicament medicament) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE medicament SET nom_medicament = ?, description_medicament = ?, " +
                    "id_fournisseur = ?, id_famille = ?, id_forme = ?, statut = ? WHERE id_medicament = ?";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, medicament.getNomMedicament());
            stmt.setString(2, medicament.getDescriptionMedicament());
            stmt.setInt(3, medicament.getFournisseur().getIdFournisseur());
            stmt.setInt(4, medicament.getFamille().getIdFamille());
            stmt.setInt(5, medicament.getForme().getIdForme());
            stmt.setString(6, medicament.getStatut());
            stmt.setInt(7, medicament.getIdMedicament());

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
                        rs.getString("statut")
                );
                searchResults.add(medicament);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return searchResults;
    }
}