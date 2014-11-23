package com.example.ArtistsLibrary;

import android.app.Application;
import com.example.ArtistsLibrary.model.Repository;
import org.json.simple.JSONObject;

import java.util.concurrent.ExecutionException;


/**
 * Created by mara on 11/22/14.
 */
public class ArtistsLibraryApp extends Application{
    private static Repository repository;
    private final String url = "https://www.dropbox.com/s/yzrp4huvmt3jkv9/data.json?dl=1";

    @Override
    public void onCreate() {
        super.onCreate();
        repository = new Repository(url);
        repository.loadInBackground();
    }

    public static Repository getRepository() {
        return repository;
    }


}
