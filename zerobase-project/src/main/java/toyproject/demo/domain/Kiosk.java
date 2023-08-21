package toyproject.demo.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Kiosk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kiosk_id")
    private Long id;

    private LocalDateTime nowDateTime;

    @OneToMany
    @JoinColumn(name = "reservaion_id")
    private List<Reservation> reservation;
}
