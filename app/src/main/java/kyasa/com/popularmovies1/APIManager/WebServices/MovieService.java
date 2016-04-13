package kyasa.com.popularmovies1.APIManager.WebServices;

import java.util.List;

import kyasa.com.popularmovies1.APIManager.Model.Movie;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by kiran on 12/4/16.
 */
public interface MovieService {
    @GET("movie/popular")
    Call<List<Movie>> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<Movie> getMovieDetails(@Path("id") int id);

    @GET("movie/top_rated")
    Call<List<Movie>> getHighestRatedMovies(@Query("api_key") String apiKey);
}
