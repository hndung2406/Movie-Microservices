package com.edu.micro.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "catalog")
public class UserCatalog {

    @Id
    private String id;

    private String name;

    private String desc;

    private int rating;

    private String director;

    private String type;

    public UserCatalog() {
    }

    public UserCatalog(String name, String desc, int rating, String director, String type) {
        this.name = name;
        this.desc = desc;
        this.rating = rating;
        this.director = director;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", rating=" + rating +
                ", director='" + director + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
