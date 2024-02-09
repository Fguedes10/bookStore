package mindera.backendProject.bookStore.apiHandler;

import mindera.backendProject.bookStore.model.Customer;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.MultiPartEmail;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl {


    public void sendEmailWithAttachment(String recipientEmail, String attachmentPath, String fileName, Customer customer, String downloadLink) {
        try {
            // CREATE
            MultiPartEmail email = new MultiPartEmail();
            email.setHostName("smp.gmail.com");
            email.setSmtpPort(587);
            email.setAuthenticator(new DefaultAuthenticator("melissasa6@gmail.com", "wsry xmsb lwww yeky"));
            email.setStartTLSRequired(true);
            email.setFrom("ebookstoremindera@gmail.com");
            email.addTo(recipientEmail);
            email.setSubject("eBook Store Mindera - Order: " + fileName + " confirmation and eBook Download details");
            email.setMsg("Dear " + customer.getFirstName() + customer.getLastName() + " ,\n\n" +
                    "we are delighted to confirm your recent order and provide you with the details you'll need to download your eBook,\n" +
                    "\n" +
                    "To download your eBook, please click on the link below: \n" +
                    downloadLink + "\n\n" +
                    "We've also sent you the invoice as an attachment.\n\n" +
                    "THANK YOU! \n" +
                    "We appreciate your trust in us. \n" +
                    "If you have any questions or encounter any issues during the download process, please feel free to reach out to our customer support team. \n" +
                    "Happy reading!");


            // CREATE ATTACHMENT

            EmailAttachment attachment = new EmailAttachment();
            attachment.setPath(attachmentPath);
            attachment.setDisposition(EmailAttachment.ATTACHMENT);
            attachment.setDescription("PDF Attachement");
            attachment.setName(fileName);

            // ATTACH THE FILE
            email.attach(attachment);

            // SEND
            email.send();
            System.out.println("Email sent.");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
