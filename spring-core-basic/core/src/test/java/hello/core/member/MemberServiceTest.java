package hello.core.member;

import hello.core.AppConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// 자바테스트에서 유창하고 풍부한 assertions를 작성하는데 사용되는 오픈소스 라이브러리
import static org.assertj.core.api.Assertions.assertThat;
// JUnit : 자바에 구축된 자동화 테스트가 가능한 프레임워크
import static org.junit.jupiter.api.Assertions.*;


class MemberServiceTest {

    MemberService memberService;

    @BeforeEach
    public void beforeEach(){
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
    }

    @Test
    void join() {
        //given
        Member member = new Member(1L, "memberA", Grade.VIP);

        //when
        memberService.join(member);
        Member findMember = memberService.findMember(1L);

        //then
        assertThat(member).isEqualTo(findMember);
    }
}