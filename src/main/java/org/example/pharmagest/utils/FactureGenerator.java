package org.example.pharmagest.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.example.pharmagest.model.Client;
import org.example.pharmagest.model.LigneVente;
import org.example.pharmagest.model.Vente;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class FactureGenerator {

    public static void generateFacture(Vente vente, String filePath) {
        try {
            // Créer le dossier "Factures" s'il n'existe pas
            File folder = new File("Factures");
            if (!folder.exists()) {
                folder.mkdirs();
            }

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Ajout du logo
            try {
                Image logo = Image.getInstance("src/main/resources/org/example/pharmagest/assets/logo.png");
                logo.scaleToFit(100, 100);
                document.add(logo);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Ajout des informations de la pharmacie
            Paragraph pharmacy = new Paragraph("Pharmagest\nMCCI Business School\n75000 Ébene\nTél : 5274 0595");
            document.add(pharmacy);

            // Ajout des informations de la facture
            Paragraph factureInfo = new Paragraph("Facture n°" + vente.getIdVente() + "\nDate : " + vente.getDateVente().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            factureInfo.setAlignment(Element.ALIGN_RIGHT);
            document.add(factureInfo);

            // Ajout des informations du client (si applicable)
            if (vente.getClient() != null) {
                int clientId = vente.getClient().getIdClient();
                Client client = getClientById(clientId);

                if (client != null) {
                    Paragraph clientInfo = new Paragraph();
                    clientInfo.add("Client : " + client.getNomClient() + " " + client.getPrenomClient() + "\n");

                    if (client.getAdresseClient() != null && !client.getAdresseClient().isEmpty()) {
                        clientInfo.add("Adresse : " + client.getAdresseClient() + "\n");
                    }

                    if (client.getTelephoneClient() != null && !client.getTelephoneClient().isEmpty()) {
                        clientInfo.add("Tél : " + client.getTelephoneClient() + "\n");
                    }

                    clientInfo.setSpacingBefore(20);
                    document.add(clientInfo);
                }
            }


            // Ajout du tableau des médicaments
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setSpacingBefore(20);

            PdfPCell cell1 = new PdfPCell(new Phrase("Médicament"));
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell1);

            PdfPCell cell2 = new PdfPCell(new Phrase("Quantité"));
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell2);

            PdfPCell cell3 = new PdfPCell(new Phrase("Prix Unitaire"));
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell3);

            PdfPCell cell4 = new PdfPCell(new Phrase("Prix Total"));
            cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell4);

            for (LigneVente ligneVente : vente.getLignesVente()) {
                table.addCell(ligneVente.getMedicament().getNomMedicament());
                table.addCell(String.valueOf(ligneVente.getQuantite()));
                table.addCell(String.format("%.2f Rs", ligneVente.getPrixUnitaire()));
                table.addCell(String.format("%.2f Rs", ligneVente.getPrixTotal()));
            }

            document.add(table);

            // Ajout du total de la facture
            Paragraph total = new Paragraph("Total : " + String.format("%.2f Rs", vente.getMontantTotal()));
            total.setAlignment(Element.ALIGN_RIGHT);
            total.setSpacingBefore(20);
            document.add(total);

            document.close();
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Client getClientById(int clientId) {
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

}