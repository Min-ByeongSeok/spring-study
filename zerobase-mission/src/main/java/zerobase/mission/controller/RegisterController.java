package zerobase.mission.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import zerobase.mission.domain.Store;
import zerobase.mission.dto.RegisterStoreDto;
import zerobase.mission.dto.StoreDto;
import zerobase.mission.service.RegisterStoreService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterStoreService registerStoreService;

    @PostMapping("/register/store")
    public RegisterStoreDto.Response registerStore(@RequestBody @Valid RegisterStoreDto.Request request) {

        StoreDto storeDto = registerStoreService.registerStore(Store.builder()
                .name(request.getName())
                .address(request.getAddress())
                .description(request.getDescription())
                .openTime(request.getOpenTime())
                .closeTime(request.getCloseTime())
                .build());

        return RegisterStoreDto.Response.fromDto(storeDto);
    }
}
