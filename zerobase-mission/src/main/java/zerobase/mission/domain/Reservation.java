package zerobase.mission.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import zerobase.mission.type.Availability;

import javax.persistence.*;
import java.time.LocalDate;

import static zerobase.mission.type.Availability.AVAILABLE;


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

    @OneToOne(mappedBy = "reservation", fetch = FetchType.LAZY)
    private Store store;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private Availability availability;
}
