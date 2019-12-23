package com.edu.micro.model;

public class MovieCatalog {

    private Movie movie;
    private double rating;

    public MovieCatalog() {
    }

    public MovieCatalog(Movie movie, double rating) {
        this.movie = movie;
        this.rating = rating;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "MovieCatalog{" +
                "movie=" + movie +
                ", rating='" + rating + '\'' +
                '}';
    }
}
