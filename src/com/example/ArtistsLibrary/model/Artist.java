package com.example.ArtistsLibrary.model;

/**
 * Created by mara on 11/21/14.
 */
public class Artist implements Comparable<Artist>{

    private final int id;
    private final String name;
    private String description;
    private String genres;
    private String picture;

    public Artist(int id, String name) {
        this.id = id;
        this.name = name;
        this.description = "";
        this.genres = "";
        this.picture = "";
    }

    public int getId() {
        return id;
    }

    public String getPictureURL() {
        return picture;
    }

    public String getGenres() {
        return genres;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public void setPicture(String pictureLocation) {
        this.picture = pictureLocation;
    }

    @Override
    public int compareTo(Artist another) {
        return this.getName().compareTo(another.getName());
    }
}
