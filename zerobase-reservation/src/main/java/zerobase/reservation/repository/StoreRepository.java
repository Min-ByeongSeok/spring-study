package zerobase.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.reservation.domain.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
}
