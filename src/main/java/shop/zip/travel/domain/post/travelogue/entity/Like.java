//package shop.zip.travel.domain.post.travelogue.entity;
//
//import io.jsonwebtoken.lang.Assert;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.Table;
//import org.hibernate.annotations.OnDelete;
//import org.hibernate.annotations.OnDeleteAction;
//import shop.zip.travel.domain.base.BaseTimeEntity;
//import shop.zip.travel.domain.member.entity.Member;
//
//@Entity
//@Table(name = "LIKES")
//public class Like extends BaseTimeEntity {
//
//  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
//  private Long id;
//
//  @ManyToOne
//  @JoinColumn(name = "travelogue_id", nullable = false)
//  @OnDelete(action = OnDeleteAction.CASCADE)
//  private Travelogue travelogue;
//
//  @ManyToOne
//  @JoinColumn(name = "member_id", nullable = false)
//  @OnDelete(action = OnDeleteAction.CASCADE)
//  private Member member;
//
//  protected Like() {
//  }
//
//  public Like(Travelogue travelogue, Member member) {
//    verify(travelogue, member);
//    this.travelogue = travelogue;
//    this.member = member;
//  }
//
//  public Long getId() {
//    return id;
//  }
//
//  public Travelogue getTravelogue() {
//    return travelogue;
//  }
//
//  public Member getMember() {
//    return member;
//  }
//
//  private void verify(Travelogue travelogue, Member member) {
//    Assert.notNull(travelogue, "게시물 없이 좋아요 생성이 불가능합니다.");
//    Assert.notNull(member, "멤버 없이 좋아요 생성이 불가능합니다.");
//  }
//}
