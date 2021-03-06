package com.demo.theasoft.popularmoviesappstage1.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Movies implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };



    private String popularity;
    private String vote_count;
    private String video;
    private String poster_path;
    private String id;
    private String adult;
    private String backdrop_path;
    private String original_language;
    private String original_title;
    private List<Integer> genre_ids = new ArrayList<Integer>();
    private String title;
    private String vote_average;
    private String overview;
    private String release_date;

    public Movies(Parcel in){
        this.popularity = in.readString();
        this.vote_count = in.readString();
        this.video = in.readString();
        this.poster_path = in.readString();
        this.id = in.readString();
        this.adult = in.readString();
        this.backdrop_path = in.readString();
        this.original_language = in.readString();
        this.original_title = in.readString();
        in.readList(this.genre_ids,Movies.class.getClassLoader());
        this.title = in.readString();
        this.vote_average = in.readString();
        this.overview = in.readString();
        this.release_date = in.readString();
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getVote_count() {
        return vote_count;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public List<Integer> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(List<Integer> genre_ids) {
        this.genre_ids = genre_ids;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.popularity);
        dest.writeString(this.vote_count);
        dest.writeString(this.video);
        dest.writeString(this.poster_path);
        dest.writeString(this.id);
        dest.writeString(this.adult);
        dest.writeString(this.backdrop_path);
        dest.writeString(this.original_language);
        dest.writeString(this.original_title);
        dest.writeList(this.genre_ids);
        dest.writeString(this.title);
        dest.writeString(this.vote_average);
        dest.writeString(this.overview);
        dest.writeString(this.release_date);

    }
}
