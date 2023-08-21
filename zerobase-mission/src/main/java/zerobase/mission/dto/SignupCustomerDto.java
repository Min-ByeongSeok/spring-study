package zerobase.mission.dto;

import lombok.*;
import zerobase.mission.dto.member.CustomerDto;
import zerobase.mission.type.Role;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignupCustomerDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        @NotBlank
        private String name;
        @NotBlank
        private String loginId;
        @NotBlank
        private String password;

        @Size(min = 11, max = 11)
        private String mobileNumber;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private String name;
        private String loginId;
        private Role role;

        public static Response fromDto(CustomerDto customerDto) {
            return Response.builder()
                    .name(customerDto.getName())
                    .loginId(customerDto.getLoginId())
                    .role(customerDto.getRole())
                    .build();
        }
    }
}
