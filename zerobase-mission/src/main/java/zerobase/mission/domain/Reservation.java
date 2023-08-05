package zerobase.mission.domain;

import lombok.*;
import zerobase.mission.type.Availability;

import javax.persistence.*;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "reservation")
    private Store store;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private Availability availability;
}
