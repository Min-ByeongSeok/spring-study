package zerobase.reservation.exception;

import lombok.*;
import zerobase.reservation.type.ErrorCode;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupException extends RuntimeException {
    private ErrorCode errorCode;
    private String errorMessage;

    public SignupException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
