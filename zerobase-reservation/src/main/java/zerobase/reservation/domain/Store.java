package zerobase.reservation.domain;


import lombok.*;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Store {
    @Id
    @GeneratedValue
    private Long id; // 매장 고유 id

    private Long StoreName; // 매장 이름

    @Embedded
    private Address address; // 매장 위치

}
