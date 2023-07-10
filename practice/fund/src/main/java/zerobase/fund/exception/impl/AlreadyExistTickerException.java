package zerobase.fund.exception.impl;

import org.springframework.http.HttpStatus;
import zerobase.fund.exception.AbstractException;

public class AlreadyExistTickerException extends AbstractException {
    @Override
    public int getStatusCode() {
        // 400
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "이미 보유하고 있는 회사의 정보 및 ticker 입니다.";
    }
}
