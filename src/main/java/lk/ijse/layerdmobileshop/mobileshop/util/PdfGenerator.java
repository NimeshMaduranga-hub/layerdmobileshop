package lk.ijse.layerdmobileshop.mobileshop.util;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import java.io.File;
import java.io.FileOutputStream;

public class PdfGenerator {

    public static void generatePdf(String html, String fileName) throws Exception {

        try (FileOutputStream os = new FileOutputStream(fileName)) {

            PdfRendererBuilder builder = new PdfRendererBuilder();
            String baseUri = new File("src/main/resources/").toURI().toString();


            builder.withHtmlContent(html, null);
            builder.toStream(os);
            builder.run();
        }
    }
}