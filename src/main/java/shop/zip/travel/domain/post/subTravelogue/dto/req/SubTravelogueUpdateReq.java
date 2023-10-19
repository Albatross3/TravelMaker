//package shop.zip.travel.domain.post.subTravelogue.dto.req;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//import java.util.Set;
//import java.util.stream.Collectors;
//import shop.zip.travel.domain.post.image.dto.TravelPhotoCreateReq;
//import shop.zip.travel.domain.post.image.entity.TravelPhoto;
//import shop.zip.travel.domain.post.subTravelogue.data.Address;
//import shop.zip.travel.domain.post.subTravelogue.data.Transportation;
//import shop.zip.travel.domain.post.subTravelogue.dto.SubTravelogueUpdate;
//
//public record SubTravelogueUpdateReq(
//    String title,
//    String content,
//    int day,
//    List<AddressCreateReq> addresses,
//    Set<Transportation> transportationSet,
//    List<TravelPhotoCreateReq> travelPhotoCreateReqs
//) {
//
//    public SubTravelogueUpdate toSubTravelogueUpdate() {
//        return new SubTravelogueUpdate(
//            "",
//            "",
//            day,
//            toAddresses(),
//            transportationSet,
//            toTravelPhotos()
//        );
//    }
//
//    private List<Address> toAddresses() {
//        if (Objects.isNull(addresses)) {
//            return new ArrayList<>();
//        }
//
//        return addresses.stream()
//            .map(AddressCreateReq::toAddress)
//            .collect(Collectors.toList());
//    }
//
//    private List<TravelPhoto> toTravelPhotos() {
//        if (Objects.isNull(travelPhotoCreateReqs)) {
//            return new ArrayList<>();
//        }
//
//        return travelPhotoCreateReqs.stream()
//            .map(TravelPhotoCreateReq::toEntity)
//            .collect(Collectors.toList());
//    }
//}
