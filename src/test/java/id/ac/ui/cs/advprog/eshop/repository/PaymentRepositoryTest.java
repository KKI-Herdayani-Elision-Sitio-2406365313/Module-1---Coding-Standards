package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {
    PaymentRepository paymentRepository;
    List<Payment> payments;
    Order order;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();

        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products, 1708560000L, "Safira Sudrajat");

        payments = new ArrayList<>();
        Map<String, String> voucherData = new HashMap<>();
        voucherData.put("voucherCode", "ESHOP1234ABC5678");
        payments.add(new Payment("pay-001", PaymentMethod.VOUCHER_CODE.getValue(), order, voucherData));

        Map<String, String> codData = new HashMap<>();
        codData.put("address", "Jl. Margonda Raya No. 100");
        codData.put("deliveryFee", "10000");
        payments.add(new Payment("pay-002", PaymentMethod.CASH_ON_DELIVERY.getValue(), order, codData));
    }

    @Test
    void testSaveCreate() {
        Payment payment = payments.get(0);
        Payment result = paymentRepository.save(payment);
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment, paymentRepository.findById(payment.getId()));
    }

    @Test
    void testSaveUpdate() {
        Payment payment = payments.get(0);
        paymentRepository.save(payment);
        payment.setStatus("REJECTED");
        Payment result = paymentRepository.save(payment);
        assertEquals("REJECTED", result.getStatus());
        assertEquals(1, paymentRepository.getAllPayments().size());
    }

    @Test
    void testFindByIdFound() {
        for (Payment p : payments) paymentRepository.save(p);
        Payment result = paymentRepository.findById("pay-001");
        assertEquals("pay-001", result.getId());
    }

    @Test
    void testFindByIdNotFound() {
        assertNull(paymentRepository.findById("nonexistent"));
    }

    @Test
    void testGetAllPayments() {
        for (Payment p : payments) paymentRepository.save(p);
        List<Payment> result = paymentRepository.getAllPayments();
        assertEquals(2, result.size());
    }
}
