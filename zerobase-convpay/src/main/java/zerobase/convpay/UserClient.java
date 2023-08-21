package zerobase.convpay;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import zerobase.convpay.config.ApplicationConfig;
import zerobase.convpay.dataTransferObject.PayCancelRequest;
import zerobase.convpay.dataTransferObject.PayCancelResponse;
import zerobase.convpay.dataTransferObject.PayRequest;
import zerobase.convpay.dataTransferObject.PayResponse;
import zerobase.convpay.service.ConveniencePayService;
import zerobase.convpay.type.ConvenienceType;
import zerobase.convpay.type.PayMethodType;

public class UserClient {
    public static void main(String[] args) {
        // 사용자 - 편의점 결제 시스템 - 머니
        ApplicationContext applicationContext
                = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        ConveniencePayService conveniencePayService
                = applicationContext.getBean("conveniencePayService", ConveniencePayService.class);

        // 결제 1000원
        PayRequest payRequest = new PayRequest(ConvenienceType.G25, 50, PayMethodType.CARD);
        PayResponse payResponse = conveniencePayService.pay(payRequest);

        System.out.println(payResponse);

        // 취소 500원
        PayCancelRequest payCancelRequest = new PayCancelRequest(ConvenienceType.G25, 500, PayMethodType.MONEY);
        PayCancelResponse payCancelResponse = conveniencePayService.payCancel(payCancelRequest);

        System.out.println(payCancelResponse);
    }
}
