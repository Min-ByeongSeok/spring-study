package zerobase.reservation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import zerobase.reservation.domain.member.Customer;
import zerobase.reservation.dto.CustomerDto;
import zerobase.reservation.dto.Signin;
import zerobase.reservation.dto.CustomerSignup;
import zerobase.reservation.service.MemberService;
import zerobase.reservation.type.Role;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @MockBean
    private MemberService memberService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("회원가입 성공")
    void success_signup() throws Exception {
        // given
//        Member member = Member.builder()
//                .id(1L)
//                .userId("zerobase")
//                .password("1234")
//                .name("hi")
//                .role(Role.CUSTOMER)
//                .build();

        Customer customer = new Customer();
        customer.setUserId("customerId");
        customer.setName("name");
        customer.setPassword("1234");
        customer.setRole(Role.CUSTOMER);
        customer.setPhoneNumber("01011112222");

        given(memberService.CustomerSignup(any())).willReturn(
                CustomerDto.builder()
                        .userId(customer.getUserId())
                        .name(customer.getName())
                        .role(customer.getRole())
                        .build());

        // when
        // then
        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new CustomerSignup.Request("customerId", "1234", "name", Role.CUSTOMER)
                        )))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value("customerId"))
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.role").value("CUSTOMER"));
    }

    @Test
    @DisplayName("로그인 성공")
    void success_signin() throws Exception {
        // given
//        Member member = Member.builder()
//                .id(1L)
//                .userId("zerobase")
//                .password("1234")
//                .name("hi")
//                .role(Role.CUSTOMER)
//                .build();

        Customer customer = new Customer();
        customer.setUserId("customerId");
        customer.setName("name");
        customer.setPassword("1234");
        customer.setRole(Role.CUSTOMER);
        customer.setPhoneNumber("01011112222");

        given(memberService.signin(anyString(), anyString())).willReturn(
                CustomerDto.builder()
                        .userId(customer.getUserId())
                        .name(customer.getName())
                        .role(customer.getRole())
                        .build()
        );

        // when
        // then
        mockMvc.perform(post("/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new Signin.Request("customerId", "1234")
                        )))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.role").value("CUSTOMER"));
    }
}