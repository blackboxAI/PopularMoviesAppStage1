package com.demo.theasoft.popularmoviesappstage1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.theasoft.popularmoviesappstage1.models.Movies;
import com.demo.theasoft.popularmoviesappstage1.utilities.NetworkUtils;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.net.URL;

import androidx.appcompat.app.AppCompatActivity;

public class MovieDetailActivity extends AppCompatActivity {


    private static final String TAG = MovieDetailActivity.class.getSimpleName();

    private ImageView imageView;
    private TextView tvOriginalTitle;
    private TextView tvUserRating;
    private TextView tvReleaseDate;
    private TextView tvOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        imageView = (ImageView)findViewById(R.id.img_poster_thumbanil);
        tvOriginalTitle = (TextView)findViewById(R.id.tv_movie_title);
        tvUserRating = (TextView)findViewById(R.id.tv_user_rating);
        tvReleaseDate = (TextView)findViewById(R.id.tv_release_date);
        tvOverview = (TextView)findViewById(R.id.tv_synopsis);


        Intent intent = getIntent();
        Gson gson = new Gson();

        Movies m = gson.fromJson(intent.getStringExtra("movieObj"),Movies.class);

        Log.d(TAG,m.getOriginal_title());
        Log.d(TAG,m.getPoster_path());
        Log.d(TAG,m.getOverview());
        Log.d(TAG,m.getVote_average());
        Log.d(TAG,m.getRelease_date());

        URL url = NetworkUtils.buildMoviePosterURL(m.getPoster_path());
        String imagePath = url.toString().replace("http","https");
        Picasso.with(getApplicationContext()).load(Uri.parse(imagePath)).fit().into(imageView);

        tvOriginalTitle.setText(m.getOriginal_title());
        tvUserRating.setText("User Rating : " + m.getVote_average());
        tvReleaseDate.setText("Release Date : " + m.getRelease_date());
        tvOverview.setText(m.getOverview());


    }
}
