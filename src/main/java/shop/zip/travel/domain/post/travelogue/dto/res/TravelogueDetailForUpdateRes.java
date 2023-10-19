//package shop.zip.travel.domain.post.travelogue.dto.res;
//
//import java.util.List;
//import shop.zip.travel.domain.post.subTravelogue.entity.SubTravelogue;
//import shop.zip.travel.domain.post.travelogue.entity.Travelogue;
//
//public record TravelogueDetailForUpdateRes(
//    String title,
//    PeriodDetailForUpdateRes period,
//    String country,
//    int cost,
//    String thumbnail,
//    List<Long> subTravelogueIds
//) {
//
//  public static TravelogueDetailForUpdateRes toDto(Travelogue travelogue) {
//    return new TravelogueDetailForUpdateRes(
//        travelogue.getTitle(),
//        PeriodDetailForUpdateRes.toDto(travelogue.getPeriod()),
//        travelogue.getCountry(),
//        travelogue.getCost(),
//        travelogue.getThumbnail(),
//        travelogue.getSubTravelogues()
//            .stream()
//            .map(SubTravelogue::getId)
//            .toList()
//    );
//  }
//}
