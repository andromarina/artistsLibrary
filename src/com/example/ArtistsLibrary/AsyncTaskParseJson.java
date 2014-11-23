package com.example.ArtistsLibrary;

import android.os.AsyncTask;
import com.example.ArtistsLibrary.utils.MyJSONParser;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.simple.JSONObject;

import java.io.IOException;

/**
 * Created by mara on 11/22/14.
 */
public class AsyncTaskParseJson extends AsyncTask<String, Void, JSONObject> {


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
    protected void onPostExecute(JSONObject result) {
        super.onPostExecute(result);
        synchronized (this) {
            this.notifyAll();
        }

    }
}
