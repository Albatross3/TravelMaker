package shop.zip.travel.domain.post.travelogue.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import shop.zip.travel.domain.post.travelogue.entity.Travelogue;

@Repository
public interface TravelogueRepository extends MongoRepository<Travelogue, ObjectId> {


//  @Query("select new shop.zip.travel.domain.post.travelogue.dto.TravelogueSimple("
//      + "t.id,t.title, t.period, t.cost, t.country, t.thumbnail, m.nickname, m.profileImageUrl, count(l)) "
//      + "from Travelogue t "
//      + "inner join Member m "
//      + "on m.id = t.member.id "
//      + "left join Like l "
//      + "on l.travelogue.id = t.id "
//      + "group by t.id ")
//  Slice<TravelogueSimple> findAllBySlice(
//      @Param("pageable") Pageable pageable);
//
//  @Lock(LockModeType.PESSIMISTIC_WRITE)
//  @Query("select t "
//      + "from Travelogue t "
//      + "left join fetch t.member "
//      + "where t.id = :travelogueId ")
//  Optional<Travelogue> getTravelogueDetail(@Param("travelogueId") Long travelogueId);
//
//  @Query(
//      "select new shop.zip.travel.domain.post.travelogue.dto.TravelogueSimple("
//          + "t.id, t.title, t.period, t.cost, t.country, t.thumbnail, m.nickname, m.profileImageUrl, count(l))"
//          + "from Travelogue t "
//          + "inner join t.member m "
//          + "left join Like l "
//          + "on l.travelogue.id = t.id "
//          + "where m.id = :memberId "
//          + "group by t.id")
//  Slice<TravelogueSimple> getMyTravelogues(
//      @Param("memberId") Long memberId,
//      @Param("pageable") Pageable pageable);


}
