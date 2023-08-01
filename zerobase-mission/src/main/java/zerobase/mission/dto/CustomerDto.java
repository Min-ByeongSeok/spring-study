package zerobase.mission.dto;

import lombok.*;
import zerobase.mission.domain.member.Customer;
import zerobase.mission.type.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {
    private String userId;
    private String name;
    private Role role;

    public static CustomerDto fromEntity(Customer customer) {
        return CustomerDto.builder()
                .userId(customer.getUserId())
                .name(customer.getName())
                .role(Role.CUSTOMER)
                .build();
    }
}
