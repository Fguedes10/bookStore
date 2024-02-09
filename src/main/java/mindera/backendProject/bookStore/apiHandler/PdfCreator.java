package mindera.backendProject.bookStore.apiHandler;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.transaction.Transactional;
import mindera.backendProject.bookStore.exception.order.PdfNotFoundException;
import mindera.backendProject.bookStore.model.Book;
import mindera.backendProject.bookStore.model.Invoice;
import mindera.backendProject.bookStore.model.OrderModel;
import mindera.backendProject.bookStore.repository.bookRepository.BookRepository;
import mindera.backendProject.bookStore.repository.customerRepository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;

import static mindera.backendProject.bookStore.util.Messages.PDF_NOT_FOUND;

@Service
public class PdfCreator {
    
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    BookRepository bookRepository;


    public void createPdf(OrderModel order, Invoice invoice) throws DocumentException, FileNotFoundException, PdfNotFoundException {

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(order.getInvoiceFilePath()));
        } catch (DocumentException | FileNotFoundException e) {
            throw new PdfNotFoundException(PDF_NOT_FOUND);
        }

        document.open();

        try {
            document.add(new Paragraph(printInvoice(invoice.getOrderModel(), invoice)));
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }

        document.close();
    }


    private String printInvoice(OrderModel orderModel, Invoice invoice) {

        LocalDate invoiceDate = LocalDate.now();


        String invoiceText = " ";
        for (Book book : orderModel.getBooks()) {
            invoiceText = invoiceText.concat(("Book: " + book.getTitle() + book.getAuthor() + "\t" + " - unit. price: " + book.getPrice()
                    + "\t" + "\n" + "Total price: " + orderModel.getOrderItems().getAmountToPay()) + "\n\n");
        }

        String finalText = "Invoice number: " + "\n" + invoice.getInvoiceNumber() + "Client: " + invoice.getCustomer().getFirstName() + " " + invoice.getCustomer().getLastName() + "\n" + "Nif: " + invoice.getCustomer().getNif()
                + "\n" + "Total Amount: " + orderModel.getOrderItems().getAmountToPay();

        return invoiceText + finalText;
    }


}
