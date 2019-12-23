package com.edu.micro.controller;

import com.edu.micro.model.Rating;
import com.edu.micro.model.UserRating;
import com.edu.micro.repository.RatingRepository;
import com.edu.micro.repository.UserRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/rating", produces = MediaType.APPLICATION_JSON_VALUE)
public class RatingController {

    private RatingRepository ratingRepository;

    private UserRatingRepository userRatingRepository;

    @Autowired
    public RatingController(RatingRepository ratingRepository, UserRatingRepository userRatingRepository) {
        this.ratingRepository = ratingRepository;
        this.userRatingRepository = userRatingRepository;
    }

    @PostMapping("/user/add")
    public ResponseEntity<UserRating> addUserRating(@RequestBody UserRating userRating) {
        userRatingRepository.save(userRating);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserRating> getUserRating(@PathVariable("userId") String userId) {
        Optional<UserRating> userRating = userRatingRepository.findByUserId(userId);
        return new ResponseEntity<>(userRating.get(), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserRating>> getAllRating() {
        List<UserRating> userRatings = userRatingRepository.findAll();
        return new ResponseEntity<>(userRatings, HttpStatus.OK);
    }

    @GetMapping("/averageRating/{movieId}")
    public ResponseEntity<Map<String, Object>> getAverageMovieRating(@PathVariable("movieId") String movieId) {
        List<UserRating> userRatingByMovieId = userRatingRepository.findByMovieId(movieId);
        double sumRating = 0;
        double countRating = 0;
        for (UserRating u : userRatingByMovieId) {
            for (Rating r : u.getUserRatings()) {
                if (r.getMovieId().equalsIgnoreCase(movieId)) {
                    sumRating += r.getRating();
                    countRating += 1;
                }
            }
        }
        double avgRating = 0;
        if(sumRating != 0 && countRating != 0) {
            avgRating = sumRating / countRating;
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("movieId", movieId);
        result.put("avgRating", avgRating);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
