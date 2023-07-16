import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class TextToPDFConverter {

    public static void main(String[] args) throws IOException {
        // Create a new document
        Document document = new Document();

        // Create a new PDF writer
        PdfWriter writer = PdfWriter.getInstance(document, new File("table.pdf"));

        // Open the document
        document.open();

        // Create a list to store the words from the text file
        List<String> words = new ArrayList<>();

        // Read the text file
        try (BufferedReader reader = new BufferedReader(new FileReader("text.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line into words
                String[] wordArray = line.split("\\|");

                // Add the words to the list
                for (String word : wordArray) {
                    words.add(word);
                }
            }
        }

        // Create a table with the words
        PdfTable table = new PdfTable(words);

        // Add the table to the document
        document.add(table);

        // Close the document
        document.close();
    }

}

class PdfTable {

    private List<String> words;

    public PdfTable(List<String> words) {
        this.words = words;
    }

    public void add(String word) {
        words.add(word);
    }

    public void toPdf(Document document) {
        // Create a paragraph to hold the table
        Paragraph paragraph = new Paragraph();

        // Create a font for the table
        Font font = new Font();
        font.setSize(10);

        // Create a table
        Table table = new Table(words.size());
        table.setWidthPercentage(100);

        // Add the words to the table
        for (String word : words) {
            table.addCell(word);
        }

        // Add the table to the paragraph
        paragraph.add(table);

        // Add the paragraph to the document
        document.add(paragraph);
    }

}
