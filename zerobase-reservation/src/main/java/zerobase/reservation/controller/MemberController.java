package zerobase.reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import zerobase.reservation.domain.member.Customer;
import zerobase.reservation.dto.CustomerDto;
import zerobase.reservation.dto.Signin;
import zerobase.reservation.dto.CustomerSignup;
import zerobase.reservation.service.MemberService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup/customer")
    public CustomerSignup.Response signupCustomer(@RequestBody @Valid CustomerSignup.Request request) {

//        Customer customer = Customer.builder()
//                .name("pororo")
//                .userId("pororo123")
//                .password("1234")
//                .role(Role.CUSTOMER)
//                .phoneNumber("01011112222")
//                .build();

        CustomerDto customerDto = memberService.CustomerSignup(Customer.builder()
                .name(request.getName())
                .userId(request.getUserId())
                .password(request.getPassword())
                .role(request.getRole())
                .build());


        return CustomerSignup.Response.fromDto(customerDto);
    }

    @PostMapping("/signin")
    public Signin.Response signin(@RequestBody @Valid Signin.Request request) {
        CustomerDto signinCustomerDto
                = memberService.signin(request.getUserId(), request.getPassword());

        return Signin.Response.fromDto(signinCustomerDto);
    }
}
