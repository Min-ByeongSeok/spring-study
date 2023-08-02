package zerobase.mission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.mission.domain.member.Customer;
import zerobase.mission.dto.CustomerDto;
import zerobase.mission.exception.CustomException;
import zerobase.mission.repository.CustomerRepository;

import static zerobase.mission.type.ErrorCode.ALREADY_EXIST_LOGIN_ID;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Transactional
    public CustomerDto signupCustomer(Customer customer) {
        // 지금 회원가입한 customer의 id로 리포지토리에서 데이터를 찾고
        boolean exists = customerRepository.existsByLoginId(customer.getLoginId());

        // 중복된 ID 가 있다면 에러
        if (exists) {
            throw new CustomException(ALREADY_EXIST_LOGIN_ID);
        }

        // 없다면 객체생성
        return CustomerDto.fromEntity(customerRepository.save(customer));
    }
}
