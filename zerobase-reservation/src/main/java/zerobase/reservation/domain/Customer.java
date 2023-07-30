package zerobase.reservation.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Customer {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany
    private Reservation reservation;


}
