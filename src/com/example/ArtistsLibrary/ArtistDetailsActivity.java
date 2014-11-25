package com.example.ArtistsLibrary;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.androidquery.AQuery;
import com.example.ArtistsLibrary.model.Album;
import com.example.ArtistsLibrary.model.Artist;
import com.example.ArtistsLibrary.model.Repository;

import java.util.ArrayList;

/**
 * Created by mara on 11/22/14.
 */
public class ArtistDetailsActivity extends Activity implements View.OnClickListener {

    private ListView albumsList;
    private TextView genre;
    private TextView description;
    private TextView albumsLabel;
    private ImageButton more;
    private LinearLayout descriptionLayout;
    private LinearLayout genresLayout;
    private final String LOG_TAG = this.getClass().getSimpleName();
    private Artist artist;
    private AQuery aq;
    boolean isHidden = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artist_details_activity_new);
        Repository repository = ArtistsLibraryApp.getRepository();
        int artistId = getIntent().getIntExtra("artistID", 1);
        Log.d(LOG_TAG, "ArtistDetailsActivity: artistID: " + artistId);
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
        this.albumsLabel = (TextView) findViewById(R.id.albums_label);
        this.more = (ImageButton) findViewById(R.id.more);
    }

    private void setValues(){
        getActionBar().setDisplayShowTitleEnabled(true);
        getActionBar().setTitle(this.artist.getName());
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

        this.more.setOnClickListener(this);
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
            this.albumsLabel.setVisibility(View.GONE);
            return;
        }

        AlbumsListAdapter adapter = new AlbumsListAdapter(this, R.layout.album_list_item, albums);
        this.albumsList.setAdapter(adapter);
        setListViewHeightBasedOnChildren(this.albumsList);
    }

    @Override
    public void onClick(View v) {
        if (isHidden) {
            this.description.setMaxLines(Integer.MAX_VALUE);
            isHidden = false;
            this.more.setRotationX(180);
        } else {
            this.description.setMaxLines(3);
            isHidden = true;
            this.more.setRotationX(-180);
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
