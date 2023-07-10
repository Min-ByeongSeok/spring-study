package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        // basepackages를 지정하지 않으면 default는 ComponentScan을 선언한 클래스를 포함한
        // 패키지부터 시작한다.
        // 패키지 위치를 지정하지 않고 설정정보 클래스의 위치를 프로젝트 최상단에 두는 것이다.
        // 최근 스프링 부트도 이 방법을 기본으로 제공한다.
        basePackages = "hello.core",
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ANNOTATION,
                classes = Configuration.class)
)
public class AutoAppConfig {

}
