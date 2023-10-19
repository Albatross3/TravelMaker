package shop.zip.travel.domain.post.travelogue.value;

import jakarta.persistence.Column;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Period {

  private static final long NO_DATE = -1L;

  @Column(nullable = false)
  private LocalDate startDate;

  @Column(nullable = false)
  private LocalDate endDate;

  @Builder
  public Period(LocalDate startDate, LocalDate endDate) {
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public long getNights() {
    return ChronoUnit.DAYS.between(this.startDate, this.endDate);
  }

}
