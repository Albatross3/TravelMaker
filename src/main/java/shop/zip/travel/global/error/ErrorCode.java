package shop.zip.travel.global.error;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

	// field validation
	BINDING_WRONG(HttpStatus.BAD_REQUEST, "요청하신 필드값이 잘못 되었습니다"),

	// invalid token
	INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "엑세스 토큰이 유효하지 않습니다"),
	INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "리프레시 토큰이 유효하지 않습니다"),
	JSON_NOT_PARSING(HttpStatus.BAD_REQUEST,"토큰을 파싱할 수 없습니다"),
	TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
	NOT_LOGGED_IN(HttpStatus.UNAUTHORIZED, "로그인후 이용이 가능합니다"),

	// member
	DUPLICATED_EMAIL(HttpStatus.CONFLICT, "이메일이 중복입니다"),
	NOT_VERIFIED_CODE(HttpStatus.BAD_REQUEST, "잘못된 인증 코드입니다"),
	DUPLICATED_NICKNAME(HttpStatus.CONFLICT, "닉네임이 중복입니다"),
	EMAIL_NOT_MATCH(HttpStatus.BAD_REQUEST, "이메일이 일치하지 않습니다"),
	PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "패스워드가 일치하지 않습니다"),
	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "멤버를 찾을 수 없습니다"),

	// travelogue
	POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시물을 찾을 수 없습니다"),
	TRAVELOGUE_NOT_FOUND(HttpStatus.NOT_FOUND, "여행기를 찾을 수 없습니다"),
	NO_AUTHORIZATION_TO_TRAVELOGUE(HttpStatus.UNAUTHORIZED, "접근권한이 없습니다."),
	CANNOT_PUBLISH_TRAVELOGUE(HttpStatus.BAD_REQUEST, "아직 작성이 완료되지 않은 페이지가 있기 때문에 공개할 수 없습니다."),

	SUB_TRAVELOGUE_NOT_FOUND(HttpStatus.NOT_FOUND, "게시물을 찾을 수 없습니다."),
	TRAVELOGUE_NOT_CONTAIN_SUB_TRAVELOGUE(HttpStatus.BAD_REQUEST, "잘못된 접근입니다."),

	// scrap
	STORAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 스크랩 문서를 찾을 수 없습니다"),
	NOT_SEND_EMAIL(HttpStatus.NOT_ACCEPTABLE,"email 발송에 실패 했습니다.");

  private final HttpStatus status;
	private final String message;

	ErrorCode(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

	public int getStatusValue() {
		return this.status.value();
	}

}
