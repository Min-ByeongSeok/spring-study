package zerobase.reservation.domain.member;

import lombok.*;
import zerobase.reservation.type.Role;

import javax.persistence.*;
import javax.validation.constraints.Min;
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
    @GeneratedValue
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String userId;

    @NotBlank
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Size(min = 11, max = 11)
    private String phoneNumber;
}
