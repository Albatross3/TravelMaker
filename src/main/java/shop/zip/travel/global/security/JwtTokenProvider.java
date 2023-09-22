package shop.zip.travel.global.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import shop.zip.travel.global.error.BusinessException;
import shop.zip.travel.global.error.ErrorCode;
import shop.zip.travel.global.error.exception.JsonNotParsingException;
import shop.zip.travel.global.util.Constants;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

  private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);

  private final CustomUserDetailsService customUserDetailsService;

  private final byte[] SECRET_KEY_BYTE_ARRAY = Constants.getSecretKey().getBytes();
  private final long ACCESS_TOKEN_EXPIRED_TIME = Duration.ofMinutes(1).toMillis();




  public String removeBearer(String bearerToken) {
    return bearerToken.substring("Bearer ".length());
  }

  public boolean validateAccessToken(String token) {
    try {
      String accessToken = removeBearer(token);
      Jwts.parserBuilder().setSigningKey(SECRET_KEY_BYTE_ARRAY);
      return true;
    } catch (SignatureException ex) {
      log.error("유효하지 않은 JWT 서명");
      throw new BusinessException(ErrorCode.TOKEN_EXCEPTION);
    } catch (MalformedJwtException ex) {
      log.error("유효하지 않은 JWT 토큰");
      throw new BusinessException(ErrorCode.TOKEN_EXCEPTION);
    } catch (ExpiredJwtException ex) {
      log.error("만료된 JWT 토큰");
      throw new BusinessException(ErrorCode.TOKEN_EXCEPTION);
    } catch (UnsupportedJwtException ex) {
      log.error("지원하지 않는 JWT 토큰");
      throw new BusinessException(ErrorCode.TOKEN_EXCEPTION);
    } catch (IllegalArgumentException ex) {
      log.error("비어있는 토큰");
      throw new BusinessException(ErrorCode.TOKEN_EXCEPTION);
    }
  }

  public String getMemberId(String accessToken) {
    return Jwts.parser().setSigningKey(SECRET_KEY_BYTE_ARRAY).parseClaimsJws(accessToken)
        .getBody().get("memberId").toString();
  }

  public Authentication getAuthentication(String token) {
    Long memberId = Long.parseLong(getMemberId(token));
    UserDetails userDetails = customUserDetailsService.loadUserById(memberId);
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public String createAccessToken(Long memberId) {
    Date nowDate = new Date();
    Date expiryDate = new Date(nowDate.getTime() + ACCESS_TOKEN_EXPIRED_TIME);
    Key key = Keys.hmacShaKeyFor(SECRET_KEY_BYTE_ARRAY);

    Map<String, Object> customClaim = new HashMap<>();
    customClaim.put("memberId", memberId);

    return Jwts.builder()
        .setIssuer("Travelogues")
        .setExpiration(expiryDate)
        .setIssuedAt(nowDate)
        .addClaims(customClaim)
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }



  public String getMemberIdUsingDecode(String accessToken) {
    String[] chunks = accessToken.split("\\.");
    Base64.Decoder decoder = Base64.getUrlDecoder();

    String payload = new String(decoder.decode(chunks[1]));

    ObjectMapper mapper = new ObjectMapper();
    try {
      Map<String, Object> returnMap = mapper.readValue(payload, Map.class);
      return returnMap.get("memberId").toString();
    } catch (JsonProcessingException e) {
      throw new JsonNotParsingException(ErrorCode.JSON_NOT_PARSING);
    }

  }
}
