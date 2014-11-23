package com.example.ArtistsLibrary;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.androidquery.AQuery;
import com.example.ArtistsLibrary.model.Album;
import com.example.ArtistsLibrary.model.Artist;
import com.example.ArtistsLibrary.model.Repository;

import java.util.ArrayList;

/**
 * Created by mara on 11/22/14.
 */
public class ArtistDetailsActivity extends Activity {

    private ListView albumsList;
    private TextView genre;
    private TextView description;
    private LinearLayout descriptionLayout;
    private LinearLayout genresLayout;
    private final String LOG_TAG = this.getClass().getSimpleName();
    private Artist artist;
    private AQuery aq;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artist_details_activity);
        Repository repository = ArtistsLibraryApp.getRepository();
        int artistId = getIntent().getIntExtra("artistID", 1);
        this.artist = repository.getArtistById(artistId);
        aq = new AQuery(this);
        findViews();
        setValues();
        ArrayList<Album> albums = repository.getArtistAlbums(artistId);
        prepareAlbumsList(albums);
    }


    private void findViews()
    {
        this.albumsList = (ListView) findViewById(R.id.albums_list);
        this.genre = (TextView) findViewById(R.id.genre);
        this.description = (TextView) findViewById(R.id.description);
        this.descriptionLayout = (LinearLayout) findViewById(R.id.description_layout);
        this.genresLayout = (LinearLayout) findViewById(R.id.genres_layout);
    }

    private void setValues(){
        setDescription();
        setGenre();
        setArtistPicture();
    }

    private void setDescription(){
        String description = this.artist.getDescription();
         if(description.isEmpty()){
             this.descriptionLayout.setVisibility(View.GONE);
         }
         else {
             this.description.setText(description);
         }
    }

    private void setGenre(){
        String genres = this.artist.getGenres();
        if(genres.isEmpty()){
            this.genresLayout.setVisibility(View.GONE);
        }
        else {
            this.genre.setText(genres);
        }
    }

    private void setArtistPicture() {
        String pictureURL = this.artist.getPictureURL();
        if(!pictureURL.isEmpty()) {
            aq.id(R.id.artist_picture_big).image(pictureURL, true, true, 0, 0, null, AQuery.FADE_IN);
        }
    }

    private void prepareAlbumsList(ArrayList<Album> albums)
    {
        if (albums.isEmpty()) {
            Log.d(LOG_TAG, "Albums array is empty; artistID ");
            this.albumsList.setVisibility(View.GONE);
            return;
        }

        AlbumsListAdapter adapter = new AlbumsListAdapter(this, R.layout.album_list_item, albums);
        this.albumsList.setAdapter(adapter);
    }
}
