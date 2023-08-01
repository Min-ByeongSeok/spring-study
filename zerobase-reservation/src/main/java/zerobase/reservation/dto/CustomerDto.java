package zerobase.reservation.dto;

import lombok.*;
import zerobase.reservation.domain.member.Customer;
import zerobase.reservation.domain.member.Member;

import zerobase.reservation.type.Role;

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
                .role(customer.getRole())
                .build();
    }
}
