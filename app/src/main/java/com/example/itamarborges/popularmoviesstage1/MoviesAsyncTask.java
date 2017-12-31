package com.example.itamarborges.popularmoviesstage1;

import android.os.AsyncTask;
import android.util.Log;

import com.example.itamarborges.popularmoviesstage1.pojo.MovieCover;
import com.example.itamarborges.popularmoviesstage1.utils.MoviesDBJsonUtils;
import com.example.itamarborges.popularmoviesstage1.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by itamarborges on 29/12/17.
 */

public class MoviesAsyncTask extends AsyncTask<URL, Void, List<MovieCover>> {

    private static final String TAG = MoviesAsyncTask.class.getSimpleName();
    MoviesListAdapter mMoviesListAdapter;
    ShowElements mShowElements;

    public MoviesAsyncTask(MoviesListAdapter moviesListAdapter, ShowElements showElements) {
        mMoviesListAdapter = moviesListAdapter;
        mShowElements = showElements;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mShowElements.showProgressBar();
    }

    @Override
    protected List<MovieCover> doInBackground(URL... params) {

        URL searchUrl = params[0];
        List<MovieCover> movieCoverList = null;
        String queryResult = null;
        try {
            queryResult = NetworkUtils.getResponseFromHttpUrl(searchUrl);

            movieCoverList = MoviesDBJsonUtils.getMoviesCoverFromJson(queryResult);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return movieCoverList;
    }

    @Override
    protected void onPostExecute(List<MovieCover> movieCovers) {
        super.onPostExecute(movieCovers);

        if (movieCovers != null) {

            mMoviesListAdapter.setMoviesCover(movieCovers);
            mMoviesListAdapter.notifyDataSetChanged();

            mShowElements.showMoviesList();
        } else {
            mShowElements.showErrorMessage();
        }
    }
}
