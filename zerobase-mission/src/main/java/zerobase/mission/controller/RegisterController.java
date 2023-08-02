package zerobase.mission.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import zerobase.mission.domain.Store;
import zerobase.mission.dto.RegisterStoreDto;
import zerobase.mission.dto.StoreDto;
import zerobase.mission.service.RegisterService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    @PostMapping("/register/store")
    public RegisterStoreDto.Response registerStore(@RequestBody @Valid RegisterStoreDto.Request request) {

        StoreDto storeDto = registerService.registerStore(Store.builder()
                .name(request.getName())
                .address(request.getAddress())
                .description(request.getDescription())
                .openTime(request.getOpenTime())
                .closeTime(request.getCloseTime())
                .build());

        return RegisterStoreDto.Response.fromDto(storeDto);
    }
}
