package zerobase.mission.dto;

import lombok.*;
import zerobase.mission.domain.Store;
import zerobase.mission.type.Availability;

public class ReservationDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response{
        private String name;
        private Availability availability;

        public static Response fromEntity(Store store) {
            return Response.builder()
                    .name(store.getName())
                    .availability(store.getReservation().getAvailability())
                    .build();
        }
    }
}
