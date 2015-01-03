package com.example.maria.cinema.common;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.EditText;

import com.example.maria.cinema.R;
import com.example.maria.cinema.models.Cinema;
import com.example.maria.cinema.models.Movie;
import com.example.maria.cinema.models.Projection;
import com.example.maria.cinema.models.Reservation;
import com.example.maria.cinema.models.User;

import java.util.ArrayList;

/**
 * Created by Maria on 12/28/2014.
 */
public class DBManager {
    private static DBManager instance;
    private int currentUserId;
    private ArrayList<User> users;
    private ArrayList<Cinema> cinemas;
    private ArrayList<Movie> movies;
    private ArrayList<Projection> projections;
    private ArrayList<Reservation> reservations;

    public int getCurrentUserId() {
        return currentUserId;
    }

    private DBManager() {
        currentUserId = 0;

        initUsers();
        initCinemas();
        initMovies();
        initProjections();
        initReservations();
    }

    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }


    private void initUsers() {
        users = new ArrayList<User>();
        for (int i = 1; i < 5; i++) {
            User user = new User();
            user.setId(users.size() + 1);
            user.setUsername("user" + Integer.toString(i));
            user.setEmail(user.getUsername() + "@example.com");
            user.setPassword("1234");
            users.add(user);
        }
    }

    private void initCinemas() {
        cinemas = new ArrayList<Cinema>();
        Cinema cinema1 = new Cinema(1, "Arena Deluxe Bulgaria Mall","София, бул. България 69, в Bulgaria Mall","11:00-23:00", "kino_arena_logo.jpg", "arena_deluxe.jpg");
        cinemas.add(cinema1);
        Cinema cinema2 = new Cinema(2, "Arena Mladost", "София, кв. Младост 4, Бизнес парк (до Технополис)","11:00-23:00", "kino_arena_logo.jpg", "arena_mladost.jpg");
        cinemas.add(cinema2);
        Cinema cinema3 = new Cinema(3, "Arena The Mall","София, бул. Цариградко шосе 115 (в търговски център The Mall)","11:00-23:00", "kino_arena_logo.jpg", "arena_the_mall.jpg");
        cinemas.add(cinema3);
        Cinema cinema4 = new Cinema(4, "Arena Zapad", "София, ж.к. Илинден, бул. Тодор Александров 64, до метростанция Вардар","11:00-23:00", "kino_arena_logo.jpg", "arena_zapad.jpg");
        cinemas.add(cinema4);
        Cinema cinema5 = new Cinema(5, "Cine Grand","София, бул. Арсеналски 2 (City Center Sofia, етаж 2 )","10:00-00:00", "cine_grand_logo.jpg", "cine_grand.jpg");
        cinemas.add(cinema5);
        Cinema cinema6 = new Cinema(6, "Cine Grand Sofia Ring Mall", "Sofia Ring Mall, бул. Околовръстен път 214, етаж 2","11:00-23:00", "cine_grand_logo.jpg", "cine_grand.jpg");
        cinemas.add(cinema6);
        Cinema cinema7 = new Cinema(7, "Cinema City","София, Мол София, бул. Ал. Стамболийски 101","11:00-23:00", "cinema_city_logo.jpg", "cinema_city.jpg");
        cinemas.add(cinema7);
        Cinema cinema8 = new Cinema(8, "Cinema City Paradise", "София, бул. Черни връх 100, в сградата на Paradise Center","11:00-23:00", "cinema_city_logo.jpg", "cinema_city_paradise.jpg");
        cinemas.add(cinema8);
    }

    private void initMovies() {
        movies = new ArrayList<Movie>();
        Movie movie1 = new Movie(1, "The Hunger Games: Mockingjay - Part 1","hunger-games.jpg","Jennifer Lawrence, Josh Hutcherson, Liam Hemsworth");
        movies.add(movie1);
        Movie movie2 = new Movie(2, "Exodus: Gods and Kings 3D","exodus.jpg","Aaron Paul, Christian Bale, Sigourney Weaver");
        movies.add(movie2);
        Movie movie3 = new Movie(3, "Interstellar", "interstellar.jpg","Matthew McConaughey, Anne Hathaway, Jessica Chastain");
        movies.add(movie3);
        Movie movie4 = new Movie(4, "Penguins of Madagascar", "penguins-of-madagascar.jpg","Benedict Cumberbatch, Peter Stormare, Annet Mahendru");
        movies.add(movie4);
        Movie movie5 = new Movie(5, "The Hobbit: The Battle of the Five Armies","hobbit.jpg", "Ian McKellen,Martin Freeman, Richard Armitage,Evangeline Lilly, Lee Pace, Luke Evans, Benedict Cumberbatch, Ken Stott, James Nesbitt, with Cate Blanchett, Ian Holm, Christopher Lee, Hugo Weaving, and Orlando Bloom");
        movies.add(movie5);
    }

    private void initProjections() {
        projections = new ArrayList<Projection>();

        Projection projection;

        for(int i=1; i <= getCinemas().size(); i++) {
            for(int j=1; j <= getMovies().size(); j++) {
                if ((2*i + 5*j)%(i+j) != 0) {
                    projection = new Projection(getProjections().size() + 1, j, i, j+i);
                    projections.add(projection);
                }
            }
        }
    }

    private void initReservations() {
        reservations = new ArrayList<Reservation>();
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public User loginUser(Context context, String username, String password) throws Exception {
        String error = "";
        if (username.length() == 0) {
            error += context.getString(R.string.missing_username);
        }
        if (password.length() == 0) {
            if (error.length() != 0) {
                error += "\n";
            }
            error += context.getString(R.string.missing_password);
        }
        if (error.length() != 0) {
            throw new Exception(error);
        }
        User currentUser = null;
        for (User user:users) {
            if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
                currentUserId = user.getId();
                currentUser = user;
                break;
            }
        }

        if (currentUser == null) {
            throw new Exception(context.getString(R.string.login_failed));
        } else {
            return currentUser;
        }
    }

    public User registerUser(Context context, String username, String email, String password, String password2) throws Exception {
        String error = "";
        if (username.length() == 0) {
            error += context.getString(R.string.missing_username);
        } else {
            for (User user:users) {
                if (username.toLowerCase().equals(user.getUsername().toLowerCase())) {
                    error += context.getString(R.string.username_exists);
                    break;
                }
            }
        }
        if (email.length() == 0) {
            if (error.length() != 0) {
                error += "\n";
            }
            error += context.getString(R.string.missing_email);
        }
        if (password.length() == 0) {
            if (error.length() != 0) {
                error += "\n";
            }
            error += context.getString(R.string.missing_password);
        }
        if (password.length() != 0 && (password2.length() == 0 || !password.equals(password2))) {
            if (error.length() != 0) {
                error += "\n";
            }
            error += context.getString(R.string.wrong_password_confirmation);
        }
        if (error.length() != 0) {
            throw new Exception(error);
        }

        ArrayList<User> users = getUsers();
        User user = new User();
        user.setId(users.size() + 1);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        users.add(user);
        currentUserId = user.getId();
        return user;
    }

    public void logoutUser() {
        currentUserId = 0;
    }

    public boolean userIsLogged() {
        return currentUserId != 0;
    }

    public ArrayList<Cinema> getCinemas() {
        return this.cinemas;
    }

    public ArrayList<Movie> getMovies() {
        return this.movies;
    }

    public ArrayList<Projection> getProjections() {
        return this.projections;
    }

    public ArrayList<Reservation> getReservations() {
        return this.reservations;
    }

    public Cinema getCinemaById(int id) {
        for (Cinema cinema:cinemas) {
            if (cinema.getId() == id) {
                return cinema;
            }
        }
        return null;
    }

    public Cinema getCinemaByName(String cinemaName) {
        for (Cinema cinema:cinemas) {
            if (cinema.getName() == cinemaName) {
                return cinema;
            }
        }
        return null;
    }

    public Movie getMovieById(int id) {
        for (Movie movie:movies) {
            if (movie.getId() == id) {
                return movie;
            }
        }
        return null;
    }

    public Cinema getCinema(int position) {
        return cinemas.get(position);
    }

    public Movie getMovie(int position) { return movies.get(position); }

    public ArrayList<Movie> getMoviesByCinemaId(int cinemaId) {
        ArrayList<Movie> result = new ArrayList<Movie>();
        for (Projection projection:projections) {
            if (projection.getCinemaId() == cinemaId) {

                result.add(getMovieById(projection.getMovieId()));
            }
        }
        return result;
    }

    public ArrayList<Cinema> getCinemasByMovieId(int movieId) {
        ArrayList<Cinema> result = new ArrayList<Cinema>();
        for (Projection projection:projections) {
            if (projection.getMovieId() == movieId) {
                result.add(getCinemaById(projection.getCinemaId()));
            }
        }
        return result;
    }

    public int getAvailableTickets(int cinemaId, int movieId) {
        int availableTicketsCount = 0;
        for (Projection projection:projections) {
            if (projection.getCinemaId() == cinemaId && projection.getMovieId() == movieId) {
                availableTicketsCount = projection.getTicketsCount();
                for (Reservation reservation:reservations) {
                    Log.d("Reservation Id", Integer.toString(reservation.getId()));
                    if (reservation.getProjectionId() == projection.getId()) {
                        availableTicketsCount -= reservation.getBookedTicketsCount();
                    }
                }
            }
        }
        return  availableTicketsCount;
    }


    public Projection getProjection(int cinemaId, int movieId) {
        for (Projection projection:projections) {
            if (projection.getCinemaId() == cinemaId && projection.getMovieId() == movieId) {
                return projection;
            }
        }
        return null;
    }

    public User getCurrentUser() {
        for (User user:users) {
            if (user.getId() == currentUserId) {
                return user;
            }
        }
        return null;
    }

    public void makeReservation(int cinemaId, int movieId, int ticketsCount) {
        Projection projection = getProjection(cinemaId, movieId);
        Reservation reservation = new Reservation(getReservations().size()+1, getCurrentUserId(),projection.getId(),ticketsCount);
        reservations.add(reservation);
    }

}
