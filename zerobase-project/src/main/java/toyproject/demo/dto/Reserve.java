package toyproject.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import toyproject.demo.domain.Member;
import toyproject.demo.domain.embedded.StoreInfo;

import javax.validation.constraints.Min;

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
                    .message(String.format("%s님 %s %d명 예약되셨습니다.",
                            reservationDto.getCustomerName(),
                            reservationDto.getStore().getStoreInfo().getName(),
                            reservationDto.getHeadCount()) +
                            String.format("예약번호는 %s입니다.",
                            reservationDto.getUuid()))
                    .build();
        }
    }
}
