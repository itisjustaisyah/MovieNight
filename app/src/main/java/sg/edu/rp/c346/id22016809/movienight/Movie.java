package sg.edu.rp.c346.id22016809.movienight;

import androidx.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by Nuur Aisyah Binte Farouk on 3/7/2023.
 * C346-1D-E63A-A
 */
public class Movie implements Serializable {


    int id;
    String title;
    String singers;
    int year;
    String movieRating;

    public Movie(int id, String title, String singers, int year, String movieRating) {
        this.id = id;
        this.title = title;
        this.singers = singers;
        this.year = year;
        this.movieRating = movieRating;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return singers;
    }

    public int getYear() {
        return year;
    }

    public String getMovieRating() {
        return movieRating;
    }

   /* public String ratingToString(){
        String a = "";
        switch (movieRating) {
            case 0:
                a = "G";
                break;
            case 1:
                a =  "PG";
                break;
            case 2:
                a =  "PG13";
                break;
            case 3:
                a =  "NC16";
                break;
            case 4:
                a =  "M18";
                break;
            case 5:
                a =  "R21";
                break;
            default:
                a =  "No Rating Found";
                break;
        }
        return a;
    }*/

    @NonNull
    @Override

    public String toString() {
        return  id + ". \nTitle: " + title + "\nSingers: " + singers+ "\nReleased: " + year+ "\nRating: " + movieRating;}
}
