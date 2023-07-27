package toyproject.account.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import toyproject.account.domain.Account;
import toyproject.account.domain.AccountUser;
import toyproject.account.dto.AccountDto;
import toyproject.account.repository.AccountRepository;
import toyproject.account.repository.AccountUserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

//@WebMvcTest(AccountService.class)
@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @MockBean
    AccountRepository accountRepository;

    @MockBean
    AccountUserRepository accountUserRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    @DisplayName("계좌 생성 성공")
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
        assertEquals("1000000002", accountDto.getAccountNumber());
    }
}