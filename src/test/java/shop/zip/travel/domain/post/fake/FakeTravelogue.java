package shop.zip.travel.domain.post.fake;

import shop.zip.travel.domain.post.travelogue.entity.Travelogue;

public class FakeTravelogue extends Travelogue {

	private final Long id;

	public FakeTravelogue(Long id, Travelogue travelogue) {
		super(
			travelogue.getPeriod(),
			travelogue.getTitle(),
			travelogue.getCountry(),
			travelogue.getThumbnail(),
			travelogue.getCost(),
			travelogue.getIsPublished(),
			travelogue.getSubTravelogues(),
			travelogue.getMember()
		);
		this.id = id;
	}

	@Override
	public Long getId() {
		return id;
	}
}
