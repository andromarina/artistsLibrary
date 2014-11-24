package com.example.ArtistsLibrary;

import android.app.Application;
import android.widget.Toast;
import com.example.ArtistsLibrary.model.ExceptionHandler;
import com.example.ArtistsLibrary.model.Repository;


/**
 * Created by mara on 11/22/14.
 */
public class ArtistsLibraryApp extends Application implements ExceptionHandler {
    private static Repository repository;
    private final String url = "https://www.dropbox.com/s/yzrp4huvmt3jkv9/data.json?dl=1";

    @Override
    public void onCreate() {
        super.onCreate();
        repository = new Repository(url, this);
        repository.loadInBackground();
    }

    public static Repository getRepository() {
        return repository;
    }


    @Override
    public void onException(Exception ex) {
        Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
    }
}
