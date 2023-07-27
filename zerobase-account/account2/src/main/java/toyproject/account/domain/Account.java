package toyproject.account.domain;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity // 설정클래스라고 보면 된다.
public class Account {

    @Id // Account의 pk로 지정
    @GeneratedValue
    private Long id;

    private String accountNumber;

    @Enumerated(EnumType.STRING) // 열거형타입을 문자그대로 저장한다.
    private AccountStatus accountStatus;
}


