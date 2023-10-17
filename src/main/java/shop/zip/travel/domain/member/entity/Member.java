package shop.zip.travel.domain.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.zip.travel.domain.base.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(unique = true, nullable = false)
  private String nickname;

  @Column(nullable = false, length = 4)
  private String birthYear;

  @Column(nullable = false)
  private String profileImageUrl;

  @Builder
  public Member(String email, String password, String nickname, String birthYear,
      String profileImageUrl) {
    this.email = email;
    this.password = password;
    this.nickname = nickname;
    this.birthYear = birthYear;
    this.profileImageUrl = profileImageUrl;
  }

  public Member update(String nickname, String profileImageUrl) {
    this.nickname = nickname;
    this.profileImageUrl = profileImageUrl;
    return this;
  }

}
