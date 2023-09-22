package shop.zip.travel.global.util;

import org.springframework.beans.factory.annotation.Value;

public class Constants {

  static class Constant {
    @Value("${spring.jwt.secret-key}")
    private static String secretKey;
  }

  public static String getSecretKey() {
    return Constant.secretKey;
  }
}
