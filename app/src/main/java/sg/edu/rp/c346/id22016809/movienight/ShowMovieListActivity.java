package sg.edu.rp.c346.id22016809.movienight;

import android.annotation.SuppressLint;
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
    ToggleButton toggleButton;

    Spinner spinner;
    ArrayList<Movie> movieAL;
    //    ArrayList<Integer> yearAL;
    String[] ratingAR;

    DBHelper dbh;
//    ArrayAdapter<?> yearAA;
    ArrayAdapter<?> ratingAA;
    CustomAdapter songAA;



    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_movie_list);
        listview = findViewById(R.id.listView);

        back = findViewById(R.id.back);
        toggleButton = findViewById(R.id.pg13Toggle);
        spinner = findViewById(R.id.yearSpinner);

        dbh = new DBHelper(ShowMovieListActivity.this);
        movieAL = dbh.getSongs();
        songAA = new CustomAdapter(this, R.layout.row, movieAL);

        listview.setAdapter(songAA);
        retrieveSongs();
        ratingAR = new String[] {"G", "PG", "PG13", "NC16", "M18", "R21"};

        ratingAA = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, ratingAR);
        ratingAA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ratingAA);
        spinner.setSelection(0);
//        yearAA = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, yearAL);
//        yearAA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        yearSpinner.setAdapter(yearAA);
//        yearSpinner.setSelection(yearAL.size()-1);


        listview.setOnItemClickListener((parent, view, position, id) -> {
            Movie data = movieAL.get(position);
            Intent i = new Intent(ShowMovieListActivity.this, EditMovieActivity.class);
            i.putExtra("data", data);
            startActivity(i);
        });

        back.setOnClickListener(v -> startActivity(new Intent(ShowMovieListActivity.this, MainActivity.class)));

        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> showFilter(isChecked));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String filter = spinner.getSelectedItem().toString();
                showFilter(toggleButton.isChecked());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String filter = *//*yearAL.get(position)*//* ratingAR[position];
                if (filter.isEmpty()) {
                    retrieveSongs();
                    Toast.makeText(ShowMovieListActivity.this, "Showing All", Toast.LENGTH_SHORT).show();
                }else{
                    movieAL.clear();
                    movieAL.addAll(dbh.getMoviesWithRating(filter));

                    songAA.notifyDataSetChanged();
                    dbh.close();
                    Toast.makeText(ShowMovieListActivity.this, "Showing songs from: " + filter, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
    }

    @Override
    protected void onResume() {
        spinner.setSelection(0);
        super.onResume();
        retrieveSongs();
        songAA.notifyDataSetChanged();
        ratingAA.notifyDataSetChanged();
    }

    public void retrieveSongs() {
        movieAL.clear();
        movieAL.addAll(dbh.getSongs());
//        ratingAR = new ArrayList<>();
//        for (Movie movie : movieAL) {
//            ratingAR.add(movie.getYear());
//        }
//        ratingAR.add(0);
        songAA.notifyDataSetChanged();
        dbh.close();
    }

    public void showFilter(boolean isChecked) {
        String filter = spinner.getSelectedItem().toString();
        if (isChecked) {
            movieAL.clear();
            movieAL.addAll(dbh.getMoviesWithRating(filter));

            songAA.notifyDataSetChanged();
//                yearAA.notifyDataSetChanged();
            dbh.close();
            Toast.makeText(ShowMovieListActivity.this, "Showing filter", Toast.LENGTH_SHORT).show();

        } else {
            retrieveSongs();
            Toast.makeText(ShowMovieListActivity.this, "Showing all ratings", Toast.LENGTH_SHORT).show();
        }
    }
}