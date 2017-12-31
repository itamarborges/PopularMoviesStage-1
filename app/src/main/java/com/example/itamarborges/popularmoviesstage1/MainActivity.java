package com.example.itamarborges.popularmoviesstage1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.itamarborges.popularmoviesstage1.pojo.MovieCover;
import com.example.itamarborges.popularmoviesstage1.utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

interface ShowElements {
    void showProgressBar();
    void showErrorMessage();
    void showMoviesList();
}


public class MainActivity extends AppCompatActivity implements ShowElements {

    private MoviesListAdapter mAdapter;
    private RecyclerView mMoviesCoverList;
    private Spinner mSpinnerSort;
    private Button mBtnTryAgain;
    private ProgressBar mProgressBar;
    private LinearLayout mErrorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMoviesCoverList = findViewById(R.id.rv_movies_cover);
        mSpinnerSort = findViewById(R.id.sp_sort_criteria);
        mBtnTryAgain = findViewById(R.id.btn_try_again);
        mProgressBar = findViewById(R.id.pb_loading_indicator);
        mErrorLayout = findViewById(R.id.layout_error);

        mBtnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeMoviesQuery(mSpinnerSort.getSelectedItemPosition());
            }
        });

        mSpinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                makeMoviesQuery(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mMoviesCoverList.setLayoutManager(layoutManager);

        mAdapter = new MoviesListAdapter(new ArrayList<MovieCover>());
        mMoviesCoverList.setAdapter(mAdapter);

        makeMoviesQuery(0);
    }


    private void makeMoviesQuery(int option) {
        URL githubSearchUrl = NetworkUtils.buildUrl(option == 0 ? NetworkUtils.THE_MOVIE_DB_SORT_HIGHEST_RATED: NetworkUtils.THE_MOVIE_DB_SORT_POPULAR);
        MoviesAsyncTask mMoviesAsyncTask = new MoviesAsyncTask(mAdapter, this);
        mMoviesAsyncTask.execute(githubSearchUrl);
    }

    @Override
    public void showProgressBar() {
        mMoviesCoverList.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        mErrorLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showErrorMessage() {
        mMoviesCoverList.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
        mErrorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMoviesList() {
        mMoviesCoverList.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
        mErrorLayout.setVisibility(View.INVISIBLE);
    }
}
