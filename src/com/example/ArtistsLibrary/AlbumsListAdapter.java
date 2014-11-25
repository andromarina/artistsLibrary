package com.example.ArtistsLibrary;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.androidquery.AQuery;
import com.androidquery.callback.ImageOptions;
import com.example.ArtistsLibrary.model.Album;

import java.util.ArrayList;

/**
 * Created by mara on 11/22/14.
 */
public class AlbumsListAdapter extends ArrayAdapter<Album> {
    private String LOG_TAG = this.getClass().getSimpleName();

    public AlbumsListAdapter(Context context, int listItemResourceId, ArrayList<Album> albums) {
        super(context, listItemResourceId, albums);
    }

    @Override
    public View getView(int position, View item, ViewGroup parent) {

        if (item == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            item = inflater.inflate(R.layout.album_list_item, null);
        }
        Album album = super.getItem(position);

        TextView name = (TextView) item.findViewById(R.id.album_name);
        name.setText(album.getTitle());

        AQuery aq = new AQuery(item);
        ImageOptions options = new ImageOptions();
        options.round = 15;
        options.memCache = true;
        options.fileCache = true;
        options.animation = AQuery.FADE_IN_NETWORK;
        options.ratio = 1.0f;

        if(!album.getPictureURL().isEmpty()) {
            Log.d(LOG_TAG, "Album picture URL: " + album.getPictureURL());

            aq.id(R.id.album_picture).image(album.getPictureURL(), options);

        }
        return item;
    }
}
