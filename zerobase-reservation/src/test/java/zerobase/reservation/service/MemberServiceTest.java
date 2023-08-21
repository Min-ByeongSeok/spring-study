package zerobase.reservation.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import zerobase.reservation.domain.member.Customer;
import zerobase.reservation.dto.CustomerDto;
import zerobase.reservation.exception.MemberException;
import zerobase.reservation.repository.CustomerRepository;
import zerobase.reservation.type.ErrorCode;
import zerobase.reservation.type.Role;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static zerobase.reservation.type.Role.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("회원가입 성공")
    void success_signup() {
        // given
//        Member member = Member.builder()
//                .userId("zerobase")
//                .password("1234")
//                .name("hi")
//                .role(CUSTOMER)
//                .build();

        Customer customer = new Customer();
        customer.setUserId("customerId");
        customer.setName("name");
        customer.setPassword("1234");
        customer.setRole(Role.CUSTOMER);
        customer.setPhoneNumber("01011112222");

        given(customerRepository.existsByUserId(anyString())).willReturn(false);
        given(customerRepository.save(any())).willReturn(customer);

        // when
        CustomerDto newCustomerDto = memberService.CustomerSignup(customer);

        // then
        Assertions.assertThat(newCustomerDto.getUserId()).isEqualTo("customerId");
        Assertions.assertThat(newCustomerDto.getName()).isEqualTo("name");
    }

    @Test
    @DisplayName("회원가입 실패 - 중복된 ID")
    void fail_signup_already_exist_userId() {
        // given
//        Member member = Member.builder()
//                .userId("zerobase")
//                .password("1234")
//                .name("hi")
//                .role(CUSTOMER)
//                .build();

        Customer customer = new Customer();
        customer.setUserId("customerId");
        customer.setName("name");
        customer.setPassword("1234");
        customer.setRole(Role.CUSTOMER);
        customer.setPhoneNumber("01011112222");

        given(customerRepository.existsByUserId(anyString())).willReturn(true);

        MemberException exception = assertThrows(MemberException.class, () -> memberService.CustomerSignup(customer));

        assertEquals(ErrorCode.ALREADY_EXIST_USER_ID, exception.getErrorCode());
    }

    @Test
    @DisplayName("로그인 성공")
    void success_signin() {
        // given
//        Member member = Member.builder()
//                .id(1L)
//                .userId("zerobase")
//                .password("1234")
//                .name("hi")
//                .role(CUSTOMER)
//                .build();


        Customer customer = new Customer();
        customer.setUserId("customerId");
        customer.setName("name");
        customer.setPassword("1234");
        customer.setRole(Role.CUSTOMER);
        customer.setPhoneNumber("01011112222");

        given(customerRepository.existsByUserId(anyString())).willReturn(true);
        given(customerRepository.findByUserId(anyString())).willReturn(Optional.of(customer));

        // when
        CustomerDto zerobase = memberService.signin("customerId", "1234");

        // then
        assertEquals("name", zerobase.getName());
        assertEquals(CUSTOMER, zerobase.getRole());
    }

    @Test
    @DisplayName("로그인 실패 - 존재하지 않는 ID")
    void fail_signin_not_exist_userId() {
        // given
        given(customerRepository.existsByUserId(anyString())).willReturn(false);

        // when
        MemberException exception = assertThrows(MemberException.class, () -> memberService.signin("zerobase", "1111"));

        // then
        assertEquals(ErrorCode.NOT_EXIST_USER_ID, exception.getErrorCode());
    }

    @Test
    @DisplayName("로그인 실패 - 틀린 비밀번호")
    void fail_signin_wrong_password() {
        // given
        Customer customer = new Customer();
        customer.setUserId("customerId");
        customer.setName("name");
        customer.setPassword("1234");
        customer.setRole(Role.CUSTOMER);
        customer.setPhoneNumber("01011112222");

        given(customerRepository.existsByUserId(anyString())).willReturn(true);
        given(customerRepository.findByUserId(anyString())).willReturn(Optional.of(customer));

        // when
        MemberException exception = assertThrows(MemberException.class, () -> memberService.signin("customerId", "1111"));

        // then
        assertEquals(ErrorCode.WRONG_PASSWORD, exception.getErrorCode());
    }
}