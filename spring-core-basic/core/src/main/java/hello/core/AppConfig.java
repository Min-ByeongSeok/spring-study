package hello.core;

import hello.core.Order.OrderService;
import hello.core.Order.OrderServiceImpl;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // 설정정보
public class AppConfig {

    @Bean // @Bean이 적힌 메서드를 모두 호출해서 반환된 객체를 스프링컨테이너에 등록
    public MemberService memberService() {
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    // @Configuration과 @Bean의 조합으로 싱글톤을 보장하는 경우는 정적이지 않은 메서드일 때입니다.
    // 정적 메서드에 @Bean을 사용하게 되면 싱글톤 보장을 위한 지원을 받지 못합니다.
    public MemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    // Bean만 사용해도 스프링빈으로 등록되지만 싱글톤은 보장하지 않는다.
    public OrderService orderService() {
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        System.out.println("call AppConfig.discountPolicy");
        return new RateDiscountPolicy();
    }

//    @Bean
//    public static DiscountPolicy getRateDiscountPolicy(){
//        return new RateDiscountPolicy();
//    }
}
