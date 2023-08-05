package zerobase.mission.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import zerobase.mission.domain.Address;
import zerobase.mission.domain.Store;
import zerobase.mission.dto.StoreDto;
import zerobase.mission.exception.CustomException;
import zerobase.mission.repository.RegisterRepository;
import zerobase.mission.type.ErrorCode;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RegisterServiceTest {

    @Mock
    RegisterRepository registerRepository;

    @InjectMocks
    RegisterService registerService;

    @Test
    @DisplayName("매장 등록 성공")
    void success_register_store() {
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

        given(registerRepository.existsByNameAndAddress(anyString(), any())).willReturn(false);
        given(registerRepository.save(any())).willReturn(crongStore);
        // when
        StoreDto registeredStore = registerService.register(crongStore);
        // then
        assertEquals("crongStore", registeredStore.getName());
    }

    @Test
    @DisplayName("매장 등록 실패 - 이미 등록된 매장")
    void fail_register_already_registered_store() {
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

        given(registerRepository.existsByNameAndAddress(anyString(), any())).willReturn(true);
        // when
        CustomException exception = assertThrows(CustomException.class, () -> registerService.register(crongStore));
        // then
        assertEquals(ErrorCode.ALREADY_REGISTERED_STORE, exception.getErrorCode());
    }
}