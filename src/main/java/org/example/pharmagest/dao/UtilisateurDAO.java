package org.example.pharmagest.dao;

import org.example.pharmagest.model.Utilisateur;
import org.example.pharmagest.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDAO {

    public void addUtilisateur(Utilisateur utilisateur) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO utilisateurs (nom_utilisateur, mot_de_passe, role) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, utilisateur.getNomUtilisateur());
            stmt.setString(2, utilisateur.getMotDePasse());
            stmt.setString(3, utilisateur.getRole());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Utilisateur> getAllUtilisateurs() {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM utilisateurs";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Utilisateur utilisateur = new Utilisateur(
                        rs.getInt("id_utilisateur"),
                        rs.getString("nom_utilisateur"),
                        rs.getString("mot_de_passe"),
                        rs.getString("role"),
                        rs.getDate("date_creation") != null ? rs.getDate("date_creation").toLocalDate() : null
                );
                utilisateurs.add(utilisateur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilisateurs;
    }

    public void updateUtilisateur(Utilisateur utilisateur) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE utilisateurs SET nom_utilisateur = ?, mot_de_passe = ?, role = ? WHERE id_utilisateur = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, utilisateur.getNomUtilisateur());
            stmt.setString(2, utilisateur.getMotDePasse());
            stmt.setString(3, utilisateur.getRole());
            stmt.setInt(4, utilisateur.getIdUtilisateur());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUtilisateur(Utilisateur utilisateur) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM utilisateurs WHERE id_utilisateur = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, utilisateur.getIdUtilisateur());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Utilisateur> searchUtilisateurs(String searchTerm) {
        List<Utilisateur> searchResults = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM utilisateurs WHERE LOWER(nom_utilisateur) LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, "%" + searchTerm.toLowerCase() + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Utilisateur utilisateur = new Utilisateur(
                        rs.getInt("id_utilisateur"),
                        rs.getString("nom_utilisateur"),
                        rs.getString("mot_de_passe"),
                        rs.getString("role"),
                        rs.getDate("date_creation") != null ? rs.getDate("date_creation").toLocalDate() : null
                );
                searchResults.add(utilisateur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return searchResults;
    }
}