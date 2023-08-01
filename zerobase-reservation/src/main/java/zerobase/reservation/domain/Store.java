package zerobase.reservation.domain;

import lombok.*;
import zerobase.reservation.domain.member.Member;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Store {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String name;

    @Embedded
    private Address address;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member manager;

    private String description;

    private LocalDateTime openTime;
    private LocalDateTime closeTime;

    @OneToMany
    private List<Reservation> reservation;
}
