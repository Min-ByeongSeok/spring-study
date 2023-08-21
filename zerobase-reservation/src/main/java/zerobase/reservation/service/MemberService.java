package zerobase.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.reservation.domain.member.Customer;
import zerobase.reservation.dto.CustomerDto;
import zerobase.reservation.exception.MemberException;
import zerobase.reservation.repository.CustomerRepository;
import zerobase.reservation.domain.member.Member;

import static zerobase.reservation.type.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final CustomerRepository customerRepository;

    @Transactional
    public CustomerDto CustomerSignup(Customer customer) {
        // 지금 회원가입한 member의 id로 리포지토리에서 데이터를 찾고
        boolean exists = customerRepository.existsByUserId(customer.getUserId());

        // 중복된 ID 가 있다면 에러
        if (exists) {
            throw new MemberException(ALREADY_EXIST_USER_ID);
        }

        // 없다면 객체생성
        return CustomerDto.fromEntity(customerRepository.save(customer));
    }

    @Transactional
    public CustomerDto signin(String userId, String password) {
        boolean exists = customerRepository.existsByUserId(userId);

        if (!exists) {
            throw new MemberException(NOT_EXIST_USER_ID);
        }

        Member loginMember = customerRepository.findByUserId(userId)
                .filter(member -> member.getPassword().equals(password))
                .orElseThrow(() -> new MemberException(WRONG_PASSWORD));

        return CustomerDto.fromEntity(loginMember);
    }
}
