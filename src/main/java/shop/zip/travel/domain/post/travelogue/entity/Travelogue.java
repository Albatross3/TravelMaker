package shop.zip.travel.domain.post.travelogue.entity;

import jakarta.persistence.Id;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;
import shop.zip.travel.domain.base.BaseTimeEntity;
import shop.zip.travel.domain.post.subTravelogue.entity.SubTravelogue;
import shop.zip.travel.domain.post.travelogue.value.Period;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "travelogue")
public class Travelogue extends BaseTimeEntity {

  private static final int INDEX_MATCHER = 1;

  @Id
  private ObjectId id;

  private Long memberId;

  private String country;

  private Period period;

  private int cost;

  private String title;

  private String thumbnail;

  private int viewCount;

  private List<SubTravelogue> subTravelogues = new ArrayList<>();

  @Builder
  public Travelogue(Long memberId, String country, Period period, int cost, String title,
      String thumbnail, int viewCount, List<SubTravelogue> subTravelogues) {
    this.memberId = memberId;
    this.country = country;
    this.period = period;
    this.cost = cost;
    this.title = title;
    this.thumbnail = thumbnail;
    this.viewCount = viewCount;
    this.subTravelogues = subTravelogues;
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
