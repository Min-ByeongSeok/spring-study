package toyproject.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.account.domain.Account;
import toyproject.account.domain.AccountUser;
import toyproject.account.dto.AccountDto;
import toyproject.account.exception.AccountException;
import toyproject.account.repository.AccountRepository;
import toyproject.account.repository.AccountUserRepository;
import toyproject.account.type.AccountStatus;
import toyproject.account.type.ErrorCode;

import java.time.LocalDateTime;
import java.util.Objects;

// Account Repository를 활용해서 데이터를 저장
@Service
// final keyword가 붙은 필드를 생성자로 생성할 수 있도록 해준다.
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountUserRepository accountUserRepository;

    @Transactional
    public AccountDto createAccount(Long userId, Long initialBalance) {
        // 사용자 유무 조회
        AccountUser accountUser = accountUserRepository
                .findById(userId)
                .orElseThrow(() -> new AccountException(ErrorCode.USER_NOT_FOUND));

        // 계좌 유효성 검사(최대개수... )
        validateCreateAccount(accountUser);

        // 계좌번호 생성
        String newAccountNumber = accountRepository.findFirstByOrderByIdDesc()
                .map(account -> (Integer.parseInt(account.getAccountNumber())) + 1 + "")
                .orElse("1000000000");

        // 계좌 저장하고 그 정보를 넘긴다.
        Account account = accountRepository.save(
                Account.builder()
                        .accountUser(accountUser)
                        .accountStatus(AccountStatus.IN_USE)
                        .accountNumber(newAccountNumber)
                        .balance(initialBalance)
                        .registeredAt(LocalDateTime.now())
                        .build());

        // Entity 타입으로 정보를 응답하는 것은 권장하지 않는다 따라서
        // Dto 타입을 하나만들고 fromEntity라는 엔티티객체를 Dto타입으로 변경하는 메서드를 하나만들어서
        // 응답한다.
        return AccountDto.fromEntity(account);
    }

    private void validateCreateAccount(AccountUser accountUser) {
        if (accountRepository.countByAccountUser(accountUser) >= 10) {
            throw new AccountException(ErrorCode.MAX_ACCOUNT_PER_USER_10);
        }
    }

    @Transactional
    public Account getAccount(Long id) {
        if (id < 0) {
            throw new RuntimeException("minus");
        }
        return accountRepository.findById(id).get();
    }

    @Transactional
    public AccountDto deleteAccount(Long userId, String accountNumber) {
        // 사용자 유무 조회
        AccountUser accountUser = accountUserRepository
                .findById(userId)
                .orElseThrow(() -> new AccountException(ErrorCode.USER_NOT_FOUND));

        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountException(ErrorCode.ACCOUNT_NOT_FOUND));

        // 유효성 검사
        validateDeleteAccount(accountUser, account);

        account.setAccountStatus(AccountStatus.UNREGISTERED);
        account.setUnRegisteredAt(LocalDateTime.now());

        accountRepository.save(account);

        return AccountDto.fromEntity(account);
    }

    private void validateDeleteAccount(AccountUser accountUser, Account account) {
        if (!Objects.equals(accountUser.getId(), account.getAccountUser().getId())) {
            throw new AccountException(ErrorCode.USER_ACCOUNT_UN_MATCH);
        }
        if (account.getAccountStatus() == AccountStatus.UNREGISTERED) {
            throw new AccountException(ErrorCode.ACCOUNT_ALREADY_UNREGISTERED);
        }
        if (account.getBalance() > 0) {
            throw new AccountException(ErrorCode.BALANCE_NOT_EMPTY);
        }
    }
}
