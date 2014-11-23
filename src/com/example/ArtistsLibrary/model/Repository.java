package com.example.ArtistsLibrary.model;



import android.util.Log;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;

/**
 * Created by mara on 11/22/14.
 */
public class Repository {
    private JSONObject jsonObject;
    private final String LOG_TAG = getClass().getSimpleName();

    public Repository(JSONObject jsonObject){
        this.jsonObject = jsonObject;
    }

    public ArrayList<Artist> getArtistsList()
    {
        TreeSet set = new TreeSet();
        JSONArray artists = (JSONArray) jsonObject.get("artists");
        for(int i = 0; i < artists.size(); ++i)
        {
            JSONObject artistJSON = (JSONObject) artists.get(i);
            long artistId = (Long) artistJSON.get("id");
            String artistName = (String) artistJSON.get("name");
            Artist artist = new Artist((int) artistId, artistName);
            String description = (String) artistJSON.get("description");
            artist.setDescription(description);
            String genres = (String) artistJSON.get("genres");
            artist.setGenres(genres);
            String picture = (String) artistJSON.get("picture");
            artist.setPicture(picture);
            set.add(artist);
            Log.d(LOG_TAG, "Artist created. Id = " + artistId + ", Name " + artistName);
        }
        ArrayList<Artist> result = new ArrayList<Artist>();
        result.addAll(set);
        return result;
    }

    public Artist getArtistById(int artistId)
    {
        ArrayList<Artist> artists = getArtistsList();
        for(Artist artist : artists){
            if(artist.getId() == artistId)
            {
                return artist;
            }
        }
        return null;
    }

    public ArrayList<Album> getArtistAlbums(int artistId)
    {
        ArrayList<Album> albums = getAllAlbums();
        TreeSet filteredAlbums = new TreeSet();

        for(Album album : albums){
            if(album.getArtistId() == artistId){
                filteredAlbums.add(album);
            }
        }
        ArrayList<Album> result = new ArrayList<Album>();
        result.addAll(filteredAlbums);
        return result;
    }

    private ArrayList<Album> getAllAlbums()
    {
        ArrayList<Album> result = new ArrayList<Album>();

        JSONArray albums = (JSONArray) jsonObject.get("albums");
        for(int i = 0; i < albums.size(); ++i){
            JSONObject albumJSON = (JSONObject) albums.get(i);
            long albumId = (Long) albumJSON.get("id");
            long artistId = (Long) albumJSON.get("artistId");
            String title = (String) albumJSON.get("title");
            Album album = new Album((int)albumId, (int) artistId, title);
            String type = (String) albumJSON.get("type");
            album.setType(type);
            String picture = (String) albumJSON.get("picture");
            album.setPicture(picture);
            result.add(album);
        }
        return result;
    }
}
