package zerobase.reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import zerobase.reservation.domain.Member;
import zerobase.reservation.dto.MemberDto;
import zerobase.reservation.dto.Signup;
import zerobase.reservation.service.MemberService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public Signup.Response signup(@RequestBody @Valid Signup.Request request) {
        MemberDto memberDto = memberService.signup(Member.builder()
                .name(request.getName())
                .password(request.getPassword())
                .userId(request.getUserId())
                .role(request.getRole())
                .build());

        return Signup.Response.fromDto(memberDto);
    }
}
