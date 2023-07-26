package zerobase.convpay.service;

import org.springframework.stereotype.Component;
import zerobase.convpay.dataTransferObject.*;
import zerobase.convpay.type.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
// 핵심 서비스
public class ConveniencePayService {

//    private final MoneyAdapter moneyAdapter = new MoneyAdapter();
//    private final CardAdapter cardAdapter = new CardAdapter();
//    private final DiscountInterface discountInterface = new DiscountByPayMethod();

    private final Map<PayMethodType, PaymentInterface> paymentInterfaceMap = new HashMap<>();
    private final DiscountInterface discountInterface;

    public ConveniencePayService(Set<PaymentInterface> paymentInterfaceSet, DiscountInterface discountInterface) {
        paymentInterfaceSet.forEach(
                paymentInterface -> paymentInterfaceMap.put(paymentInterface.getPayMethodType(), paymentInterface));

        this.discountInterface = discountInterface;

    }

    // 결제
    public PayResponse pay(PayRequest payRequest) {
        PaymentInterface paymentInterface = paymentInterfaceMap.get(payRequest.getPayMethodType());

        Integer discountedAmount = discountInterface.getDiscountedAmount(payRequest);
        PaymentResult paymentResult = paymentInterface.payment(discountedAmount);
        // 성공과 실패를 if-else로 구현하는 것은 권장하지 않는다.
        // fail fast 권장 -> 예외 케이스들을 모든 예외케이스를 다 거친 후 단 하나의 성공케이스를 마지막에 처리한다.
        // exception case 1
        if (paymentResult == PaymentResult.PAYMENT_FAIL) {
            return new PayResponse(PayResult.FAIL, 0);
        }
        // exception case 2
        // exception case 3
        // exception case 4
        // ...

        // success case
        return new PayResponse(PayResult.SUCCESS, discountedAmount);

    }

    // 결제 취소
    public PayCancelResponse payCancel(PayCancelRequest payCancelRequest) {
        PaymentInterface paymentInterface = paymentInterfaceMap.get(payCancelRequest.getPayMethodType());

        CancelPaymentResult cancelPaymentResult = paymentInterface.cancelPayment(payCancelRequest.getPayCancelAmount());

        if (cancelPaymentResult == CancelPaymentResult.CANCEL_PAYMENT_FAIL) {
            return new PayCancelResponse(PayCancelResult.PAY_CANCEL_FAIL, 0);
        }

        return new PayCancelResponse(PayCancelResult.PAY_CANCEL_SUCCESS, payCancelRequest.getPayCancelAmount());
    }
}
