package com.ogokilearning.popularmoviesapp;

import android.net.Uri;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by ogoki on 2017-10-10.
 */

public class NetworkUtils {

    /**
     *  API key for themoviedb V3 AUTH
     */
    public static final String  DATA_API_KEY = "45db09bc5d4a4600174a9e64e6c5f6f6";

    /**
     *  Base URL for  requesting images from the movie database
     */
    final static String BASE_URL = "http://image.tmdb.org/t/p/";


    /**
     *   Size request is controlled with strings (ex: w92, w154, w185 ,...)
     */
    final static String SIZE_URL = "w185";


    /**
     * Reuse this locationURL for building and requesting new images. This one is for interstellar.
     * */
    final static String locationURL = "/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg";



    /**
     * Builds the URL used to query the movie database
     *
     * @param searchQuery the keyword that will be queried.
     * @return The URL to use to query the database.
     */
    public static URL buildURL(String searchQuery) {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(SIZE_URL)
                .appendPath(searchQuery)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection)  url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }

        } finally {
            urlConnection.disconnect();
        }
    }


}
