package zerobase.mission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.mission.domain.Store;
import zerobase.mission.exception.CustomException;
import zerobase.mission.repository.RegisterRepository;
import zerobase.mission.type.ErrorCode;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final RegisterRepository registerRepository;

    @Transactional(readOnly = true)
    public Store checkReservationAvailability(String name) {
        return registerRepository.findByName(name)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_STORE));
    }

}
