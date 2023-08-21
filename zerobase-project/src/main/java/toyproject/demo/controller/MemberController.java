package toyproject.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import toyproject.demo.domain.Member;
import toyproject.demo.dto.MemberDto;
import toyproject.demo.dto.Signup;
import toyproject.demo.service.MemberService;
import toyproject.demo.type.Role;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/register/manager")
    public Signup.Response signUpManager(@RequestBody @Valid Signup.Request request) {
        MemberDto memberDto = memberService.signUp(Member.builder()
                .userId(request.getUserId())
                .password(request.getPassword())
                .name(request.getName())
                .role(Role.MANAGER)
                .build());

        return Signup.Response.fromDto(memberDto);
    }

    @PostMapping("/register/customer")
    public Signup.Response signUpCustomer(@RequestBody @Valid Signup.Request request) {
        MemberDto memberDto = memberService.signUp(Member.builder()
                .userId(request.getUserId())
                .password(request.getPassword())
                .name(request.getName())
                .role(Role.CUSTOMER)
                .build());

        return Signup.Response.fromDto(memberDto);
    }
}
