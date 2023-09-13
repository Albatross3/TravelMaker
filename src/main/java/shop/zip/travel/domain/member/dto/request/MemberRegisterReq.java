package shop.zip.travel.domain.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.security.crypto.password.PasswordEncoder;
import shop.zip.travel.domain.member.entity.Member;

public record MemberRegisterReq(
    @NotBlank
    @Email
    String email,

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*])[A-Za-z\\d~!@#$%^&*]{8,}$")
    String password,

    @NotBlank
    @Pattern(regexp = "^[가-힣|a-zA-Z]{2,12}$")
    String nickname,

    @NotBlank
    @Pattern(regexp = "^[0-9]{4}$")
    String birthYear
) {

  public static Member toMember(MemberRegisterReq memberRegisterReq, PasswordEncoder passwordEncoder) {
    return new Member(
        memberRegisterReq.email(),
        passwordEncoder.encode(memberRegisterReq.password()),
        memberRegisterReq.nickname(),
        memberRegisterReq.birthYear());
  }
}
