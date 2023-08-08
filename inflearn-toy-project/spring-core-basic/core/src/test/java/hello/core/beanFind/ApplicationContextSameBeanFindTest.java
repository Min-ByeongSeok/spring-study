package hello.core.beanFind;

import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ApplicationContextSameBeanFindTest {
    AnnotationConfigApplicationContext ac
            = new AnnotationConfigApplicationContext(SameBeanConfigTest.class);

    @Test
    @DisplayName("타입으로 조회시 같은 타입이 둘 있으면 중복오류가 발생한다.")
    void findByTypeDuplicate(){
        Assertions.assertThrows(NoUniqueBeanDefinitionException.class,
                () -> ac.getBean(MemberRepository.class));
    }

    @Test
    @DisplayName("타입으로 조회시 같은 타입이 둘 이상 있으면 빈이름을 지정한다.")
    void findByTypeDuplicateAndName() {
        // given
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);
        MemberRepository memberRepository2 = ac.getBean("memberRepository2", MemberRepository.class);
        // when

        // then
        assertThat(memberRepository).isInstanceOf(MemberRepository.class);
        assertThat(memberRepository2).isInstanceOf(MemberRepository.class);
    }

    @Test
    @DisplayName("특정 타입을 모두 조회하기")
    void findAllBeanByType() {
        Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);
        // given

        // when
        for (String s : beansOfType.keySet()) {
            System.out.println("s  = " + s);
            System.out.println("beansOfType = " + beansOfType.get(s));
        }
        System.out.println("beansOfType = " + beansOfType);;
        // then
        assertThat(beansOfType.size()).isEqualTo(2);
    }

    @Configuration
    static class SameBeanConfigTest {
        @Bean
        public MemberRepository memberRepository(){
            return new MemoryMemberRepository();
        }

        @Bean
        public MemberRepository memberRepository2(){
            return new MemoryMemberRepository();
        }
    }
}
