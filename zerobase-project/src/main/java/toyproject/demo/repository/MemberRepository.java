package toyproject.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toyproject.demo.domain.Member;

import java.util.Optional;

/**
 * 회원 리포지토리
 */
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByUserId(String userId);

    Optional<Member> findByUserId(String userId);
}
