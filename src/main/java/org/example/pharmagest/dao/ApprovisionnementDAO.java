package org.example.pharmagest.dao;

import org.example.pharmagest.model.Approvisionnement;
import org.example.pharmagest.model.Medicament;
import org.example.pharmagest.model.Fournisseur;
import org.example.pharmagest.model.Famille;
import org.example.pharmagest.model.Forme;
import org.example.pharmagest.utils.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ApprovisionnementDAO {

    /**
     * Ajoute un nouvel approvisionnement dans la base de données.
     *
     * @param approvisionnement L'objet Approvisionnement à ajouter.
     */
    public void addApprovisionnement(Approvisionnement approvisionnement) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO approvisionnement (id_medicament, id_fournisseur, quantite_commandee, date_approvisionnement, statut, prix_fournisseur, commentaire) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, approvisionnement.getMedicament().getIdMedicament());
            stmt.setInt(2, approvisionnement.getFournisseur().getIdFournisseur());
            stmt.setInt(3, approvisionnement.getQuantiteCommandee());
            stmt.setDate(4, Date.valueOf(approvisionnement.getDateApprovisionnement()));
            stmt.setString(5, approvisionnement.getStatut());
            stmt.setDouble(6, approvisionnement.getPrixFournisseur());
            stmt.setString(7, approvisionnement.getCommentaire());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Récupère tous les approvisionnements depuis la base de données.
     *
     * @return Une liste d'objets Approvisionnement.
     */
    public List<Approvisionnement> getAllApprovisionnements() {
        List<Approvisionnement> approvisionnements = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT a.*, m.nom_medicament, m.description_medicament, m.quantite_medicament, m.prix_vente, m.prix_fournisseur, m.statut, f.id_fournisseur, f.nom_fournisseur, f.email_fournisseur, f.tel_fournisseur, f.adresse_fournisseur, fa.id_famille, fa.nom_famille, fo.id_forme, fo.nom_forme " +
                    "FROM approvisionnement a " +
                    "JOIN medicament m ON a.id_medicament = m.id_medicament " +
                    "JOIN fournisseur f ON m.id_fournisseur = f.id_fournisseur " +
                    "JOIN famille fa ON m.id_famille = fa.id_famille " +
                    "JOIN forme fo ON m.id_forme = fo.id_forme";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Approvisionnement approvisionnement = new Approvisionnement(
                        rs.getInt("id_approvisionnement"),
                        new Medicament(
                                rs.getInt("id_medicament"),
                                rs.getString("nom_medicament"),
                                rs.getString("description_medicament"),
                                new Fournisseur(
                                        rs.getInt("id_fournisseur"),
                                        rs.getString("nom_fournisseur"),
                                        rs.getString("email_fournisseur"),
                                        rs.getString("tel_fournisseur"),
                                        rs.getString("adresse_fournisseur"),
                                        null, null
                                ),
                                new Famille(
                                        rs.getInt("id_famille"),
                                        rs.getString("nom_famille"),
                                        null, null
                                ),
                                new Forme(
                                        rs.getInt("id_forme"),
                                        rs.getString("nom_forme"),
                                        null, null
                                ),
                                rs.getInt("quantite_medicament"),
                                rs.getDouble("prix_vente"),
                                rs.getDouble("prix_fournisseur"),
                                rs.getString("statut")
                        ),
                        new Fournisseur(
                                rs.getInt("id_fournisseur"),
                                rs.getString("nom_fournisseur"),
                                rs.getString("email_fournisseur"),
                                rs.getString("tel_fournisseur"),
                                rs.getString("adresse_fournisseur"),
                                null, null
                        ),
                        rs.getInt("quantite_commandee"),
                        rs.getDate("date_approvisionnement").toLocalDate(),
                        rs.getString("statut"),
                        rs.getDouble("prix_fournisseur"),
                        rs.getInt("quantite_recue"),
                        rs.getString("commentaire")
                );
                approvisionnements.add(approvisionnement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return approvisionnements;
    }


    /**
     * Met à jour un approvisionnement dans la base de données.
     *
     * @param approvisionnement L'objet Approvisionnement à mettre à jour.
     */
    public void updateApprovisionnement(Approvisionnement approvisionnement) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE approvisionnement SET statut = ?, prix_fournisseur = ? WHERE id_approvisionnement = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, approvisionnement.getStatut());
            stmt.setDouble(2, approvisionnement.getPrixFournisseur());
            stmt.setInt(3, approvisionnement.getIdApprovisionnement());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Supprime un approvisionnement de la base de données.
     *
     * @param approvisionnement L'objet Approvisionnement à supprimer.
     */
    public void deleteApprovisionnement(Approvisionnement approvisionnement) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM approvisionnement WHERE id_approvisionnement = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, approvisionnement.getIdApprovisionnement());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}