package zerobase.convpay.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import zerobase.convpay.type.CardUseCancelResult;
import zerobase.convpay.type.CardUseResult;

import static org.junit.jupiter.api.Assertions.*;

class CardAdapterTest {

    private CardAdapter cardAdapter = new CardAdapter();

    @Test
    @DisplayName("카드 사용 결제 성공")
    void capture_success() {
        // given
        Integer payAmount = 99;
        // when
        CardUseResult cardUseResult = cardAdapter.capture(payAmount);
        // then
        assertEquals(cardUseResult, CardUseResult.USE_SUCCESS);
    }

    @Test
    @DisplayName("카드 사용 결제 실패")
    void capture_fail() {
        // given
        Integer payAmount = 101;
        // when
        CardUseResult cardUseResult = cardAdapter.capture(payAmount);
        // then
        assertEquals(cardUseResult, CardUseResult.USE_FAIL);
    }

    @Test
    @DisplayName("카드 사용 결제 취소 성공")
    void cancelCapture_success() {
        // given
        Integer cancelAmount = 999;
        // when
        CardUseCancelResult cardUseCancelResult = cardAdapter.cancelCapture(cancelAmount);
        // then
        assertEquals(cardUseCancelResult, CardUseCancelResult.USE_CANCEL_FAIL);
    }

    @Test
    @DisplayName("카드 사용 결제 취소 실패")
    void cancelCapture_fail() {
        // given
        Integer cancelAmount = 1001;
        // when
        CardUseCancelResult cardUseCancelResult = cardAdapter.cancelCapture(cancelAmount);
        // then
        assertEquals(cardUseCancelResult, CardUseCancelResult.USE_CANCEL_SUCCESS);
    }
}