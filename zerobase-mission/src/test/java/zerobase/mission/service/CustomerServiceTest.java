package zerobase.mission.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import zerobase.mission.domain.member.Customer;
import zerobase.mission.dto.CustomerDto;
import zerobase.mission.exception.CustomException;
import zerobase.mission.repository.CustomerRepository;
import zerobase.mission.type.ErrorCode;
import zerobase.mission.type.Role;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    @DisplayName("고객 회원가입 성공")
    void success_signup_customer() {
        // given
        Customer customer = Customer.builder()
                .userId("pororo123")
                .phoneNumber("1234")
                .name("pororo")
                .phoneNumber("01011112222")
                .build();

        given(customerRepository.existsByUserId(anyString())).willReturn(false);
        given(customerRepository.save(any())).willReturn(customer);

        // when
        CustomerDto customerDto = customerService.customerSignup(customer);

        // then
        assertEquals(Role.CUSTOMER, customerDto.getRole());
        assertEquals("pororo123", customerDto.getUserId());
        assertEquals("pororo", customerDto.getName());
    }

    @Test
    @DisplayName("고객 회원가입 실패 - 중복된 ID")
    void fail_signup_customer_already_exist_userId() {
        // given
        Customer customer = Customer.builder()
                .userId("pororo123")
                .phoneNumber("1234")
                .name("pororo")
                .phoneNumber("01011112222")
                .build();

        given(customerRepository.existsByUserId(anyString())).willReturn(true);

        CustomException exception = assertThrows(CustomException.class, () -> customerService.customerSignup(customer));

        assertEquals(ErrorCode.ALREADY_EXIST_USER_ID, exception.getErrorCode());
    }


}