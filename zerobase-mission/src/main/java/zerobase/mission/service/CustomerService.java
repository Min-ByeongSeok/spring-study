package zerobase.mission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.mission.domain.member.Customer;
import zerobase.mission.dto.member.CustomerDto;
import zerobase.mission.exception.CustomException;
import zerobase.mission.repository.CustomerRepository;

import static zerobase.mission.type.ErrorCode.ALREADY_EXIST_LOGIN_ID;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Transactional
    public CustomerDto signupCustomer(Customer customer) {

        boolean exists = customerRepository.existsByLoginId(customer.getLoginId());

        if (exists) {
            throw new CustomException(ALREADY_EXIST_LOGIN_ID);
        }

        return CustomerDto.fromEntity(customerRepository.save(customer));
    }
}
