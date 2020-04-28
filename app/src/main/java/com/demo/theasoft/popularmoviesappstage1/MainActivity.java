package com.demo.theasoft.popularmoviesappstage1;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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

    private List<Movies> moviesList = new ArrayList<Movies>();

    private RecyclerView recyclerView;
    private MovieListAdapter movieListAdapter;
    private static final String popularStr = "popular";
    private static final String topRatedStr = "top_rated";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.main_recycler_view);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
//        LinearLayoutManager layoutManager
//                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(gridLayoutManager);
//        recyclerView.setHasFixedSize(true);

        movieListAdapter = new MovieListAdapter(getApplicationContext(),this);
        recyclerView.setAdapter(movieListAdapter);

        //as soon as activity gets loaded
        //build URL from base URL
        //fetch movies

        loadMoviesData(popularStr);

       // iterateMoviePaths();


    }



    private void loadMoviesData(String search_query) {

        new FetchMoviesTask().execute(search_query);
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
                moviesList.clear();
                loadMoviesData(popularStr);
                return true;
            case R.id.top_rated:
                moviesList.clear();
                loadMoviesData(topRatedStr);
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



        Gson gson = new Gson();
        String movie = gson.toJson(m);
        Log.d(TAG,movie);

        intent.putExtra("movieObj",movie);

        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }


    private class FetchMoviesTask extends AsyncTask<String,Void,JSONArray>{
        @Override
        protected JSONArray doInBackground(String... params) {

            if(params.length == 0){
                return null;
            }

            String query = params[0];
            URL url = NetworkUtils.buildUrl(query);
            try {
                String response =  NetworkUtils.getResponseFromHttpUrl(url);
                if(response == null){
                    return new JSONArray();
                }
                JSONArray jsonArray = MoviesJSONUtils.getMoviesJSONArray(response);
                return jsonArray;


            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return null;
            }

        }


        @Override
        protected void onPostExecute(JSONArray jsonArray) {

            Gson gson = new Gson();
            for(int i=0;i<jsonArray.length();i++){

                JSONObject res = null;
                try {
                    res = jsonArray.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String s = res.toString();
                Movies movies = gson.fromJson(s,Movies.class);
//                Log.d(TAG,movies.getPoster_path());

//                URL url = NetworkUtils.buildMoviePosterURL(movies.getPoster_path());
//                Log.d(TAG,movies.getPoster_path());
//                Log.d(TAG,url.toString());

//                moviePaths.add(url.toString());
                moviesList.add(movies);

            }

//            movieListAdapter.setMoviePaths(moviePaths);
            movieListAdapter.setMovieList(moviesList);


        }
    }
}
