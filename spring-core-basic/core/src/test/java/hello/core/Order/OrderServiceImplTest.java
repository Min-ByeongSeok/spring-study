package hello.core.Order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {

    @Test
    @DisplayName("생성자 주입 테스트")
    void createOrder() {
        // given
        MemberRepository memberRepository = new MemoryMemberRepository();
        memberRepository.save(new Member(1L, "name", Grade.VIP));

        DiscountPolicy discountPolicy = new FixDiscountPolicy();

        OrderServiceImpl orderService = new OrderServiceImpl(memberRepository, discountPolicy);
        // when
        Order order = orderService.createOrder(1L, "itemA", 10000);
        // then
        Assertions.assertThat(order.getDiscountPrice())
                .isEqualTo(1000);
    }

}