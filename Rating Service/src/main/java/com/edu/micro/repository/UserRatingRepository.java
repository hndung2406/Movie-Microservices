package com.edu.micro.repository;

import com.edu.micro.model.UserRating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRatingRepository extends MongoRepository<UserRating, String> {

    Optional<UserRating> findByUserId(String userId);

    @Query("{ 'userRatings._id': ?0 }")
    List<UserRating> findByMovieId(String movieId);

}
