package zerobase.weather.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import zerobase.weather.domain.Diary;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Integer> {
    // 동작과정을 코딩하지않았지만 jpa가 함수를 기준으로 알아서 쿼리를 생성해서 값을 가져온다.

    // 지정된 날짜를 기준으로 모든 일기 조회
    List<Diary> findAllByDate(LocalDate date);

    // 지정된 날짜 사이의 모든 일기 조회
    List<Diary> findAllByDateBetween(LocalDate start, LocalDate endDate);

    // 지정된 날짜의 일기 수정
    // 일기가 여러개일 수도 있기 때문에 날짜를 기준으로 첫번째 일기만 가져온다.
    Diary getFirstByDate(LocalDate date);

    // 지정된 날짜의 모든 일기 삭제
    @Transactional
    void deleteAllByDate(LocalDate date);
}
