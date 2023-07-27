package toyproject.account.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import toyproject.account.domain.Account;
import toyproject.account.type.AccountStatus;
import toyproject.account.repository.AccountRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

// 메인환경의 스프링부트와 같은 환경으로 테스트를 가능하게함
// @SpringBootTest
// 하지만 클래스가 의존하는 클래스를 모두 만들고 사전작업이 필요하고, 또한 테스트하려는
// 빈을 제외한 것들은 필요가 없기때문에 이를 해결하기 위해 Mokito를 사용함
@ExtendWith(MockitoExtension.class)
class SampleAccountServiceTest {

    @Mock
    // AccountService는 AccountRepository를 의존하고 있는데 이 의존하는 것을 가짜(Mock)으로 만들어준다
    private AccountRepository accountRepository;

    @InjectMocks
    // 가짜로 만든 Mock을 주입해준다.
    private AccountService accountService;

    @Test
    @DisplayName("계좌 조회 성공")
    void accountServiceTest() {
        // given
        given(accountRepository.findById(anyLong())).willReturn(
                //accountRepository.findById()메서드는 Optional타입의 반환값을 준다.
                Optional.of(Account.builder()
                        .accountStatus(AccountStatus.UNREGISTERED)
                        .accountNumber("65789")
                        .build()));

        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);

        // when
        Account account = accountService.getAccount(4555L);

        // then
        // accountRepository.getAccount가 실행되었을 때 findById메소드를 times()번 실행하였다. 라는걸 검증해준다.
        verify(accountRepository, times(1)).findById(anyLong());
        // accountRepository.getAccount가 실행되었을 때 save메소드를 times()번 실행하였다. 라는걸 검증해준다.
        verify(accountRepository, times(0)).save(any());

        verify(accountRepository, times(1)).findById(captor.capture());
        assertThat(captor.getValue()).isEqualTo(4555L);
        assertThat(account.getAccountNumber()).isEqualTo("65789");

        assertThat(account.getAccountStatus()).isEqualTo(AccountStatus.UNREGISTERED);
    }

    @Test
    @DisplayName("계좌 조회 실패 - 음수 조회")
    void testFailedToSearchAccount() {
        // given
//        Account account = accountService.getAccount(-10L);
        // when
        RuntimeException e = assertThrows(RuntimeException.class, () -> accountService.getAccount(-10L));
        // then
        assertEquals("minus", e.getMessage());
    }
}