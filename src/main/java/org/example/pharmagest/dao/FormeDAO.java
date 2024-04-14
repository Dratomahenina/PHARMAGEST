package org.example.pharmagest.dao;

import org.example.pharmagest.model.Forme;
import org.example.pharmagest.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FormeDAO {

    public void addForme(Forme forme) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO forme (nom_forme, statut) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, forme.getNomForme());
            stmt.setString(2, forme.getStatut());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Forme> getAllFormes() {
        List<Forme> formes = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM forme";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Forme forme = new Forme(
                        rs.getInt("id_forme"),
                        rs.getString("nom_forme"),
                        rs.getString("statut"),
                        rs.getDate("date_creation").toLocalDate()
                );
                formes.add(forme);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return formes;
    }

    public void updateForme(Forme forme) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE forme SET nom_forme = ?, statut = ? WHERE id_forme = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, forme.getNomForme());
            stmt.setString(2, forme.getStatut());
            stmt.setInt(3, forme.getIdForme());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteForme(Forme forme) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM forme WHERE id_forme = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, forme.getIdForme());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Forme> searchFormes(String searchTerm) {
        List<Forme> searchResults = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM forme WHERE LOWER(nom_forme) LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, "%" + searchTerm + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Forme forme = new Forme(
                        rs.getInt("id_forme"),
                        rs.getString("nom_forme"),
                        rs.getString("statut"),
                        rs.getDate("date_creation").toLocalDate()
                );
                searchResults.add(forme);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return searchResults;
    }

    public Forme getFormeById(int formeId) {
        Forme forme = null;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM forme WHERE id_forme = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, formeId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                forme = new Forme(
                        rs.getInt("id_forme"),
                        rs.getString("nom_forme"),
                        rs.getString("statut"),
                        rs.getDate("date_creation").toLocalDate()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return forme;
    }
}