package shop.zip.travel.domain.post.travelogue.service;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.zip.travel.domain.member.entity.Member;
import shop.zip.travel.domain.post.travelogue.dto.req.TravelogueStartWriteRequest;
import shop.zip.travel.domain.post.travelogue.entity.Travelogue;
import shop.zip.travel.domain.post.travelogue.repository.TravelogueRepository;

@Service
@RequiredArgsConstructor
public class TravelogueService {

  private final MongoTemplate mongoTemplate;
  private final TravelogueRepository travelogueRepository;

  @Transactional
  public void startWriting(TravelogueStartWriteRequest travelogueStartWriteRequest) {
    // mongoDB의 temporaryTravelogue 컬렉션에 저장 시작
    Travelogue travelogue = travelogueStartWriteRequest.toTravelogue();
    mongoTemplate.save(travelogue, "temporaryTravelogue");
  }

  @Transactional
  public void temporalSave() {

  }

  @Transactional
  public void publish(Long travelogueId) {
    // 1. TravelogueId 로 임시저장된 글 불러오기

    // 2. Entity 로 변환 및 저장
  }

//  public TravelogueCustomSlice<TravelogueSimpleRes> getTravelogues(Pageable pageable) {
//    Slice<TravelogueSimple> travelogues =
//        travelogueRepository.findAllBySlice(pageable);
//
//    return TravelogueCustomSlice.toDto(
//        travelogues.map(TravelogueSimpleRes::toDto)
//    );
//  }

//  @Transactional
//  public Travelogue getTravelogue(Long id) {
//    return travelogueRepository.findById(id)
//        .orElseThrow(() -> new TravelogueNotFoundException(ErrorCode.TRAVELOGUE_NOT_FOUND));
//  }
//
//  public TravelogueCustomSlice<TravelogueSimpleRes> search(String keyword, Pageable pageable) {
//    return TravelogueCustomSlice.toDto(travelogueRepository.search(keyword, pageable));
//  }
//
//  @Transactional
//  public TravelogueCustomSlice<TravelogueSimpleRes> filtering(String keyword, Pageable pageable,
//      TravelogueSearchFilter searchFilter) {
//    return TravelogueCustomSlice.toDto(
//        travelogueRepository.filtering(keyword, pageable, searchFilter));
//  }

//  @Transactional
//  public TravelogueDetailRes getTravelogueDetail(Long travelogueId, boolean canAddViewCount,
//      Long memberId) {
//    Long countLikes = travelogueRepository.countLikes(travelogueId);
//    boolean isLiked = travelogueRepository.isLiked(memberId, travelogueId);
//    boolean isBookmarked = bookmarkRepository.exists(memberId, travelogueId);
//
//    Travelogue travelogue = travelogueRepository.getTravelogueDetail(travelogueId)
//        .orElseThrow(() -> new TravelogueNotFoundException(ErrorCode.TRAVELOGUE_NOT_FOUND));
//
//    updateViewCount(travelogueId, canAddViewCount);
//
//    Suggestion suggestion = new Suggestion(travelogue.getCountry(), memberId);
//    suggestionRepository.save(suggestion);
//
//    boolean isWriter = isWriter(travelogue.getMemberId(), memberId);
//
//    return TravelogueDetailRes.toDto(travelogue, countLikes, isLiked, isBookmarked, isWriter);
//  }

//  private void updateViewCount(Long travelogueId, boolean canAddViewCount) {
//    if (canAddViewCount) {
//      getTravelogue(travelogueId).addViewCount();
//    }
//  }

  private boolean isWriter(Member writer, Long requestMemberId) {
    return Objects.equals(writer.getId(), requestMemberId);
  }
}
