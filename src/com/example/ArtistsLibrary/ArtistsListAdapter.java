package com.example.ArtistsLibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.androidquery.AQuery;
import com.androidquery.callback.ImageOptions;
import com.example.ArtistsLibrary.model.Artist;

import java.util.ArrayList;

/**
 * Created by mara on 11/22/14.
 */
public class ArtistsListAdapter extends ArrayAdapter<Artist>{

    private String LOG_TAG = this.getClass().getSimpleName();

    public ArtistsListAdapter(Context context, int listItemResourceId, ArrayList<Artist> artists) {
        super(context, listItemResourceId, artists);
    }

    @Override
    public View getView(int position, View item, ViewGroup parent) {

        if (item == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            item = inflater.inflate(R.layout.artist_list_item, null);
        }
        Artist artist = super.getItem(position);

        TextView name = (TextView) item.findViewById(R.id.artist_name);
        name.setText(artist.getName());

        TextView genres = (TextView) item.findViewById(R.id.genres);

        if (artist.getGenres().isEmpty()) {
            genres.setVisibility(View.GONE);
        } else {
            genres.setText(artist.getGenres());
        }

        AQuery aq = new AQuery(item);
        ImageOptions options = new ImageOptions();
        options.round = 15;
        options.memCache = true;
        options.fileCache = true;
        options.animation = AQuery.FADE_IN_NETWORK;
        options.ratio = 1.0f;

        if(!artist.getPictureURL().isEmpty()) {
            aq.id(R.id.artist_picture).image(artist.getPictureURL(), options);
        }

        return item;
    }



}
