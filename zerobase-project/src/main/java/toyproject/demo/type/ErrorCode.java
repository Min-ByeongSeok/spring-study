package toyproject.demo.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    ALREADY_EXIST_LOGIN_ID("중복된 ID입니다."),
    NOT_A_MANAGER("점장이 아닙니다."),
    ALREADY_REGISTERED_STORE("이미 등록된 매장입니다."),
    NOT_FOUND_STORE("등록된 매장이 없습니다."),
    NOT_EXIST_LOGIN_ID("존재하지 않는 ID입니다."),
    NOT_REGISTERED_STORE("등록된 매장이 아닙니다."),
    WRONG_PASSWORD("비밀번호가 틀렸습니다.");

    private final String description;
}
