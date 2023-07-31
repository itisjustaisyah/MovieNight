package sg.edu.rp.c346.id22016809.movienight;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ShowMovieListActivity extends AppCompatActivity {

    ListView listview;
    Button back;
    ToggleButton fiveStars;

    Spinner yearSpinner;
    ArrayList<Movie> movieAL;
    ArrayList<Integer> yearAL;

    DBHelper dbh;
    ArrayAdapter<?> yearAA;
    CustomAdapter songAA;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_movie_list);
        listview = findViewById(R.id.listView);

        back = findViewById(R.id.back);
        fiveStars = findViewById(R.id.pg13Toggle);
        yearSpinner = findViewById(R.id.yearSpinner);

        dbh = new DBHelper(ShowMovieListActivity.this);
        movieAL = dbh.getSongs();
        songAA = new CustomAdapter(this, R.layout.row, movieAL);

        listview.setAdapter(songAA);
        retrieveSongs();

        yearAA = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, yearAL);
        yearAA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAA);
        yearSpinner.setSelection(yearAL.size()-1);


        listview.setOnItemClickListener((parent, view, position, id) -> {
            Movie data = movieAL.get(position);
            Intent i = new Intent(ShowMovieListActivity.this, EditMovieActivity.class);
            i.putExtra("data", data);
            startActivity(i);
        });

        back.setOnClickListener(v -> startActivity(new Intent(ShowMovieListActivity.this, MainActivity.class)));

        fiveStars.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                movieAL.clear();
                movieAL.addAll(dbh.getMoviesWithRating("PG13"));

                songAA.notifyDataSetChanged();
                yearAA.notifyDataSetChanged();
                dbh.close();
                Toast.makeText(ShowMovieListActivity.this, "Showing PG-13", Toast.LENGTH_SHORT).show();

            }else{
                retrieveSongs();
                Toast.makeText(ShowMovieListActivity.this, "Showing all ratings", Toast.LENGTH_SHORT).show();
            }
        });
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int filterYear = yearAL.get(position);
                if (filterYear == 0) {
                    retrieveSongs();
                    Toast.makeText(ShowMovieListActivity.this, "Showing All", Toast.LENGTH_SHORT).show();
                }else{
                    movieAL.clear();
                    movieAL.addAll(dbh.getMoviesWithYear((int) filterYear));

                    songAA.notifyDataSetChanged();
                    dbh.close();
                    Toast.makeText(ShowMovieListActivity.this, "Showing songs from: " + filterYear, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onResume() {
        yearSpinner.setSelection(yearAL.size()-1);
        super.onResume();
        retrieveSongs();
        songAA.notifyDataSetChanged();
        yearAA.notifyDataSetChanged();
    }

    public void retrieveSongs() {
        movieAL.clear();
        movieAL.addAll(dbh.getSongs());
        yearAL = new ArrayList<>();
        for (Movie movie : movieAL) {
            yearAL.add(movie.getYear());
        }
        yearAL.add(0);
        songAA.notifyDataSetChanged();
        dbh.close();
    }
}