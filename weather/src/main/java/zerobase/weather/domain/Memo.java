package zerobase.weather.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="memo")
public class Memo {
    // Entity를 지정해주기위해 pk 명시
    @Id
    // 어떤 전략(identity: 기본적인 키 생성을 데이터베이스에게 맡긴다)으로
    // 값을 자동적으로 생성한다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 식별만의 목적을 가진 id로 사용할 때는
    // @GeneratedValue를 사용하면 좋을 수 있다.
    private int id;
    private String text;
}
