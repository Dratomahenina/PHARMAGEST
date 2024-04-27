package org.example.pharmagest.dao;

import org.example.pharmagest.model.VenteMedicament;
import org.example.pharmagest.model.Vente;
import org.example.pharmagest.utils.DatabaseConnection;
import org.example.pharmagest.model.Client;
import org.example.pharmagest.model.Medicament;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CaisseDAO {

    public List<VenteMedicament> getAllVenteMedicaments() {
        List<VenteMedicament> venteMedicaments = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT vm.*, v.*, c.nom_client, c.prenom_client, m.nom_medicament " +
                    "FROM vente_medicament vm " +
                    "JOIN vente v ON vm.id_vente = v.id_vente " +
                    "JOIN client c ON vm.id_client = c.id_client " +
                    "JOIN medicament m ON vm.id_medicament = m.id_medicament";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                VenteMedicament venteMedicament = new VenteMedicament();
                venteMedicament.setIdVenteMedicament(rs.getInt("id_vente_medicament"));
                venteMedicament.setQuantite(rs.getInt("quantite"));
                venteMedicament.setPrixUnitaire(rs.getDouble("prix_unitaire"));
                venteMedicament.setPrixTotal(rs.getInt("quantite") * rs.getDouble("prix_unitaire")); // Calculer le prix total
                venteMedicament.setTypeVente(rs.getString("type_vente"));

                Vente vente = new Vente();
                vente.setIdVente(rs.getInt("id_vente"));

                Date dateVenteSQL = rs.getDate("date_vente");
                if (dateVenteSQL != null) {
                    vente.setDateVente(dateVenteSQL.toLocalDate());
                }

                vente.setTypeVente(rs.getString("type_vente"));
                vente.setStatut(rs.getString("statut"));
                vente.setMontantTotal(rs.getDouble("montant_total"));
                vente.setRemise(rs.getDouble("remise"));

                Timestamp datePaiement = rs.getTimestamp("date_paiement");
                if (datePaiement != null) {
                    vente.setDatePaiement(datePaiement.toLocalDateTime());
                } else {
                    vente.setDatePaiement(null);
                }

                Client client = new Client(
                        rs.getInt("id_client"),
                        rs.getString("nom_client"),
                        rs.getString("prenom_client"),
                        null, // Vous n'avez pas de date de naissance dans votre requête
                        null, // Vous n'avez pas d'adresse dans votre requête
                        null, // Vous n'avez pas de téléphone dans votre requête
                        null, // Vous n'avez pas de statut dans votre requête
                        null  // Vous n'avez pas de date de création dans votre requête
                );

                Medicament medicament = new Medicament(
                        rs.getInt("id_medicament"),
                        rs.getString("nom_medicament"),
                        null, // Vous n'avez pas de description dans votre requête
                        null, // Vous n'avez pas de fournisseur dans votre requête
                        null, // Vous n'avez pas de famille dans votre requête
                        null, // Vous n'avez pas de forme dans votre requête
                        0,    // Vous n'avez pas de quantité dans votre requête
                        0.0,  // Vous n'avez pas de prix de vente dans votre requête
                        0.0,  // Vous n'avez pas de prix fournisseur dans votre requête
                        null  // Vous n'avez pas de statut dans votre requête
                );

                venteMedicament.setVente(vente);
                venteMedicament.setClient(client);
                venteMedicament.setMedicament(medicament);

                venteMedicaments.add(venteMedicament);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return venteMedicaments;
    }

    public void updateVenteStatut(int idVente, String statut) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE vente SET statut = ? WHERE id_vente = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, statut);
            stmt.setInt(2, idVente);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateVenteDatePaiement(int idVente, LocalDateTime datePaiement) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE vente SET date_paiement = ? WHERE id_vente = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setTimestamp(1, Timestamp.valueOf(datePaiement));
            stmt.setInt(2, idVente);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}