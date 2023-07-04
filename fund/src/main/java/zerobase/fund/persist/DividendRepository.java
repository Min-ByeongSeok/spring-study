package zerobase.fund.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.fund.persist.entity.DividendEntity;

import java.util.List;

@Repository
public interface DividendRepository extends JpaRepository<DividendEntity, Long> {
    // DividendEntity에 있는 companyId와 일치하는 엔티티들을 찾아서 리스트에 담아서 반환
    List<DividendEntity> findAllByCompanyId(Long companyId);
}
