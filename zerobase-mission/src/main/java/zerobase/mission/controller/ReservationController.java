package zerobase.mission.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import zerobase.mission.domain.Store;

import zerobase.mission.dto.ReservationDto;
import zerobase.mission.dto.StoreListDto;
import zerobase.mission.service.RegisterService;
import zerobase.mission.service.ReservationService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/store/{name}")
    public ReservationDto.Response getReservationAvailability(@PathVariable String name){
        Store store = reservationService.checkReservationAvailability(name);

        return ReservationDto.Response.fromEntity(store);
    }
}
