package shop.zip.travel.presentation.suggestion;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.zip.travel.domain.suggestion.service.SuggestionService;

@RestController
@RequestMapping("/api/suggestions")
public class SuggestionController {

  private static final int DEFAULT_SIZE = 5;

  private final SuggestionService suggestionService;

  public SuggestionController(SuggestionService suggestionService) {
    this.suggestionService = suggestionService;
  }

//  @GetMapping
//  public ResponseEntity<TravelogueCustomSlice<TravelogueSimpleRes>> suggestion(
//      @AuthenticationPrincipal UserPrincipal userPrincipal,
//      @PageableDefault(size = DEFAULT_SIZE) Pageable pageable) {
//
//    TravelogueCustomSlice<TravelogueSimpleRes> travelogueSimpleResList = suggestionService.findByMemberId(
//        userPrincipal.getUserId(), pageable);
//
//    return ResponseEntity.ok(travelogueSimpleResList);
//  }

}
