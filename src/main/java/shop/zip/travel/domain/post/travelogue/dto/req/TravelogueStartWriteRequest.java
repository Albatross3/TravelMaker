package shop.zip.travel.domain.post.travelogue.dto.req;

import shop.zip.travel.domain.post.travelogue.value.Period;

public record TravelogueStartWriteRequest(
    String country,
    Period period,
    int cost,
    String title,
    String thumbnail
) {

}
