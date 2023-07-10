package zerobase.weather.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.weather.domain.Memo;

@Repository
public interface JpaMemoRepository extends JpaRepository<Memo, Integer> {
    // 기본적인 save, findById 등과 같은 메소드는 이미
    // JpaRepository 클래스안에 다 구현되어있다.
}
