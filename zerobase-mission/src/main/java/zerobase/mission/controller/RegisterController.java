package zerobase.mission.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import zerobase.mission.domain.Store;
import zerobase.mission.dto.RegisterStoreDto;
import zerobase.mission.dto.StoreDto;
import zerobase.mission.service.RegisterStoreService;
import zerobase.mission.service.ReservationService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterStoreService registerStoreService;
    private final ReservationService reservationService;

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

    @GetMapping("/storelist")
    public ResponseEntity<?> getStoreList(final Pageable pageable) {
//    public List<StoreListDto.Response> getStoreList(@RequestBody @Valid StoreListDto.Request request) {
        Page<Store> allStore = reservationService.getAllStore(pageable);

        return ResponseEntity.ok(allStore);
    }
}
