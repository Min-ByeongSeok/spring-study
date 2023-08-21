package zerobase.reservation.domain.member;

import lombok.*;
import zerobase.reservation.type.Role;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@DiscriminatorColumn
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String userId;

    @NotBlank
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
