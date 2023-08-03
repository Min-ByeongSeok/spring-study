package zerobase.mission.domain.member;

import lombok.*;
import zerobase.mission.type.Role;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String loginId;

    @NotBlank
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @NotBlank
    @Size(min = 11, max = 11)
    private String phoneNumber;
}
