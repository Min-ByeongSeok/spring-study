package zerobase.weather;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import zerobase.weather.domain.Memo;
import zerobase.weather.repository.JdbcMemoRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
// 이 객체가 테스트임을 명시함
@Transactional
public class JdbcMemoRepositoryTest {

    @Autowired
    JdbcMemoRepository jdbcMemoRepository;

    @Test
    void insertMemoTest(){

        //given
        // ~ 가 주어지고
        Memo newMemo = new Memo(2, "insertMemoTest");

        //when
        // ~ 을 할 때
        jdbcMemoRepository.save(newMemo);

        //then
        // ~ 이럴 것이다.
        Optional<Memo> result = jdbcMemoRepository.findById(2);
        assertEquals(result.get().getText(), "insertMemoTest");
    }

    @Test
    void findAllMemoTest(){
        //given
        List<Memo> memoList = jdbcMemoRepository.findAll();
        //when
        System.out.println(memoList);
        //then
        assertNotNull(memoList);
    }
}

