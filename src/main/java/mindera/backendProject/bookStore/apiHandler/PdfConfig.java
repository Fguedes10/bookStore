package mindera.backendProject.bookStore.apiHandler;

import com.itextpdf.text.pdf.BaseFont;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PdfConfig {

    @Bean
    public BaseFont baseFont() throws Exception {
        String fontPath = BaseFont.SYMBOL;
        String encoding = BaseFont.CP1252;
        boolean embedded = BaseFont.EMBEDDED;
        return BaseFont.createFont(fontPath, encoding, embedded);
    }
}
