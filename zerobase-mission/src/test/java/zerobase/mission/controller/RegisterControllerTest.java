package zerobase.mission.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import zerobase.mission.domain.Address;
import zerobase.mission.domain.Store;
import zerobase.mission.dto.RegisterStoreDto;
import zerobase.mission.dto.StoreDto;
import zerobase.mission.service.RegisterService;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(RegisterController.class)
class RegisterControllerTest {

    @MockBean
    private RegisterService registerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("매장 등록 성공")
    void success_register_store() throws Exception {
        // given
        Address address = Address.builder()
                .city("bbo-rong")
                .street("bbo-rong")
                .zipcode("123")
                .build();

        Store crongStore = Store.builder()
                .name("crongStore")
                .address(address)
                .description("crong's store")
                .openTime(LocalTime.of(9, 0, 0))
                .closeTime(LocalTime.of(18, 0, 0))
                .build();


        given(registerService.registerStore(any())).willReturn(
                StoreDto.builder()
                        .name(crongStore.getName())
                        .isRegistered(true)
                        .build());
        // when
        // then
        mockMvc.perform(post("/register/store")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new RegisterStoreDto.Request(
                                        "crongStore",
                                        new Address("bbo-rong", "bbo-rong", "123"),
                                        "hi this is crong's store",
                                        LocalTime.of(9, 0, 0),
                                        LocalTime.of(18, 0, 0))
                        )))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("crongStore"))
                .andExpect(jsonPath("$.registered").value(true));
    }

}