package zerobase.mission.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    ALREADY_EXIST_LOGIN_ID("중복된 ID입니다."),
    ALREADY_REGISTERED_STORE("이미 등록된 매장입니다."),
    NOT_EXIST_LOGIN_ID("존재하지 않는 ID입니다."),
    WRONG_PASSWORD("비밀번호가 틀렸습니다.");

    private final String description;
}
