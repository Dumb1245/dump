import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class TextToPDF {
    public static void main(String[] args) {
        String inputFile = "input.txt";  // input text file
        String outputFile = "output.pdf";  // output PDF file
        String imagePath = "logo.jpg";  // replace with the path to your image

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(outputFile));
            document.open();
            
            // Add image
            Image img = Image.getInstance(imagePath);
            img.scaleAbsolute(200, 100);  // scale image to 200x100
            img.setAlignment(Element.ALIGN_CENTER);
            document.add(img);

            // Add title
            Paragraph title = new Paragraph("Module Report", 
                                new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD));
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Add one-liner below title
            Paragraph oneLiner = new Paragraph("This is a module report for SIT env", 
                                new Font(Font.FontFamily.TIMES_ROMAN, 10));
            oneLiner.setAlignment(Element.ALIGN_CENTER);
            document.add(oneLiner);

            document.add(new Paragraph("\n"));  // add a line break after the title
            
            PdfPTable table = new PdfPTable(6);  // number of columns in the table
            table.setWidthPercentage(100);  // this makes the table width 100% of the page
            table.setWidths(new float[] {1f, 0.5f, 1f, 1f, 1f, 1f});  // set relative widths of columns
            
            // Define a smaller font
            Font smallerFont = new Font(Font.FontFamily.TIMES_ROMAN, 10);

            // Add table headers
            table.addCell(new PdfPCell(new Phrase("Header 1", smallerFont)));
            table.addCell(new PdfPCell(new Phrase("Header 2", smallerFont)));
            table.addCell(new PdfPCell(new Phrase("Header 3", smallerFont)));
            table.addCell(new PdfPCell(new Phrase("Header 4", smallerFont)));
            table.addCell(new PdfPCell(new Phrase("Header 5", smallerFont)));
            table.addCell(new PdfPCell(new Phrase("Header 6", smallerFont)));

            // Open the text file
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            String line;
            
            // Loop through each line in the file
            while ((line = br.readLine()) != null) {
                // Skip lines that start with "DM11_Module_Report"
                if (line.startsWith("DM11_Module_Report")) {
                    continue;
                }

                // Split the line by "|"
                String[] parts = line.split("\\|");

                // Check and rename third column if necessary
                if (parts.length > 2) {
                    if (parts[2].equals("1173")) {
                        parts[2] = "custom";
                    } else if (parts[2].equals("1174")) {
                        parts[2] = "query";
                    }
                }
                
                // Add a cell for each part with the smaller font
                for (String part : parts) {
                    table.addCell(new PdfPCell(new Phrase(part, smallerFont)));
                }
            }
            br.close();
            
            // Add the table to the document
            document.add(table);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }
}
