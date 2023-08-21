package zerobase.convpay.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import zerobase.convpay.dataTransferObject.PayRequest;

@Primary
@Component
public class DiscountByPayMethod implements DiscountInterface {
    @Override
    public Integer getDiscountedAmount(PayRequest payRequest) {
        switch (payRequest.getPayMethodType()) {

            case MONEY:
                return payRequest.getPayAmount() * 7 / 10;
            case CARD:
                return payRequest.getPayAmount();
        }

        return payRequest.getPayAmount();
    }
}
