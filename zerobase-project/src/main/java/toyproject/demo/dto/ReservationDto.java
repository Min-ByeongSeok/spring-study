package toyproject.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import toyproject.demo.domain.Member;
import toyproject.demo.domain.Reservation;
import toyproject.demo.domain.Store;
import toyproject.demo.type.Role;

import java.time.LocalDateTime;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime;

    public static ReservationDto fromEntity(Reservation reservation) {
        return ReservationDto.builder()
                .store(reservation.getStore())
                .customerName(reservation.getCustomerName())
                .headCount(reservation.getHeadCount())
                .uuid(reservation.getUuid())
                .dateTime(reservation.getDateTime())
                .build();
    }

}
