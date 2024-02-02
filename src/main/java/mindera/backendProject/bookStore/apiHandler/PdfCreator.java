package mindera.backendProject.bookStore.apiHandler;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.transaction.Transactional;
import mindera.backendProject.bookStore.model.Book;
import mindera.backendProject.bookStore.model.Invoice;
import mindera.backendProject.bookStore.repository.bookRepository.BookRepository;
import mindera.backendProject.bookStore.repository.customerRepository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class PdfCreator {
    private BaseFont bfBold;
    private BaseFont bf;
    private int pageNumber = 0;
    private String customerName;
    private String invoiceDate;
    private String invoiceId;
    private List<Book> bookList;
    private double totalPrice;
    private CustomerRepository customerRepository;
    private BookRepository bookRepository;

    public PdfCreator(BaseFont bfBold, BaseFont bf, CustomerRepository customerRepository, BookRepository bookRepository) {
        this.bfBold = bfBold;
        this.bf = bf;
        this.customerRepository = customerRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public void createPdf(String fileName, Invoice invoice) {

        Document document = new Document();
        PdfWriter pdfWriter = null;
        startFonts();


        this.customerName = invoice.getCustomer().getFirstName() + " " + invoice.getCustomer().getLastName();
        this.invoiceDate = invoice.getIssueDate().toString();
        this.invoiceId = invoice.getId().toString();
        this.bookList = invoice.getOrderModel().getBooks();
        this.totalPrice = invoice.getTotalAmount();
    }

    private void startFonts() {
        try {
            bfBold = BaseFont.createFont(BaseFont.COURIER, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            bf = BaseFont.createFont(BaseFont.SYMBOL, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
