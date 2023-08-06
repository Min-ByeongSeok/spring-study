package toyproject.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.demo.domain.Member;
import toyproject.demo.dto.MemberDto;
import toyproject.demo.exception.CustomException;
import toyproject.demo.repository.MemberRepository;
import toyproject.demo.type.ErrorCode;
import toyproject.demo.type.Role;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;


    public MemberDto signUp(Member member) {
        boolean isExistsUserId = memberRepository.existsByUserId(member.getUserId());

        if (isExistsUserId) {
            throw new CustomException(ErrorCode.ALREADY_EXIST_LOGIN_ID);
        }

        return MemberDto.fromEntity(memberRepository.save(member));
    }

    public Member findByManagerUserId(String userId) {
        Member manager = memberRepository.findByUserId(userId).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_EXIST_LOGIN_ID));

        if (manager.getRole() != Role.MANAGER) {
            throw new CustomException(ErrorCode.NOT_A_MANAGER);
        }

        return manager;
    }

    public Member signIn(Member customer) {
        boolean isExistsUserId = memberRepository.existsByUserId(customer.getUserId());

        if (!isExistsUserId) {
            throw new CustomException(ErrorCode.NOT_EXIST_LOGIN_ID);
        }

        return memberRepository.findByUserId(customer.getUserId())
                .filter(m -> m.getPassword().equals(customer.getPassword()))
                .orElseThrow(() -> new CustomException(ErrorCode.WRONG_PASSWORD));
    }
}
