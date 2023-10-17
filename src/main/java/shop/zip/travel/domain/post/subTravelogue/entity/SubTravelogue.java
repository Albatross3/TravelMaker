package shop.zip.travel.domain.post.subTravelogue.entity;

import jakarta.persistence.Id;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import shop.zip.travel.domain.base.BaseTimeEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "sub_travelogue")
public class SubTravelogue extends BaseTimeEntity {

    private static final int MIN_LENGTH = 0;
    private static final int MAX_LENGTH = 51;
    private static final int ZERO = 0;

    @Id
    private ObjectId id;

    private String title;

    private int day;

    private List<String> places;

    private String content;

    private List<String> photos;

    @Builder
    public SubTravelogue(String title, int day, List<String> places, String content,
        List<String> photos) {
        this.title = title;
        this.day = day;
        this.places = places;
        this.content = content;
        this.photos = photos;
    }


//    public void updateTravelogue(Travelogue travelogue) {
//        if(travelogue!=null) {
//            this.travelogue = travelogue;
//            travelogue.addSubTravelogue(this);
//        }
//    }
//
//    public void addTravelPhotos(List<TravelPhoto> travelPhotos) {
//        travelPhotos.forEach(this::addTraveloguePhoto);
//    }
//
//    public void addTraveloguePhoto(TravelPhoto travelPhoto) {
//        if(travelPhoto!= null) {
//            travelPhoto.updateSubTravelogue(this);
//            this.photos.add(travelPhoto);
//        }
//    }

}
