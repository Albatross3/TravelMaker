package shop.zip.travel.global.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Objects;
import org.springframework.http.ResponseCookie;
import org.springframework.web.util.WebUtils;

public class CookieUtil {

  private static final String VIEW_COUNT = "viewCountCookie_";
  private static final int DAY = 60 * 60 * 24;

  private CookieUtil() {
  }

  public static boolean canAddViewCount(HttpServletRequest request, HttpServletResponse response,
      Long travelogueId) {
    if (hasNotViewCountCookie(request, travelogueId)) {
      ResponseCookie cookie = ResponseCookie.from(VIEW_COUNT + travelogueId, String.valueOf(travelogueId))
          .path("/")
          .maxAge(DAY)
          .secure(true)
          .sameSite("None")
          .build();

      response.addHeader("Set-Cookie", cookie.toString());
      return true;
    }
    return false;
  }

  private static boolean hasNotViewCountCookie(HttpServletRequest request, Long travelogueId) {
    Cookie cookie = WebUtils.getCookie(request, VIEW_COUNT + travelogueId);

    if (Objects.isNull(cookie)) {
      return true;
    }
    return !cookie.getValue().equals(String.valueOf(travelogueId));
  }
}