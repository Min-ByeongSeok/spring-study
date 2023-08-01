package zerobase.reservation.dto;

import lombok.*;
import zerobase.reservation.type.Role;

import javax.validation.constraints.NotBlank;

public class Signin {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        @NotBlank
        private String userId;
        @NotBlank
        private String password;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private String userId;
        private String name;
        private Role role;

        public static Response fromDto(CustomerDto customerDto) {
            return Response.builder()
                    .name(customerDto.getName())
                    .userId(customerDto.getUserId())
                    .role(customerDto.getRole())
                    .build();
        }
    }
}
