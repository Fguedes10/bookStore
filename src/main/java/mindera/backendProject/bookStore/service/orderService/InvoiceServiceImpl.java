package mindera.backendProject.bookStore.service.orderService;

import mindera.backendProject.bookStore.converter.order.InvoiceConverter;
import mindera.backendProject.bookStore.dto.order.InvoiceGetByCustomerDto;
import mindera.backendProject.bookStore.dto.order.InvoiceGetDto;
import mindera.backendProject.bookStore.exception.customer.CustomerNotFoundException;
import mindera.backendProject.bookStore.exception.order.InvoiceAlreadyExistsException;
import mindera.backendProject.bookStore.exception.order.InvoiceNotFoundException;
import mindera.backendProject.bookStore.model.Customer;
import mindera.backendProject.bookStore.model.Invoice;
import mindera.backendProject.bookStore.repository.customerRepository.CustomerRepository;
import mindera.backendProject.bookStore.repository.orderRepository.InvoiceRepository;
import mindera.backendProject.bookStore.service.customerService.CustomerServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static mindera.backendProject.bookStore.util.Messages.*;

@Service
public class InvoiceServiceImpl implements InvoiceService {


    private final InvoiceRepository invoiceRepository;
    private final CustomerServiceImpl customerService;
    private final CustomerRepository customerRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, CustomerServiceImpl customerService, CustomerRepository customerRepository) {
        this.invoiceRepository = invoiceRepository;
        this.customerService = customerService;
        this.customerRepository = customerRepository;
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

