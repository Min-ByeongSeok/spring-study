package toyproject.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import toyproject.demo.domain.Kiosk;
import toyproject.demo.domain.Member;
import toyproject.demo.domain.Store;
import toyproject.demo.dto.Register;
import toyproject.demo.service.MemberService;
import toyproject.demo.service.RegisterService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class RegisterController {
    private final MemberService memberService;
    private final RegisterService registerService;

    @PostMapping("/register/store")
    public Register.Response registerStore(@RequestBody @Valid Register.Request request) {
        Member manager = memberService.findByManagerUserId(request.getManagerId());

        registerService.register(Store.builder()
                .storeInfo(request.getStoreInfo())
                .manager(manager)
                .kiosk(Kiosk.builder().build())
                .build());

        registerService.addAutocompleteKeyword(request.getStoreInfo().getName());

        return Register.Response.builder()
                .message("매장 등록 완료")
                .build();
    }
}
