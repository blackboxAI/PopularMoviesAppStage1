package com.demo.theasoft.popularmoviesappstage1.models;

import java.util.List;

public class MovieResponse {

        private int page;
        private int total_results;
        private int total_pages;
        private List<Movies> movies;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getTotal_results() {
            return total_results;
        }

        public void setTotal_results(int total_results) {
            this.total_results = total_results;
        }

        public int getTotal_pages() {
            return total_pages;
        }

        public void setTotal_pages(int total_pages) {
            this.total_pages = total_pages;
        }

        public List<Movies> getMovies() {
            return movies;
        }

        public void setMovies(List<Movies> movies) {
            this.movies = movies;
        }
}
