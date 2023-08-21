package zerobase.mission.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import zerobase.mission.domain.member.Customer;
import zerobase.mission.dto.SignupCustomerDto;
import zerobase.mission.dto.member.CustomerDto;
import zerobase.mission.service.CustomerService;
import zerobase.mission.type.Role;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/signup/customer")
    public SignupCustomerDto.Response SignupCustomer(@RequestBody @Valid SignupCustomerDto.Request request) {

        CustomerDto customerDto = customerService.signupCustomer(Customer.builder()
                .name(request.getName())
                .loginId(request.getLoginId())
                .password(request.getPassword())
                .role(Role.CUSTOMER)
                .phoneNumber(request.getMobileNumber())
                .build());

        return SignupCustomerDto.Response.fromDto(customerDto);
    }
}
