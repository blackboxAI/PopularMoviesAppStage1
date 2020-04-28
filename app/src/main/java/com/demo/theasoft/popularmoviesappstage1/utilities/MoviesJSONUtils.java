package com.demo.theasoft.popularmoviesappstage1.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MoviesJSONUtils {

    private static final String TAG = MoviesJSONUtils.class.getSimpleName();

    public static JSONArray getMoviesJSONArray(String moviesJSONResponseStr) throws JSONException {

        JSONObject jsonObject = new JSONObject(moviesJSONResponseStr);
        if(jsonObject.has("results")) {
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            return jsonArray;

        }else {
            return new JSONArray();
        }

    }
}
