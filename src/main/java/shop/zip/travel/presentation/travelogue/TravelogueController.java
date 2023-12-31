package shop.zip.travel.presentation.travelogue;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.zip.travel.domain.post.travelogue.dto.req.TraveloguePublishRequest;
import shop.zip.travel.domain.post.travelogue.dto.req.TravelogueStartWriteRequest;
import shop.zip.travel.domain.post.travelogue.dto.req.TravelogueTemporarySaveRequest;
import shop.zip.travel.domain.post.travelogue.service.TravelogueService;
import shop.zip.travel.global.security.UserPrincipal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/travelogues")
public class TravelogueController {

  private static final int DEFAULT_SIZE = 5;
  private static final String DEFAULT_SORT_FIELD = "createDate";

  private final TravelogueService travelogueService;

  @PostMapping("/start")
  public ResponseEntity<Void> startWritingTravelogue(
      @RequestBody TravelogueStartWriteRequest travelogueStartWriteRequest) {
    travelogueService.startWriting(travelogueStartWriteRequest);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/temporary")
  public ResponseEntity<Void>  temporarySaveTravelogue(
      @RequestBody TravelogueTemporarySaveRequest travelogueTemporarySaveRequest) {

    return ResponseEntity.ok().build();
  }


  @PostMapping("/publish")
  public ResponseEntity<Void> publishTravelogue(
      @RequestBody @Valid TraveloguePublishRequest tempTraveloguePublishRequest,
      @AuthenticationPrincipal UserPrincipal userPrincipal
  ) {

    return new ResponseEntity<>(HttpStatus.CREATED);
  }

//  @PatchMapping("/{travelogueId}")
//  public ResponseEntity<TravelogueDetailRes> get(
//      HttpServletRequest request,
//      HttpServletResponse response,
//      @PathVariable Long travelogueId,
//      @AuthenticationPrincipal UserPrincipal userPrincipal
//  ) {
//    boolean canAddViewCount = CookieUtil.canAddViewCount(request, response, travelogueId);
//    TravelogueDetailRes travelogueDetail =
//        travelogueService.getTravelogueDetail(
//            travelogueId,
//            canAddViewCount,
//            userPrincipal.getUserId()
//        );
//
//    return ResponseEntity.ok(travelogueDetail);
//  }

//  @GetMapping
//  public ResponseEntity<TravelogueCustomSlice<TravelogueSimpleRes>> getAll(
//      @PageableDefault(size = DEFAULT_SIZE, sort = DEFAULT_SORT_FIELD, direction = Direction.DESC) Pageable pageable
//  ) {
//    TravelogueCustomSlice<TravelogueSimpleRes> travelogueSimpleRes =
//        travelogueService.getTravelogues(pageable);
//
//    return ResponseEntity.ok(travelogueSimpleRes);
//  }
//
//  @GetMapping("/search")
//  public ResponseEntity<TravelogueCustomSlice<TravelogueSimpleRes>> search(
//      @RequestParam(name = "keyword") String keyword,
//      @PageableDefault(size = DEFAULT_SIZE) Pageable pageable
//  ) {
//    TravelogueCustomSlice<TravelogueSimpleRes> travelogueSimpleResList =
//        travelogueService.search(keyword.trim(), pageable);
//
//    return ResponseEntity.ok(travelogueSimpleResList);
//  }
//
//  @GetMapping("/search/filters")
//  public ResponseEntity<TravelogueCustomSlice<TravelogueSimpleRes>> filtering(
//      @RequestParam(name = "keyword") String keyword,
//      @PageableDefault(size = DEFAULT_SIZE) Pageable pageable,
//      TravelogueSearchFilter searchFilter
//  ) {
//    TravelogueCustomSlice<TravelogueSimpleRes> filtered =
//        travelogueService.filtering(keyword, pageable, searchFilter);
//
//    return ResponseEntity.ok(filtered);
//  }

}