package zerobase.mission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zerobase.mission.domain.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
