package sg.edu.rp.c346.id22016809.movienight;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EditMovieActivity extends AppCompatActivity {
    Button update, delete, cancel;
    EditText updateTitle, updateGenre, updateYear, movieId;
    Spinner updateRatingSpinner;
    String[] ratingAR;
    ArrayAdapter<?> ratingAA;
    Movie data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);
        cancel = findViewById(R.id.cancel);
        movieId = findViewById(R.id.editID);

        updateTitle = findViewById(R.id.updateTitle);
        updateGenre = findViewById(R.id.updateGenre);
        updateYear = findViewById(R.id.updateYear);

        updateRatingSpinner = findViewById(R.id.updateRatingSpinner);
        ratingAR = new String[]{"G", "PG", "PG13", "NC16", "M18", "R21"};
        ratingAA = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, ratingAR);
        ratingAA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        updateRatingSpinner.setAdapter(ratingAA);
        DBHelper dbh = new DBHelper(EditMovieActivity.this);
        Intent i = getIntent();
        data = (Movie) i.getSerializableExtra("data");

        preparingEditing(data);


        update.setOnClickListener(v -> {
            String title = updateTitle.getText().toString();
            String genre = updateGenre.getText().toString();
            int year = Integer.parseInt(updateYear.getText().toString());
            String rating = updateRatingSpinner.getSelectedItem().toString();

            Log.i("MainActivity.java", title + ", " + genre + ", " + year + ", " + rating + ", ");
            boolean valid = true;

            if (title.isEmpty()) {
                updateTitle.setError("Cannot be empty");
                valid = false;
            }
            if (genre.isEmpty()) {
                updateGenre.setError("Cannot be empty");
                valid = false;
            }
            if (year < 0) {
                updateYear.setError("Cannot be empty");
                valid = false;
            }
            if (valid) {
                if (dbh.updateMovie(data, title, genre, year, rating) < 1) {
                    Log.i("Edit Activity", "update unsuccessful");

                } else {
                    Log.i("Edit Activity", "update successfully");
                }

                Toast.makeText(this, "Updated Movie Successfully", Toast.LENGTH_SHORT).show();
            }
            startActivity(new Intent(EditMovieActivity.this, ShowMovieListActivity.class));
        });
        cancel.setOnClickListener(v -> {
            AlertDialog.Builder myBuilder = new AlertDialog.Builder(EditMovieActivity.this);
            myBuilder.setTitle("Danger");
            myBuilder.setMessage("Are you sure you want to discard changes");
            myBuilder.setCancelable(false);
            myBuilder.setNegativeButton("Discard", (dialog, which) -> {
                startActivity(new Intent(EditMovieActivity.this, ShowMovieListActivity.class));
            });
            myBuilder.setPositiveButton("Do Not Discard", null);
            AlertDialog myDialog = myBuilder.create();
            myDialog.show();
        });
        delete.setOnClickListener(v -> {
            AlertDialog.Builder myBuilder = new AlertDialog.Builder(EditMovieActivity.this);
            myBuilder.setTitle("Danger");
            myBuilder.setMessage("Are you sure you want to delete the movie " + data.getTitle());
            myBuilder.setCancelable(false);
            myBuilder.setNegativeButton("Delete", (dialog, which) -> {
                dbh.deleteMovie(data.getId());
                startActivity(new Intent(EditMovieActivity.this, ShowMovieListActivity.class));
                Log.i("Edit Activty", "Deleted successfully");
                Toast.makeText(this, "Deleted Movie Successfully", Toast.LENGTH_SHORT).show();
            });
            myBuilder.setPositiveButton("Cancel", null);

            AlertDialog myDialog = myBuilder.create();
            myDialog.show();

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        preparingEditing(data);
    }

    public void preparingEditing(Movie data) {
        movieId.setText(String.valueOf(data.getId()));
        updateTitle.setText(data.getTitle());
        updateGenre.setText(data.getGenre());
        updateYear.setText(String.valueOf(data.getYear()));

        int i;
        switch (data.getMovieRating()) {
            case "G":
                default:
                    i = 0;
            case "PG":
                i = 1;
                break;
            case "PG13":
                i = 2;
                break;
            case "NC16":
                i = 3;
                break;
            case "M18":
                i = 4;
                break;
            case "R21":
                i = 5;
                break;
        }
        updateRatingSpinner.setSelection(i);

    }
}