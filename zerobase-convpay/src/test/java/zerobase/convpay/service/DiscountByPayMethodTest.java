package zerobase.convpay.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import zerobase.convpay.dataTransferObject.PayRequest;
import zerobase.convpay.type.ConvenienceType;
import zerobase.convpay.type.PayMethodType;

import static org.junit.jupiter.api.Assertions.*;

class DiscountByPayMethodTest {
    DiscountByPayMethod discountByPayMethod = new DiscountByPayMethod();

    @Test
    @DisplayName("결제수단에 따른 할인금액 확인")
    void discountbyMethod() {
        // given
        PayRequest payRequestMoney = new PayRequest(ConvenienceType.G25, 1000, PayMethodType.MONEY);
        PayRequest payRequestCard = new PayRequest(ConvenienceType.G25, 1000, PayMethodType.CARD);
        // when
        Integer payAmountCard = discountByPayMethod.getDiscountedAmount(payRequestCard);
        Integer payAmountMoney = discountByPayMethod.getDiscountedAmount(payRequestMoney);
        // then
        assertEquals(700, payAmountMoney);
        assertEquals(1000, payAmountCard);
    }
}