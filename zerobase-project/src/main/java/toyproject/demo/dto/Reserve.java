package toyproject.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import toyproject.demo.domain.Member;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Reserve {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        private String storeName;
        private Member customer;
        @Min(1)
        private int headCount;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime dateTime;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private String message;

        public static Response fromDto(ReservationDto reservationDto) {
            return Response.builder()
                    .message(
                            String.format("%s님 %s %s %d명 예약되었습니다. 예약번호는 %s 입니다",
                                    reservationDto.getCustomerName(),
                                    reservationDto.getDateTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)),
                                    reservationDto.getStore().getStoreInfo().getName(),
                                    reservationDto.getHeadCount(),
                                    reservationDto.getUuid()))
                    .build();
        }
    }
}
