package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    Order order;
    Map<String, String> voucherData;
    Map<String, String> codData;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products, 1708560000L, "Safira Sudrajat");

        voucherData = new HashMap<>();
        voucherData.put("voucherCode", "ESHOP1234ABC5678");

        codData = new HashMap<>();
        codData.put("address", "Jl. Margonda Raya No. 100");
        codData.put("deliveryFee", "10000");
    }

    @Test
    void testAddPaymentVoucherSuccess() {
        Payment payment = new Payment("pay-001",
                PaymentMethod.VOUCHER_CODE.getValue(), order, voucherData);
        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order,
                PaymentMethod.VOUCHER_CODE.getValue(), voucherData);

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
    }

    @Test
    void testAddPaymentCODSuccess() {
        Payment payment = new Payment("pay-002",
                PaymentMethod.CASH_ON_DELIVERY.getValue(), order, codData);
        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order,
                PaymentMethod.CASH_ON_DELIVERY.getValue(), codData);

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
    }

    @Test
    void testSetStatusSuccessUpdatesOrder() {
        Payment payment = new Payment("pay-001",
                PaymentMethod.VOUCHER_CODE.getValue(), order, voucherData);
        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());

        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), result.getOrder().getStatus());
    }

    @Test
    void testSetStatusRejectedUpdatesOrderFailed() {
        Payment payment = new Payment("pay-001",
                PaymentMethod.VOUCHER_CODE.getValue(), order, voucherData);
        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.setStatus(payment, PaymentStatus.REJECTED.getValue());

        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), result.getOrder().getStatus());
    }

    @Test
    void testGetPaymentFound() {
        Payment payment = new Payment("pay-001",
                PaymentMethod.VOUCHER_CODE.getValue(), order, voucherData);
        doReturn(payment).when(paymentRepository).findById("pay-001");

        Payment result = paymentService.getPayment("pay-001");
        assertEquals("pay-001", result.getId());
    }

    @Test
    void testGetPaymentNotFound() {
        doReturn(null).when(paymentRepository).findById("nonexistent");
        assertNull(paymentService.getPayment("nonexistent"));
    }

    @Test
    void testGetAllPayments() {
        Payment p1 = new Payment("pay-001",
                PaymentMethod.VOUCHER_CODE.getValue(), order, voucherData);
        Payment p2 = new Payment("pay-002",
                PaymentMethod.CASH_ON_DELIVERY.getValue(), order, codData);
        doReturn(List.of(p1, p2)).when(paymentRepository).getAllPayments();

        List<Payment> results = paymentService.getAllPayments();
        assertEquals(2, results.size());
    }
}
