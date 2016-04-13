package kyasa.com.popularmovies1.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import kyasa.com.popularmovies1.Model.Movie;
import kyasa.com.popularmovies1.R;

/**
 * Created by kiran on 12/4/16.
 */
public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.MovieItemViewHolder>{

    Context mContext;
    OnItemclickListener mListener;

    ArrayList<Movie> mMoviesList;
    public MoviesListAdapter(Context context, ArrayList<Movie> moviesList,OnItemclickListener listener) {
        mContext =context;
        mMoviesList = moviesList;
        mListener = listener;
    }

    @Override
    public MovieItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_movies, parent, false);

        return new MovieItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MovieItemViewHolder holder, int position) {
        Movie mMovie = mMoviesList.get(position);
        holder.bind(mMovie,mListener);
    }

    @Override
    public int getItemCount() {
        return mMoviesList.size();
    }

    public class MovieItemViewHolder extends RecyclerView.ViewHolder {

        ImageView movie_poster_iv;
        TextView movie_title_tv;
        public MovieItemViewHolder(View itemView) {
            super(itemView);
            movie_poster_iv = (ImageView) itemView.findViewById(R.id.movie_poster_iv);
            movie_title_tv = (TextView) itemView.findViewById(R.id.movie_title_tv);
        }

        public void bind(final Movie item, final OnItemclickListener listener) {
            final String image_base_url = "http://image.tmdb.org/t/p/w185"+item.getBackdropPath();
            movie_title_tv.setText(item.getOriginalTitle());
            Picasso.with(mContext).
                    load(image_base_url)
                    .into(movie_poster_iv, new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.e("Picasso","ImageLoaded");
                        }

                        @Override
                        public void onError() {
                            Log.e("PicassoError","ImageNOTLoaded");
                            Log.e("PicassoError",image_base_url);
                        }
                    });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item.getId());
                }
            });
        }
    }

    public interface OnItemclickListener{
        void onItemClick(int movieId);
    }
}
