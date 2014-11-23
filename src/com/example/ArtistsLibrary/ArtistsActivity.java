package com.example.ArtistsLibrary;

import android.app.Activity;
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
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ArtistsActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    private final String LOG_TAG = this.getClass().getSimpleName();

    private ListView artistsList;
    private TextView errorMessage;
    private final String url = "https://www.dropbox.com/s/yzrp4huvmt3jkv9/data.json?dl=1";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artists_activity);
        findViews();
        if(ArtistsLibraryApp.getRepository() == null) {
            createRepository();
        }
        prepareArtistsList(ArtistsLibraryApp.getRepository().getArtistsList());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        long triggerSize = 3000000; //starts cleaning when cache size is larger than 3M
        long targetSize = 2000000;      //remove the least recently used files until cache size is less than 2M
        AQUtility.cleanCacheAsync(this, triggerSize, targetSize);
    }

    private void createRepository()
    {
        AsyncTaskParseJson backgroundTask = new AsyncTaskParseJson();
        try {
            JSONObject result = backgroundTask.execute(url).get();
            Repository repository = new Repository(result);
            ArtistsLibraryApp.setRepository(repository);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void findViews()
    {
        this.artistsList = (ListView) findViewById(R.id.artists_list);
        this.errorMessage = (TextView) findViewById(R.id.error_message);
    }

    private void prepareArtistsList(final ArrayList<Artist> artists) {

        if (artists.isEmpty()) {
            Log.d(LOG_TAG, "Artists array is empty");
            this.errorMessage.setVisibility(View.VISIBLE);
            return;
        }

        ArtistsListAdapter adapter = new ArtistsListAdapter(this, R.layout.artist_list_item, artists);
        this.artistsList.setAdapter(adapter);

        this.artistsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mOnItemClick(artists, position);
            }
        });
    }

    private void mOnItemClick(ArrayList<Artist> artists, int position) {
        Intent intent = new Intent(this, ArtistDetailsActivity.class);
        int artistID = artists.get(position).getId();
        intent.putExtra("artistID", artistID);
        startActivity(intent);
    }

}
