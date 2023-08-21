package zerobase.mission.dto;

import lombok.*;
import zerobase.mission.domain.Address;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

public class RegisterDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        @NotBlank
        private String name;
        @NotNull
        private Address address;
        private String description;
        private LocalTime openTime;
        private LocalTime closeTime;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private String name;
        private boolean isRegistered;

        public static Response fromDto(StoreDto storeDto) {
            return Response.builder()
                    .name(storeDto.getName())
                    .isRegistered(true)
                    .build();
        }
    }
}
