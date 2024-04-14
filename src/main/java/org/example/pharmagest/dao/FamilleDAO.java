package org.example.pharmagest.dao;

import org.example.pharmagest.model.Famille;
import org.example.pharmagest.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FamilleDAO {

    public void addFamille(Famille famille) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO famille (nom_famille, statut) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, famille.getNomFamille());
            stmt.setString(2, famille.getStatut());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Famille> getAllFamilles() {
        List<Famille> familles = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM famille";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Famille famille = new Famille(
                        rs.getInt("id_famille"),
                        rs.getString("nom_famille"),
                        rs.getString("statut"),
                        rs.getDate("date_creation").toLocalDate()
                );
                familles.add(famille);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return familles;
    }

    public void updateFamille(Famille famille) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE famille SET nom_famille = ?, statut = ? WHERE id_famille = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, famille.getNomFamille());
            stmt.setString(2, famille.getStatut());
            stmt.setInt(3, famille.getIdFamille());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFamille(Famille famille) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM famille WHERE id_famille = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, famille.getIdFamille());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Famille> searchFamilles(String searchTerm) {
        List<Famille> searchResults = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM famille WHERE LOWER(nom_famille) LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, "%" + searchTerm + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Famille famille = new Famille(
                        rs.getInt("id_famille"),
                        rs.getString("nom_famille"),
                        rs.getString("statut"),
                        rs.getDate("date_creation").toLocalDate()
                );
                searchResults.add(famille);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return searchResults;
    }

    public Famille getFamilleById(int familleId) {
        Famille famille = null;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM famille WHERE id_famille = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, familleId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                famille = new Famille(
                        rs.getInt("id_famille"),
                        rs.getString("nom_famille"),
                        rs.getString("statut"),
                        rs.getDate("date_creation").toLocalDate()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return famille;
    }
}