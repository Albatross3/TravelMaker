package shop.zip.travel.presentation.travelogue;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.zip.travel.domain.post.travelogue.dto.TravelogueSearchFilter;
import shop.zip.travel.domain.post.travelogue.dto.req.TraveloguePublishRequest;
import shop.zip.travel.domain.post.travelogue.dto.res.TravelogueCustomSlice;
import shop.zip.travel.domain.post.travelogue.dto.res.TravelogueDetailRes;
import shop.zip.travel.domain.post.travelogue.dto.res.TravelogueSimpleRes;
import shop.zip.travel.domain.post.travelogue.service.TravelogueService;
import shop.zip.travel.global.security.UserPrincipal;
import shop.zip.travel.global.util.CookieUtil;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/travelogues")
public class TravelogueController {

  private static final int DEFAULT_SIZE = 5;
  private static final String DEFAULT_SORT_FIELD = "createDate";

  private final TravelogueService travelogueService;

  @PostMapping
  public ResponseEntity<Void> publishTravelogue(
      @RequestBody @Valid TraveloguePublishRequest tempTraveloguePublishRequest,
      @AuthenticationPrincipal UserPrincipal userPrincipal
  ) {

    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PatchMapping("/{travelogueId}")
  public ResponseEntity<TravelogueDetailRes> get(
      HttpServletRequest request,
      HttpServletResponse response,
      @PathVariable Long travelogueId,
      @AuthenticationPrincipal UserPrincipal userPrincipal
  ) {
    boolean canAddViewCount = CookieUtil.canAddViewCount(request, response, travelogueId);
    TravelogueDetailRes travelogueDetail =
        travelogueService.getTravelogueDetail(
            travelogueId,
            canAddViewCount,
            userPrincipal.getUserId()
        );

    return ResponseEntity.ok(travelogueDetail);
  }

  @GetMapping
  public ResponseEntity<TravelogueCustomSlice<TravelogueSimpleRes>> getAll(
      @PageableDefault(size = DEFAULT_SIZE, sort = DEFAULT_SORT_FIELD, direction = Direction.DESC) Pageable pageable
  ) {
    TravelogueCustomSlice<TravelogueSimpleRes> travelogueSimpleRes =
        travelogueService.getTravelogues(pageable);

    return ResponseEntity.ok(travelogueSimpleRes);
  }

  @GetMapping("/search")
  public ResponseEntity<TravelogueCustomSlice<TravelogueSimpleRes>> search(
      @RequestParam(name = "keyword") String keyword,
      @PageableDefault(size = DEFAULT_SIZE) Pageable pageable
  ) {
    TravelogueCustomSlice<TravelogueSimpleRes> travelogueSimpleResList =
        travelogueService.search(keyword.trim(), pageable);

    return ResponseEntity.ok(travelogueSimpleResList);
  }

  @GetMapping("/search/filters")
  public ResponseEntity<TravelogueCustomSlice<TravelogueSimpleRes>> filtering(
      @RequestParam(name = "keyword") String keyword,
      @PageableDefault(size = DEFAULT_SIZE) Pageable pageable,
      TravelogueSearchFilter searchFilter
  ) {
    TravelogueCustomSlice<TravelogueSimpleRes> filtered =
        travelogueService.filtering(keyword, pageable, searchFilter);

    return ResponseEntity.ok(filtered);
  }

}