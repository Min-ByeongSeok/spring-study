package hello.core.singleton;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SingletonService {

    // static 영역에 객체를 생성한다.
    private static final SingletonService instance = new SingletonService();

    // 이 메소드로만 객체가 조회가 가능하다
    public static SingletonService getInstance(){
        return instance;
    }

    // 생성자가 private이기 때문에 외부에서 new 키워드를 사용하지 못한다.
    private SingletonService(){

    }

    public void logic(){
        System.out.println("싱글톤 객체 로직 호출");
    }
}
