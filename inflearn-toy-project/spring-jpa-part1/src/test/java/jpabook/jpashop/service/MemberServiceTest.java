package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
//    @Rollback(value = false)
    @DisplayName("회원가입")
    void 회원가입() {
        // given
        Member member = new Member();
        member.setName("jim");
        // when
        Long savedId = memberService.join(member);
        // then
        Assertions.assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test
    @DisplayName("중복회원 예외")
    void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("kom1");
        Member member2 = new Member();
        member2.setName("kom1");
        // when
        Long member1Id = memberService.join(member1);
        // then
        Assertions.assertThrows(IllegalStateException.class, () -> memberService.join(member2));
    }
}