package sg.edu.rp.c346.id22016809.movienight;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button show, insert;
    EditText editTitle, editGenre, editYear;
    RatingBar rating;
    Spinner ratingSpinner;

    String[] ratingAR;
    ArrayAdapter<?> ratingAA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show = findViewById(R.id.showList);
        insert = findViewById(R.id.insert);

        editTitle = findViewById(R.id.editTitle);
        editGenre = findViewById(R.id.editGenre);
        editYear = findViewById(R.id.editYear);

        ratingSpinner = findViewById(R.id.ratingSpinner);
        ratingAR = new String[] {"G", "PG", "PG13", "NC16", "M18", "R21"};
        ratingAA = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, ratingAR);
        ratingAA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ratingSpinner.setAdapter(ratingAA);


        DBHelper db = new DBHelper(MainActivity.this);

        insert.setOnClickListener(v -> {
            String title = editTitle.getText().toString();
            String genre = editGenre.getText().toString();
            int year = Integer.parseInt(editYear.getText().toString());
            String rating = ratingSpinner.getSelectedItem().toString();

            Log.i("MainActivity.java", title + ", " + genre + ", " + year + ", " + rating + ", ");
            boolean valid = true;

            if (title.isEmpty()) {
                editTitle.setError("Cannot be empty");
                valid = false;
            }
            if (genre.isEmpty()) {
                editGenre.setError("Cannot be empty");
                valid = false;
            }
            if (year < 0) {
                editYear.setError("Cannot be empty");
                valid = false;
            }
            if (valid) {
                db.insertMovie(title, genre, year, rating);
                Log.i("insert Main Activity", "inserted successfully");
                Toast.makeText(this, "Inserted Movie Successfully", Toast.LENGTH_SHORT).show();
            }
        });
        show.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ShowMovieListActivity.class)));

    }
}