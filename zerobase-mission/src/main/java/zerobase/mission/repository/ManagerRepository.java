package zerobase.mission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.mission.domain.Address;
import zerobase.mission.domain.member.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
    boolean existsByLoginId(String loginId);
    boolean existsByNameAndAddress(String name, Address address);
}