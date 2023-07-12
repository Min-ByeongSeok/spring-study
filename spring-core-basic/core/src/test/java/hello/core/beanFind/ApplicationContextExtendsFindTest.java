package hello.core.beanFind;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

public class ApplicationContextExtendsFindTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

    @Test
    @DisplayName("부모타입으로 조회 시, 자식이 둘 이상 있으면 중복오류 발생")
    void findBeanByParentTypeDuplicate() {
        Assertions.assertThrows(NoUniqueBeanDefinitionException.class,
                () -> ac.getBean(DiscountPolicy.class));
    }

    @Test
    @DisplayName("부모타입으로 조회 시, 자식이 둘 이상 있으면 이름으로 조회한다.")
    void findBeanByParentTypeBeanName() {
        // given
        DiscountPolicy bean = ac.getBean("rateDiscountPolicy", DiscountPolicy.class);

        // then
        org.assertj.core.api.Assertions.assertThat(bean).isInstanceOf(RateDiscountPolicy.class);
    }

    @Test
    @DisplayName("하위 타입으로 조회 (권장하지 않음)")
    void findBeanBySubType() {
        // given
        DiscountPolicy bean = ac.getBean(RateDiscountPolicy.class);

        // then
        org.assertj.core.api.Assertions.assertThat(bean).isInstanceOf(RateDiscountPolicy.class);
    }

    @Test
    @DisplayName("부모 타입으로 모두 조회하기")
    void findAllBeanByParentType() {
        // given
        Map<String, DiscountPolicy> beansOfType = ac.getBeansOfType(DiscountPolicy.class);
        // when

        // then
        org.assertj.core.api.Assertions.assertThat(beansOfType.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("부모타입으로 모두 조회하기 - object 타입")
    void findAllBeanOfObjectType() {
        // given
        Map<String, Object> beansOfType = ac.getBeansOfType(Object.class);
        // when
        for (String s : beansOfType.keySet()) {
            System.out.println("s = " + s);
            System.out.println("value = " + beansOfType.get(s));
        }
    }

    @Configuration
    static class TestConfig{

        @Bean
        public DiscountPolicy rateDiscountPolicy(){
            return new RateDiscountPolicy();
        }

        @Bean DiscountPolicy fixDiscountPolicy(){
            return new FixDiscountPolicy();
        }
    }
}
