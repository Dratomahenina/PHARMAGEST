package org.example.pharmagest.dao;

import org.example.pharmagest.model.Fournisseur;
import org.example.pharmagest.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FournisseurDAO {

    public void addFournisseur(Fournisseur fournisseur) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO fournisseur (nom_fournisseur, email_fournisseur, tel_fournisseur, adresse_fournisseur, statut) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, fournisseur.getNomFournisseur());
            stmt.setString(2, fournisseur.getEmailFournisseur());
            stmt.setString(3, fournisseur.getTelFournisseur());
            stmt.setString(4, fournisseur.getAdresseFournisseur());
            stmt.setString(5, fournisseur.getStatut());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Fournisseur> getAllFournisseurs() {
        List<Fournisseur> fournisseurs = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM fournisseur";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Fournisseur fournisseur = new Fournisseur(
                        rs.getInt("id_fournisseur"),
                        rs.getString("nom_fournisseur"),
                        rs.getString("email_fournisseur"),
                        rs.getString("tel_fournisseur"),
                        rs.getString("adresse_fournisseur"),
                        rs.getString("statut"),
                        rs.getDate("date_creation").toLocalDate()
                );
                fournisseurs.add(fournisseur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fournisseurs;
    }

    public void updateFournisseur(Fournisseur fournisseur) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE fournisseur SET nom_fournisseur = ?, email_fournisseur = ?, tel_fournisseur = ?, adresse_fournisseur = ?, statut = ? WHERE id_fournisseur = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, fournisseur.getNomFournisseur());
            stmt.setString(2, fournisseur.getEmailFournisseur());
            stmt.setString(3, fournisseur.getTelFournisseur());
            stmt.setString(4, fournisseur.getAdresseFournisseur());
            stmt.setString(5, fournisseur.getStatut());
            stmt.setInt(6, fournisseur.getIdFournisseur());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFournisseur(Fournisseur fournisseur) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM fournisseur WHERE id_fournisseur = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, fournisseur.getIdFournisseur());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Fournisseur> searchFournisseurs(String searchTerm) {
        List<Fournisseur> searchResults = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM fournisseur WHERE LOWER(nom_fournisseur) LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, "%" + searchTerm + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Fournisseur fournisseur = new Fournisseur(
                        rs.getInt("id_fournisseur"),
                        rs.getString("nom_fournisseur"),
                        rs.getString("email_fournisseur"),
                        rs.getString("tel_fournisseur"),
                        rs.getString("adresse_fournisseur"),
                        rs.getString("statut"),
                        rs.getDate("date_creation").toLocalDate()
                );
                searchResults.add(fournisseur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return searchResults;
    }

    public Fournisseur getFournisseurById(int fournisseurId) {
        Fournisseur fournisseur = null;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM fournisseur WHERE id_fournisseur = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, fournisseurId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                fournisseur = new Fournisseur(
                        rs.getInt("id_fournisseur"),
                        rs.getString("nom_fournisseur"),
                        rs.getString("email_fournisseur"),
                        rs.getString("tel_fournisseur"),
                        rs.getString("adresse_fournisseur"),
                        rs.getString("statut"),
                        rs.getDate("date_creation").toLocalDate()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fournisseur;
    }
}