package org.example.pharmagest.dao;

import javafx.collections.ObservableList;
import org.example.pharmagest.model.Client;
import org.example.pharmagest.model.Medicament;
import org.example.pharmagest.model.Vente;
import org.example.pharmagest.model.VenteMedicament;
import org.example.pharmagest.utils.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;

public class VenteDAO {

    public void addVente(Vente vente) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO vente (id_client, date_vente, type_vente, statut, montant_total, remise) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, vente.getClient().getIdClient());
            if (vente.getDateVente() != null) {
                stmt.setDate(2, Date.valueOf(vente.getDateVente()));
            } else {
                stmt.setDate(2, null);
            }
            stmt.setString(3, vente.getTypeVente());
            stmt.setString(4, vente.getStatut());
            stmt.setDouble(5, vente.getMontantTotal());
            stmt.setDouble(6, vente.getRemise());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int idVente = rs.getInt(1);
                vente.setIdVente(idVente);
                addVenteMedicaments(idVente, vente.getMedicaments()); // Appelez la méthode existante ici
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addVenteMedicaments(int idVente, List<VenteMedicament> medicaments) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO vente_medicament (id_vente, id_medicament, quantite, prix_unitaire, id_client, type_vente) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            for (VenteMedicament venteMedicament : medicaments) {
                stmt.setInt(1, idVente);
                stmt.setInt(2, venteMedicament.getMedicament().getIdMedicament());
                stmt.setInt(3, venteMedicament.getQuantite());
                stmt.setDouble(4, venteMedicament.getPrixUnitaire());
                stmt.setInt(5, venteMedicament.getIdClient());
                stmt.setString(6, venteMedicament.getTypeVente());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Vente> getAllVentes() {
        List<Vente> ventes = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM vente";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Vente vente = new Vente();
                vente.setIdVente(rs.getInt("id_vente"));
                vente.setClient(new ClientDAO().getClientById(rs.getInt("id_client")));
                Date dateVente = rs.getDate("date_vente");
                if (dateVente != null) {
                    vente.setDateVente(dateVente.toLocalDate());
                }
                vente.setTypeVente(rs.getString("type_vente"));
                vente.setStatut(rs.getString("statut"));
                vente.setMontantTotal(rs.getDouble("montant_total"));
                vente.setRemise(rs.getDouble("remise"));

                List<VenteMedicament> venteMedicamentList = getVenteMedicaments(vente.getIdVente());
                ObservableList<VenteMedicament> observableVenteMedicamentList = FXCollections.observableArrayList(venteMedicamentList);
                vente.setMedicaments(observableVenteMedicamentList);

                ventes.add(vente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ventes;
    }

    public List<Vente> getAllVentesEnAttente() {
        List<Vente> ventes = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM vente WHERE statut = 'En attente'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Vente vente = new Vente();
                vente.setIdVente(rs.getInt("id_vente"));
                vente.setClient(new ClientDAO().getClientById(rs.getInt("id_client")));
                Date dateVente = rs.getDate("date_vente");
                if (dateVente != null) {
                    vente.setDateVente(dateVente.toLocalDate());
                }
                vente.setTypeVente(rs.getString("type_vente"));
                vente.setStatut(rs.getString("statut"));
                vente.setMontantTotal(rs.getDouble("montant_total"));
                vente.setRemise(rs.getDouble("remise"));

                List<VenteMedicament> venteMedicamentList = getVenteMedicaments(vente.getIdVente());
                ObservableList<VenteMedicament> observableVenteMedicamentList = FXCollections.observableArrayList(venteMedicamentList);
                vente.setMedicaments(observableVenteMedicamentList);

                ventes.add(vente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ventes;
    }

    private List<VenteMedicament> getVenteMedicaments(int idVente) {
        List<VenteMedicament> venteMedicaments = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM vente_medicament WHERE id_vente = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, idVente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                VenteMedicament venteMedicament = new VenteMedicament();
                venteMedicament.setIdVenteMedicament(rs.getInt("id_vente_medicament"));
                Medicament medicament = new MedicamentDAO().getMedicamentById(rs.getInt("id_medicament"));
                venteMedicament.setMedicament(medicament);
                venteMedicament.setQuantite(rs.getInt("quantite"));
                venteMedicament.setPrixUnitaire(rs.getDouble("prix_unitaire"));
                venteMedicament.setIdClient(rs.getInt("id_client"));
                venteMedicament.setTypeVente(rs.getString("type_vente"));
                venteMedicaments.add(venteMedicament);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return venteMedicaments;
    }

    public void updateVente(Vente vente) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE vente SET statut = ?, date_paiement = ? WHERE id_vente = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, vente.getStatut());
            stmt.setTimestamp(2, Timestamp.valueOf(vente.getDatePaiement()));
            stmt.setInt(3, vente.getIdVente());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Vente getVenteByIdVente(int idVente) {
        Vente vente = null;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM vente WHERE id_vente = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, idVente);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                vente = new Vente();
                vente.setIdVente(rs.getInt("id_vente"));
                vente.setClient(new ClientDAO().getClientById(rs.getInt("id_client")));
                Date dateVente = rs.getDate("date_vente");
                if (dateVente != null) {
                    vente.setDateVente(dateVente.toLocalDate());
                }
                vente.setTypeVente(rs.getString("type_vente"));
                vente.setStatut(rs.getString("statut"));
                vente.setMontantTotal(rs.getDouble("montant_total"));
                vente.setRemise(rs.getDouble("remise"));
                Timestamp datePaiement = rs.getTimestamp("date_paiement");
                if (datePaiement != null) {
                    vente.setDatePaiement(datePaiement.toLocalDateTime());
                }

                List<VenteMedicament> venteMedicamentList = getVenteMedicaments(vente.getIdVente());
                ObservableList<VenteMedicament> observableVenteMedicamentList = FXCollections.observableArrayList(venteMedicamentList);
                vente.setMedicaments(observableVenteMedicamentList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vente;
    }

    public List<Vente> getVentesPayees() {
        List<Vente> ventesPayees = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM vente WHERE statut = 'Payée'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Vente vente = new Vente();
                vente.setIdVente(rs.getInt("id_vente"));
                vente.setClient(new ClientDAO().getClientById(rs.getInt("id_client")));
                Date dateVente = rs.getDate("date_vente");
                if (dateVente != null) {
                    vente.setDateVente(dateVente.toLocalDate());
                }
                vente.setTypeVente(rs.getString("type_vente"));
                vente.setStatut(rs.getString("statut"));
                vente.setMontantTotal(rs.getDouble("montant_total"));
                vente.setRemise(rs.getDouble("remise"));
                Timestamp datePaiement = rs.getTimestamp("date_paiement");
                if (datePaiement != null) {
                    vente.setDatePaiement(datePaiement.toLocalDateTime());
                }

                ventesPayees.add(vente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ventesPayees;
    }
}