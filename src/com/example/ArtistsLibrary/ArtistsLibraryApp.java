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

    public static Repository getRepository() {
        return repository;
    }

    public static void setRepository(Repository repository) {
        ArtistsLibraryApp.repository = repository;
    }
}
