import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileOutputStream;
import java.io.IOException;

public class TextToPDF {
    public static void main(String[] args) {
        String inputFile = "input.txt";  // input text file
        String outputFile = "output.pdf";  // output PDF file

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(outputFile));
            document.open();
            
            PdfPTable table = new PdfPTable(6);  // number of columns in the table
            
            // Add table headers
            table.addCell("Header 1");
            table.addCell("Header 2");
            table.addCell("Header 3");
            table.addCell("Header 4");
            table.addCell("Header 5");
            table.addCell("Header 6");

            // Open the text file
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            String line;
            
            // Loop through each line in the file
            while ((line = br.readLine()) != null) {
                // Split the line by "|"
                String[] parts = line.split("\\|");
                
                // Add a cell for each part
                for (String part : parts) {
                    table.addCell(part);
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
