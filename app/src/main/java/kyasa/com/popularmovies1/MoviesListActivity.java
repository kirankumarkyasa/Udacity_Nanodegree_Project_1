package kyasa.com.popularmovies1;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import kyasa.com.popularmovies1.APIManager.RetroManager;
import kyasa.com.popularmovies1.Adapters.MoviesListAdapter;
import kyasa.com.popularmovies1.Interface.OnLoadMoreListener;
import kyasa.com.popularmovies1.Model.Movie;
import kyasa.com.popularmovies1.Model.MoviesResult;
import kyasa.com.popularmovies1.WebServices.MovieService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesListActivity extends AppCompatActivity {

    RecyclerView mMoviesListRv;
    ArrayList<Movie> mMoviesList = new ArrayList<>();
    MoviesListAdapter moviesListAdapter;
    boolean isLoading;
    private int pageNumber=1;
    private GridLayoutManager gridLayoutManager;
    private boolean isPopular = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_list);

        getSupportActionBar().setTitle(R.string.popular_movies_title);
        mMoviesListRv = (RecyclerView) findViewById(R.id.movies_list_rv);
        mMoviesListRv.setVisibility(View.GONE);
        gridLayoutManager = new GridLayoutManager(MoviesListActivity.this,2);
        mMoviesListRv.setLayoutManager(gridLayoutManager);
        mMoviesListRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleThreshold = 5;
                int totalItemCount = gridLayoutManager.getItemCount();
                int lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        pageNumber++;
                        onLoadMoreListener.onLoadMore(pageNumber);
                    }
                    isLoading = true;
                }
            }
        });

        moviesListAdapter=new MoviesListAdapter(MoviesListActivity.this,
                new MoviesListAdapter.OnItemclickListener() {
                    @Override
                    public void onItemClick(int movieId) {
                        Intent i = new Intent(MoviesListActivity.this,MovieDetailActivity.class);
                        i.putExtra("movie_id",movieId);
                        startActivity(i);
                    }
                });
        mMoviesListRv.setAdapter(moviesListAdapter);
        getMoviesData(pageNumber);
    }

    OnLoadMoreListener onLoadMoreListener = new OnLoadMoreListener() {
        @Override
        public void onLoadMore(int pageNumber) {
           new Handler().post(new Runnable() {
                @Override
                public void run() {
                    if (mMoviesList.size() > 0) {
                        mMoviesList.add(null);
                        moviesListAdapter.notifyItemInserted(mMoviesList.size() - 1);
                    }
                }
            });
           getMoviesData(pageNumber);
        }
    };

    public void getMoviesData(int page) {
        String sort= "popular";
        if(isPopular) {
            sort= "popular";
        } else {
            sort = "top_rated";
        }
        final Call<MoviesResult> moviesList =  new RetroManager().getRetrofit().create(MovieService.class)
                .getMovies(sort,RetroManager.API_KEY,page);
        moviesList.enqueue(new Callback<MoviesResult>() {
            @Override
            public void onResponse(Call<MoviesResult> call, Response<MoviesResult> response) {
                mMoviesListRv.setVisibility(View.VISIBLE);
                MoviesResult mr = response.body();
                if (mMoviesList.size() > 0) {
                    mMoviesList.remove(mMoviesList.size() - 1);
                    moviesListAdapter.notifyItemRemoved(mMoviesList.size());
                }
                mMoviesList.addAll(mr.getResults());
                moviesListAdapter.setmMoviesList(mMoviesList);
                moviesListAdapter.notifyDataSetChanged();
                isLoading = false;
            }

            @Override
            public void onFailure(Call<MoviesResult> call, Throwable t) {

            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_toggle:
                if(isPopular) {
                    item.setTitle(getString(R.string.sort_by_top_rated));
                    getSupportActionBar().setTitle(R.string.top_rated_title);
                    isPopular = false;

                } else {
                    item.setTitle(getString(R.string.sort_by_popular));
                    getSupportActionBar().setTitle(R.string.popular_movies_title);
                    isPopular = true;
                }
                getMoviesData(1);
                mMoviesList.clear();
                return true;
            default:
                return false;
        }
    }
}
