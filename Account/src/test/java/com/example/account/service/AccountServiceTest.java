package com.example.account.service;

import com.example.account.domain.Account;
import com.example.account.domain.AccountStatus;
import com.example.account.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    //    @Autowired
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

//    @BeforeEach
//    void init(){
//        accountService.createAccount();
//    }

    @Test
    @DisplayName("계좌 조회 성공")
    void testMock() {
        //given
        given(accountRepository.findById(anyLong()))
                .willReturn(Optional.of(Account
                        .builder()
                        .accountStatus(AccountStatus.UNREGISTERED)
                        .accountNumber("65789")
                        .build()));

        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        //when
        Account account = accountService.getAccount(4555L);
        //then
        verify(accountRepository, times(1)).findById(captor.capture());
        verify(accountRepository, times(0)).save(any());
        assertEquals(4555L, captor.getValue());
        assertNotEquals(41555L, captor.getValue());

        assertEquals("65789", account.getAccountNumber());
        assertEquals(AccountStatus.UNREGISTERED, account.getAccountStatus());
    }

    @Test
    @DisplayName("계좌 조회 실패 - 음수로 조회")
    void testMockFailedToSearchAccount() {
        //when
        RuntimeException e = assertThrows(RuntimeException.class, () -> accountService.getAccount(-10L));

        //then
        assertEquals("Minus", e.getMessage());
//        assertEquals(AccountStatus.UNREGISTERED, account.getAccountStatus());
    }

//    @Test
//    @DisplayName("테스트 이름 변경")
//    void testGetAccount() {
////        accountService.createAccount();
//        Account account = accountService.getAccount(1L);
//
//        assertEquals("40000", account.getAccountNumber());
//        assertEquals(AccountStatus.IN_USE, account.getAccountStatus());
//    }
//
//    @Test
//    void testGetAccount2() {
////        accountService.createAccount();
//        Account account = accountService.getAccount(1L);
//
//        assertEquals("40000", account.getAccountNumber());
//        assertEquals(AccountStatus.IN_USE, account.getAccountStatus());
//    }

}