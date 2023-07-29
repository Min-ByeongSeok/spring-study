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
import toyproject.account.dto.AccountDto;
import toyproject.account.exception.AccountException;
import toyproject.account.repository.AccountRepository;
import toyproject.account.repository.AccountUserRepository;
import toyproject.account.type.AccountStatus;
import toyproject.account.type.ErrorCode;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

//@WebMvcTest(AccountService.class)
@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    AccountRepository accountRepository;

    @Mock
    AccountUserRepository accountUserRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    @DisplayName("계좌 생성 성공 - 추가 계좌 개설")
    void createAccountSuccess() {
        // given
        AccountUser user = AccountUser.builder()
                .id(12L)
                .name("Pobi")
                .build();

        given(accountUserRepository.findById(anyLong()))
                .willReturn(Optional.of(user));

        given(accountRepository.findFirstByOrderByIdDesc())
                .willReturn(Optional.of(Account.builder()
//                        .accountUser(user)
                        .accountNumber("1000000002")
                        .build()));

        given(accountRepository.save(any())).willReturn(
                Account.builder().accountUser(user)
                        .accountNumber("1000000003").build());

        ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);

        // when
        AccountDto accountDto = accountService.createAccount(1L, 1000L);

        // then
        verify(accountRepository, times(1)).save(captor.capture());
        assertEquals(12L, accountDto.getUserId());
        assertEquals("1000000003", captor.getValue().getAccountNumber());
    }

    @Test
    @DisplayName("계좌 생성 성공 - 첫 계좌 개설")
    void createFirstAccount() {
        // given
        AccountUser user = AccountUser.builder()
                .id(1L)
                .name("Pobi")
                .build();

        given(accountUserRepository.findById(anyLong()))
                .willReturn(Optional.of(user));

        given(accountRepository.findFirstByOrderByIdDesc())
                .willReturn(Optional.empty());

        given(accountRepository.save(any())).willReturn(
                Account.builder().accountUser(user)
                        .accountNumber("1000000003").build());

        ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);

        // when
        AccountDto accountDto = accountService.createAccount(1L, 1000L);

        // then
        verify(accountRepository, times(1)).save(captor.capture());
        assertEquals(1L, accountDto.getUserId());
        assertEquals("1000000000", captor.getValue().getAccountNumber());
    }

    @Test
    @DisplayName("계좌 생성 실패 - 해당 유저 없음")
    void createAccount_UserNotFound() {
        // given
        given(accountUserRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        // when
        AccountException exception
                = assertThrows(AccountException.class, () -> accountService.createAccount(1L, 1000L));

        // then
        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    @DisplayName("계좌 생성 실패 - 최대 계좌수 초과")
    void createAccount_maxAccountIs10() {
        // given
        AccountUser user = AccountUser.builder()
                .id(1L)
                .name("Pobi")
                .build();

        given(accountUserRepository.findById(anyLong()))
                .willReturn(Optional.of(user));

        given(accountRepository.countByAccountUser(any())).willReturn(10);

        // when
        AccountException exception
                = assertThrows(AccountException.class, () -> accountService.createAccount(1L, 1000L));
        // then
        assertEquals(ErrorCode.MAX_ACCOUNT_PER_USER_10, exception.getErrorCode());
    }

    @Test
    @DisplayName("계좌 해지 성공")
    void deleteAccountSuccess() {
        // given
        AccountUser user = AccountUser.builder()
                .id(12L)
                .name("Pobi")
                .build();

        given(accountUserRepository.findById(anyLong()))
                .willReturn(Optional.of(user));

        given(accountRepository.findByAccountNumber(anyString()))
                .willReturn(Optional.of(Account.builder()
                        .accountUser(user)
                        .accountNumber("1000000002")
                        .balance(0L)
                        .build()));

        ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);

        // when
        AccountDto accountDto = accountService.deleteAccount(1L, "1000000002");

        // then
        verify(accountRepository, times(1)).save(captor.capture());
        assertEquals(12L, accountDto.getUserId());
        assertEquals("1000000002", captor.getValue().getAccountNumber());
        assertEquals(AccountStatus.UNREGISTERED, captor.getValue().getAccountStatus());
    }

    @Test
    @DisplayName("계좌 해지 실패 - 해당 유저 없음")
    void deleteAccount_UserNotFound() {
        // given
        given(accountUserRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        // when
        AccountException exception
                = assertThrows(AccountException.class, () -> accountService.deleteAccount(1L, "1111111111"));

        // then
        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    @DisplayName("계좌 해지 실패 - 해당 계좌 없음")
    void deleteAccount_AccountNotFound() {
        // given
        AccountUser user = AccountUser.builder()
                .id(12L)
                .name("Pobi")
                .build();

        given(accountUserRepository.findById(anyLong()))
                .willReturn(Optional.of(user));

        given(accountRepository.findByAccountNumber(anyString()))
                .willReturn(Optional.empty());

        ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);

        // when
        AccountException exception
                = assertThrows(AccountException.class, () -> accountService.deleteAccount(1L, "1111111111"));

        // then
        assertEquals(ErrorCode.ACCOUNT_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    @DisplayName("계좌 해지 실패 - 계좌 소유주 다름")
    void deleteAccountFailed_userUnMatched() {
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
                = assertThrows(AccountException.class, () -> accountService.deleteAccount(12L, "1111111111"));

        // then
        assertEquals(ErrorCode.USER_ACCOUNT_UN_MATCH, exception.getErrorCode());
    }

    @Test
    @DisplayName("계좌 해지 실패 - 잔액 남아 있음")
    void deleteAccountFailed_balanceNotEmpty() {
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
                        .accountNumber("1000000002")
                        .balance(100L)
                        .build()));

        // when
        AccountException exception
                = assertThrows(AccountException.class, () -> accountService.deleteAccount(12L, "1111111111"));

        // then
        assertEquals(ErrorCode.BALANCE_NOT_EMPTY, exception.getErrorCode());
    }

    @Test
    @DisplayName("계좌 해지 실패 - 해지된 계좌")
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
                = assertThrows(AccountException.class, () -> accountService.deleteAccount(12L, "1111111111"));

        // then
        assertEquals(ErrorCode.ACCOUNT_ALREADY_UNREGISTERED, exception.getErrorCode());
    }

    @Test
    @DisplayName("계좌 조회 성공 - 유저 아이디로 조회")
    void successGetAccountsByUserId() {
        // given
        AccountUser Pobi = AccountUser.builder()
                .id(12L)
                .name("Pobi")
                .build();

        List<Account> accounts = Arrays.asList(
                Account.builder()
                        .accountUser(Pobi)
                        .accountNumber("1234567890")
                        .balance(1000L)
                        .build(),
                Account.builder()
                        .accountUser(Pobi)
                        .accountNumber("1234567891")
                        .balance(2000L)
                        .build(),
                Account.builder()
                        .accountUser(Pobi)
                        .accountNumber("1234567892")
                        .balance(3000L)
                        .build());

        given(accountUserRepository.findById(anyLong()))
                .willReturn(Optional.of(Pobi));

        given(accountRepository.findByAccountUser(any())).willReturn(accounts);

        // when
        List<AccountDto> accountDtos = accountService.getAccountsByUserId(12L);

        // then
        assertEquals(3, accountDtos.size());
        assertEquals("1234567890", accountDtos.get(0).getAccountNumber());
        assertEquals("1234567891", accountDtos.get(1).getAccountNumber());
        assertEquals("1234567892", accountDtos.get(2).getAccountNumber());
    }
    
    @Test
    @DisplayName("계좌 조회 실패 - 해댱 유저 없음")
    void failedToGetAccount() {
        // given
        given(accountUserRepository.findById(anyLong()))
                .willReturn(Optional.empty());
        // when
        AccountException exception
                = assertThrows(AccountException.class, () -> accountService.getAccountsByUserId(1L));

        // then
        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
    }
}