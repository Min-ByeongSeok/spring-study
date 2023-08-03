package zerobase.mission.dto.member;

import lombok.*;
import zerobase.mission.domain.member.Manager;
import zerobase.mission.type.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManagerDto {
    private String loginId;
    private String name;
    private Role role;
    private String storeName;

    public static ManagerDto fromEntity(Manager manager) {
        return ManagerDto.builder()
                .loginId(manager.getLoginId())
                .name(manager.getName())
                .role(Role.MANAGER)
                .storeName(manager.getStore().getName())
                .build();
    }
}
