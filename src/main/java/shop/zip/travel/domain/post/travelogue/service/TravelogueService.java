package shop.zip.travel.domain.post.travelogue.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.zip.travel.domain.bookmark.repository.BookmarkRepository;
import shop.zip.travel.domain.member.entity.Member;
import shop.zip.travel.domain.member.service.MemberService;
import shop.zip.travel.domain.post.travelogue.dto.TravelogueSearchFilter;
import shop.zip.travel.domain.post.travelogue.dto.TravelogueSimple;
import shop.zip.travel.domain.post.travelogue.dto.req.TravelogueCreateReq;
import shop.zip.travel.domain.post.travelogue.dto.res.TravelogueCreateRes;
import shop.zip.travel.domain.post.travelogue.dto.res.TravelogueCustomSlice;
import shop.zip.travel.domain.post.travelogue.dto.res.TravelogueDetailRes;
import shop.zip.travel.domain.post.travelogue.dto.res.TravelogueSimpleRes;
import shop.zip.travel.domain.post.travelogue.entity.Travelogue;
import shop.zip.travel.domain.post.travelogue.exception.TravelogueNotFoundException;
import shop.zip.travel.domain.post.travelogue.repository.TravelogueRepository;
import shop.zip.travel.global.error.ErrorCode;

@Service
public class TravelogueService {

  private static final boolean PUBLISH = true;

  private final TravelogueRepository travelogueRepository;
  private final MemberService memberService;
	private final BookmarkRepository bookmarkRepository;

  public TravelogueService(TravelogueRepository travelogueRepository, MemberService memberService,
      BookmarkRepository bookmarkRepository) {
    this.travelogueRepository = travelogueRepository;
    this.memberService = memberService;
    this.bookmarkRepository = bookmarkRepository;
  }

  @Transactional
  public TravelogueCreateRes save(TravelogueCreateReq createReq, Long memberId) {
    Member findMember = memberService.getMember(memberId);
    Travelogue travelogue = travelogueRepository.save(createReq.toTravelogue(findMember));
    Long nights = travelogue.getPeriod().getNights();
    return TravelogueCreateRes.toDto(travelogue.getId(), nights);
  }

  @Transactional(readOnly = true)
  public TravelogueCustomSlice<TravelogueSimpleRes> findTravelogueList(Pageable pageable) {
    Slice<TravelogueSimple> travelogues =
        travelogueRepository.findAllBySlice(pageable, PUBLISH);

    return TravelogueCustomSlice.toDto(
        travelogues.map(TravelogueSimpleRes::toDto)
    );
  }

  @Transactional(readOnly = true)
  public Travelogue getTravelogue(Long id) {
    return travelogueRepository.findById(id)
        .orElseThrow(() -> new TravelogueNotFoundException(ErrorCode.TRAVELOGUE_NOT_FOUND));
  }

  @Transactional
  public TravelogueDetailRes getTravelogueDetail(Long travelogueId, boolean canAddViewCount,
      Long memberId) {

    setViewCount(travelogueId, canAddViewCount);
    Long countLikes = travelogueRepository.countLikes(travelogueId);
    boolean isLiked = travelogueRepository.isLiked(memberId, travelogueId);
    Boolean isBookmarked = bookmarkRepository.exists(memberId, travelogueId);

    return TravelogueDetailRes.toDto(
        travelogueRepository.getTravelogueDetail(travelogueId)
            .orElseThrow(() -> new TravelogueNotFoundException(ErrorCode.TRAVELOGUE_NOT_FOUND))
        , countLikes, isLiked, isBookmarked);
  }

  private void setViewCount(Long travelogueId, boolean canAddViewCount) {
    if (canAddViewCount) {
      Travelogue findTravelogue = getTravelogue(travelogueId);
      findTravelogue.addViewCount();
    }
  }

  public TravelogueCustomSlice<TravelogueSimpleRes> search(String keyword, Pageable pageable) {
    return TravelogueCustomSlice.toDto(travelogueRepository.search(keyword, pageable));
  }

  public TravelogueCustomSlice<TravelogueSimpleRes> filtering(String keyword, Pageable pageable,
      TravelogueSearchFilter searchFilter) {
    return TravelogueCustomSlice.toDto(
        travelogueRepository.filtering(keyword, pageable, searchFilter));
  }
}