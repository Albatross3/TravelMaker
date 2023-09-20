package shop.zip.travel.global.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtTokenProviderTest {

  @Autowired
  JwtTokenProvider jwtTokenProvider;

  @Test
  void testCreateAccessToken() {
    String accessToken = jwtTokenProvider.createAccessToken(1L);
    System.out.println(accessToken);
  }
}