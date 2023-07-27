package toyproject.account.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import toyproject.account.domain.Account;
import toyproject.account.dto.CreateAccount;
import toyproject.account.service.AccountService;
import toyproject.account.service.RedisTestService;

import javax.validation.Valid;

/**
 * 레이어드 아키텍처는 외부에서는 컨트롤러로만 접속이 가능하고
 * 컨트롤러는 서비스로 접속하고, 서비스는 리포지토리로 접속하는
 * 순차적인 계층화된 구조
 */

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final RedisTestService redisTestService;

    @PostMapping("/account")
    public CreateAccount.Response createAccount(@RequestBody @Valid CreateAccount.Request request) {
        return CreateAccount.Response.fromDto(
                accountService.createAccount(request.getUserId(), request.getInitialBalance())
        );
    }

    @GetMapping("/get-lock")
    public String getLock() {
        return redisTestService.getLock();
    }

    @GetMapping("/account/{id}")
    public Account getAccount(@PathVariable Long id) {
        return accountService.getAccount(id);
    }
}
