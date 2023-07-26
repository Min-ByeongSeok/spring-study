package zerobase.convpay.service;

import zerobase.convpay.dataTransferObject.PayRequest;

public interface DiscountInterface {
    /**
     * 할인된 금액을 받음
     */
    Integer getDiscountedAmount(PayRequest payRequest);

}
