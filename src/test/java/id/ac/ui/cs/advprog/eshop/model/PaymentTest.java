package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    private List<Product> products;
    private Order order;
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products, 1708560000L, "Safira Sudrajat");

        paymentData = new HashMap<>();
    }

    @Test
    void testCreatePaymentVoucherValid() {
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("pay-001", PaymentMethod.VOUCHER_CODE.getValue(),
                order, paymentData);

        assertEquals("pay-001", payment.getId());
        assertEquals(PaymentMethod.VOUCHER_CODE.getValue(), payment.getMethod());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
        assertEquals(paymentData, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentVoucherInvalidLength() {
        paymentData.put("voucherCode", "ESHOP123");
        Payment payment = new Payment("pay-002", PaymentMethod.VOUCHER_CODE.getValue(),
                order, paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherNotStartWithESHOP() {
        paymentData.put("voucherCode", "ABCDE1234ABC5678");
        Payment payment = new Payment("pay-003", PaymentMethod.VOUCHER_CODE.getValue(),
                order, paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherInsufficientNumericalChars() {
        paymentData.put("voucherCode", "ESHOP1234ABCDEFG");
        Payment payment = new Payment("pay-004", PaymentMethod.VOUCHER_CODE.getValue(),
                order, paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentCODValid() {
        paymentData.put("address", "Jl. Margonda Raya No. 100");
        paymentData.put("deliveryFee", "10000");
        Payment payment = new Payment("pay-005", PaymentMethod.CASH_ON_DELIVERY.getValue(),
                order, paymentData);
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentCODEmptyAddress() {
        paymentData.put("address", "");
        paymentData.put("deliveryFee", "10000");
        Payment payment = new Payment("pay-006", PaymentMethod.CASH_ON_DELIVERY.getValue(),
                order, paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentCODNullDeliveryFee() {
        paymentData.put("address", "Jl. Margonda Raya No. 100");
        paymentData.put("deliveryFee", null);
        Payment payment = new Payment("pay-007", PaymentMethod.CASH_ON_DELIVERY.getValue(),
                order, paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testSetStatusValid() {
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("pay-008", PaymentMethod.VOUCHER_CODE.getValue(),
                order, paymentData);
        payment.setStatus(PaymentStatus.REJECTED.getValue());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testSetStatusInvalid() {
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("pay-009", PaymentMethod.VOUCHER_CODE.getValue(),
                order, paymentData);
        assertThrows(IllegalArgumentException.class, () -> payment.setStatus("MEOW"));
    }

    @Test
    void testCreatePaymentInvalidMethod() {
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        assertThrows(IllegalArgumentException.class, () ->
                new Payment("pay-010", "MEOW", order, paymentData));
    }
}
