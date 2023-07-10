package zerobase.fund.exception.impl;

import org.springframework.http.HttpStatus;
import zerobase.fund.exception.AbstractException;

public class NotMatchPasswordException extends AbstractException {
    @Override
    public int getStatusCode() {
        // 400
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "비밀번호가 일치하지 않습니다.";
    }
}
