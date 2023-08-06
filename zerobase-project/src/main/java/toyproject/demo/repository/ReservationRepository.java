package toyproject.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toyproject.demo.domain.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
