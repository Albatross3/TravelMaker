package shop.zip.travel.presentation.member;

import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.zip.travel.domain.member.dto.request.AccessTokenReissueReq;
import shop.zip.travel.domain.member.dto.request.MemberLoginReq;
import shop.zip.travel.domain.member.dto.request.MemberRegisterReq;
import shop.zip.travel.domain.member.dto.response.DuplicatedNicknameCheckRes;
import shop.zip.travel.domain.member.dto.response.MemberLoginRes;
import shop.zip.travel.domain.member.service.MemberService;
import shop.zip.travel.global.security.UserPrincipal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

  private final MemberService memberService;

  @PostMapping("/check/{nickname}")
  public ResponseEntity<DuplicatedNicknameCheckRes> checkNicknameDuplication(
      @PathVariable("nickname") String nickname
  ) {
    boolean isDuplicated = memberService.checkNicknameDuplication(nickname);
    return ResponseEntity.ok(new DuplicatedNicknameCheckRes(isDuplicated));
  }

  @PostMapping("/register")
  public ResponseEntity<Void> register(
      @RequestBody @Valid MemberRegisterReq memberRegisterReq
  ) {
    Long memberId = memberService.createMember(memberRegisterReq);
    return ResponseEntity.created(URI.create("/api/members/" + memberId)).build();
  }

  @PostMapping("/login")
  public ResponseEntity<MemberLoginRes> login(
      @RequestBody @Valid MemberLoginReq memberLoginReq
  ) {
    MemberLoginRes memberLoginRes = memberService.login(memberLoginReq);
    return ResponseEntity.ok(memberLoginRes);
  }

  @PostMapping("/refresh")
  public ResponseEntity<MemberLoginRes> reissueAccessToken(
      @RequestBody AccessTokenReissueReq accessTokenReissueReq
  ) {
    MemberLoginRes memberLoginRes = memberService.recreateAccessAndRefreshToken(
        accessTokenReissueReq);
    return ResponseEntity.ok(memberLoginRes);
  }

  @DeleteMapping("/logout")
  public ResponseEntity<Void> logout(
      @AuthenticationPrincipal UserPrincipal userPrincipal
  ) {
    memberService.deleteRefreshToken(userPrincipal.getUserId());
    return ResponseEntity.ok().build();
  }

}
