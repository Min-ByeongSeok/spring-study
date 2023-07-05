package zerobase.fund.config;

import org.apache.commons.collections4.Trie;
import org.apache.commons.collections4.trie.PatriciaTrie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    // 트라이는 서비스내에서 하나만 유지되어야하고 코드의 일관성을 유지하기 위해서
    // bean으로 트라이를 관리한다.
    @Bean
    public Trie<String, String> trie(){
        return new PatriciaTrie<>();
    }
}
