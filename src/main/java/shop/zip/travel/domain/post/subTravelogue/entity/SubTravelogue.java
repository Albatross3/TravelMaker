package shop.zip.travel.domain.post.subTravelogue.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;
import shop.zip.travel.domain.base.BaseTimeEntity;
import shop.zip.travel.domain.post.image.entity.TravelPhoto;
import shop.zip.travel.domain.post.subTravelogue.data.Address;
import shop.zip.travel.domain.post.subTravelogue.data.Transportation;
import shop.zip.travel.domain.post.travelogue.entity.Travelogue;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubTravelogue extends BaseTimeEntity {

    private static final int MIN_LENGTH = 0;
    private static final int MAX_LENGTH = 51;
    private static final int ZERO = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int day;

    @ElementCollection
    @CollectionTable(name = "address", joinColumns = @JoinColumn(name = "sub_travelogue_id"))
    private List<Address> addresses = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "transportation", joinColumns = @JoinColumn(name = "sub_travelogue_id"))
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Set<Transportation> transportationSet = new HashSet<>();

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @OneToMany(mappedBy = "subTravelogue")
    private List<TravelPhoto> photos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "travelogue_id")
    private Travelogue travelogue;


    public SubTravelogue(String title,
        String content, int day,
        List<Address> addresses,
        Set<Transportation> transportationSet,
        List<TravelPhoto> photos
    ) {
        verify(title, day, addresses, transportationSet, photos);

        this.title = title;
        this.content = content;
        this.day = day;
        this.addresses = addresses;
        this.transportationSet = transportationSet;
        this.photos = photos;
    }

    public void updateTravelogue(Travelogue travelogue) {
        if(travelogue!=null) {
            this.travelogue = travelogue;
            travelogue.addSubTravelogue(this);
        }
    }

    public void addTravelPhotos(List<TravelPhoto> travelPhotos) {
        travelPhotos.forEach(this::addTraveloguePhoto);
    }

    public void addTraveloguePhoto(TravelPhoto travelPhoto) {
        if(travelPhoto!= null) {
            travelPhoto.updateSubTravelogue(this);
            this.photos.add(travelPhoto);
        }
    }

    private void verify(String title,
        int day,
        List<Address> addresses,
        Set<Transportation> transportationSet,
        List<TravelPhoto> photos
    ) {
        nullCheck(addresses, transportationSet, photos);

        if (!title.isBlank()) {
            verifyTitle(title);
        }

        verifyDay(day);
    }


    private void verifyDay(int day) {
        if (day <= ZERO) {
            throw new IllegalArgumentException("일차는 0보다 작을 수 없습니다.");
        }
    }

    private void nullCheck(List<Address> addresses,
        Set<Transportation> transportationSet, List<TravelPhoto> photos) {
        Assert.notNull(addresses, "주소를 확인해주세요");
        Assert.notNull(transportationSet, "이동수단을 확인해주세요");
        Assert.notNull(photos, "이미지를 확인해주세요");
    }

    private void verifyTitle(String title) {
        Assert.isTrue(title.length() < MAX_LENGTH && title.length() > MIN_LENGTH,
            "제목의 길이는 1글자 이상 50글자 이하여야 합니다");
    }


}
