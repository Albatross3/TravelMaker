package shop.zip.travel.domain.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberMyTravelogueService {

//  private final TravelogueRepository travelogueRepository;
//
//
//  public MemberMyTravelogueService(TravelogueRepository travelogueRepository) {
//    this.travelogueRepository = travelogueRepository;
//  }

//  public TravelogueCustomSlice<TravelogueSimpleRes> getTravelogues(Long memberId,
//      Pageable pageable) {
//
//    Slice<TravelogueSimple> myTravelogues = travelogueRepository.getMyTravelogues(memberId,
//        pageable);
//
//    return TravelogueCustomSlice.toDto(myTravelogues.map(TravelogueSimpleRes::toDto));
//  }


}
