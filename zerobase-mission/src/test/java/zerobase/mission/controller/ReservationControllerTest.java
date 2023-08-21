package zerobase.mission.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import zerobase.mission.domain.Address;
import zerobase.mission.domain.Store;
import zerobase.mission.dto.RegisterDto;
import zerobase.mission.dto.StoreDto;
import zerobase.mission.dto.StoreListDto;
import zerobase.mission.repository.RegisterRepository;
import zerobase.mission.service.RegisterService;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @MockBean
    private RegisterService registerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("매장 검색 성공 - 모든 매장 별점순 정렬")
    void success_search_store_order_by_name() throws Exception {
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
                .thumbsUp(1)
                .build();

        Store pobiStore = Store.builder()
                .name("pobiStore")
                .address(Address.builder()
                        .city("bbo-rong")
                        .street("bbo-rong")
                        .zipcode("234")
                        .build())
                .description("pobi's store")
                .openTime(LocalTime.of(9, 0, 0))
                .closeTime(LocalTime.of(18, 0, 0))
                .thumbsUp(2)
                .build();

        Store eddieStore = Store.builder()
                .name("eddieStore")
                .address(Address.builder()
                        .city("bbo-rong")
                        .street("bbo-rong")
                        .zipcode("345")
                        .build())
                .description("eddie's store")
                .openTime(LocalTime.of(9, 0, 0))
                .closeTime(LocalTime.of(18, 0, 0))
                .thumbsUp(3)
                .build();

        List<Store> storeList = Arrays.asList(eddieStore, pobiStore, crongStore);
        given(registerService.getAllStore()).willReturn(storeList);
        // when
        // then
        mockMvc.perform(get("/store"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("eddieStore"))
                .andExpect(jsonPath("$[0].description").value("eddie's store"));
    }
}