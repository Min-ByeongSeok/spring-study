package zerobase.mission.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import zerobase.mission.domain.Store;
import zerobase.mission.dto.RegisterDto;
import zerobase.mission.dto.StoreDto;
import zerobase.mission.dto.StoreListDto;
import zerobase.mission.service.RegisterService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    @PostMapping("/register/store")
    public RegisterDto.Response registerStore(@RequestBody @Valid RegisterDto.Request request) {

        StoreDto storeDto = registerService.register(Store.builder()
                .name(request.getName())
                .address(request.getAddress())
                .description(request.getDescription())
                .openTime(request.getOpenTime())
                .closeTime(request.getCloseTime())
                .thumbsUp(5)
                .build());

        return RegisterDto.Response.fromDto(storeDto);
    }

    @GetMapping("/store")
    public List<StoreListDto.Response> findAllStore() {

        List<Store> stores = registerService.getAllStore();

        return stores.stream().map(StoreListDto.Response::fromEntity).collect(Collectors.toList());
    }

}
