package zerobase.fund.model;

import lombok.Builder;
import lombok.Data;

/**
 * 이미 구현해놓은 companyEntity를 사용하지 않고
 * model 클래스를 따로 정의해주는 이유는
 * Entity는 데이터베이스와 직접적으로 매핑되기 위한 클래스이기때문에
 * Entity 인스턴스를 서비스코드내부에서 데이터를 주고받기 위한 용도로 쓰거나
 * 이 과정에서 데이터 내용을 변경하는 로직이 들어가게 되면
 * 클래스의 원래의 역할을 벗어나는 걸로 볼수 있다.
 * 코드의 재사용성과는 헷갈리면 안되는게 역할을 벗어난 한 코드가 감당하게
 * 되면 좋은 신호가 아니다.
 *
 * 코드의 재사용은 여러 로직의 동작을 쪼개서 쪼갠 동작간의 유사성을 가진 부분,
 * 비슷한 역할을 가지는 부분을 찾고 그 역할을 일반화해서 코드를 재사용할 수 있도록
 * 코드의 중복을 없애주는 것.
 * */
// getter, setter 등 다양한 애노테이션을 포함된 애노테이션
@Data
// 디자인 패턴 중 빌더패턴을 사용하는 애노테이션
@Builder
public class Company {
    private String ticker;
    private String name;
}
