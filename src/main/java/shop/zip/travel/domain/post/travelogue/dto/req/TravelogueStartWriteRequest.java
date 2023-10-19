package shop.zip.travel.domain.post.travelogue.dto.req;

import shop.zip.travel.domain.post.travelogue.entity.Travelogue;
import shop.zip.travel.domain.post.travelogue.value.Period;

public record TravelogueStartWriteRequest(
    Long memberId,
    String country,
    Period period,
    int cost,
    String title,
    String thumbnail
) {

  public Travelogue toTravelogue() {
    return Travelogue.builder()
        .memberId(memberId)
        .country(country)
        .period(period)
        .cost(cost)
        .title(title)
        .thumbnail(thumbnail)
        .build();
  }

}
