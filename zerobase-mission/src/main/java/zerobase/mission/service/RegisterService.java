package zerobase.mission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.mission.domain.Store;
import zerobase.mission.dto.StoreDto;
import zerobase.mission.exception.CustomException;
import zerobase.mission.repository.RegisteredStoreRepository;
import zerobase.mission.type.ErrorCode;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final RegisteredStoreRepository registeredStoreRepository;

    @Transactional
    public StoreDto registerStore(Store store) {
        boolean isRegistered = registeredStoreRepository.existsByNameAndAddress(store.getName(), store.getAddress());

        if(isRegistered){
            throw new CustomException(ErrorCode.ALREADY_REGISTERED_STORE);
        }

        return StoreDto.fromEntity(registeredStoreRepository.save(store));
    }
}
