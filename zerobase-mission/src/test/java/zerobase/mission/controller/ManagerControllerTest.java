package zerobase.mission.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import zerobase.mission.domain.Address;
import zerobase.mission.domain.Store;
import zerobase.mission.domain.member.Manager;
import zerobase.mission.dto.SignupManagerDto;
import zerobase.mission.dto.member.ManagerDto;
import zerobase.mission.service.ManagerService;
import zerobase.mission.service.RegisterService;
import zerobase.mission.type.Role;

import java.time.LocalTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ManagerController.class)
class ManagerControllerTest {

    @MockBean
    private ManagerService managerService;
    //    @MockBean
//    private RegisterStoreRepository registerStoreRepository;
    @MockBean
    private RegisterService registerService;


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("점장 회원가입 성공")
    void success_signup_manager() throws Exception {
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

        given(managerService.signupManager(any())).willReturn(
                ManagerDto.builder()
                        .loginId(manager.getLoginId())
                        .name(manager.getName())
                        .role(Role.MANAGER)
                        .storeName(manager.getStore().getName())
                        .build());
        // when
        // then
        mockMvc.perform(post("/signup/manager")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new SignupManagerDto.Request(
                                        "crong",
                                        "crong123",
                                        "1234",
                                        "crongStore",
                                        Address.builder()
                                                .city("bbo-rong")
                                                .street("bbo-rong")
                                                .zipcode("11111")
                                                .build()
                                ))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("crong"))
                .andExpect(jsonPath("$.loginId").value("crong123"))
                .andExpect(jsonPath("$.role").value("MANAGER"))
                .andExpect(jsonPath("$.storeName").value("crongStore"));

    }
//
//    @Test
//    @DisplayName("점장 회원가입 실패 - 등록된 매장 정보 없음")
//    void fail_signup_manager_not_registered_store_info() {
//        // given
//
//        // when
//
//        // then
//    }
}