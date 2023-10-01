package shop.zip.travel.domain.post.subTravelogue.dto.res;

import shop.zip.travel.domain.post.subTravelogue.data.Address;

public record AddressRes(
    String region
) {

  public static AddressRes toDto(Address address) {
    return new AddressRes(
        ""
    );
  }
}
