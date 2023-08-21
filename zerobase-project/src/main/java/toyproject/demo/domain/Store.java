package toyproject.demo.domain;

import lombok.*;
import toyproject.demo.domain.embedded.StoreInfo;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "store")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @Embedded
    private StoreInfo storeInfo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member manager;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kiost_id")
    private Kiosk kiosk;
}
