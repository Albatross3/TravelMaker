package shop.zip.travel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableJpaAuditing
@EnableMongoRepositories(basePackages = {"shop.zip.travel.domain.post", "shop.zip.travel.domain.storage"})
@EnableJpaRepositories(basePackages = {"shop.zip.travel.domain.member"})
@SpringBootApplication
public class TraveloguesApplication {

  public static void main(String[] args) {
    SpringApplication.run(TraveloguesApplication.class, args);
  }

}
