package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import lombok.Getter;

import java.util.Map;

@Getter
public class Payment {
    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;
    private Order order;

    public Payment(String id, String method, Order order, Map<String, String> paymentData) {
        if (!PaymentMethod.contains(method)) {
            throw new IllegalArgumentException("Invalid payment method: " + method);
        }
        this.id = id;
        this.method = method;
        this.order = order;
        this.paymentData = paymentData;
        this.status = computeStatus(method, paymentData);
    }

    private String computeStatus(String method, Map<String, String> data) {
        if (PaymentMethod.VOUCHER_CODE.getValue().equals(method)) {
            return validateVoucher(data.get("voucherCode"))
                    ? PaymentStatus.SUCCESS.getValue()
                    : PaymentStatus.REJECTED.getValue();
        } else if (PaymentMethod.CASH_ON_DELIVERY.getValue().equals(method)) {
            return validateCOD(data)
                    ? PaymentStatus.SUCCESS.getValue()
                    : PaymentStatus.REJECTED.getValue();
        }
        return PaymentStatus.REJECTED.getValue();
    }

    private boolean validateVoucher(String code) {
        if (code == null || code.length() != 16) return false;
        if (!code.startsWith("ESHOP")) return false;
        long numCount = code.chars().filter(Character::isDigit).count();
        return numCount == 8;
    }

    private boolean validateCOD(Map<String, String> data) {
        String address = data.get("address");
        String fee = data.get("deliveryFee");
        return address != null && !address.isEmpty()
                && fee != null && !fee.isEmpty();
    }

    public void setStatus(String status) {
        if (!PaymentStatus.contains(status)) {
            throw new IllegalArgumentException("Invalid payment status: " + status);
        }
        this.status = status;
    }
}
