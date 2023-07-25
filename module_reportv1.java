import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class TextToPdfConverter {

    public static void main(String[] args) {
        String inputFilePath = "path/to/your/input_file.txt";
        String outputFilePath = "path/to/your/output_file.pdf";

        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Define column headings
            String[] headings = { "Column 1", "Column 2", "Column 3", "Column 4", "Column 5", "Column 6" };
            float[] columnWidths = { 100, 100, 100, 100, 100, 100 };
            float tableTopY = 700; // Adjust this value based on where you want to place the table on the page
            float margin = 50;
            float yPosition = tableTopY;

            // Draw the table headings
            drawTable(contentStream, yPosition, margin, headings, columnWidths);

            // Parse the input text file
            CSVParser parser = CSVParser.parse(new File(inputFilePath), java.nio.charset.StandardCharsets.UTF_8,
                    CSVFormat.DEFAULT.withDelimiter('|'));

            // Write the table data to the PDF
            for (CSVRecord record : parser) {
                yPosition -= 20; // Adjust the row height as needed
                drawTable(contentStream, yPosition, margin, record.toArray(), columnWidths);
            }

            contentStream.close();
            document.save(outputFilePath);
            document.close();

            System.out.println("PDF created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void drawTable(PDPageContentStream contentStream, float y, float margin, String[] content,
            float[] columnWidths) throws IOException {
        final int rows = 1;
        final int cols = content.length;
        final float rowHeight = 20f;
        final float tableWidth = 600; // Adjust this value based on your page width

        // Start the new row
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream.beginText();
        contentStream.moveTextPositionByAmount(margin, y);

        for (int i = 0; i < cols; i++) {
            // Draw the cell
            contentStream.drawString(content[i]);
            contentStream.moveTextPositionByAmount(columnWidths[i], 0);
        }

        contentStream.endText();
    }
}
