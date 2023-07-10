package hello.core.Order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    // 인터페이스뿐아니라 구체클래스도 함께의존하고 있다 -> DIP 위반
    // Fix -> Rate로 바꾸는 순간 소스코드도 함께 변경해야한다 -> OCP 위반
    // private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    // private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    // 추상화 인터페이스에만 의존하도록 변경해야한다.
    // 클라이언트인 OrderServiceImpl에 DiscountPolicy의 구현객체를 대신 생성하고 주입해야한다.
    //  -> 생성자 주입

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }


    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
