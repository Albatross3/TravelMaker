package shop.zip.travel.domain.post.travelogue.dto;

import shop.zip.travel.domain.post.travelogue.entity.Period;

public record TravelogueSimple(
		Long travelogueId,
		String title,
		Period period,
		int totalCost,
		String country,
		String thumbnail,
		String memberNickname,
		String memberProfileImageUrl,
		Long likeCount
) {

}
