package shop.zip.travel.presentation.member;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.zip.travel.domain.member.service.MemberMyTravelogueService;

@RestController
@RequestMapping("/api/members/my/travelogues")
public class MemberMyTravelogueController {

  private static final int DEFAULT_SIZE = 5;

  private final MemberMyTravelogueService memberMyTravelogueService;

  public MemberMyTravelogueController(MemberMyTravelogueService memberMyTravelogueService) {
    this.memberMyTravelogueService = memberMyTravelogueService;
  }

//  @GetMapping
//  public ResponseEntity<TravelogueCustomSlice<TravelogueSimpleRes>> getMyTravelogues(
//      @PageableDefault(size = DEFAULT_SIZE) Pageable pageable,
//      @AuthenticationPrincipal UserPrincipal userPrincipal
//  ) {
//    TravelogueCustomSlice<TravelogueSimpleRes> travelogues =
//        memberMyTravelogueService.getTravelogues(userPrincipal.getUserId(), pageable);
//
//    return ResponseEntity.ok(travelogues);
//  }

}
