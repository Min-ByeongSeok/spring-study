package zerobase.mission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.mission.domain.member.Manager;
import zerobase.mission.dto.member.ManagerDto;
import zerobase.mission.exception.CustomException;
import zerobase.mission.repository.ManagerRepository;
import zerobase.mission.repository.RegisterStoreRepository;

import static zerobase.mission.type.ErrorCode.ALREADY_EXIST_LOGIN_ID;
import static zerobase.mission.type.ErrorCode.NOT_REGISTERED_STORE;

@Service
@RequiredArgsConstructor
public class ManagerService {
    private final ManagerRepository managerRepository;
    private final RegisterStoreRepository registerStoreRepository;

    @Transactional
    public ManagerDto signupManager(Manager manager) {
        boolean isExistLoginId
                = managerRepository.existsByLoginId(manager.getLoginId());
        boolean isExistRegisteredStore
                = registerStoreRepository.existsByNameAndAddress(manager.getName(), manager.getStore().getAddress());

        if (isExistLoginId) {
            throw new CustomException(ALREADY_EXIST_LOGIN_ID);
        }
        if (!isExistRegisteredStore) {
            throw new CustomException(NOT_REGISTERED_STORE);
        }

        return ManagerDto.fromEntity(managerRepository.save(manager));
    }
}
