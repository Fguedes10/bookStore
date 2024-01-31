package mindera.backendProject.bookStore.service.orderService;

import mindera.backendProject.bookStore.converter.order.InvoiceConverter;
import mindera.backendProject.bookStore.dto.order.InvoiceCreateDto;
import mindera.backendProject.bookStore.dto.order.InvoiceGetDto;
import mindera.backendProject.bookStore.exception.customer.CustomerNotFoundException;
import mindera.backendProject.bookStore.exception.order.InvoiceAlreadyExistsException;
import mindera.backendProject.bookStore.exception.order.InvoiceNotFoundException;
import mindera.backendProject.bookStore.exception.order.OrderNotFoundException;
import mindera.backendProject.bookStore.model.Customer;
import mindera.backendProject.bookStore.model.Invoice;
import mindera.backendProject.bookStore.model.OrderModel;
import mindera.backendProject.bookStore.repository.orderRepository.InvoiceRepository;
import mindera.backendProject.bookStore.service.customerService.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static mindera.backendProject.bookStore.util.Messages.*;

@Service
public class InvoiceServiceImpl implements InvoiceService {


    private final InvoiceRepository invoiceRepository;

    private final CustomerServiceImpl customerService;

    private final OrderServiceImpl orderServiceImpl;


    @Autowired
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, CustomerServiceImpl customerService, OrderServiceImpl orderService) {
        this.invoiceRepository = invoiceRepository;
        this.customerService = customerService;
        this.orderServiceImpl = orderService;
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

   @Override
    public InvoiceGetDto createInvoice(InvoiceCreateDto invoice) throws InvoiceAlreadyExistsException, CustomerNotFoundException, OrderNotFoundException {
        verifyInvoiceExists(invoice);
        Customer customer = customerService.findById(invoice.customerId());
        OrderModel orderModel = orderServiceImpl.findById(invoice.orderModelId());
        Invoice invoiceToSave = InvoiceConverter.fromCreateDtoToModel(invoice, customer, orderModel);

        invoiceRepository.save(invoiceToSave);
        return InvoiceConverter.fromModelToInvoiceGetDto(invoiceToSave);
    }

    @Override
    public List<InvoiceGetDto> createInvoices(List<InvoiceCreateDto> invoiceCreateDto) throws CustomerNotFoundException, OrderNotFoundException, InvoiceAlreadyExistsException {
        List<InvoiceGetDto> invoicesCreated = new ArrayList<>();
        for (InvoiceCreateDto invoiceToCreate : invoiceCreateDto) {
            Customer customer = customerService.findById(invoiceToCreate.customerId());
            OrderModel orderModel = orderServiceImpl.findById(invoiceToCreate.orderModelId());
            verifyInvoiceExists(invoiceToCreate);
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

    private void verifyInvoiceExists(InvoiceCreateDto invoiceToCreate) throws InvoiceAlreadyExistsException {
        Optional<Invoice> invoiceFoundByInvoiceNumber = invoiceRepository.findByInvoiceNumber(invoiceToCreate.invoiceNumber());
        if (invoiceFoundByInvoiceNumber.isPresent()) {
            throw new InvoiceAlreadyExistsException(INVOICE_WITH_INVOICE_NUMBER + invoiceToCreate + ALREADY_EXISTS);
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

