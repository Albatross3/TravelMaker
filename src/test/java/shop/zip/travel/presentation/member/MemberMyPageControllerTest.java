package shop.zip.travel.presentation.member;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import shop.zip.travel.domain.bookmark.entity.Bookmark;
import shop.zip.travel.domain.bookmark.repository.BookmarkRepository;
import shop.zip.travel.domain.member.dto.request.MemberUpdateReq;
import shop.zip.travel.domain.member.entity.Member;
import shop.zip.travel.domain.member.repository.MemberRepository;
import shop.zip.travel.domain.post.travelogue.DummyGenerator;
import shop.zip.travel.domain.post.travelogue.entity.Travelogue;
import shop.zip.travel.domain.post.travelogue.repository.TravelogueRepository;
import shop.zip.travel.global.security.JwsManager;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
@Transactional
@ExtendWith(SpringExtension.class)
class MemberMyPageControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private TravelogueRepository travelogueRepository;

  @Autowired
  private BookmarkRepository bookmarkRepository;

  @Autowired
  private JwsManager jwsManager;

  private final ObjectMapper objectMapper = new ObjectMapper();

  private Member member;

  private Travelogue travelogue;

  @BeforeEach
  void setUp() {
    member = new Member(
        "user@naver.com",
        "password1234!",
        "nickname",
        "2000",
        "ProfileUrlForTest");

    memberRepository.save(member);
    travelogue = travelogueRepository.save(DummyGenerator.createTravelogue(member));
  }

  @DisplayName("유저는 본인의 개인정보를 조회할 수 있다")
  @Test
  public void get_my_page_info() throws Exception {

    String token = "Bearer " + jwsManager.createAccessToken(member.getId());

    mockMvc.perform(get("/api/members/my/info").header("AccessToken", token))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("get-my-info",
            preprocessResponse(prettyPrint()),
            responseFields(fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
                fieldWithPath("birthYear").type(JsonFieldType.STRING).description("생년월일"),
                fieldWithPath("profileImageUrl").type(JsonFieldType.STRING)
                    .description("프로필 이미지 url"))));
  }

  @DisplayName("유저는 본인의 프로필 사진과 닉네임을 변경할 수 있다")
  @Test
  public void update_my_profile() throws Exception {
    String token = "Bearer " + jwsManager.createAccessToken(member.getId());
    MemberUpdateReq memberUpdateReq = new MemberUpdateReq(
        "test-profile-image-url",
        "testNickname"
    );

    mockMvc.perform(patch("/api/members/my/settings")
            .header("AccessToken", token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(memberUpdateReq)))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("update-my-profile",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            requestFields(
                fieldWithPath("profileImageUrl").type(JsonFieldType.STRING)
                    .description("변경할 프로필 이미지 url"),
                fieldWithPath("nickname").type(JsonFieldType.STRING).description("변경할 닉네임")
            )
            ,
            responseFields(
                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
                fieldWithPath("birthYear").type(JsonFieldType.STRING).description("생년월일"),
                fieldWithPath("profileImageUrl").type(JsonFieldType.STRING)
                    .description("프로필 이미지 url")
            )
        ));
  }

  @DisplayName("유저는 본인이 북마크한 여행기목록을 조회할 수 있다")
  @Test
  public void get_my_bookmark_list() throws Exception {
    String token = "Bearer " + jwsManager.createAccessToken(member.getId());
    Bookmark bookmark = new Bookmark(travelogue, member);
    bookmarkRepository.save(bookmark);

    mockMvc.perform(get("/api/members/my/bookmarks")
            .header("AccessToken", token))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("get_my_bookmark_list",
            preprocessResponse(prettyPrint()),
            responseFields(
                fieldWithPath("content[].travelogueId").type(JsonFieldType.NUMBER)
                    .description("게시글 아이디"),
                fieldWithPath("content[].title").type(JsonFieldType.STRING).description("게시글 제목"),
                fieldWithPath("content[].nights").type(JsonFieldType.NUMBER).description("숙박일"),
                fieldWithPath("content[].days").type(JsonFieldType.NUMBER).description("여행 전체 일"),
                fieldWithPath("content[].totalCost").type(JsonFieldType.NUMBER)
                    .description("여행 전체 비용"),
                fieldWithPath("content[].country").type(JsonFieldType.STRING).description("여행한 나라"),
                fieldWithPath("content[].thumbnail").type(JsonFieldType.STRING)
                    .description("게시글 썸네일"),
                fieldWithPath("content[].member.nickname").type(JsonFieldType.STRING)
                    .description("작성자 닉네임"),
                fieldWithPath("content[].member.profileImageUrl").type(JsonFieldType.STRING)
                    .description("작성자 프로필 이미지 링크"),
                fieldWithPath("content[].likeCount").type(JsonFieldType.NUMBER)
                    .description("좋아요 갯수"),
                fieldWithPath("pageable.sort.empty").description("데이터가 비어있는지에 대한 여부"),
                fieldWithPath("pageable.sort.sorted").description("데이터가 정렬되어있는지에 대한 여부"),
                fieldWithPath("pageable.sort.unsorted").description("데이터가 정렬되어 있지 않은지에 대한 여부"),
                fieldWithPath("pageable.offset").description("페이징 offset"),
                fieldWithPath("pageable.pageNumber").description("현재 요청한 페이지 넘버"),
                fieldWithPath("pageable.pageSize").description("요청한 데이터 갯수"),
                fieldWithPath("pageable.paged").description("페이징이 된 여부"),
                fieldWithPath("pageable.unpaged").description("페이징이 되지 않은 여부"),
                fieldWithPath("size").description("요청된 페이징 사이즈"),
                fieldWithPath("number").description("페이지 번호"),
                fieldWithPath("numberOfElements").description("조회된 데이터 갯수"),
                fieldWithPath("first").description("첫번째 페이지인지의 여부"),
                fieldWithPath("last").description("마지막 페이지인지의 여부"),
                fieldWithPath("empty").description("데이터가 없는지의 여부")
            )
        ));
  }
}