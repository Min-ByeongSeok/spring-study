package toyproject.account.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import toyproject.account.domain.Account;
import toyproject.account.dto.AccountInfo;
import toyproject.account.dto.CreateAccount;
import toyproject.account.dto.DeleteAccount;
import toyproject.account.service.AccountService;
import toyproject.account.service.RedisTestService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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

    @DeleteMapping("/account")
    public DeleteAccount.Response deleteAccount(@RequestBody @Valid DeleteAccount.Request request) {
        return DeleteAccount.Response.fromDto(
                accountService.deleteAccount(request.getUserId(), request.getAccountNumber())
        );
    }

    @GetMapping("/account")
    public List<AccountInfo> getAccountByUserId(@RequestParam("user_id") Long userId) {
        return accountService.getAccountsByUserId(userId).stream().map(
                accountDto -> AccountInfo.builder()
                        .accountNumber(accountDto.getAccountNumber())
                        .balance(accountDto.getBalance())
                        .build()
        ).collect(Collectors.toList());
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
