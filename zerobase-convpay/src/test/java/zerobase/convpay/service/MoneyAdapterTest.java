package zerobase.convpay.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import zerobase.convpay.type.MoneyUseCancelResult;
import zerobase.convpay.type.MoneyUseResult;

import static org.junit.jupiter.api.Assertions.*;

class MoneyAdapterTest {

    MoneyAdapter moneyAdapter = new MoneyAdapter();

    @Test
    @DisplayName("사용할 수 있는 금액이 초과하였습니다.")
    void money_use_fail() {
        // given
        Integer payAmount = 1000_001;
        // when
        MoneyUseResult use = moneyAdapter.use(payAmount);
        // then
        assertEquals(use, MoneyUseResult.USE_FAIL);
    }

    @Test
    @DisplayName("결제금액이 사용할 수 있는 한도내의 금액이어서 결제가 성공했습니다.")
    void money_use_success() {
        // given
        Integer payAmount = 1000_000;
        // when
        MoneyUseResult use = moneyAdapter.use(payAmount);
        // then
        assertEquals(use, MoneyUseResult.USE_SUCCESS);
    }

    @Test
    @DisplayName("결제 취소 성공")
    void money_use_cancel_success() {
        // given
        Integer payCancelAmount = 101;
        // when
        MoneyUseCancelResult moneyUseCancelResult = moneyAdapter.useCancel(payCancelAmount);
        // then
        assertEquals(MoneyUseCancelResult.MONEY_USE_CANCEL_SUCCESS, moneyUseCancelResult);
    }

    @Test
    @DisplayName("결제 취소 취소")
    void money_use_cancel_fail() {
        // given
        Integer payCancelAmount = 99;
        // when
        MoneyUseCancelResult moneyUseCancelResult = moneyAdapter.useCancel(payCancelAmount);
        // then
        assertEquals(MoneyUseCancelResult.MONEY_USE_CANCEL_FAIL, moneyUseCancelResult);
    }
}