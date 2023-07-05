package zerobase.fund.persist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.fund.persist.entity.CompanyEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
    boolean existsByTicker(String ticker);

    // Optional로 감싼이유는
    // 1. nullPointException 방지
    // 2. 값이 없는 경우에 대한 처리도 코드적으로 조금더 깔끔하고 정리됨
    Optional<CompanyEntity> findByName(String name);

    Optional<CompanyEntity> findByTicker(String ticker);

    // like 쿼리문을 통한 자동완성 구현
    Page<CompanyEntity> findByNameStartingWithIgnoreCase(String s, Pageable pageable);
}
