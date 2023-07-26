package zerobase.convpay.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import zerobase.convpay.dataTransferObject.PayCancelRequest;
import zerobase.convpay.dataTransferObject.PayCancelResponse;
import zerobase.convpay.type.ConvenienceType;
import zerobase.convpay.dataTransferObject.PayRequest;
import zerobase.convpay.dataTransferObject.PayResponse;
import zerobase.convpay.type.PayCancelResult;
import zerobase.convpay.type.PayMethodType;
import zerobase.convpay.type.PayResult;

import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ConveniencePayServiceTest {

    ConveniencePayService conveniencePayService = new ConveniencePayService(
            new HashSet<>(
                    Arrays.asList(new MoneyAdapter(), new CardAdapter())),
            new DiscountByPayMethod()
    );

    @Test
    @DisplayName("결제 성공")
    void pay_success() {
        // given
        PayRequest payRequest = new PayRequest(ConvenienceType.G25, 500, PayMethodType.MONEY);
        // when
        PayResponse payResponse = conveniencePayService.pay(payRequest);
        // then
        assertThat(PayResult.SUCCESS).isEqualTo(payResponse.getPayResult());
        assertEquals(350, payResponse.getPaidAmount());
    }

    @Test
    @DisplayName("결제 실패")
    void pay_fail() {
        // given
        PayRequest payRequest = new PayRequest(ConvenienceType.G25, 3000_000, PayMethodType.MONEY);
        // when
        PayResponse payResponse = conveniencePayService.pay(payRequest);
        // then
        assertThat(PayResult.FAIL).isEqualTo(payResponse.getPayResult());
        assertEquals(0, payResponse.getPaidAmount());
    }

    @Test
    @DisplayName("결제 취소 성공")
    void pay_cancel_success() {
        // given
        PayCancelRequest payCancelRequest = new PayCancelRequest(ConvenienceType.G25, 1000, PayMethodType.MONEY);
        // when
        PayCancelResponse payCancelResponse = conveniencePayService.payCancel(payCancelRequest);
        // then
        assertEquals(payCancelResponse.getPayCancelResult(), PayCancelResult.PAY_CANCEL_SUCCESS);
        assertEquals(payCancelResponse.getPayCanceledAmount(), 1000);
    }

    @Test
    @DisplayName("결제 취소 실패")
    void pay_cancel_fail() {
        // given
        PayCancelRequest payCancelRequest = new PayCancelRequest(ConvenienceType.G25, 50, PayMethodType.MONEY);
        // when
        PayCancelResponse payCancelResponse = conveniencePayService.payCancel(payCancelRequest);
        // then
        assertEquals(payCancelResponse.getPayCancelResult(), PayCancelResult.PAY_CANCEL_FAIL);
        assertEquals(payCancelResponse.getPayCanceledAmount(), 0);
    }

}