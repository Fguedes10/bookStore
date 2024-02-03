package mindera.backendProject.bookStore.service.orderService;

import mindera.backendProject.bookStore.converter.order.PaymentConverter;
import mindera.backendProject.bookStore.dto.order.PaymentCreateDto;
import mindera.backendProject.bookStore.dto.order.PaymentGetDto;
import mindera.backendProject.bookStore.exception.order.OrderItemAlreadyExistsException;
import mindera.backendProject.bookStore.exception.order.PaymentNotFoundException;
import mindera.backendProject.bookStore.model.OrderItem;
import mindera.backendProject.bookStore.model.Payment;
import mindera.backendProject.bookStore.repository.orderRepository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static mindera.backendProject.bookStore.util.Messages.DOESNT_EXIST;
import static mindera.backendProject.bookStore.util.Messages.PAYMENT_WITH_ID;

@Service
public class PaymentServiceImpl implements PaymentService {


    private final PaymentRepository paymentRepository;
    private final OrderItemServiceImpl orderItemService;

    public PaymentServiceImpl(PaymentRepository paymentRepository, OrderItemServiceImpl orderItemService) {
        this.paymentRepository = paymentRepository;
        this.orderItemService = orderItemService;
    }

    @Override
    public List<PaymentGetDto> getAllPayments() {
        List<Payment> paymentList = paymentRepository.findAll();
        return paymentList.stream().map(PaymentConverter::fromModelToPaymentGetDto).toList();
    }

    @Override
    public PaymentGetDto getPayment(Long paymentId) throws PaymentNotFoundException {
        Optional<Payment> paymentOptional = verifyPaymentExistsById(paymentId);
        return PaymentConverter.fromModelToPaymentGetDto(paymentOptional.get());
    }

    private Optional<Payment> verifyPaymentExistsById(Long paymentId) throws PaymentNotFoundException {
        Optional<Payment> paymentOptional = paymentRepository.findById(paymentId);
        if (paymentOptional.isEmpty()) {
            throw new PaymentNotFoundException(PAYMENT_WITH_ID + paymentId + DOESNT_EXIST);
        }
        return paymentOptional;
    }

    @Override
    public PaymentGetDto createPayment(PaymentCreateDto payment, Long paymentId) throws OrderItemAlreadyExistsException {
        Optional<Payment> paymentFindById = paymentRepository.findById(paymentId);
        OrderItem orderItem = orderItemService.findById(payment.orderItemId());
        Payment paymentToSave = PaymentConverter.fromPaymentCreateDtoToModel(payment, orderItem);
        paymentRepository.save(paymentToSave);
        return PaymentConverter.fromModelToPaymentGetDto(paymentToSave);
    }


    @Override
    public Payment deletePayment(Long paymentId) throws PaymentNotFoundException {
        Optional<Payment> paymentOptional = verifyPaymentExistsById(paymentId);
        if (paymentOptional.isEmpty()) {
            throw new PaymentNotFoundException(PAYMENT_WITH_ID + paymentId + DOESNT_EXIST);
        }
        return paymentOptional.get();

    }
}
