package mindera.backendProject.bookStore.service.orderService;

import mindera.backendProject.bookStore.converter.order.PaymentConverter;
import mindera.backendProject.bookStore.dto.order.PaymentGetDto;
import mindera.backendProject.bookStore.exception.order.PaymentNotFoundException;
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
    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public List<PaymentGetDto> getAllPayments() {
        List<Payment> paymentList = paymentRepository.findAll();
        return paymentList.stream().map(PaymentConverter::fromModelToPAymentGetDto).toList();
    }

    @Override
    public PaymentGetDto getPayment(Long paymentId) throws PaymentNotFoundException {
        Optional<Payment> paymentOptional = verifyPaymentExistsById(paymentId);
        return PaymentConverter.fromModelToPAymentGetDto(paymentOptional.get());

    }

    private Optional<Payment> verifyPaymentExistsById(Long paymentId) throws PaymentNotFoundException {
        Optional<Payment> paymentOptional = paymentRepository.findById(paymentId);
        if(paymentOptional.isEmpty()){
            throw new PaymentNotFoundException(PAYMENT_WITH_ID + paymentId + DOESNT_EXIST);
        }
        return paymentOptional;
    }

    @Override
    public PaymentGetDto createPayment(Payment payment) {
        verifyPaymentExists(payment);

    }

    private void verifyPaymentExists(Payment payment) {
    }

    @Override
    public List<Payment> createPayments(List<Payment> payment) {
        return null;
    }

    @Override
    public void deletePayment(Long paymentId) {

    }
}
