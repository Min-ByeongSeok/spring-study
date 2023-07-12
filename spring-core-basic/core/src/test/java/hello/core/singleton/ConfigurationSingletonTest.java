package hello.core.singleton;

import hello.core.Order.OrderServiceImpl;
import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSingletonTest {
    @Test
    @DisplayName("싱글톤 객체 테스트")
    void configurationTest() {
        // given
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        // when
        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);
        // then
        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        System.out.println("memberRepository1 = " + memberRepository1);
        System.out.println("memberRepository2 = " + memberRepository2);
        System.out.println("memberRepository = " + memberRepository);

        Assertions.assertThat(memberService.getMemberRepository()).isSameAs(memberRepository2);
        Assertions.assertThat(orderService.getMemberRepository()).isSameAs(memberRepository1);
    }

    @Test
    void configurationDeep() {
        // given
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        // when
        AppConfig bean = ac.getBean(AppConfig.class);
        // then
        System.out.println("bean = " + bean.getClass());
    }
}
