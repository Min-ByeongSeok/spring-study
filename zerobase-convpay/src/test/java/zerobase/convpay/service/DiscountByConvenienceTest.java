package zerobase.convpay.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import zerobase.convpay.dataTransferObject.PayRequest;
import zerobase.convpay.type.ConvenienceType;
import zerobase.convpay.type.PayMethodType;

import static org.junit.jupiter.api.Assertions.*;

class DiscountByConvenienceTest {

    DiscountByConvenience discountByConvenience = new DiscountByConvenience();

    @Test
    @DisplayName("할인금액 테스트")
    void discountTest() {
        // given
        PayRequest payRequestG25 = new PayRequest(ConvenienceType.G25, 1000, PayMethodType.MONEY);
        PayRequest payRequestGU = new PayRequest(ConvenienceType.GU, 1000, PayMethodType.MONEY);
        PayRequest payRequestSEVEN = new PayRequest(ConvenienceType.SEVEN, 1000, PayMethodType.MONEY);
        // when
        Integer discountedAmountG25 = discountByConvenience.getDiscountedAmount(payRequestG25);
        Integer discountedAmountGU = discountByConvenience.getDiscountedAmount(payRequestGU);
        Integer discountedAmountSEVEN = discountByConvenience.getDiscountedAmount(payRequestSEVEN);
        // then
        assertEquals(800, discountedAmountG25);
        assertEquals(900, discountedAmountGU);
        assertEquals(1000, discountedAmountSEVEN);
    }

}