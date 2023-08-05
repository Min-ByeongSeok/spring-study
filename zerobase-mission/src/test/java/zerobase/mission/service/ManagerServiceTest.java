package zerobase.mission.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import zerobase.mission.domain.Address;
import zerobase.mission.domain.Store;
import zerobase.mission.domain.member.Manager;
import zerobase.mission.dto.member.ManagerDto;
import zerobase.mission.exception.CustomException;
import zerobase.mission.repository.ManagerRepository;
import zerobase.mission.repository.RegisterRepository;
import zerobase.mission.type.ErrorCode;
import zerobase.mission.type.Role;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ManagerServiceTest {

    @Mock
    private ManagerRepository managerRepository;
    @Mock
    private RegisterRepository registerRepository;

    @InjectMocks
    private ManagerService managerService;
    @InjectMocks
    private RegisterService registerService;

    @Test
    @DisplayName("점장 회원가입 성공")
    void success_signup_manager() {
        // given
        Store crongStore = Store.builder()
                .name("crongStore")
                .address(Address.builder()
                        .city("bbo-rong")
                        .street("bbo-rong")
                        .zipcode("123")
                        .build())
                .description("crong's store")
                .openTime(LocalTime.of(9, 0, 0))
                .closeTime(LocalTime.of(18, 0, 0))
                .build();

        Manager manager = Manager.builder()
                .loginId("crong123")
                .password("1234")
                .name("crong")
                .store(crongStore)
                .build();

        given(managerRepository.existsByLoginId(anyString())).willReturn(false);
        given(registerRepository.existsByNameAndAddress(anyString(), any())).willReturn(true);
        given(managerRepository.save(any())).willReturn(manager);

        // when
        ManagerDto managerDto = managerService.signupManager(manager);

        // then
        assertEquals(Role.MANAGER, managerDto.getRole());
        assertEquals("crongStore", managerDto.getStoreName());
    }

    @Test
    @DisplayName("점장 회원가입 실패 - 중복된 ID")
    void fail_signup_already_exist_loginId() {
        // given
        Store crongStore = Store.builder()
                .name("crongStore")
                .address(Address.builder()
                        .city("bbo-rong")
                        .street("bbo-rong")
                        .zipcode("123")
                        .build())
                .description("crong's store")
                .openTime(LocalTime.of(9, 0, 0))
                .closeTime(LocalTime.of(18, 0, 0))
                .build();

        Manager manager = Manager.builder()
                .loginId("crong123")
                .password("1234")
                .name("crong")
                .store(crongStore)
                .build();

        given(managerRepository.existsByLoginId(anyString())).willReturn(true);
        // when
        CustomException exception = assertThrows(CustomException.class, () -> managerService.signupManager(manager));
        // then
        assertEquals(ErrorCode.ALREADY_EXIST_LOGIN_ID, exception.getErrorCode());
    }

    @Test
    @DisplayName("점장 회원가입 실패 - 등록되지 않은 매장")
    void fail_signup_not_exist_store_info() {
        // given
        Store crongStore = Store.builder()
                .name("crongStore")
                .address(Address.builder()
                        .city("bbo-rong")
                        .street("bbo-rong")
                        .zipcode("123")
                        .build())
                .description("crong's store")
                .openTime(LocalTime.of(9, 0, 0))
                .closeTime(LocalTime.of(18, 0, 0))
                .build();

        Manager manager = Manager.builder()
                .loginId("crong123")
                .password("1234")
                .name("crong")
                .store(crongStore)
                .build();

        given(managerRepository.existsByLoginId(anyString())).willReturn(false);
        given(registerRepository.existsByNameAndAddress(anyString(), any())).willReturn(false);
        // when
        CustomException exception = assertThrows(CustomException.class, () -> managerService.signupManager(manager));
        // then
        assertEquals(ErrorCode.NOT_REGISTERED_STORE, exception.getErrorCode());

    }

}