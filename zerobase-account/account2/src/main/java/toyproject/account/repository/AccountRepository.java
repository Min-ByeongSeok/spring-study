package toyproject.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toyproject.account.domain.Account;

import java.util.Optional;

@Repository
// Account Entity를 DB에 저장해주기위해서는 JPA에서 제공해주는 리포지토리가 필요하다
// Account라는 테이블에 접속하기 위한 인터페이스
// JpaRepository<Account, Long>에서 첫번째 타입은 이 리포지토리가 활용하게될 클래스, 두번째타입은 그 활용하게될 클래스의 pk값
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findFirstByOrderByIdDesc(); // 역순으로 조회한 1번째값을 가져옴
}
