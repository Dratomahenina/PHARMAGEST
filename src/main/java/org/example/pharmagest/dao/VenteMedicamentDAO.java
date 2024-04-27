package org.example.pharmagest.dao;

import org.example.pharmagest.model.VenteMedicament;
import org.example.pharmagest.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VenteMedicamentDAO {

    public List<VenteMedicament> getAllVenteMedicamentsNonPayes() {
        List<VenteMedicament> venteMedicaments = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT vm.*, m.nom_medicament, v.date_vente " +
                    "FROM vente_medicament vm " +
                    "JOIN medicament m ON vm.id_medicament = m.id_medicament " +
                    "JOIN vente v ON vm.id_vente = v.id_vente " +
                    "WHERE v.statut = 'En attente'";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                VenteMedicament venteMedicament = new VenteMedicament();
                venteMedicament.setIdVenteMedicament(rs.getInt("id_vente_medicament"));
                venteMedicament.setIdVente(rs.getInt("id_vente"));
                venteMedicament.setMedicament(new MedicamentDAO().getMedicamentById(rs.getInt("id_medicament")));
                venteMedicament.setQuantite(rs.getInt("quantite"));
                venteMedicament.setPrixUnitaire(rs.getDouble("prix_unitaire"));
                venteMedicament.setDateVente(rs.getTimestamp("date_vente").toLocalDateTime());
                venteMedicaments.add(venteMedicament);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return venteMedicaments;
    }
}