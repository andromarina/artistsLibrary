package com.example.ArtistsLibrary.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;


/**
 * Created by mara on 11/22/14.
 */
public class MyJSONParser {

    JSONObject jsonObj;

    public MyJSONParser(String url) throws ParseException,
            ClientProtocolException, JSONException, IOException {

        jsonObj = getJSONObject(url);
    }

    public JSONObject getJSONObject() {
        return jsonObj;
    }

    public static JSONObject parseStringToJSON(String jsonInputString) {
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonInputString);
            return jsonObject;

        } catch (ParseException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static JSONObject getJSONObject(String url)
            throws IOException, JSONException {

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpUriRequest req = new HttpGet(url);
        HttpResponse resp = httpClient.execute(req);
        HttpEntity entity = resp.getEntity();
        String temp = EntityUtils.toString(entity);
        JSONParser jsonParser = new JSONParser();
        JSONObject obj = null;
        try {
            obj = (JSONObject) jsonParser.parse(temp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return obj;

    }
}
