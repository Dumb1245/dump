import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TextToPdf {

    public static void main(String[] args) throws IOException {
        String dest = "./output.pdf";
        String src = "./input.txt";

        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, PageSize.A4.rotate());
        doc.setMargins(20, 20, 20, 20);

        Table table = new Table(6);
        BufferedReader br = new BufferedReader(new FileReader(src));
        String line;

        while ((line = br.readLine()) != null) {
            String[] fields = line.split("\\|");

            for (String field : fields) {
                table.addCell(new Cell().add(new Paragraph(field)
                        .setFont(PdfFontFactory.createFont(FontConstants.HELVETICA))
                        .setFontSize(10)
                        .setTextAlignment(TextAlignment.LEFT)));
            }
        }
        br.close();

        doc.add(table);
        doc.close();
    }
}
