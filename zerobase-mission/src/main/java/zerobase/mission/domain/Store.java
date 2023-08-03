package zerobase.mission.domain;

import lombok.*;
import zerobase.mission.domain.member.Manager;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @NotBlank
    private String name;

    @Embedded
    private Address address;

    private String description;

    private LocalTime openTime;
    private LocalTime closeTime;
}
