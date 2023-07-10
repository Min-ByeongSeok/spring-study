package zerobase.weather.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import zerobase.weather.domain.Memo;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
// 해당 클래스가 리포지토리라는걸 알려줌
public class JdbcMemoRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcMemoRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        // Datasource 객체는 application.properties에서
        // spring.datasource.~~ 에서 정보를 담았는데 이 정보들이
        // datasource라는 객체에 담기게 된다.
    }

    public Memo save(Memo memo){
        String sql = "insert into memo values(?,?);";
        jdbcTemplate.update(sql, memo.getId(), memo.getText());

        return memo;
    }

    public List<Memo> findAll(){
        String sql = "select * from memo;";

        // 조회할때는 jdbcTemplate의 query메소드를 주로 쓴다
        // query는 반환을 해온 데이터값을 어떻게 반환을 할건지를 명시해줘야 한다.
        // ResultSet형식의 데이터를 memoRowMapper를 이용해서 Memo객체로 가져온다.
        return jdbcTemplate.query(sql, memoRowMapper());
    }

    public Optional<Memo> findById(int id){
        String sql = "select * from memo where id = ?;";

        return jdbcTemplate.query(sql, memoRowMapper(), id).stream().findFirst();
    }



    private RowMapper<Memo> memoRowMapper(){
        // jdbc를 통해서 mysql 데이터베이스에서 데이터를 가져오면 가져온 값은
        // ResultSet 형식({id=1, text='this is memo~'})의 데이터를 가져오는데
        // ResultSet을 Memo클래스 형식으로 대입시켜야하기때문에
        // ResultSet을 Memo라는 형식으로 매핑해주는걸 RowMapper한다.

        return (rs, rowNum) -> new Memo(
                rs.getInt("id"),
                rs.getString("text")
        );
    }
}
