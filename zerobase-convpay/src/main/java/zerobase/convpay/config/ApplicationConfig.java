package zerobase.convpay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import zerobase.convpay.service.*;

import java.util.Arrays;
import java.util.HashSet;

@Configuration
@ComponentScan(basePackages = "zerobase.convpay")
public class ApplicationConfig {
//    @Bean
//    public ConveniencePayService conveniencePayService(){
//        return new ConveniencePayService(
//                new HashSet<>(
//                        Arrays.asList(moneyAdapter(), cardAdapter())),
//                discountByConvenience());
//    }
//
//    @Bean
//    public CardAdapter cardAdapter() {
//        return new CardAdapter();
//    }
//
//    @Bean
//    public MoneyAdapter moneyAdapter() {
//        return new MoneyAdapter();
//    }
//
//    @Bean
//    public DiscountByConvenience discountByConvenience() {
//        return new DiscountByConvenience();
//    }

}
