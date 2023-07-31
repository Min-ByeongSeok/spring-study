package zerobase.reservation.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    ALREADY_EXIST_USER_ID("중복된 ID입니다.");

    private final String description;
}
