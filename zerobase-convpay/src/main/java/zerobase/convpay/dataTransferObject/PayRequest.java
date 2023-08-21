package zerobase.convpay.dataTransferObject;

import zerobase.convpay.type.ConvenienceType;
import zerobase.convpay.type.PayMethodType;

public class PayRequest {
    // 편의점 종류
    ConvenienceType convenienceType;
    // 결제 금액
    Integer payAmount;
    // 결제 수단
    PayMethodType payMethodType;

    public PayRequest(ConvenienceType convenienceType, Integer payAmount, PayMethodType payMethodType) {
        this.convenienceType = convenienceType;
        this.payAmount = payAmount;
        this.payMethodType = payMethodType;
    }

    public ConvenienceType getConvenienceType() {
        return convenienceType;
    }

    public void setConvenienceType(ConvenienceType convenienceType) {
        this.convenienceType = convenienceType;
    }

    public Integer getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Integer payAmount) {
        this.payAmount = payAmount;
    }

    public PayMethodType getPayMethodType() {
        return payMethodType;
    }

    public void setPayMethodType(PayMethodType payMethodType) {
        this.payMethodType = payMethodType;
    }
}
