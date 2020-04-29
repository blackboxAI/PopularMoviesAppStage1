package com.demo.theasoft.popularmoviesappstage1.utilities;

import android.net.Uri;
import android.util.Log;


import com.demo.theasoft.popularmoviesappstage1.Constants.Constant;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    public static URL buildUrl(String query){

        Uri buildUri = null;
        if(query == Constant.BASE_MOVIE_POPULAR_PATH) {

            buildUri = Uri.parse(Constant.BASE_URL).buildUpon()
                    .appendPath(Constant.BASE_API_VERSION)
                    .appendPath(Constant.BASE_MOVIE_PATH)
                    .appendPath(Constant.BASE_MOVIE_POPULAR_PATH)
                    .appendQueryParameter(Constant.QUERY_PARAM, Constant.API_KEY)
                    .build();
        }else if(query == Constant.BASE_MOVIE_TOP_RATED_PATH){
            buildUri = Uri.parse(Constant.BASE_URL).buildUpon()
                    .appendPath(Constant.BASE_API_VERSION)
                    .appendPath(Constant.BASE_MOVIE_PATH)
                    .appendPath(Constant.BASE_MOVIE_TOP_RATED_PATH)
                    .appendQueryParameter(Constant.QUERY_PARAM, Constant.API_KEY)
                    .build();
        }

        URL url = null;

        try{
            url = new URL(buildUri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    public static URL buildMoviePosterURL(String posterPath){

        Uri buildUri = Uri.parse(Constant.BASE_IMAGE_FETCH_URL).buildUpon()
                            .appendPath(Constant.BASE_IMAGE_SIZE)
                            .appendEncodedPath(posterPath)
                            .build();

        URL url = null;

        try{
            url = new URL(buildUri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }

        Log.v(TAG, "Built Movies Poster URI " + url);

        return url;

    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
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
        else{
            return null;
        }
    }
}
