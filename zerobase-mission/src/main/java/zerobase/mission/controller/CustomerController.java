package zerobase.mission.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import zerobase.mission.domain.member.Customer;
import zerobase.mission.dto.CustomerDto;
import zerobase.mission.dto.CustomerSignupDto;
import zerobase.mission.service.CustomerService;
import zerobase.mission.type.Role;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/signup/customer")
    public CustomerSignupDto.Response CustomerSignup(@RequestBody @Valid CustomerSignupDto.Request request) {

        CustomerDto customerDto = customerService.customerSignup(Customer.builder()
                .userId(request.getUserId())
                .password(request.getPassword())
                .name(request.getName())
                .role(Role.CUSTOMER)
                .phoneNumber(request.getMobileNumber())
                .build());

        return CustomerSignupDto.Response.fromDto(customerDto);
    }
}
