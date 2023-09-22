package shop.zip.travel.domain.member.service;

import static shop.zip.travel.domain.member.dto.request.MemberRegisterReq.toMember;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.zip.travel.domain.member.dto.request.MemberLoginReq;
import shop.zip.travel.domain.member.dto.request.MemberRegisterReq;
import shop.zip.travel.domain.member.dto.response.MemberLoginResponse;
import shop.zip.travel.domain.member.entity.Member;
import shop.zip.travel.domain.member.exception.MemberNotFoundException;
import shop.zip.travel.domain.member.exception.PasswordNotMatchException;
import shop.zip.travel.domain.member.repository.MemberRepository;
import shop.zip.travel.global.error.ErrorCode;
import shop.zip.travel.global.security.JwtTokenProvider;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;
  private final JwtTokenProvider jwtTokenProvider;
  private final PasswordEncoder passwordEncoder;

  @Transactional(readOnly = true)
  public boolean checkNicknameDuplication(String nickname) {
    return memberRepository.existsByNickname(nickname);
  }

  @Transactional
  public Long createMember(MemberRegisterReq memberRegisterReq) {
    Member member = toMember(memberRegisterReq, passwordEncoder);
    Member savedMember = memberRepository.save(member);
    return savedMember.getId();
  }

  @Transactional
  public MemberLoginResponse login(MemberLoginReq memberLoginReq) {
    Member member = findMemberByEmail(memberLoginReq.email());

    if (!passwordEncoder.matches(memberLoginReq.password(), member.getPassword())) {
      throw new PasswordNotMatchException(ErrorCode.PASSWORD_NOT_MATCH);
    }

    String accessToken = jwtTokenProvider.createAccessToken(member.getId());

    return new MemberLoginResponse(accessToken);
  }

  public Member getMember(Long id) {
    return memberRepository.findById(id)
        .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
  }

  private Member findMemberByEmail(String email) {
    return memberRepository.findByEmail(email)
        .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
  }
}

