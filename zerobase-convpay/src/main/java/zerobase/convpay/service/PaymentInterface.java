package zerobase.convpay.service;

import zerobase.convpay.type.CancelPaymentResult;
import zerobase.convpay.type.PayMethodType;
import zerobase.convpay.type.PaymentResult;

public interface PaymentInterface {
    PayMethodType getPayMethodType();
    PaymentResult payment(Integer payAmount);

    CancelPaymentResult cancelPayment(Integer cancelAmount);
}
