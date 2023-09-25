package shop.zip.travel.global.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
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

  public String getMemberIdFromAccessToken(String jws) {
    Jwt<Header, Claims> parsedJws = validateAccessToken(jws);
    return parsedJws.getBody()
        .get("memberId")
        .toString();
  }

  private Jwt<Header,Claims> validateAccessToken(String jws) {
    return Jwts.parserBuilder()
        .setSigningKey(jwsProperties.getSecretKey())
        .build()
        .parseClaimsJwt(jws);
  }
}
