//package shop.zip.travel.domain.post.travelogue.dto.res;
//
//import java.util.List;
//import shop.zip.travel.domain.post.subTravelogue.dto.res.SubTravelogueDetailRes;
//import shop.zip.travel.domain.post.travelogue.entity.Travelogue;
//
//public record TravelogueDetailRes(
//    String profileImageUrl,
//    String nickname,
//    Long id,
//    String title,
//    String country,
//    Long nights,
//    Long days,
//    int totalCost,
//    String thumbnail,
//    List<SubTravelogueDetailRes> subTravelogues,
//    Long countLikes,
//    boolean isLiked,
//    int viewCount,
//    Boolean bookmarked,
//    boolean isWriter
//) {
//
//  public static TravelogueDetailRes toDto(
//      Travelogue travelogue,
//      Long countLikes,
//      Boolean isLiked,
//      Boolean isBookmarked,
//      boolean isWriter
//  ) {
//    return new TravelogueDetailRes(
//        travelogue.getMember().getProfileImageUrl(),
//        travelogue.getMember().getNickname(),
//        travelogue.getId(),
//        travelogue.getTitle(),
//        travelogue.getCountry(),
//        travelogue.getPeriod().getNights(),
//        travelogue.getPeriod().getNights() + 1,
//        travelogue.getCost(),
//        travelogue.getThumbnail(),
//        travelogue.getSubTravelogues()
//            .stream()
//            .map(SubTravelogueDetailRes::toDto)
//            .toList(),
//        countLikes,
//        isLiked,
//        travelogue.getViewCount(),
//        isBookmarked,
//        isWriter
//    );
//
//  }
//
//}
