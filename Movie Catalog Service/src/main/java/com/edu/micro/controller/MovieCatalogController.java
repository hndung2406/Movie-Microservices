package com.edu.micro.controller;

import com.edu.micro.model.Movie;
import com.edu.micro.model.MovieCatalog;
import com.edu.micro.model.UserCatalog;
import com.edu.micro.model.UserRating;
import com.edu.micro.repository.MovieCatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/catalog", produces = MediaType.APPLICATION_JSON_VALUE)
public class MovieCatalogController {

    private MovieCatalogRepository movieRepository;

    private RestTemplate restTemplate;

    private WebClient.Builder webClientBuilder;

    @Autowired
    public MovieCatalogController(MovieCatalogRepository movieRepository, RestTemplate restTemplate, WebClient.Builder webClientBuilder) {
        this.movieRepository = movieRepository;
        this.restTemplate = restTemplate;
        this.webClientBuilder = webClientBuilder;
    }

    @PostMapping("/add")
    public ResponseEntity<UserCatalog> addCatalog(@RequestBody UserCatalog catalog) {
        movieRepository.save(catalog);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> getUserCatalog(@PathVariable("userId") String userId) {
        List<UserCatalog> catalogs = new ArrayList<>();
        // Get all rated movie IDs
        ResponseEntity<UserRating> userRatings = restTemplate.getForEntity("http://movie-rating-service/rating/user/" + userId, UserRating.class);

        // For each movie ID, call movie info service and get details
        userRatings.getBody().getUserRatings().stream().forEach(rating -> {
            Movie movie = restTemplate.getForObject("http://movie-info-service/movie/" + rating.getMovieId(), Movie.class);
            catalogs.add(new UserCatalog(movie.getName(), movie.getDescription(), rating.getRating(), movie.getDirector(), movie.getType()));
        });

        // A map to render output
        Map<String, Object> userRatingMap = new HashMap<String, Object>();
        userRatingMap.put("userId", userId);
        userRatingMap.put("catalogs", catalogs);

        // Put them all together
        return new ResponseEntity<>(userRatingMap, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MovieCatalog>> getMovieCatalog() {
        List<MovieCatalog> movieCatalogs = new ArrayList<>();
        ResponseEntity<Movie[]> moviesRest = restTemplate.getForEntity("http://movie-info-service/movie/all", Movie[].class);
        List<Movie> movies = Arrays.asList(moviesRest.getBody());
        movies.forEach(m -> {
            Map<String, Object> movie = restTemplate.getForObject("http://movie-rating-service/rating/averageRating/" + m.getMovieId(), Map.class);
            double avgRating = (double) movie.get("avgRating");
            movieCatalogs.add(new MovieCatalog(m, avgRating));
        });
        return new ResponseEntity<>(movieCatalogs, HttpStatus.OK);
    }

}
