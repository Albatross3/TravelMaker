package shop.zip.travel.domain.member.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.zip.travel.domain.post.travelogue.dto.TravelogueSimple;
import shop.zip.travel.domain.post.travelogue.dto.res.TravelogueCustomSlice;
import shop.zip.travel.domain.post.travelogue.dto.res.TravelogueSimpleRes;
import shop.zip.travel.domain.post.travelogue.repository.TravelogueRepository;

@Service
@Transactional(readOnly = true)
public class MemberMyTravelogueService {

  private final TravelogueRepository travelogueRepository;


  public MemberMyTravelogueService(TravelogueRepository travelogueRepository) {
    this.travelogueRepository = travelogueRepository;
  }

  public TravelogueCustomSlice<TravelogueSimpleRes> getTravelogues(Long memberId,
      Pageable pageable) {

    Slice<TravelogueSimple> myTravelogues = travelogueRepository.getMyTravelogues(memberId,
        pageable);

    return TravelogueCustomSlice.toDto(myTravelogues.map(TravelogueSimpleRes::toDto));
  }


}
