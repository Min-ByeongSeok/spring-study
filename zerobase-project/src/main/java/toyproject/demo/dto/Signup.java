package toyproject.demo.dto;

import lombok.*;
import toyproject.demo.type.Role;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class Signup {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request{
        private String name;
        private String userId;
        private String password;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response{
        private String name;
        private Role role;

        public static Response fromDto(MemberDto memberDto) {
            return Response.builder()
                    .name(memberDto.getName())
                    .role(memberDto.getRole())
                    .build();
        }
    }

}
