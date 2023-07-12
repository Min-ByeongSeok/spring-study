package hello.core.scan;

import hello.core.config.AutoAppConfig;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AutoAppConfigTest {
    
    @Test
    @DisplayName("컴포넌트와 컴포넌트 스캔 동작확인")
    void basicScan() {
        // given
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
        // when
        MemberService memberService = ac.getBean(MemberService.class);
        // then
        Assertions.assertThat(memberService).isInstanceOf(MemberService.class);
    }
}
