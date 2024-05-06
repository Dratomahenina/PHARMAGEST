package org.example.pharmagest.dao;

import org.example.pharmagest.model.Client;
import org.example.pharmagest.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientDAO {

    public void addClient(Client client) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO client (nom_client, prenom_client, date_naissance_client, adresse_client, telephone_client, statut) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, client.getNomClient());
            stmt.setString(2, client.getPrenomClient());
            stmt.setDate(3, Date.valueOf(client.getDateNaissanceClient()));
            stmt.setString(4, client.getAdresseClient());
            stmt.setString(5, client.getTelephoneClient());
            stmt.setString(6, client.getStatut());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM client";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Client client = new Client(
                        rs.getInt("id_client"),
                        rs.getString("nom_client"),
                        rs.getString("prenom_client"),
                        rs.getDate("date_naissance_client").toLocalDate(),
                        rs.getString("adresse_client"),
                        rs.getString("telephone_client"),
                        rs.getString("statut"),
                        rs.getDate("date_creation").toLocalDate()
                );
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    public void updateClient(Client client) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE client SET nom_client = ?, prenom_client = ?, date_naissance_client = ?, adresse_client = ?, telephone_client = ?, statut = ? WHERE id_client = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, client.getNomClient());
            stmt.setString(2, client.getPrenomClient());
            stmt.setDate(3, Date.valueOf(client.getDateNaissanceClient()));
            stmt.setString(4, client.getAdresseClient());
            stmt.setString(5, client.getTelephoneClient());
            stmt.setString(6, client.getStatut());
            stmt.setInt(7, client.getIdClient());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteClient(Client client) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM client WHERE id_client = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, client.getIdClient());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Client> searchClients(String searchTerm) {
        List<Client> searchResults = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM client WHERE LOWER(nom_client) LIKE ? OR LOWER(prenom_client) LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, "%" + searchTerm + "%");
            stmt.setString(2, "%" + searchTerm + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Client client = new Client(
                        rs.getInt("id_client"),
                        rs.getString("nom_client"),
                        rs.getString("prenom_client"),
                        rs.getDate("date_naissance_client").toLocalDate(),
                        rs.getString("adresse_client"),
                        rs.getString("telephone_client"),
                        rs.getString("statut"),
                        rs.getDate("date_creation").toLocalDate()
                );
                searchResults.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return searchResults;
    }

    public Client getClientById(int clientId) {
        Client client = null;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM client WHERE id_client = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, clientId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                client = new Client(
                        rs.getInt("id_client"),
                        rs.getString("nom_client"),
                        rs.getString("prenom_client"),
                        rs.getDate("date_naissance_client").toLocalDate(),
                        rs.getString("adresse_client"),
                        rs.getString("telephone_client"),
                        rs.getString("statut"),
                        rs.getDate("date_creation").toLocalDate()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    public List<Client> getClientsActifs() {
        List<Client> clients = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM client WHERE statut = 'actif'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Client client = new Client(
                        rs.getInt("id_client"),
                        rs.getString("nom_client"),
                        rs.getString("prenom_client"),
                        rs.getDate("date_naissance_client").toLocalDate(),
                        rs.getString("adresse_client"),
                        rs.getString("telephone_client"),
                        rs.getString("statut"),
                        rs.getDate("date_creation").toLocalDate()
                );
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

}