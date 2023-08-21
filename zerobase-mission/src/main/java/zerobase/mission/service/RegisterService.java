package zerobase.mission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.mission.domain.Address;
import zerobase.mission.domain.Store;
import zerobase.mission.dto.ReservationDto;
import zerobase.mission.dto.StoreDto;
import zerobase.mission.dto.StoreListDto;
import zerobase.mission.exception.CustomException;
import zerobase.mission.repository.RegisterRepository;
import zerobase.mission.type.ErrorCode;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final RegisterRepository registerRepository;

    @Transactional
    public StoreDto register(Store store) {
        boolean isRegistered = registerRepository.existsByNameAndAddress(store.getName(), store.getAddress());

        if (isRegistered) {
            throw new CustomException(ErrorCode.ALREADY_REGISTERED_STORE);
        }

        return StoreDto.fromEntity(registerRepository.save(store));
    }

    @Transactional(readOnly = true)
    public Store findRegisteredStore(String name, Address address) {
        return registerRepository.findByNameAndAddress(name, address)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_REGISTERED_STORE));
    }

    @Transactional(readOnly = true)
    public List<Store> getAllStore(){
        return registerRepository.findAll(Sort.by(
                Sort.Order.desc("thumbsUp"),
                Sort.Order.asc("name")));
    }
}
