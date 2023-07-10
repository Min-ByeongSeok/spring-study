package zerobase.weather.config;
// config 패키지는 프로젝트 전반적으로 쓰이는 설정파일등을 넣게된다
// 자바클래스 형태의 프로젝트 전체를 위한 설정파일

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 전역예외처리
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    // 모든 exception에 대해 동작시키기 위함
    @ExceptionHandler(Exception.class)
    public Exception handleAllException(){
        System.out.println("error from GlobalExceptionHandler");

        return new Exception();
    }
}
