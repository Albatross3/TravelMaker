package shop.zip.travel.domain.post.subTravelogue.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import shop.zip.travel.domain.post.subTravelogue.entity.SubTravelogue;

@Repository
public interface SubTravelogueRepository extends MongoRepository<SubTravelogue, ObjectId> {

}
