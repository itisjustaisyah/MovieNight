package sg.edu.rp.c346.id22016809.movienight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Nuur Aisyah Binte Farouk on 17/7/2023.
 * C346-1D-E63A-A
 */
public class CustomAdapter extends ArrayAdapter {
    Context parent_context;
    int layout_id;
    ArrayList<Movie> moviesList;

    public CustomAdapter(Context context, int resource, ArrayList<Movie> objects) {
        super(context, resource, objects);

        parent_context = context;
        layout_id = resource;
        moviesList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Obtain LayoutInflater object
        LayoutInflater inflater = (LayoutInflater) parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //"Inflate" the view for each row
        View rowView = inflater.inflate(layout_id, parent, false);

        //Obtain Ui components and do necessary binding
        TextView tvName = rowView.findViewById(R.id.tvName);
        TextView tvYear = rowView.findViewById(R.id.tvYear);
//        TextView tvRating = rowView.findViewById(R.id.tvRating);
        ImageView ivRating = rowView.findViewById(R.id.ivRating);
        TextView tvGenre = rowView.findViewById(R.id.tvSingers);



        //Obtain the andorid version information based on positon
        Movie selectedMovie = moviesList.get((position));

        //Set values to textview to display the corresponging information
        tvName.setText(selectedMovie.getId() + ": " + selectedMovie.getTitle());
        tvYear.setText(String.valueOf(selectedMovie.getYear()));
//        tvRating.setText(selectedMovie.ratingToString());
        tvGenre.setText(selectedMovie.getGenre());

        int rIcon = 0;
        switch (selectedMovie.getMovieRating()){
            case "G":
                rIcon = R.drawable.rating_g;
                break;
            case"PG":
                rIcon = R.drawable.rating_pg;
                break;
            case "PG13":
                rIcon = R.drawable.rating_pg13;
                break;
            case "NC16":
                rIcon = R.drawable.rating_nc16;
                break;
            case "M18":
                rIcon = R.drawable.rating_m18;
                break;
            case "R21":
                rIcon = R.drawable.rating_r21;
                break;
            default:
        }
        ivRating.setImageResource(rIcon);

        return rowView;
    }
}
