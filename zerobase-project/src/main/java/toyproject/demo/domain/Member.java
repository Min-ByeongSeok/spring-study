package toyproject.demo.domain;

import lombok.*;
import toyproject.demo.type.Role;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
