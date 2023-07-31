package sg.edu.rp.c346.id22016809.movienight;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Nuur Aisyah Binte Farouk on 3/7/2023.
 * C346-1D-E63A-A
 */
public class DBHelper extends SQLiteOpenHelper{
    // Start version with 1
    // increment by 1 whenever db schema changes.
    private static final int DATABASE_VER = 1;
    // Filename of the database
    private static final String DATABASE_NAME = "movie.db";


    public static final String TABLE_MOVIES = "movie";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_GENRE = "genre";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_RATING = "rating";



    public ArrayList<Movie> getSongs() {
        ArrayList<Movie> movies = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_TITLE, COLUMN_GENRE,COLUMN_YEAR, COLUMN_RATING};
        Cursor cursor = db.query(TABLE_MOVIES, columns, null, null, null, null, COLUMN_TITLE + " asc", null);

        if (cursor.moveToFirst()) {
            Log.i("cursor", "moveToFirst() false");
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String genre = cursor.getString(2);
                int year = cursor.getInt(3);
                String rating = cursor.getString(4);
                Movie obj = new Movie(id,title,genre, year, rating);
                movies.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return movies;
    }

    public ArrayList<Movie> getMoviesWithRating(String ratingFilter) {
        ArrayList<Movie> movies = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_TITLE, COLUMN_GENRE,COLUMN_YEAR, COLUMN_RATING};
        String condition = COLUMN_RATING + " Like ?";
        String[] args = {ratingFilter};

        Cursor cursor = db.query(TABLE_MOVIES, columns, condition, args, null, null, COLUMN_TITLE + " asc", null);

        if (cursor.moveToFirst()) {
            Log.i("cursor", "moveToFirst() false");
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String genre
                        = cursor.getString(2);
                int year = cursor.getInt(3);
                String rating = cursor.getString(4);
                Movie obj = new Movie(id,title,genre, year, rating);
                Log.i("getMovie()", String.format("id: %d, title: %s, genre" +
                        ": %s, year: %d, rating: %s", id,title,genre,year,rating));

                movies.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return movies;
    }

    public ArrayList<Movie> getMoviesWithYear(int yearFilter) {
        ArrayList<Movie> movies = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_TITLE, COLUMN_GENRE,COLUMN_YEAR, COLUMN_RATING};
        String condition = COLUMN_YEAR + " Like ?";
        String[] args = {String.valueOf(yearFilter)};

        Cursor cursor = db.query(TABLE_MOVIES, columns, condition, args, null, null, COLUMN_TITLE + " asc", null);

        if (cursor.moveToFirst()) {
            Log.i("cursor", "moveToFirst() false");
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String genre
                        = cursor.getString(2);
                int year = cursor.getInt(3);
                String rating = cursor.getString(4);
                Movie obj = new Movie(id,title,genre
                        , year, rating);
                Log.i("getMovies()", String.format("id: %d, title: %s, genre" +
                        ": %s, year: %d, rating: %s", id,title,genre
                        ,year,rating));

                movies.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return movies;
    }


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSql = "CREATE TABLE " + TABLE_MOVIES +  "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_GENRE + " TEXT,"
                + COLUMN_YEAR + " INTEGER,"
                + COLUMN_RATING + " TEXT)";
        db.execSQL(createTableSql);
        Log.i("info", "created tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        // Create table(s) again
        onCreate(db);

    }

    public void insertMovie(String title, String genre
            , int year, String rating){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_TITLE, COLUMN_GENRE,COLUMN_YEAR, COLUMN_RATING};
        Cursor cursor = db.query(TABLE_MOVIES, columns, null, null, null, null, COLUMN_TITLE + " asc", null);

        cursor.moveToLast();
        ContentValues values = new ContentValues();
        // Store the column name as key and the description as value
        values.put(COLUMN_TITLE, title);
        // Store the column name as key and the date as value
        values.put(COLUMN_GENRE, genre
        );
        // Insert the row into the TABLE_TASK
        values.put(COLUMN_YEAR, year);
        // Insert the row into the TABLE_TASK
        values.put(COLUMN_RATING, rating);
        // Insert the row into the TABLE_TASK
        db.insert(TABLE_MOVIES, null, values);
        // Close the database connection
        Log.i("info", "Movie inserted");
        db.close();
        // Get an instance of the database for writing
        // We use ContentValues object to store the values for
        //  the db operation
        cursor.close();
        db.close();

    }

    public int updateMovie(Movie data, String title, String genre
            , int year, String rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        // Store the column name as key and the date as value
        values.put(COLUMN_GENRE, genre
        );
        // Insert the row into the TABLE_TASK
        values.put(COLUMN_YEAR, year);
        // Insert the row into the TABLE_TASK
        values.put(COLUMN_RATING, rating);
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        return db.update(TABLE_MOVIES, values, condition, args);
    }

    public void deleteMovie(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        db.delete(TABLE_MOVIES, condition, args);
         if (getSongs().size() > 0){
             db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TABLE_MOVIES + "'");
         }
    }
}
