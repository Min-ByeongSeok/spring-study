package zerobase.mission.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import zerobase.mission.domain.member.Customer;
import zerobase.mission.dto.member.CustomerDto;
import zerobase.mission.dto.SignupCustomerDto;
import zerobase.mission.service.CustomerService;
import zerobase.mission.type.Role;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @MockBean
    private CustomerService customerService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("고객 회원가입 성공")
    void success_signup_customer() throws Exception {
        // given
        Customer customer = Customer.builder()
                .loginId("pororo123")
                .password("1234")
                .name("pororo")
                .phoneNumber("01011112222")
                .build();

        given(customerService.signupCustomer(any())).willReturn(
                CustomerDto.builder()
                        .loginId(customer.getLoginId())
                        .name(customer.getName())
                        .role(Role.CUSTOMER)
                        .build());

        // when
        // then
        mockMvc.perform(post("/signup/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new SignupCustomerDto.Request(
                                        "pororo",
                                        "pororo123",
                                        "1234",
                                        "01011112222"
                                ))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.loginId").value("pororo123"))
                .andExpect(jsonPath("$.name").value("pororo"))
                .andExpect(jsonPath("$.role").value("CUSTOMER"));
    }
}