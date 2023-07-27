package toyproject.account.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import toyproject.account.domain.Account;
import toyproject.account.service.AccountService;

import java.util.List;

/**
 * 레이어드 아키텍처는 외부에서는 컨트롤러로만 접속이 가능하고
 * 컨트롤러는 서비스로 접속하고, 서비스는 리포지토리로 접속하는
 * 순차적인 계층화된 구조
 */

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/create-account")
    public String createAccount() {
        accountService.createAccount();

        return "success";
    }

    @GetMapping("/account/{id}")
    public Account getAccount(@PathVariable Long id) {
        return accountService.getAccount(id);
    }

}
