package kyasa.com.popularmovies1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import kyasa.com.popularmovies1.APIManager.RetroManager;
import kyasa.com.popularmovies1.Adapters.MoviesListAdapter;
import kyasa.com.popularmovies1.Model.Movie;
import kyasa.com.popularmovies1.Model.MoviesResult;
import kyasa.com.popularmovies1.WebServices.MovieService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesListActivity extends AppCompatActivity {

    RecyclerView mMoviesListRv;
    ArrayList<Movie> mMoviesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_list);
        mMoviesListRv = (RecyclerView) findViewById(R.id.movies_list_rv);
        mMoviesListRv.setVisibility(View.GONE);
        final Call<MoviesResult> moviesList =  new RetroManager().getRetrofit().create(MovieService.class)
                .getPopularMovies(RetroManager.API_KEY);

        moviesList.enqueue(new Callback<MoviesResult>() {
            @Override
            public void onResponse(Call<MoviesResult> call, Response<MoviesResult> response) {
                mMoviesListRv.setVisibility(View.VISIBLE);
                mMoviesListRv.setLayoutManager(new GridLayoutManager(MoviesListActivity.this,2));
                MoviesResult mr = response.body();
                mMoviesList = (ArrayList<Movie>) mr.getResults();
                mMoviesListRv.setAdapter(new MoviesListAdapter(MoviesListActivity.this, mMoviesList, new MoviesListAdapter.OnItemclickListener() {
                    @Override
                    public void onItemClick(int movieId) {
                        Intent i = new Intent(MoviesListActivity.this,MovieDetailActivity.class);
                        startActivity(i);
                    }
                }));
            }

            @Override
            public void onFailure(Call<MoviesResult> call, Throwable t) {

            }
        });



    }
}
