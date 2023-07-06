package zerobase.fund.exception.impl;

import org.springframework.http.HttpStatus;
import zerobase.fund.exception.AbstractException;

import java.io.IOException;

public class NoExistTickerException extends AbstractException {

    @Override
    public int getStatusCode() {
        // 400
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "존재하지 않는 회사 ticker 입니다.";
    }
}
