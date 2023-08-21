package toyproject.account.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
public class LocalRedisConfig {

    @Value("${spring.redis.port}")
    // yml파일의 spring.redis.port의 경로의 값을 redisPort 변수에 담는다.
    private int redisPort;

    private RedisServer redisServer;

    @PostConstruct
    public void startRedis() {
        redisServer = new RedisServer(redisPort);

        redisServer.start();
        System.out.println("redis start");
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            System.out.println("redis stop");
            redisServer.stop();
        }
    }

}
