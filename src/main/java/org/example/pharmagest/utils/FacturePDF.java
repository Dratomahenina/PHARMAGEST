package org.example.pharmagest.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.example.pharmagest.model.Vente;
import org.example.pharmagest.model.VenteMedicament;

import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;

public class FacturePDF {

    public static void genererFacturePDF(Vente vente) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("facture_vente_" + vente.getIdVente() + ".pdf"));
        document.open();

        // Logo Pharmagest
        Image logo = Image.getInstance("src/main/resources/org/example/pharmagest/assets/logo.png");
        logo.scaleAbsolute(100, 100);
        logo.setAlignment(Element.ALIGN_LEFT);
        document.add(logo);

        // Titre de la facture
        Font fontTitre = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
        Chunk chunkTitre = new Chunk("Facture de Vente", fontTitre);
        Paragraph paragrapheTitre = new Paragraph(chunkTitre);
        paragrapheTitre.setAlignment(Element.ALIGN_CENTER);
        document.add(paragrapheTitre);

        document.add(new Paragraph(" "));

        // Détails de la vente
        Font fontDetails = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
        Paragraph paragrapheDetails = new Paragraph();
        paragrapheDetails.add(new Chunk("ID Vente : " + vente.getIdVente(), fontDetails));
        paragrapheDetails.add(new Chunk(Chunk.NEWLINE));
        paragrapheDetails.add(new Chunk("Date : " + vente.getDateVente().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontDetails));
        paragrapheDetails.add(new Chunk(Chunk.NEWLINE));
        paragrapheDetails.add(new Chunk("Client : " + vente.getClient().getNomClient() + " " + vente.getClient().getPrenomClient(), fontDetails));
        paragrapheDetails.add(new Chunk(Chunk.NEWLINE));
        paragrapheDetails.add(new Chunk("Type de Vente : " + vente.getTypeVente(), fontDetails));
        document.add(paragrapheDetails);

        document.add(new Paragraph(" "));

        // Tableau des médicaments vendus
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{2, 1, 1, 1});

        Font fontEnTete = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
        Font fontContenu = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);

        PdfPCell cellEnTete = new PdfPCell(new Phrase("Médicament", fontEnTete));
        cellEnTete.setBackgroundColor(BaseColor.GRAY);
        cellEnTete.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cellEnTete);

        cellEnTete = new PdfPCell(new Phrase("Quantité", fontEnTete));
        cellEnTete.setBackgroundColor(BaseColor.GRAY);
        cellEnTete.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cellEnTete);

        cellEnTete = new PdfPCell(new Phrase("Prix Unitaire", fontEnTete));
        cellEnTete.setBackgroundColor(BaseColor.GRAY);
        cellEnTete.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cellEnTete);

        cellEnTete = new PdfPCell(new Phrase("Prix Total", fontEnTete));
        cellEnTete.setBackgroundColor(BaseColor.GRAY);
        cellEnTete.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cellEnTete);

        for (VenteMedicament venteMedicament : vente.getMedicaments()) {
            PdfPCell cellContenu = new PdfPCell(new Phrase(venteMedicament.getMedicament().getNomMedicament(), fontContenu));
            table.addCell(cellContenu);

            cellContenu = new PdfPCell(new Phrase(String.valueOf(venteMedicament.getQuantite()), fontContenu));
            cellContenu.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cellContenu);

            cellContenu = new PdfPCell(new Phrase(String.format("%.2f", venteMedicament.getPrixUnitaire()), fontContenu));
            cellContenu.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cellContenu);

            double prixTotal = venteMedicament.getQuantite() * venteMedicament.getPrixUnitaire();
            cellContenu = new PdfPCell(new Phrase(String.format("%.2f", prixTotal), fontContenu));
            cellContenu.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cellContenu);
        }

        document.add(table);

        document.add(new Paragraph(" "));

        // Remise et montant final
        Font fontRemise = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
        Paragraph paragrapheRemise = new Paragraph();
        paragrapheRemise.add(new Chunk("Remise : " + String.format("%.2f", vente.getRemise()) + " Dhs", fontRemise));
        paragrapheRemise.setAlignment(Element.ALIGN_RIGHT);
        document.add(paragrapheRemise);

        Font fontTotal = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.BLACK);
        Paragraph paragrapheTotal = new Paragraph(new Chunk("Montant Total : " + String.format("%.2f", vente.getMontantTotal() - vente.getRemise()) + " Dhs", fontTotal));
        paragrapheTotal.setAlignment(Element.ALIGN_RIGHT);
        document.add(paragrapheTotal);

        document.close();
    }
}
