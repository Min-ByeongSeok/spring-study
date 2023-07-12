package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
    public static void main(String[] args) {
//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();

        // 스프링 컨테이너 (Bean 객체 관리) 생성
        ApplicationContext applicationContext
                // 명시된 클래스(AppConfig.class)의 환경설정정보를 가지고 빈들을 스프링 컨테이너에 넣는다.
                = new AnnotationConfigApplicationContext(AppConfig.class);

        // 빈에 "memberService"로 설정된 것을 찾아 명시된 클래스(MemberService.class)를 넘겨준다
        MemberService memberService
                = applicationContext.getBean("memberService", MemberService.class);

        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("member = " + member.getName());
        System.out.println("findMember = " + findMember.getName());
    }
}
