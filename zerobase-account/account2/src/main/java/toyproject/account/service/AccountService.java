package toyproject.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.account.domain.Account;
import toyproject.account.domain.AccountStatus;
import toyproject.account.repository.AccountRepository;

import java.util.List;

// Account Repository를 활용해서 데이터를 저장
@Service
// final keyword가 붙은 필드를 생성자로 생성할 수 있도록 해준다.
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    @Transactional
    public void createAccount(){
        Account account = Account.builder()
                .accountNumber("40000")
                .accountStatus(AccountStatus.IN_USE)
                .build();

        accountRepository.save(account);
    }

    @Transactional
    public Account getAccount(Long id) {
        return accountRepository.findById(id).get();
    }


}
