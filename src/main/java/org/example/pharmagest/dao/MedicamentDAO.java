package org.example.pharmagest.dao;

import org.example.pharmagest.model.Medicament;
import org.example.pharmagest.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicamentDAO {

    public void addMedicament(Medicament medicament) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO medicament (nom_medicament, categorie, type, statut) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, medicament.getNomMedicament());
            stmt.setString(2, medicament.getCategorieMedicament());
            stmt.setString(3, medicament.getFormeMedicament());
            stmt.setString(4, medicament.getStatut());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Medicament> getAllMedicaments() {
        List<Medicament> medicaments = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM medicament";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Medicament medicament = new Medicament(
                        rs.getInt("id_medicament"),
                        rs.getString("nom_medicament"),
                        rs.getString("categorie"),
                        rs.getString("type"),
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
            String query = "UPDATE medicament SET nom_medicament = ?, categorie = ?, type = ?, statut = ? WHERE id_medicament = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, medicament.getNomMedicament());
            stmt.setString(2, medicament.getCategorieMedicament());
            stmt.setString(3, medicament.getFormeMedicament());
            stmt.setString(4, medicament.getStatut());
            stmt.setInt(5, medicament.getIdMedicament());
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
            String query = "SELECT * FROM medicament WHERE LOWER(nom_medicament) LIKE ? OR LOWER(categorie) LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, "%" + searchTerm.toLowerCase() + "%");
            stmt.setString(2, "%" + searchTerm.toLowerCase() + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Medicament medicament = new Medicament(
                        rs.getInt("id_medicament"),
                        rs.getString("nom_medicament"),
                        rs.getString("categorie"),
                        rs.getString("type"),
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