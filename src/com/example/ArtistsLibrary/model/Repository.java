package com.example.ArtistsLibrary.model;



import android.os.AsyncTask;
import android.util.Log;
import com.example.ArtistsLibrary.utils.MyJSONParser;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Created by mara on 11/22/14.
 */
public class Repository {

    private final String LOG_TAG = getClass().getSimpleName();
    private final String url;
    private ArrayList<Artist> artists = new ArrayList<Artist>();
    private ArrayList<Album> albums = new ArrayList<Album>();
    private RepositoryUpdateListener listener;


    public Repository(String url){
         this.url = url;
    }

    public void subscribeForUpdates(RepositoryUpdateListener listener) {
        this.listener = listener;
    }

    public void loadInBackground()
    {
        Repository.AsyncTaskParseJSON backgroundTask = new Repository.AsyncTaskParseJSON();
        backgroundTask.execute(url);
    }

    public ArrayList<Artist> getArtistsList()
    {
       return this.artists;
    }

    public Artist getArtistById(int artistId)
    {
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

    private void parseArtistsList(JSONObject jsonObject) {
        TreeSet set = new TreeSet();
        JSONArray artistsJSON = (JSONArray) jsonObject.get("artists");
        for(int i = 0; i < artistsJSON.size(); ++i)
        {
            JSONObject artistJSON = (JSONObject) artistsJSON.get(i);
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
        artists.addAll(set);
    }

    private void parseAlbums(JSONObject jsonObject) {
        JSONArray albumsJson = (JSONArray) jsonObject.get("albums");
        for(int i = 0; i < albumsJson.size(); ++i){
            JSONObject albumJSON = (JSONObject) albumsJson.get(i);
            long albumId = (Long) albumJSON.get("id");
            long artistId = (Long) albumJSON.get("artistId");
            String title = (String) albumJSON.get("title");
            Album album = new Album((int)albumId, (int) artistId, title);
            String type = (String) albumJSON.get("type");
            album.setType(type);
            String picture = (String) albumJSON.get("picture");
            album.setPicture(picture);
            albums.add(album);
        }
    }

    private void downloadFinished(JSONObject jsonObject) {
        parseArtistsList(jsonObject);
        parseAlbums(jsonObject);
        if(listener != null) {
            listener.onRepositoryUpdate();
        }
    }

    private class AsyncTaskParseJSON extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... urlStrs) {
            JSONObject jsonObjResult = null;
            try {
                jsonObjResult = MyJSONParser.getJSONObject(urlStrs[0]);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonObjResult;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            downloadFinished(jsonObject);
        }
    }
}
