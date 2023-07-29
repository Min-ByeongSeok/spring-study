package toyproject.account.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import toyproject.account.domain.Account;
import toyproject.account.domain.AccountUser;
import toyproject.account.domain.Transaction;
import toyproject.account.dto.TransactionDto;
import toyproject.account.exception.AccountException;
import toyproject.account.repository.AccountRepository;
import toyproject.account.repository.AccountUserRepository;
import toyproject.account.repository.TransactionRepository;
import toyproject.account.type.AccountStatus;
import toyproject.account.type.ErrorCode;
import toyproject.account.type.TransactionResultType;
import toyproject.account.type.TransactionType;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    public static final long USE_AMOUNT = 200L;
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountUserRepository accountUserRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    @DisplayName("잔액 사용 성공")
    void successUseBalance() {
        // given
        AccountUser user = AccountUser.builder()
                .id(12L)
                .name("pobi")
                .build();

        given(accountUserRepository.findById(anyLong()))
                .willReturn(Optional.of(user));

        Account account = Account.builder()
                .accountStatus(AccountStatus.IN_USE)
                .accountUser(user)
                .accountNumber("1000000002")
                .balance(10000L)
                .build();

        given(accountRepository.findByAccountNumber(anyString()))
                .willReturn(Optional.of(account));

        given(transactionRepository.save(any())).willReturn(
                Transaction.builder()
                        .account(account)
                        .transactionType(TransactionType.USE)
                        .transactionResultType(TransactionResultType.SUCCESS)
                        .transactionId("transactionId")
                        .transactedAt(LocalDateTime.now())
                        .amount(1000L)
                        .balanceSnapshot(9000L)
                        .build());

        ArgumentCaptor<Transaction> captor = ArgumentCaptor.forClass(Transaction.class);

        // when
        TransactionDto transactionDto = transactionService.useBalance(1L, "1000000000", USE_AMOUNT);
        // then
        verify(transactionRepository, times(1)).save(captor.capture());
        assertEquals(9800L, captor.getValue().getBalanceSnapshot());
        assertEquals(USE_AMOUNT, captor.getValue().getAmount());


        assertEquals(9000L, transactionDto.getBalanceSnapshot());
        assertEquals(TransactionResultType.SUCCESS, transactionDto.getTransactionResultType());
        assertEquals(TransactionType.USE, transactionDto.getTransactionType());
        assertEquals(1000L, transactionDto.getAmount());
    }

    @Test
    @DisplayName("잔액 사용 실패 - 해당 유저 없음")
    void useBalance_UserNotFound() {
        // given
        given(accountUserRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        // when
        AccountException exception
                = assertThrows(AccountException.class, () -> transactionService.useBalance(1L, "1111111111", 1000L));

        // then
        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    @DisplayName("잔액 사용 실패 - 해당 유저 없음")
    void useBalance_AccountNotFound() {
        // given
        AccountUser user = AccountUser.builder()
                .id(12L)
                .name("pobi")
                .build();

        given(accountUserRepository.findById(anyLong()))
                .willReturn(Optional.of(user));

        given(accountRepository.findByAccountNumber(anyString()))
                .willReturn(Optional.empty());
        // when
        AccountException exception
                = assertThrows(AccountException.class, () -> transactionService.useBalance(1L, "1111111111", 1000L));

        // then
        assertEquals(ErrorCode.ACCOUNT_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    @DisplayName("잔액 사용 실패 - 계좌 소유주 다름")
    void useBalanceFailed_userUnMatched() {
        // given
        AccountUser Pobi = AccountUser.builder()
                .id(12L)
                .name("Pobi")
                .build();

        AccountUser Pororo = AccountUser.builder()
                .id(13L)
                .name("Pororo")
                .build();

        given(accountUserRepository.findById(anyLong()))
                .willReturn(Optional.of(Pobi));

        given(accountRepository.findByAccountNumber(anyString()))
                .willReturn(Optional.of(Account.builder()
                        .accountUser(Pororo)
                        .accountNumber("1000000002")
                        .balance(0L)
                        .build()));

        // when
        AccountException exception
                = assertThrows(AccountException.class, () -> transactionService.useBalance(12L, "1111111111", 1000L));

        // then
        assertEquals(ErrorCode.USER_ACCOUNT_UN_MATCH, exception.getErrorCode());
    }

    @Test
    @DisplayName("잔액 사용 실패 - 해지된 계좌")
    void deleteAccountFailed_alreadyUnregistered() {
        // given
        AccountUser Pobi = AccountUser.builder()
                .id(12L)
                .name("Pobi")
                .build();

        given(accountUserRepository.findById(anyLong()))
                .willReturn(Optional.of(Pobi));

        given(accountRepository.findByAccountNumber(anyString()))
                .willReturn(Optional.of(Account.builder()
                        .accountUser(Pobi)
                        .accountStatus(AccountStatus.UNREGISTERED)
                        .accountNumber("1000000002")
                        .balance(0L)
                        .build()));

        // when
        AccountException exception
                = assertThrows(AccountException.class, () -> transactionService.useBalance(12L, "1111111111", 1000L));

        // then
        assertEquals(ErrorCode.ACCOUNT_ALREADY_UNREGISTERED, exception.getErrorCode());
    }

    @Test
    @DisplayName("잔액 사용 실패 - 한도 초과")
    void useBalanceFailed_amountExceedBalance() {
        // given
        AccountUser user = AccountUser.builder()
                .id(12L)
                .name("pobi")
                .build();

        Account account = Account.builder()
                .accountStatus(AccountStatus.IN_USE)
                .accountUser(user)
                .accountNumber("1000000002")
                .balance(100L)
                .build();

        given(accountUserRepository.findById(anyLong()))
                .willReturn(Optional.of(user));

        given(accountRepository.findByAccountNumber(anyString()))
                .willReturn(Optional.of(account));

        // when
        AccountException exception
                = assertThrows(AccountException.class,
                () -> transactionService.useBalance(12L, "1111111111", 1000L));

        // then
        assertEquals(ErrorCode.AMOUNT_EXCEED_BALANCE, exception.getErrorCode());
    }

    @Test
    @DisplayName("잔액 사용 취소 - 실패 트랜잭션 저장 성공")
    void saveFailedUseTransaction() {
        // given
        AccountUser user = AccountUser.builder()
                .id(12L)
                .name("pobi")
                .build();

        Account account = Account.builder()
                .accountStatus(AccountStatus.IN_USE)
                .accountUser(user)
                .accountNumber("1000000002")
                .balance(10000L)
                .build();

        given(accountRepository.findByAccountNumber(anyString()))
                .willReturn(Optional.of(account));

        given(transactionRepository.save(any())).willReturn(
                Transaction.builder()
                        .account(account)
                        .transactionType(TransactionType.USE)
                        .transactionResultType(TransactionResultType.SUCCESS)
                        .transactionId("transactionId")
                        .transactedAt(LocalDateTime.now())
                        .amount(1000L)
                        .balanceSnapshot(9000L)
                        .build());

        ArgumentCaptor<Transaction> captor = ArgumentCaptor.forClass(Transaction.class);

        // when
        transactionService.saveFailedUseTransaction("1000000000", USE_AMOUNT);
        // then
        verify(transactionRepository, times(1)).save(captor.capture());
        assertEquals(USE_AMOUNT, captor.getValue().getAmount());
        assertEquals(10000L, captor.getValue().getBalanceSnapshot());
        assertEquals(TransactionResultType.FAIL, captor.getValue().getTransactionResultType());
    }
}