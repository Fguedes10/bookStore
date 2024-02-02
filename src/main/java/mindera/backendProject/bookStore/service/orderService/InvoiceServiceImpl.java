package mindera.backendProject.bookStore.service.orderService;

import com.itextpdf.text.DocumentException;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import mindera.backendProject.bookStore.apiHandler.EmailServiceImpl;
import mindera.backendProject.bookStore.apiHandler.PdfCreator;
import mindera.backendProject.bookStore.converter.order.InvoiceConverter;
import mindera.backendProject.bookStore.dto.order.InvoiceCreateDto;
import mindera.backendProject.bookStore.dto.order.InvoiceGetByCustomerDto;
import mindera.backendProject.bookStore.dto.order.InvoiceGetDto;
import mindera.backendProject.bookStore.exception.customer.CustomerNotFoundException;
import mindera.backendProject.bookStore.exception.order.InvoiceAlreadyExistsException;
import mindera.backendProject.bookStore.exception.order.InvoiceNotFoundException;
import mindera.backendProject.bookStore.exception.order.OrderNotFoundException;
import mindera.backendProject.bookStore.model.Book;
import mindera.backendProject.bookStore.model.Customer;
import mindera.backendProject.bookStore.model.Invoice;
import mindera.backendProject.bookStore.model.OrderModel;
import mindera.backendProject.bookStore.repository.customerRepository.CustomerRepository;
import mindera.backendProject.bookStore.repository.orderRepository.InvoiceRepository;
import mindera.backendProject.bookStore.service.customerService.CustomerServiceImpl;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static mindera.backendProject.bookStore.util.Messages.*;

@Service
public class InvoiceServiceImpl implements InvoiceService {


    private final InvoiceRepository invoiceRepository;
    private final CustomerServiceImpl customerService;
    private final OrderServiceImpl orderServiceImpl;
    private final CustomerRepository customerRepository;
    private final PdfCreator pdfCreator;
    private final EmailServiceImpl emailService;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, CustomerServiceImpl customerService, OrderServiceImpl orderServiceImpl, CustomerRepository customerRepository, PdfCreator pdfCreator, EmailServiceImpl emailService) {
        this.invoiceRepository = invoiceRepository;
        this.customerService = customerService;
        this.orderServiceImpl = orderServiceImpl;
        this.customerRepository = customerRepository;
        this.pdfCreator = pdfCreator;
        this.emailService = emailService;
    }

    @Override
    public List<InvoiceGetDto> getInvoices() {
        List<Invoice> invoiceList = invoiceRepository.findAll();
        return invoiceList.stream().map(InvoiceConverter::fromModelToInvoiceGetDto).toList();
    }

    @Override
    public InvoiceGetDto getInvoice(Long invoiceId) throws InvoiceNotFoundException {
        Optional<Invoice> invoiceOptional = verifyIfInvoiceExistsById(invoiceId);
        return InvoiceConverter.fromModelToInvoiceGetDto(invoiceOptional.get());
    }


    public List<InvoiceGetByCustomerDto> getInvoiceByCustomer(Long customerId) throws CustomerNotFoundException {
        if (customerId <= 0) {
            throw new CustomerNotFoundException(CUSTOMER_WITH_ID + customerId + DOESNT_EXIST);
        }
        Optional<Customer> getCustomer = customerRepository.findById(customerId);
        if (getCustomer.isEmpty()) {
            throw new CustomerNotFoundException(CUSTOMER_WITH_ID + customerId + DOESNT_EXIST);
        }
        List<Invoice> findedInvoices = invoiceRepository.findInvoicesByCustomer(customerId);
        if (findedInvoices.isEmpty()) {
            throw new CustomerNotFoundException(NO_INVOICE_WITH_CUSTOMER + customerId);
        }
        return invoiceRepository.findInvoicesByCustomer(customerId)
                .stream()
                .map(InvoiceConverter::fromModelToInvoiceGetByCustomerDto)
                .toList();
    }

    @Override
    @Transactional
    public InvoiceGetDto createInvoice(InvoiceCreateDto invoice, int invoiceNumber) throws InvoiceAlreadyExistsException, CustomerNotFoundException, OrderNotFoundException, DocumentException, FileNotFoundException {
        verifyInvoiceExists(invoiceNumber);
        Customer customer = customerService.findById(invoice.customerId());
        OrderModel orderModel = orderServiceImpl.findById(invoice.orderModelId());
        Invoice invoiceToSave = InvoiceConverter.fromCreateDtoToModel(invoice, customer, orderModel);


        double totalPrice = 0;

        for (Book book : orderModel.getBooks()) {
            totalPrice += book.getPrice();
        }

        invoiceToSave.setOrderModel(orderModel);
        invoiceToSave.setIssueDate(LocalDate.now());
        invoiceToSave.setInvoiceNumber(invoiceNumber);
        invoiceToSave.setTotalAmount(totalPrice);
        invoiceToSave.getVAT();


        invoiceRepository.save(invoiceToSave);
        String invoiceId = invoiceToSave.getId().toString();
        System.out.println(invoiceId);


        pdfCreator.createPdf("InvoicePDF".concat(invoiceToSave.getId().toString()).concat(".pdf"), invoiceToSave);

        //insert download here

        try {
            emailService.sendEmailWithAttachment(invoiceToSave.getCustomer());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }


        return InvoiceConverter.fromModelToInvoiceGetDto(invoiceToSave);
    }

    @Override
    public List<InvoiceGetDto> createInvoices(List<InvoiceCreateDto> invoiceCreateDto, int invoiceNumber) throws CustomerNotFoundException, OrderNotFoundException, InvoiceAlreadyExistsException {
        List<InvoiceGetDto> invoicesCreated = new ArrayList<>();
        for (InvoiceCreateDto invoiceToCreate : invoiceCreateDto) {
            Customer customer = customerService.findById(invoiceToCreate.customerId());
            OrderModel orderModel = orderServiceImpl.findById(invoiceToCreate.orderModelId());
            verifyInvoiceExists(invoiceNumber);
            Invoice invoiceTSave = InvoiceConverter.fromCreateDtoToModel(invoiceToCreate, customer, orderModel);
            invoiceRepository.save(invoiceTSave);
            invoicesCreated.add(InvoiceConverter.fromModelToInvoiceGetDto(invoiceTSave));
        }
        return invoicesCreated;
    }

    @Override
    public void deleteInvoice(Long invoiceId) throws InvoiceNotFoundException {
        Optional<Invoice> invoiceOptional = verifyIfInvoiceExistsById(invoiceId);
        invoiceRepository.delete(invoiceOptional.get());
    }

    private Optional<Invoice> verifyIfInvoiceExistsById(Long invoiceId) throws InvoiceNotFoundException {
        Optional<Invoice> invoiceOptional = invoiceRepository.findById(invoiceId);
        if (invoiceOptional.isEmpty()) {
            throw new InvoiceNotFoundException(INVOICE_WITH_ID + invoiceId + DOESNT_EXIST);
        }
        return invoiceOptional;
    }

    private void verifyInvoiceExists(int invoiceNumber) throws InvoiceAlreadyExistsException {
        Optional<Invoice> invoiceFoundByInvoiceNumber = invoiceRepository.findByInvoiceNumber(invoiceNumber);
        if (invoiceFoundByInvoiceNumber.isPresent()) {
            throw new InvoiceAlreadyExistsException(INVOICE_WITH_INVOICE_NUMBER + invoiceNumber + ALREADY_EXISTS);
        }
    }


    public Invoice findById(Long invoiceId) throws InvoiceNotFoundException {
        Optional<Invoice> invoiceOptional = invoiceRepository.findById(invoiceId);
        if (invoiceOptional.isEmpty()) {
            throw new InvoiceNotFoundException(INVOICE_WITH_ID + invoiceId + DOESNT_EXIST);
        }
        return invoiceOptional.get();
    }


}

