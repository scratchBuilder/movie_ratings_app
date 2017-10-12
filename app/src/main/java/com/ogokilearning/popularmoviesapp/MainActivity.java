package com.ogokilearning.popularmoviesapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.net.ConnectivityManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private GridView mGridView;

    private static final String API_KEY = BuildConfig.API_KEY;


    private MovieAdapter movieAdapter;

    private String[] movies = {"dummy ","dummy ","dummy ","dummy ","dummy "};

    private JSONObject movieDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();

        movieAdapter = new MovieAdapter(mContext, Arrays.asList(movies));

        mGridView = (GridView) findViewById(R.id.movieGridView);
        mGridView.setAdapter(movieAdapter);

        makePopularSearchQuery();



    }

    private void makePopularSearchQuery() {
        String searchQuery = "http://api.themoviedb.org/3/movie/popular?api_key=" + API_KEY;
        URL searchURL = NetworkUtils.buildURL(searchQuery);
        new MovieDataBaseQueryTask().execute(searchURL);
    }

    private void makeTopRatedSearchQuery() {
        String searchQuery = "http://api.themoviedb.org/3/movie/top_rated?api_key=" + API_KEY;
        URL searchURL = NetworkUtils.buildURL(searchQuery);
        new MovieDataBaseQueryTask().execute(searchURL);
    }

    private void resizeArray() {

    }

    class MovieDataBaseQueryTask extends AsyncTask<URL, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String searchResults = null;
            try {
                searchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e ) {
                e.printStackTrace();
            }

            return searchResults;
        }

        @Override
        protected void onPostExecute(String results) {
            if (results != null && !results.equals("")) {
                try {
                    JSONObject query = new JSONObject(results);
                    JSONObject count = query.getJSONObject("total");
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }

        }
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
