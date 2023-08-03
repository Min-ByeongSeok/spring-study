package zerobase.mission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.mission.domain.Address;
import zerobase.mission.domain.Store;

import java.util.Optional;

@Repository
public interface RegisterStoreRepository extends JpaRepository<Store, Long> {
    boolean existsByNameAndAddress(String name, Address address);

    Optional<Store> findByNameAndAddress(String name, Address address);
}