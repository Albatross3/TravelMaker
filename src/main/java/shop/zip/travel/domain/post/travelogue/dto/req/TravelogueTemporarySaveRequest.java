package shop.zip.travel.domain.post.travelogue.dto.req;

import java.util.List;
import org.bson.types.ObjectId;
import shop.zip.travel.domain.post.subTravelogue.entity.SubTravelogue;

public record TravelogueTemporarySaveRequest(
    ObjectId objectId, // id 로 Travelogue 찾기
    int day,
    String title,
    List<String> places,
    String content,
    List<String> photos
) {
    public SubTravelogue toSubTravelogue() {
      return SubTravelogue.builder()
          .day(day)
          .title(title)
          .places(places)
          .content(content)
          .photos(photos)
          .build();
    }
}
