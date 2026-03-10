package id.ac.ui.cs.advprog.eshop.enums;

import lombok.Getter;

public enum PaymentStatus {
    SUCCESS("SUCCESS"),
    REJECTED("REJECTED"),
    PENDING("PENDING");

    @Getter
    private final String value;

    PaymentStatus(String value) {
        this.value = value;
    }

    public static boolean contains(String param) {
        for (PaymentStatus status : PaymentStatus.values()) {
            if (status.getValue().equals(param)) return true;
        }
        return false;
    }
}