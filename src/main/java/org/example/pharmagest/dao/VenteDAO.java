package org.example.pharmagest.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.pharmagest.model.Client;
import org.example.pharmagest.model.LigneVente;
import org.example.pharmagest.model.Medicament;
import org.example.pharmagest.model.Vente;
import org.example.pharmagest.utils.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VenteDAO {

    public int addVente(Vente vente) {
        int idVente = 0;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO vente (id_client, type_vente, montant_total, date_vente, statut) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            if (vente.getClient() != null) {
                stmt.setInt(1, vente.getClient().getIdClient());
            } else {
                stmt.setNull(1, Types.INTEGER);
            }
            stmt.setString(2, vente.getTypeVente());
            stmt.setDouble(3, vente.getMontantTotal());
            stmt.setDate(4, Date.valueOf(vente.getDateVente()));
            stmt.setString(5, vente.getStatut());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                idVente = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idVente;
    }

    public void addLigneVente(LigneVente ligneVente) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO ligne_vente (id_vente, id_medicament, quantite, prix_unitaire, prix_total) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, ligneVente.getIdVente());
            stmt.setInt(2, ligneVente.getMedicament().getIdMedicament());
            stmt.setInt(3, ligneVente.getQuantite());
            stmt.setDouble(4, ligneVente.getPrixUnitaire());
            stmt.setDouble(5, ligneVente.getPrixTotal());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Vente> getAllVentes() {
        List<Vente> ventes = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT v.*, c.nom_client, c.prenom_client FROM vente v LEFT JOIN client c ON v.id_client = c.id_client";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int idVente = rs.getInt("id_vente");
                Client client = new Client(rs.getInt("id_client"), rs.getString("nom_client"), rs.getString("prenom_client"), null, null, null, null, null);
                String typeVente = rs.getString("type_vente");
                double montantTotal = rs.getDouble("montant_total");
                LocalDate dateVente = rs.getDate("date_vente").toLocalDate();
                String statut = rs.getString("statut");
                ObservableList<LigneVente> lignesVente = FXCollections.observableArrayList(getLignesVenteByIdVente(idVente));
                Vente vente = new Vente(idVente, client, typeVente, montantTotal, dateVente, statut, lignesVente, null);
                ventes.add(vente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ventes;
    }

    public void updateVente(Vente vente) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE vente SET statut = ? WHERE id_vente = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, vente.getStatut());
            stmt.setInt(2, vente.getIdVente());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<LigneVente> getLignesVenteByIdVente(int idVente) {
        List<LigneVente> lignesVente = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT lv.*, m.nom_medicament, m.prix_vente FROM ligne_vente lv JOIN medicament m ON lv.id_medicament = m.id_medicament WHERE lv.id_vente = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, idVente);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int idLigneVente = rs.getInt("id_ligne_vente");
                int idMedicament = rs.getInt("id_medicament");
                String nomMedicament = rs.getString("nom_medicament");
                double prixVente = rs.getDouble("prix_vente");
                Medicament medicament = new Medicament(idMedicament, nomMedicament, null, null, null, null, 0, prixVente, 0, null);
                int quantite = rs.getInt("quantite");
                double prixUnitaire = rs.getDouble("prix_unitaire");
                double prixTotal = rs.getDouble("prix_total");
                LigneVente ligneVente = new LigneVente(idLigneVente, medicament, quantite, prixUnitaire, prixTotal);
                lignesVente.add(ligneVente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lignesVente;
    }

    public void deleteVente(Vente vente) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM vente WHERE id_vente = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, vente.getIdVente());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Vente> getAllVentesEnAttente() {
        List<Vente> ventes = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT v.*, c.nom_client, c.prenom_client FROM vente v LEFT JOIN client c ON v.id_client = c.id_client WHERE v.statut = 'En attente'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int idVente = rs.getInt("id_vente");
                Client client = new Client(rs.getInt("id_client"), rs.getString("nom_client"), rs.getString("prenom_client"), null, null, null, null, null);
                String typeVente = rs.getString("type_vente");
                double montantTotal = rs.getDouble("montant_total");
                LocalDate dateVente = rs.getDate("date_vente").toLocalDate();
                String statut = rs.getString("statut");
                ObservableList<LigneVente> lignesVente = FXCollections.observableArrayList(getLignesVenteByIdVente(idVente));
                Vente vente = new Vente(idVente, client, typeVente, montantTotal, dateVente, statut, lignesVente, null);
                ventes.add(vente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ventes;
    }

    public void enregistrerVentePayee(Vente vente) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO ventes_payees (id_vente, id_client, type_vente, montant_total, date_vente, date_paiement) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, vente.getIdVente());
            stmt.setInt(2, vente.getClient() != null ? vente.getClient().getIdClient() : 0);
            stmt.setString(3, vente.getTypeVente());
            stmt.setDouble(4, vente.getMontantTotal());
            stmt.setDate(5, Date.valueOf(vente.getDateVente()));
            stmt.setDate(6, Date.valueOf(LocalDate.now()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteVentePayee(Vente vente) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM ventes_payees WHERE id_vente = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, vente.getIdVente());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Vente> getVentesPayees() {
        List<Vente> ventesPayees = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT v.id_vente, v.id_client, c.nom_client, c.prenom_client, v.type_vente, v.montant_total, v.date_vente, vp.date_paiement " +
                    "FROM ventes_payees vp " +
                    "JOIN vente v ON vp.id_vente = v.id_vente " +
                    "LEFT JOIN client c ON v.id_client = c.id_client";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int idVente = rs.getInt("id_vente");
                Client client = new Client(rs.getInt("id_client"), rs.getString("nom_client"), rs.getString("prenom_client"), null, null, null, null, null);
                String typeVente = rs.getString("type_vente");
                double montantTotal = rs.getDouble("montant_total");
                LocalDate dateVente = rs.getDate("date_vente").toLocalDate();
                LocalDate datePaiement = rs.getDate("date_paiement").toLocalDate();
                ObservableList<LigneVente> lignesVente = FXCollections.observableArrayList(getLignesVenteByIdVente(idVente));
                Vente vente = new Vente(idVente, client, typeVente, montantTotal, dateVente, "Payée", lignesVente, datePaiement);
                ventesPayees.add(vente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ventesPayees;
    }

    public double getDailySales() {
        double dailySales = 0;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT SUM(montant_total) AS daily_sales FROM vente WHERE date_vente = CURRENT_DATE";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                dailySales = rs.getDouble("daily_sales");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dailySales;
    }

    public double getDailyProfit() {
        double dailyProfit = 0;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT SUM(montant_total - (SELECT SUM(prix_fournisseur * lv.quantite) FROM ligne_vente lv JOIN medicament m ON lv.id_medicament = m.id_medicament WHERE lv.id_vente = v.id_vente)) AS daily_profit FROM vente v WHERE date_vente = CURRENT_DATE";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                dailyProfit = rs.getDouble("daily_profit");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dailyProfit;
    }

    public List<Vente> getVentesByPeriod(String period) {
        List<Vente> ventes = new ArrayList<>();
        String query = "";

        switch (period) {
            case "Journalier":
                query = "SELECT * FROM vente WHERE date_vente = CURRENT_DATE";
                break;
            case "Hebdomadaire":
                query = "SELECT * FROM vente WHERE date_vente >= DATE_TRUNC('week', CURRENT_DATE) AND date_vente < DATE_TRUNC('week', CURRENT_DATE) + INTERVAL '1 week'";
                break;
            case "Mensuel":
                query = "SELECT * FROM vente WHERE date_vente >= DATE_TRUNC('month', CURRENT_DATE) AND date_vente < DATE_TRUNC('month', CURRENT_DATE) + INTERVAL '1 month'";
                break;
            case "Annuel":
                query = "SELECT * FROM vente WHERE date_vente >= DATE_TRUNC('year', CURRENT_DATE) AND date_vente < DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '1 year'";
                break;
            case "Total":
                query = "SELECT * FROM vente";
                break;
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idVente = rs.getInt("id_vente");
                int idClient = rs.getInt("id_client");
                String typeVente = rs.getString("type_vente");
                double montantTotal = rs.getDouble("montant_total");
                LocalDate dateVente = rs.getDate("date_vente").toLocalDate();
                String statut = rs.getString("statut");

                ObservableList<LigneVente> lignesVente = FXCollections.observableArrayList(getLignesVenteByIdVente(idVente));

                Vente vente = new Vente(idVente, null, typeVente, montantTotal, dateVente, statut, lignesVente, null);
                ventes.add(vente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ventes;
    }

    public List<Vente> getProfitsByPeriod(String period) {
        List<Vente> profits = new ArrayList<>();
        String query = "";

        switch (period) {
            case "Journalier":
                query = "SELECT v.id_vente, v.date_vente, SUM(lv.prix_total - (lv.quantite * m.prix_fournisseur)) AS profit " +
                        "FROM vente v " +
                        "JOIN ligne_vente lv ON v.id_vente = lv.id_vente " +
                        "JOIN medicament m ON lv.id_medicament = m.id_medicament " +
                        "WHERE v.date_vente = CURRENT_DATE " +
                        "GROUP BY v.id_vente, v.date_vente";
                break;
            case "Hebdomadaire":
                query = "SELECT v.id_vente, v.date_vente, SUM(lv.prix_total - (lv.quantite * m.prix_fournisseur)) AS profit " +
                        "FROM vente v " +
                        "JOIN ligne_vente lv ON v.id_vente = lv.id_vente " +
                        "JOIN medicament m ON lv.id_medicament = m.id_medicament " +
                        "WHERE v.date_vente >= DATE_TRUNC('week', CURRENT_DATE) AND v.date_vente < DATE_TRUNC('week', CURRENT_DATE) + INTERVAL '1 week' " +
                        "GROUP BY v.id_vente, v.date_vente";
                break;
            case "Mensuel":
                query = "SELECT v.id_vente, v.date_vente, SUM(lv.prix_total - (lv.quantite * m.prix_fournisseur)) AS profit " +
                        "FROM vente v " +
                        "JOIN ligne_vente lv ON v.id_vente = lv.id_vente " +
                        "JOIN medicament m ON lv.id_medicament = m.id_medicament " +
                        "WHERE v.date_vente >= DATE_TRUNC('month', CURRENT_DATE) AND v.date_vente < DATE_TRUNC('month', CURRENT_DATE) + INTERVAL '1 month' " +
                        "GROUP BY v.id_vente, v.date_vente";
                break;
            case "Annuel":
                query = "SELECT v.id_vente, v.date_vente, SUM(lv.prix_total - (lv.quantite * m.prix_fournisseur)) AS profit " +
                        "FROM vente v " +
                        "JOIN ligne_vente lv ON v.id_vente = lv.id_vente " +
                        "JOIN medicament m ON lv.id_medicament = m.id_medicament " +
                        "WHERE v.date_vente >= DATE_TRUNC('year', CURRENT_DATE) AND v.date_vente < DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '1 year' " +
                        "GROUP BY v.id_vente, v.date_vente";
                break;
            case "Total":
                query = "SELECT v.id_vente, v.date_vente, SUM(lv.prix_total - (lv.quantite * m.prix_fournisseur)) AS profit " +
                        "FROM vente v " +
                        "JOIN ligne_vente lv ON v.id_vente = lv.id_vente " +
                        "JOIN medicament m ON lv.id_medicament = m.id_medicament " +
                        "GROUP BY v.id_vente, v.date_vente";
                break;
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idVente = rs.getInt("id_vente");
                LocalDate dateVente = rs.getDate("date_vente").toLocalDate();
                double profit = rs.getDouble("profit");

                Vente vente = new Vente(idVente, null, null, 0, dateVente, null, null, null);
                vente.setProfit(profit);
                profits.add(vente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return profits;
    }

}
