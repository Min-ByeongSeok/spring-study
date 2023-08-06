package toyproject.demo.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.Trie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.demo.domain.Store;
import toyproject.demo.domain.embedded.StoreInfo;
import toyproject.demo.exception.CustomException;
import toyproject.demo.repository.RegisteredStoreRepository;
import toyproject.demo.type.ErrorCode;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RegisterService {

    private final Trie trie;
    private final RegisteredStoreRepository registeredStoreRepository;

    public void register(Store store) {
        boolean isExist = registeredStoreRepository.existsByStoreInfoName(store.getStoreInfo().getName());

        if (isExist) {
            throw new CustomException(ErrorCode.ALREADY_REGISTERED_STORE);
        }

        registeredStoreRepository.save(store);
    }

    public List<StoreInfo> getAllStore(Pageable pageable) {
        return registeredStoreRepository.findAll(pageable).stream().map(Store::getStoreInfo).collect(Collectors.toList());
    }

    public List<StoreInfo> getSearchStore(String name) {
        Page<Store> findRegisterForm
                = registeredStoreRepository.findByStoreInfoNameStartingWithIgnoreCase(name, PageRequest.of(0, 10));

        return findRegisterForm.stream().map(Store::getStoreInfo).collect(Collectors.toList());
    }

    public void addAutocompleteKeyword(String keyword) {
        trie.put(keyword, null);
    }

    public Store getStoreByName(String name) {
        return registeredStoreRepository.findByStoreInfoName(name).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_STORE)
        );
    }
}
