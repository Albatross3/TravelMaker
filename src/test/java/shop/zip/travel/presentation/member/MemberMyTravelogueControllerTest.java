package shop.zip.travel.presentation.member;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import shop.zip.travel.domain.member.entity.Member;
import shop.zip.travel.domain.member.repository.MemberRepository;
import shop.zip.travel.domain.post.travelogue.DummyGenerator;
import shop.zip.travel.domain.post.travelogue.repository.TravelogueRepository;
import shop.zip.travel.global.security.JwsManager;


@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Transactional
@SpringBootTest
class MemberMyTravelogueControllerTest {

  private static final String tokenName = "AccessToken";

  @Autowired
  private JwsManager jwsManager;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private TravelogueRepository travelogueRepository;

  @Autowired
  private MemberRepository memberRepository;

  private String token;

  @BeforeEach
  void setUp() {
    Member member = memberRepository.save(DummyGenerator.createMember());
    travelogueRepository.save(DummyGenerator.createTravelogue(member));
    travelogueRepository.save(
        DummyGenerator.createNotPublishedTravelogueWithSubTravelogues(new ArrayList<>(), member)
    );
    token = "Bearer " + jwsManager.createAccessToken(member.getId());
  }

  @Test
  @DisplayName("내가 작성한 발행된 게시물들을 가져올 수 있다.")
  void getMyTravelogues() throws Exception {

    mockMvc.perform(get("/api/members/my/travelogues")
            .header(tokenName, token)
            .param("size", "2")
            .param("page", "0"))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("get-all-my-travelogues",
            preprocessResponse(prettyPrint()),
            requestHeaders(
                headerWithName("AccessToken").description("인증 토큰")
            ),
            queryParameters(
                parameterWithName("size").description("한번에 가져올 데이터 갯수, 기본으로 5로 설정되어 있습니다."),
                parameterWithName("page").description("조회할 페이지 넘버, 기본으로 0으로 설정되어 있습니다.(첫페이지)")
            ),
            responseFields(
                fieldWithPath("content[].travelogueId").type(JsonFieldType.NUMBER)
                    .description("Travelogue id"),
                fieldWithPath("content[].title").type(JsonFieldType.STRING)
                    .description("Travelogue 제목"),
                fieldWithPath("content[].nights").type(JsonFieldType.NUMBER).description("숙박일"),
                fieldWithPath("content[].days").type(JsonFieldType.NUMBER).description("전체일"),
                fieldWithPath("content[].totalCost").type(JsonFieldType.NUMBER)
                    .description("여행 총 경비"),
                fieldWithPath("content[].country").type(JsonFieldType.STRING).description("여행한 나라"),
                fieldWithPath("content[].thumbnail").type(JsonFieldType.STRING)
                    .description("Travelogue 썸네일"),
                fieldWithPath("content[].member.nickname").type(JsonFieldType.STRING)
                    .description("작성자 닉네임"),
                fieldWithPath("content[].member.profileImageUrl").type(JsonFieldType.STRING)
                    .description("작성자 프로필 이미지 URL"),
                fieldWithPath("content[].likeCount").type(JsonFieldType.NUMBER)
                    .description("게시글 좋아요 수"),
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

  @Test
  @DisplayName("내가 작성한 임시 저장 게시물들을 가져올 수 있다.")
  void getTempAll() throws Exception {

    mockMvc.perform(get("/api/members/my/travelogues/temp")
            .header(tokenName, token)
            .param("size", "2")
            .param("page", "0"))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("get-all-my-temp-travelogues",
            preprocessResponse(prettyPrint()),
            requestHeaders(
                headerWithName("AccessToken").description("인증 토큰")
            ),
            queryParameters(
                parameterWithName("size").description("한번에 가져올 데이터 갯수, 기본으로 5로 설정되어 있습니다."),
                parameterWithName("page").description("조회할 피이지 넘버, 기본으로 0으로 설정되어 있습니다.(첫페이지)")
            ),
            responseFields(
                fieldWithPath("content[].travelogueId").type(JsonFieldType.NUMBER)
                    .description("Travelogue id"),
                fieldWithPath("content[].title").type(JsonFieldType.STRING)
                    .description("Travelogue 제목"),
                fieldWithPath("content[].nights").type(JsonFieldType.NUMBER).description("숙박일"),
                fieldWithPath("content[].days").type(JsonFieldType.NUMBER).description("전체일"),
                fieldWithPath("content[].totalCost").type(JsonFieldType.NUMBER)
                    .description("여행 총 경비"),
                fieldWithPath("content[].country").type(JsonFieldType.STRING).description("여행한 나라"),
                fieldWithPath("content[].thumbnail").type(JsonFieldType.STRING)
                    .description("Travelogue 썸네일"),
                fieldWithPath("content[].member.nickname").type(JsonFieldType.STRING)
                    .description("작성자 닉네임"),
                fieldWithPath("content[].member.profileImageUrl").type(JsonFieldType.STRING)
                    .description("작성자 프로필 이미지 URL"),
                fieldWithPath("content[].member.profileImageUrl").type(JsonFieldType.STRING)
                    .description("작성자 프로필 이미지 URL"),
                fieldWithPath("content[].likeCount").type(JsonFieldType.NUMBER)
                    .description("좋아요 수"),
                fieldWithPath("content[].subTravelogueId[]").type(JsonFieldType.ARRAY)
                    .description("Travelogue 가 가진 subTravelogue Id 리스트"),
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