package zerobase.mission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.mission.domain.Store;
import zerobase.mission.repository.RegisterStoreRepository;
import zerobase.mission.repository.ReservationRepository;

@Service
@RequiredArgsConstructor
public class ReservationService {
//    private final ReservationRepository reservationRepository;
    private final RegisterStoreRepository registerStoreRepository;

    @Transactional(readOnly = true)
    public Page<Store> getAllStore(final Pageable pageable) {
        return registerStoreRepository.findAll(pageable);
    }
}
