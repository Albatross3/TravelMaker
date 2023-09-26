package shop.zip.travel.global.security;

import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import javax.crypto.SecretKey;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class JwsProperties {
  @Value("${jws.secret-key}")
  private String stringSecretKey;

  @Value("${jws.expiration-time}")
  private Long expiredTime;

  private SecretKey secretKey;

  @PostConstruct
  private void makeSecretKey() {
    secretKey = Keys.hmacShaKeyFor(stringSecretKey.getBytes());
  }
}
