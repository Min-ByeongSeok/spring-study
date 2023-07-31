package zerobase.reservation.dto;

import lombok.*;
import zerobase.reservation.domain.Member;
import zerobase.reservation.type.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {
    private String userId;
    private String name;
    private Role role;

    public static MemberDto fromEntity(Member member) {
        return MemberDto.builder()
                .userId(member.getUserId())
                .name(member.getName())
                .role(member.getRole())
                .build();
    }
}
