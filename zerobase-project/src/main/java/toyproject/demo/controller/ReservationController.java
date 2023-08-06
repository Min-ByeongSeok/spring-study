package toyproject.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;
import toyproject.demo.domain.Member;
import toyproject.demo.domain.Reservation;
import toyproject.demo.domain.Store;
import toyproject.demo.domain.embedded.StoreInfo;
import toyproject.demo.dto.Register;
import toyproject.demo.dto.ReservationDto;
import toyproject.demo.dto.Reserve;
import toyproject.demo.repository.ReservationRepository;
import toyproject.demo.service.MemberService;
import toyproject.demo.service.RegisterService;
import toyproject.demo.service.ReservationService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final MemberService memberService;
    private final RegisterService registerService;
    private final ReservationService reservationService;

    @GetMapping("/store")
    public List<StoreInfo> getAllStore(@PageableDefault(size = 20)
                                          @SortDefault.SortDefaults({
                                                  @SortDefault(sort = "storeInfo.thumbsUp", direction = Sort.Direction.DESC),
                                                  @SortDefault(sort = "storeInfo.name", direction = Sort.Direction.ASC)}) final Pageable pageable) {
        return registerService.getAllStore(pageable);
    }

    @GetMapping("/search/store")
    public List<StoreInfo> searchStore(@RequestParam String name) {
        return registerService.getSearchStore(name);
    }

    @PostMapping("/reserve")
    public Reserve.Response reserve(@RequestBody @Valid Reserve.Request request) {
        Store store = registerService.getStoreByName(request.getStoreName());
        Member customer = memberService.signIn(request.getCustomer());

        ReservationDto reservationDto = reservationService.reserve(Reservation.builder()
                .store(store)
                .customerName(customer.getName())
                .headCount(request.getHeadCount())
                .uuid(UUID.randomUUID().toString().replace("-", ""))
                .build());

        return Reserve.Response.fromDto(reservationDto);
    }
}
