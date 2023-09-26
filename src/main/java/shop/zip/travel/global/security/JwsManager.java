package shop.zip.travel.global.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwsManager {

  private final JwsProperties jwsProperties;

  public String createAccessToken(Long memberId) {
    Date nowDate = new Date();
    Date expiryDate = new Date(nowDate.getTime() + jwsProperties.getExpiredTime());

    Map<String, Object> customClaim = new HashMap<>();
    customClaim.put("memberId", memberId);

    return Jwts.builder()
        .setHeaderParam("typ", "JWS")
        .setIssuer("Travelogues")
        .setExpiration(expiryDate)
        .setIssuedAt(nowDate)
        .addClaims(customClaim)
        .signWith(jwsProperties.getSecretKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public boolean validateAccessToken(String jws) {
    try {
      Jwts.parserBuilder()
          .setSigningKey(jwsProperties.getSecretKey())
          .build()
          .parseClaimsJwt(jws);
      return true;
    } catch (JwtException ex) {
      // TODO 비어있거나(인증 필요없는 요청) 만료기간이 되었거나 하는 요청들에 대해 client 전달 어떻게 할 것인가?
    }
    return false;
  }

  public String getMemberIdFromAccessToken(String jws) {
    return Jwts.parserBuilder()
        .setSigningKey(jwsProperties.getSecretKey())
        .build()
        .parseClaimsJwt(jws)
        .getBody()
        .get("memberId")
        .toString();
  }
}
