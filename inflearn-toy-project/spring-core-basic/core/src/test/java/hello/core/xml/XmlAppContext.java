package hello.core.xml;

import hello.core.Order.OrderServiceImpl;
import hello.core.member.Member;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class XmlAppContext {

    @Test
    @DisplayName("XML 파일로 설정정보 구성하고 조회하기")
    void xmlAppContext() {
        // given
        ApplicationContext ac = new GenericXmlApplicationContext("appConfig.xml");
        // when
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        // then
        Assertions.assertThat(memberService).isInstanceOf(MemberService.class);
        Assertions.assertThat(orderService).isInstanceOf(OrderServiceImpl.class);
    }
}
