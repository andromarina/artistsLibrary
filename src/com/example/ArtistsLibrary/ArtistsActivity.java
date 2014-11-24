package com.example.ArtistsLibrary;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.androidquery.util.AQUtility;
import com.example.ArtistsLibrary.model.Artist;
import com.example.ArtistsLibrary.model.Repository;
import com.example.ArtistsLibrary.model.RepositoryUpdateListener;
import java.util.ArrayList;

public class ArtistsActivity extends Activity implements RepositoryUpdateListener {
    /**
     * Called when the activity is first created.
     */
    private final String LOG_TAG = "ArtistsActivity";

    private ListView artistsList;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artists_activity);
        findViews();
        ArrayList<Artist> artists = ArtistsLibraryApp.getRepository().getArtistsList();
        if(ArtistsLibraryApp.getRepository().getStatus() == Repository.Status.UNDEFINED) {
            startProgressDialog();
        }
        prepareArtistsList(artists);
        ArtistsLibraryApp.getRepository().subscribeForUpdates(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(progressDialog != null) {
            this.progressDialog.dismiss();
        }
        long triggerSize = 3000000; //starts cleaning when cache size is larger than 3M
        long targetSize = 2000000;      //remove the least recently used files until cache size is less than 2M
        AQUtility.cleanCacheAsync(this, triggerSize, targetSize);
    }

    private void findViews() {
        this.artistsList = (ListView) findViewById(R.id.artists_list);
    }

    private void prepareArtistsList(final ArrayList<Artist> artists) {

        ArtistsListAdapter adapter = new ArtistsListAdapter(this, R.layout.artist_list_item, artists);
        this.artistsList.setAdapter(adapter);

        this.artistsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mOnItemClick(artists, position);
            }
        });
    }

    private void startProgressDialog() {
        progressDialog = ProgressDialog.show(this, "test", "wait");
        progressDialog.setCancelable(false);
        progressDialog.setContentView(R.layout.loading_spinner);
    }

    private void mOnItemClick(ArrayList<Artist> artists, int position) {
        Intent intent = new Intent(this, ArtistDetailsActivity.class);
        int artistID = artists.get(position).getId();
        intent.putExtra("artistID", artistID);
        startActivity(intent);
    }

    @Override
    public void onRepositoryUpdate() {
        if(progressDialog != null) {
            progressDialog.dismiss();
        }
        prepareArtistsList(ArtistsLibraryApp.getRepository().getArtistsList());
    }
}
