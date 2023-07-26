package zerobase.convpay.service;

import zerobase.convpay.type.*;

public class MoneyAdapter implements PaymentInterface{
    // 결제 - 머니 사용
    public MoneyUseResult use(Integer payAmount){
        System.out.println("MoneyAdapter.use : " + payAmount);

        // 너무 큰 금액을 사용했을 때 사용 실패
        if (payAmount > 1000_000) {
            return MoneyUseResult.USE_FAIL;
        }

        return MoneyUseResult.USE_SUCCESS;
    }

    // 결제 취소 - 머니 사용 취소
    public MoneyUseCancelResult useCancel(Integer payCancelAmount){
        System.out.println("MoneyAdapter.useCancel : " + payCancelAmount);

        // 취소 금액이 너무 작다면 결제취소를 취소
        if (payCancelAmount < 100) {
            return MoneyUseCancelResult.MONEY_USE_CANCEL_FAIL;
        }

        return MoneyUseCancelResult.MONEY_USE_CANCEL_SUCCESS;
    }

    @Override
    public PayMethodType getPayMethodType() {
        return PayMethodType.MONEY;
    }

    @Override
    public PaymentResult payment(Integer payAmount) {
        MoneyUseResult moneyUseResult = use(payAmount);

        if (moneyUseResult == MoneyUseResult.USE_FAIL) {
            return PaymentResult.PAYMENT_FAIL;
        }

        return PaymentResult.PAYMENT_SUCCESS;
    }

    @Override
    public CancelPaymentResult cancelPayment(Integer cancelAmount) {
        MoneyUseCancelResult moneyUseCancelResult = useCancel(cancelAmount);

        if (moneyUseCancelResult == MoneyUseCancelResult.MONEY_USE_CANCEL_FAIL) {
            return CancelPaymentResult.CANCEL_PAYMENT_FAIL;
        }

        return CancelPaymentResult.CANCEL_PAYMENT_SUCCESS;
    }
}
