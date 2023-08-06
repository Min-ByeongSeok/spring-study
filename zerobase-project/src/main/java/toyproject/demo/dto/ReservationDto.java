package toyproject.demo.dto;

import lombok.*;
import toyproject.demo.domain.Member;
import toyproject.demo.domain.Reservation;
import toyproject.demo.domain.Store;
import toyproject.demo.type.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDto {
    private Store store;
    private String customerName;
    private int headCount;
    private String uuid;

    public static ReservationDto fromEntity(Reservation reservation) {
        return ReservationDto.builder()
                .store(reservation.getStore())
                .customerName(reservation.getCustomerName())
                .headCount(reservation.getHeadCount())
                .uuid(reservation.getUuid())
                .build();
    }
}
