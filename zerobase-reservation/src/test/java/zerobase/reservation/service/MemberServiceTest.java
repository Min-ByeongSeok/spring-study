package zerobase.reservation.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import zerobase.reservation.domain.Member;
import zerobase.reservation.dto.MemberDto;
import zerobase.reservation.exception.SignupException;
import zerobase.reservation.repository.MemberRepository;
import zerobase.reservation.type.ErrorCode;
import zerobase.reservation.type.Role;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    MemberService memberService;

    @Test
    @DisplayName("회원가입 성공")
    void success_signup() {
        // given
        Member member = Member.builder()
                .userId("zerobase")
                .password("1234")
                .name("hi")
                .role(Role.CUSTOMER)
                .build();

        given(memberRepository.findByUserId(anyString())).willReturn(Optional.of(member));
        given(memberRepository.save(any())).willReturn(member);

        // when
        MemberDto newMemberDto = memberService.signup(member);

        // then
        Assertions.assertThat(newMemberDto.getName()).isEqualTo("hi");
        Assertions.assertThat(newMemberDto.getUserId()).isEqualTo("zerobase");
    }

    @Test
    @DisplayName("회원가입 실패 - 중복된 ID")
    void fail_signup_already_exist_userId() {

    }
}