package com.demo.theasoft.popularmoviesappstage1;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.demo.theasoft.popularmoviesappstage1.Adapters.MovieListAdapter;
import com.demo.theasoft.popularmoviesappstage1.models.Movies;
import com.demo.theasoft.popularmoviesappstage1.utilities.MoviesJSONUtils;
import com.demo.theasoft.popularmoviesappstage1.utilities.NetworkUtils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements MovieListAdapter.MovieItemClickListener{


    private static final String TAG = MainActivity.class.getSimpleName();

    private static List<Movies> moviesList = new ArrayList<Movies>();

    private static RecyclerView recyclerView;
    private static MovieListAdapter movieListAdapter;
    private static final String popularStr = "popular";
    private static final String topRatedStr = "top_rated";
    private static ProgressBar mLoadingIndicator;
    private static TextView mErrorTextview;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.main_recycler_view);
        mLoadingIndicator = (ProgressBar)findViewById(R.id.pb_loading_indicator);
        mErrorTextview = (TextView)findViewById(R.id.tv_error_display);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
//        LinearLayoutManager layoutManager
//                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(gridLayoutManager);
//        recyclerView.setHasFixedSize(true);

        movieListAdapter = new MovieListAdapter(getApplicationContext(),this);
        recyclerView.setAdapter(movieListAdapter);


        loadMoviesData(popularStr);

       // iterateMoviePaths();




    }





    private void loadMoviesData(String search_query) {
        if(moviesList != null){
            moviesList.clear();
        }
        new FetchMoviesTask(this).execute(search_query);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){

            case R.id.popular_movies:
                if(moviesList != null){
                    moviesList.clear();
                }
                loadMoviesData(popularStr);
                return true;
            case R.id.top_rated:
                if(moviesList != null){
                    moviesList.clear();
                }
                loadMoviesData(topRatedStr);
                return true;
            case R.id.menu_refresh:
                if(moviesList != null){
                    moviesList.clear();
                }
                loadMoviesData(popularStr);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMovieItemClick(int itemIndex) {
        Log.d(TAG,String.valueOf(itemIndex));

        Intent intent = new Intent(this,MovieDetailActivity.class);
        Movies m = moviesList.get(itemIndex);


        intent.putExtra("movieObj",m);

        if(intent.resolveActivity(getPackageManager()) != null){
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }




    private static class FetchMoviesTask extends AsyncTask<String,Void,JSONArray>{

        private Context mContext;

        public FetchMoviesTask(Context context) {
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected JSONArray doInBackground(String... params) {

            if(params.length == 0){
                return null;
            }


            String query = params[0];
            URL url = NetworkUtils.buildUrl(query);
            try {
                String ns = NetworkUtils.getNetworkConnectivityStatus(mContext);
                Log.d(TAG,ns);
                if(ns.equals("WIFI_ENABLED") || ns.equals("MOBILE_DATA_ENABLED")) {
                    String response = NetworkUtils.getResponseFromHttpUrl(url);
                    Log.d(TAG, response);
                    if (response == null) {
                        return null;
                    }
                    JSONArray jsonArray = MoviesJSONUtils.getMoviesJSONArray(response);
                    return jsonArray;
                }
                else{
                    return null;
                }


            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return null;
            }

        }


        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);

            Gson gson = new Gson();
            if(jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject res = null;
                    try {
                        res = jsonArray.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String s = res.toString();
                    Movies movies = gson.fromJson(s, Movies.class);
                    moviesList.add(movies);

                }

//            movieListAdapter.setMoviePaths(moviePaths);
                movieListAdapter.setMovieList(moviesList);
                recyclerView.setAdapter(movieListAdapter);
            }else{
                Log.d(TAG,"error");
                recyclerView.setAdapter(null);
                mErrorTextview.setVisibility(View.VISIBLE);
                mErrorTextview.setText(R.string.error_display);
            }


        }
    }
}
