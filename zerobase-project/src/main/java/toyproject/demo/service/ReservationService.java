package toyproject.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.demo.domain.Reservation;
import toyproject.demo.dto.ReservationDto;
import toyproject.demo.exception.CustomException;
import toyproject.demo.repository.RegisteredStoreRepository;
import toyproject.demo.repository.ReservationRepository;
import toyproject.demo.type.ErrorCode;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final RegisteredStoreRepository registeredStoreRepository;

    public ReservationDto reserve(Reservation reservation) {
        boolean isExist
                = registeredStoreRepository.existsByStoreInfoName(reservation.getStore().getStoreInfo().getName());

        if (!isExist) {
            throw new CustomException(ErrorCode.NOT_REGISTERED_STORE);
        }

        return ReservationDto.fromEntity(reservationRepository.save(reservation));
    }
}
