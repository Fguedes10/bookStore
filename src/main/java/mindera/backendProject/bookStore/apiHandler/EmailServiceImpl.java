package mindera.backendProject.bookStore.apiHandler;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import mindera.backendProject.bookStore.model.Customer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailServiceImpl {

    private JavaMailSender emailSender;


    public void sendemailWithAttachment(Customer customer) throws MessagingException {


        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);


        helper.setFrom("ebookstoremindera@gmail.com");
        helper.setTo(customer.getEmail());
        helper.setSubject("eBook Store Mindera - Order confirmation and eBook Download details");
        helper.setText("Dear " + customer.getFirstName() + customer.getLastName() + " ,\n\n" +
                "we are delighted to confirm your recent order and provide you with the details you'll need to download your eBook,\n" +
                "\n" +
                "To download your eBook, please click on the link below: \n" +
                //Link do Download

                "We've also sent you the invoice as an attachment.\n\n" +
                "THANK YOU! \n" +
                "We appreciate your trust in us. \n" +
                "If you have any questions or encounter any issues during the download process, please feel free to reach out to our customer support team. \n" +
                "Happy reading!");

        FileSystemResource file = new FileSystemResource(new File("  ".concat(customer.getInvoices().toString()).concat(".pdf"))); // aqui -> caminho do PDF criado
        helper.addAttachment("Invoice", file);

        emailSender.send(message);


    }

}
