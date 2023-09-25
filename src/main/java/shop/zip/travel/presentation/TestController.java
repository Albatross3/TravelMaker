package shop.zip.travel.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.zip.travel.global.security.JwsManager;

@RequiredArgsConstructor
@RestController
public class TestController {

  private final JwsManager jwsManager;

  @GetMapping("/token")
  public String getToken() {
    return jwsManager.createAccessToken(1L);
  }
}
