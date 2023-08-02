package zerobase.mission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.mission.domain.Store;
import zerobase.mission.dto.StoreDto;
import zerobase.mission.exception.CustomException;
import zerobase.mission.repository.RegisterStoreRepository;
import zerobase.mission.type.ErrorCode;

@Service
@RequiredArgsConstructor
public class RegisterStoreService {

    private final RegisterStoreRepository registerStoreRepository;

    @Transactional
    public StoreDto registerStore(Store store) {
        boolean isRegistered = registerStoreRepository.existsByNameAndAddress(store.getName(), store.getAddress());

        if(isRegistered){
            throw new CustomException(ErrorCode.ALREADY_REGISTERED_STORE);
        }

        return StoreDto.fromEntity(registerStoreRepository.save(store));
    }
}
