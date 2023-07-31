package zerobase.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.reservation.domain.Member;
import zerobase.reservation.dto.MemberDto;
import zerobase.reservation.exception.SignupException;
import zerobase.reservation.repository.MemberRepository;
import zerobase.reservation.type.ErrorCode;

@Service
@RequiredArgsConstructor
public class MemberService {
    private MemberRepository memberRepository;

    @Transactional
    public MemberDto signup(Member member) {
        Member newMember = memberRepository.findByUserId(member.getUserId()).orElseThrow(
                () -> new SignupException(ErrorCode.ALREADY_EXIST_USER_ID));

        return MemberDto.fromEntity(memberRepository.save(newMember));
    }
}
