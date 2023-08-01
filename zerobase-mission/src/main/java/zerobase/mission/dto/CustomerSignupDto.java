package zerobase.mission.dto;

import lombok.*;
import zerobase.mission.type.Role;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CustomerSignupDto {
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
        @NotBlank
        private String name;

        @Size(min = 11, max = 11)
        private String mobileNumber;
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
