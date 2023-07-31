package zerobase.reservation.domain;

import lombok.*;
import zerobase.reservation.type.Role;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Member {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String userId;
    @NotBlank
    private String password;
    @NotBlank
    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;
}
