package zerobase.mission.dto;

import lombok.*;
import zerobase.mission.domain.Address;
import zerobase.mission.dto.member.ManagerDto;
import zerobase.mission.type.Role;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SignupManagerDto {
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
        @NotBlank
        private String storeName;

        private Address address;
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
        private String storeName;

        public static Response fromDto(ManagerDto managerDto) {
            return Response.builder()
                    .name(managerDto.getName())
                    .loginId(managerDto.getLoginId())
                    .role(managerDto.getRole())
                    .storeName(managerDto.getStoreName())
                    .build();
        }
    }
}
