package shop.zip.travel.domain.post.image.dto;

import shop.zip.travel.domain.post.image.entity.TravelPhoto;

public record TravelPhotoCreateReq(
    String url
) {

    public TravelPhoto toEntity() {
        return new TravelPhoto(
            ""
        );
    }

    public static TravelPhotoCreateReq toDto(TravelPhoto travelPhoto) {
        return new TravelPhotoCreateReq(
            ""
        );
    }
}
