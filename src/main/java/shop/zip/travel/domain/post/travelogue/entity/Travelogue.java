package shop.zip.travel.domain.post.travelogue.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;
import shop.zip.travel.domain.base.BaseTimeEntity;
import shop.zip.travel.domain.member.entity.Member;
import shop.zip.travel.domain.post.subTravelogue.entity.SubTravelogue;
import shop.zip.travel.domain.post.travelogue.data.Period;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Travelogue extends BaseTimeEntity {

  private static final int INDEX_MATCHER = 1;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String country;

  @Embedded
  private Period period;

  @Column(nullable = false)
  private int cost;

  @Column(nullable = false)
  private String title;

  @Lob
  private String thumbnail;

  private int viewCount;

  @OneToMany(mappedBy = "travelogue")
  private List<SubTravelogue> subTravelogues = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @Builder
  public Travelogue(String country, Period period, int cost, String title, String thumbnail,
      int viewCount, List<SubTravelogue> subTravelogues, Member member) {
    this.country = country;
    this.period = period;
    this.cost = cost;
    this.title = title;
    this.thumbnail = thumbnail;
    this.viewCount = viewCount;
    this.subTravelogues = subTravelogues;
    this.member = member;
  }

  public void addViewCount() {
    this.viewCount++;
  }

  public void addSubTravelogue(SubTravelogue subTravelogue) {
    verifySubTravelogueDuplicate(subTravelogue);

    if (hasAllSubTravelogues()) {
      throw new IllegalArgumentException("모든 서브트래블로그가 작성되어 있습니다.");
    }

    this.subTravelogues.add(subTravelogue);
  }

  private void verifySubTravelogueDuplicate(SubTravelogue subTravelogue) {
    Assert.isTrue(!subTravelogues.contains(subTravelogue), "이미 존재하는 서브게시물 입니다.");
  }

  public void updateSubTravelogues(SubTravelogue newSubTravelogue) {
    removeOldSubTravelogue(newSubTravelogue.getDay() - INDEX_MATCHER);

    this.subTravelogues.add(newSubTravelogue);
    subTravelogues.sort(Comparator.comparingInt(SubTravelogue::getDay));
  }

  private void removeOldSubTravelogue(int idx) {
    this.subTravelogues.remove(idx);
  }

  private boolean hasAllSubTravelogues() {
    return subTravelogues.size() == period.getNights() + 1;
  }

}
