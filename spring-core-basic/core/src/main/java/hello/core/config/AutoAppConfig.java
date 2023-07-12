package hello.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
// @Component이 붙은 클래스를 찾아 자동으로 스프링빈으로 등록한다.
// 범위는 해당 패키지를 기준으로 탐색한다
@ComponentScan(
        // 아래의 항목은 제외한다.
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION,
        classes = Configuration.class)
//        , basePackages = "hello.core"
)
public class AutoAppConfig {

}
