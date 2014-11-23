package com.example.ArtistsLibrary.model;

/**
 * Created by mara on 11/21/14.
 */
public class Album implements Comparable<Album>{

    private final int id;
    private final int artistId;
    private final String title;
    private String type;
    private String picture;

    public Album(int id, int artistId, String title){
        this.id = id;
        this.artistId = artistId;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public int getArtistId() {
        return artistId;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getPictureURL() {
        return picture;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public int compareTo(Album another) {
        return this.getTitle().compareTo(another.getTitle());
    }
}
