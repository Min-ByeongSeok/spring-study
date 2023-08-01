package zerobase.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.reservation.domain.member.Customer;
import zerobase.reservation.domain.member.Member;


import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByUserId(String userId);

    Optional<Member> findByUserId(String userId);
}