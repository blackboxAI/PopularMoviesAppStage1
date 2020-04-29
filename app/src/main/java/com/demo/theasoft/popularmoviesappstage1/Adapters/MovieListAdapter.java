package com.demo.theasoft.popularmoviesappstage1.Adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.demo.theasoft.popularmoviesappstage1.R;
import com.demo.theasoft.popularmoviesappstage1.models.Movies;
import com.demo.theasoft.popularmoviesappstage1.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MoviePosterViewHolder>{

    private static final String TAG = MovieListAdapter.class.getSimpleName();

    private List<Movies> mMoviesList = new ArrayList<Movies>();
    private final Context mContext;

    private MovieItemClickListener movieItemClickListener;

    public MovieListAdapter(Context context,MovieItemClickListener mItemClickListener) {
        mContext = context;
        movieItemClickListener = mItemClickListener;
    }

    public interface MovieItemClickListener{
        void onMovieItemClick(int itemIndex);
    }

    @NonNull
    @Override
    public MoviePosterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_movie_posters,parent,false);
        return new MoviePosterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MoviePosterViewHolder moviePosterViewHolder, int position) {

        /*


        Picasso is not able to load http resource instead it loads from https resource
        because From API level 28 direct http not allowed so either use OkHttp3 client
         */
        String posterPath = mMoviesList.get(position).getPoster_path();
        URL url = NetworkUtils.buildMoviePosterURL(posterPath);

        String imagePath = url.toString().replace("http","https");
//        Log.d(TAG,imagePath);

//        moviePosterViewHolder.imageView.setImageResource(R.drawable.ic_launcher_background);
        Picasso.with(mContext).load(Uri.parse(imagePath)).fit().into(moviePosterViewHolder.imageView);
//        Log.d(TAG,"done");

    }

    @Override
    public int getItemCount() {
//        Log.d(TAG,String.valueOf(mMoviePaths.size()));
//        if(mMoviePaths.size()==0) return 0;
//        return mMoviePaths.size();
        Log.d(TAG,String.valueOf(mMoviesList.size()));
        if(mMoviesList.size() == 0) return 0;
        return mMoviesList.size();
    }


    public void setMovieList(List<Movies> moviesList) {
        mMoviesList = moviesList;
        notifyDataSetChanged();
    }

    public class MoviePosterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public  ImageView imageView;

        public MoviePosterViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView)itemView.findViewById(R.id.img_view);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
           int itemindex = getAdapterPosition();
           movieItemClickListener.onMovieItemClick(itemindex);
        }
    }
}
