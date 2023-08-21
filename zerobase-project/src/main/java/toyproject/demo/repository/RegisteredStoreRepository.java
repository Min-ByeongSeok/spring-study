package toyproject.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toyproject.demo.domain.Store;

import java.util.Optional;

/**
 * 등록된 매장 리포지토리
 */
@Repository
public interface RegisteredStoreRepository extends JpaRepository<Store, Long> {
    boolean existsByStoreInfoName(String name);

    Page<Store> findAll(Pageable pageable);

    Page<Store> findByStoreInfoNameStartingWithIgnoreCase(String name, Pageable pageable);

    Optional<Store> findByStoreInfoName(String name);
}
