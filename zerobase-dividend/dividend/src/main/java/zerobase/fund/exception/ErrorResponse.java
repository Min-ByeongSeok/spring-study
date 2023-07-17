package zerobase.fund.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Bean;

@Data
@Builder
public class ErrorResponse {
    private int code;
    private String message;
}
