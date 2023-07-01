package zerobase.dividend.model;

import lombok.Builder;
import lombok.Data;

// 서비스코드 구현을 위한 모델클래스 정의

// Getter, Setter, RequiredArgsConstructor, ToString, EqualsAndHashCode, Value 포함하는 애노테이션
@Data
// 디자인패턴중 빌더패턴을 사용
@Builder
public class Company {
    // CompanyEntity 클래스를 사용하지않고 모델클래스를 따로 정의한 이유는
    // 엔티티는 DB 테이블과 직접적으로 매핑되기위한 클래스이기때문에 엔티티 인스턴스를 서비스코드 내부에서
    // 데이터를 주고받기 위한 용도로 쓰거나 이 과정에서 데이터테이블을 변경하고 이러한 로직이 들어가게 되면
    // 클래스의 역할을 벗어난 것으로 볼수 있다.
    private String ticker;
    private String name;
}
