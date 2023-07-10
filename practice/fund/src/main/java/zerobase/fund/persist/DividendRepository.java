package zerobase.fund.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import zerobase.fund.persist.entity.DividendEntity;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DividendRepository extends JpaRepository<DividendEntity, Long> {
    // DividendEntity에 있는 companyId와 일치하는 엔티티들을 찾아서 리스트에 담아서 반환
    List<DividendEntity> findAllByCompanyId(Long companyId);

    // companyId와 date를 기준으로 하는 복합 유니크키 설정을 걸어주었기 때문에
    // companyId와 date로 이 레코드를 조회하게 되면 일반 select ~ where 절의 조건으로
    // 데이터를 호출해야할 때보다 훨씬더 빠르게 조회가 가능하다
    // 데이터 특성상 DividendEntity는 Company 하나당 다수의 데이터가 존재할 수 있기때문에
    // Company size에 비해 DividendEntity size가 훨씬 더 크다
    // 인덱스를 걸어주면 db의 성능을 향상시킬 수 있다.
    boolean existsByCompanyIdAndDate(Long companyId, LocalDateTime date);

    @Transactional
    void deleteAllByCompanyId(Long id);
}
