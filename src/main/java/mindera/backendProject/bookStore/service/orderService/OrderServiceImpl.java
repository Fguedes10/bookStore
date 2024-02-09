package mindera.backendProject.bookStore.service.orderService;

import com.itextpdf.text.DocumentException;
import mindera.backendProject.bookStore.apiHandler.EmailServiceImpl;
import mindera.backendProject.bookStore.apiHandler.PdfCreator;
import mindera.backendProject.bookStore.converter.order.OrderConverter;
import mindera.backendProject.bookStore.dto.order.OrderCreateDto;
import mindera.backendProject.bookStore.dto.order.OrderGetByBookDto;
import mindera.backendProject.bookStore.dto.order.OrderGetByCustomerDto;
import mindera.backendProject.bookStore.dto.order.OrderGetDto;
import mindera.backendProject.bookStore.exception.book.BookNotFoundException;
import mindera.backendProject.bookStore.exception.customer.CustomerNotFoundException;
import mindera.backendProject.bookStore.exception.order.OrderAlreadyExistsException;
import mindera.backendProject.bookStore.exception.order.OrderNotFoundException;
import mindera.backendProject.bookStore.exception.order.PdfNotFoundException;
import mindera.backendProject.bookStore.model.Book;
import mindera.backendProject.bookStore.model.Customer;
import mindera.backendProject.bookStore.model.Invoice;
import mindera.backendProject.bookStore.model.OrderModel;
import mindera.backendProject.bookStore.repository.bookRepository.BookRepository;
import mindera.backendProject.bookStore.repository.customerRepository.CustomerRepository;
import mindera.backendProject.bookStore.repository.orderRepository.InvoiceRepository;
import mindera.backendProject.bookStore.repository.orderRepository.OrderItemRepository;
import mindera.backendProject.bookStore.repository.orderRepository.OrderRepository;
import mindera.backendProject.bookStore.service.bookService.BookServiceImpl;
import mindera.backendProject.bookStore.service.customerService.CustomerServiceImpl;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static mindera.backendProject.bookStore.util.Messages.*;


@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final BookServiceImpl bookService;
    private final CustomerServiceImpl customerService;
    private final CustomerRepository customerRepository;
    private final BookRepository bookRepository;
    private final InvoiceServiceImpl invoiceService;
    private final InvoiceRepository invoiceRepository;
    private final OrderItemRepository orderItemRepository;
    private final PdfCreator pdfCreator;
    private final EmailServiceImpl emailService;

    public OrderServiceImpl(OrderRepository orderRepository, BookServiceImpl bookService, CustomerServiceImpl customerService, CustomerRepository customerRepository, BookRepository bookRepository, InvoiceServiceImpl invoiceService, InvoiceRepository invoiceRepository, OrderItemRepository orderItemRepository, PdfCreator pdfCreator, EmailServiceImpl emailService) {
        this.orderRepository = orderRepository;
        this.bookService = bookService;
        this.customerService = customerService;
        this.customerRepository = customerRepository;
        this.bookRepository = bookRepository;
        this.invoiceService = invoiceService;
        this.invoiceRepository = invoiceRepository;
        this.orderItemRepository = orderItemRepository;
        this.pdfCreator = pdfCreator;
        this.emailService = emailService;
    }

    @Override
    public List<OrderGetDto> getOrders() {
        List<OrderModel> orderList = orderRepository.findAll();
        return orderList.stream().map(OrderConverter::fromModelToOrderGetDto).toList();
    }

    @Override
    public OrderGetDto getOrder(Long orderModelId) throws OrderNotFoundException {
        Optional<OrderModel> orderModelOptional = verifyOrderExistsById(orderModelId);
        return OrderConverter.fromModelToOrderGetDto(orderModelOptional.get());
    }

    public List<OrderGetByCustomerDto> getOrderByCostumer(Long customerId) throws CustomerNotFoundException, OrderNotFoundException {
        if (customerId <= 0) {
            throw new CustomerNotFoundException(CUSTOMER_WITH_ID + customerId + DOESNT_EXIST);
        }
        Optional<Customer> getCustomer = customerRepository.findById(customerId);
        if (getCustomer.isEmpty()) {
            throw new CustomerNotFoundException(CUSTOMER_WITH_ID + customerId + DOESNT_EXIST);
        }
        List<OrderModel> findedOrders = orderRepository.findOrderByCustomer(customerId);
        if (findedOrders.isEmpty()) {
            throw new OrderNotFoundException(NO_ORDER_WITH_CUSTOMER + customerId);
        }
        return orderRepository.findOrderByCustomer(customerId)
                .stream()
                .map(OrderConverter::fromModelToOderGetByCustomerDto)
                .toList();
    }

    public List<OrderGetByBookDto> getOrderByBook(Long bookId) throws BookNotFoundException, OrderNotFoundException {
        if (bookId <= 0) {
            throw new BookNotFoundException(BOOK_WITH_ID + bookId + DOESNT_EXIST);
        }
        Optional<Book> getBook = bookRepository.findById(bookId);
        if (getBook.isEmpty()) {
            throw new BookNotFoundException(BOOK_WITH_ID + bookId + DOESNT_EXIST);
        }
        List<OrderModel> findedOrders = orderRepository.getOrderModelsByBookId(bookId);
        if (findedOrders.isEmpty()) {
            throw new OrderNotFoundException(NO_ORDER_WITH_BOOK + bookId);
        }
        return orderRepository.getOrderModelsByBookId(bookId)
                .stream()
                .map(OrderConverter::fromModelToOderGetByBookDto)
                .toList();
    }

    private Optional<OrderModel> verifyOrderExistsById(Long orderModelId) throws OrderNotFoundException {
        Optional<OrderModel> orderModelOptional = orderRepository.findById(orderModelId);
        if (orderModelOptional.isEmpty()) {
            throw new OrderNotFoundException(ORDERMODEL_WITH_ID + orderModelId + DOESNT_EXIST);
        }
        return orderModelOptional;
    }

    @Override
    public OrderGetDto createOrder(OrderCreateDto order, Long orderId) throws CustomerNotFoundException,
            OrderAlreadyExistsException, DocumentException, FileNotFoundException, BookNotFoundException, PdfNotFoundException {
        Optional<OrderModel> orderModelFindById = orderRepository.findById(orderId);
        Customer customer = customerService.findById(order.customerId());
        List<Book> bookList = bookService.getBooksByIds(order.books());
        if (orderModelFindById.isPresent()) {
            throw new OrderAlreadyExistsException(ORDERMODEL_WITH_ID + orderId + ALREADY_EXISTS);
        }
        OrderModel newOrder = OrderConverter.fromCreateDtoToModel(order, customer, bookList);

        double totalAmount = orderModelFindById.get().getOrderItems().getAmountToPay();

        Invoice invoice = new Invoice(
                customer,
                newOrder,
                LocalDate.now(),
                totalAmount);

        invoiceRepository.save(invoice);

        String path = "PdfPath" + newOrder.getId() + ".pdf";
        newOrder.setInvoicePath(path);

        try {
            pdfCreator.createPdf(newOrder, invoice);
        } catch (PdfNotFoundException e) {
            throw new PdfNotFoundException(PDF_NOT_FOUND);
        }

        String downloadLink = "https://ebookStore.com/download?invoiceId=" + invoice.getId();

        emailService.sendEmailWithAttachment(customer.getEmail(), path, "invoice_" + newOrder.getId(), customer, downloadLink);
        orderRepository.save(newOrder);
        return OrderConverter.fromModelToOrderGetDto(newOrder);
    }


    @Override
    public void deleteOrder(Long orderId) throws OrderNotFoundException {
        Optional<OrderModel> orderOptional = verifyOrderExistsById(orderId);
        orderRepository.delete(orderOptional.get());
    }

    public OrderModel findById(Long orderModelId) throws OrderNotFoundException {
        Optional<OrderModel> orderOptional = orderRepository.findById(orderModelId);
        if (orderOptional.isEmpty()) {
            throw new OrderNotFoundException(ORDERMODEL_WITH_ID + orderModelId + DOESNT_EXIST);
        }
        return orderOptional.get();
    }


}
