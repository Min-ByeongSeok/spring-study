package toyproject.account.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import toyproject.account.domain.Account;
import toyproject.account.dto.AccountDto;
import toyproject.account.dto.CreateAccount;
import toyproject.account.dto.DeleteAccount;
import toyproject.account.service.AccountService;
import toyproject.account.service.RedisTestService;
import toyproject.account.type.AccountStatus;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


// 테스트하려는 컨트롤러를 명시함
@WebMvcTest(AccountController.class)
class AccountControllerTest {

    // 메인의 AccountController가 AccountService와 RedisTestService를 의존하고있기때문에
    // mock으로 두 객체의 의존관계를 설정한다.
    @MockBean
    private AccountService accountService;
    @MockBean
    private RedisTestService redisTestService;

    // 원래는 Inject을 해줘야하는데 mockbean으로 등록하고,
    // 위에 테스트하려는 컨트롤러를 명시해주었기때문에 자동으로 주입해서 코드생략이 가능함

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // json을 쉽게 바꿔줌

    @Test
    @DisplayName("계좌 생성 성공")
    void successCreateAccount() throws Exception {
        // given
        // given : accountService.createAccount메서드가 파라미터로 userId : anyLong(), initialBalance : anyLong() 을 받으면
        // willReturn : 응답값으로 AccountDto의 객체가 만들어지고,
        //        .userId(1L)
        //                .accountNumber("1234567890")
        //                .registeredAt(LocalDateTime.now())
        //                .unRegisteredAt(LocalDateTime.now()) 이러한 정보를 담고있을 것이다.
        given(accountService.createAccount(anyLong(), anyLong())).willReturn(
                AccountDto.builder()
                        .userId(1L)
                        .accountNumber("1234567890")
                        .registeredAt(LocalDateTime.now())
                        .unRegisteredAt(LocalDateTime.now())
                        .build());
        // when

        // then : 위의 실행결과로
        // mockMvc로 테스트를 할건데 post /account 요청을 보내게되면
        mockMvc.perform(post("/account")
                        .contentType(MediaType.APPLICATION_JSON) // JSON 타입의
                        .content(objectMapper.writeValueAsString( // responsne body에 담길 값
                                new CreateAccount.Request(1L, 100L))))
                // 그럼 post요청의 결과로 상태코드 200값과
                .andExpect(status().isOk())
                // 응답값에 있는 json값중에 userId는 1이라는 값을 가지게되고
                .andExpect(jsonPath("$.userId").value(1))
                // 응답값에 있는 accountNumber는 1234567890이라는 값을 가지게된다.
                .andExpect(jsonPath("$.accountNumber").value("1234567890"))
                .andDo(print());
    }

    @Test
    @DisplayName("계좌 해지 성공")
    void successDeleteAccount() throws Exception {
        given(accountService.deleteAccount(anyLong(), anyString())).willReturn(
                AccountDto.builder()
                        .userId(1L)
                        .accountNumber("1234567890")
                        .registeredAt(LocalDateTime.now())
                        .unRegisteredAt(LocalDateTime.now())
                        .build());
        // when

        // then : 위의 실행결과로
        // mockMvc로 테스트를 할건데 Delete /account 요청을 보내게되면
        mockMvc.perform(delete("/account")
                        .contentType(MediaType.APPLICATION_JSON) // JSON 타입의
                        .content(objectMapper.writeValueAsString( // responsne body에 담길 값
                                new DeleteAccount.Request(1L, "1234567890"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.accountNumber").value("1234567890"))
                .andDo(print());
    }

    @Test
    @DisplayName("계좌 조회 성공 - 유저 아이디로 조회")
    void successGetAccountsByUserId() throws Exception {
        // given
        List<AccountDto> accountDtos =
                Arrays.asList(
                        AccountDto.builder()
                                .accountNumber("1234567890")
                                .balance(1000L)
                                .build(),
                        AccountDto.builder()
                                .accountNumber("1234567891")
                                .balance(2000L)
                                .build(),
                        AccountDto.builder()
                                .accountNumber("1234567892")
                                .balance(3000L)
                                .build()
                );

        given(accountService.getAccountsByUserId(anyLong())).willReturn(accountDtos);

        // when


        // then
        mockMvc.perform(get("/account?user_id=1"))
                .andDo(print())
                .andExpect(jsonPath("$[0].accountNumber").value("1234567890"))
                .andExpect(jsonPath("$[0].balance").value(1000L))
                .andExpect(jsonPath("$[1].accountNumber").value("1234567891"))
                .andExpect(jsonPath("$[1].balance").value(2000L))
                .andExpect(jsonPath("$[2].accountNumber").value("1234567892"))
                .andExpect(jsonPath("$[2].balance").value(3000L));
    }

    @Test
    @DisplayName("test - test")
    void successGetAccount() throws Exception {
        // given
        given(accountService.getAccount(anyLong())).willReturn(
                Account.builder()
                        .accountStatus(AccountStatus.IN_USE)
                        .accountNumber("3456").build());
        // when

        // then
        mockMvc.perform(get("/account/678"))
                .andDo(print())
                .andExpect(jsonPath("$.accountNumber").value("3456"))
                .andExpect(jsonPath("$.accountStatus").value("IN_USE"))
                .andExpect(status().isOk());
    }
}