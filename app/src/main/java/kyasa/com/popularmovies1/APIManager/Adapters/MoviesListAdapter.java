package kyasa.com.popularmovies1.APIManager.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kyasa.com.popularmovies1.R;

/**
 * Created by kiran on 12/4/16.
 */
public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.MovieItemViewHolder>{


    @Override
    public MovieItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout., parent, false);

        return new RecyclerView.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MovieItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MovieItemViewHolder extends RecyclerView.ViewHolder{

        public MovieItemViewHolder(View itemView) {
            super(itemView);
        }
    }
}
