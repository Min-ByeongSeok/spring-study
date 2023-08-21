package zerobase.mission.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.mission.domain.Address;
import zerobase.mission.domain.Store;
import zerobase.mission.dto.ReservationDto;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegisterRepository extends JpaRepository<Store, Long> {
    boolean existsByNameAndAddress(String name, Address address);

    Optional<Store> findByNameAndAddress(String name, Address address);

    Page<Store> findAll(Pageable pageable);

    Page<Store> findAllByOrderByName(Pageable pageable);

    Optional<Store> findByName(String name);
}