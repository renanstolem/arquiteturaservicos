package com.netflix.movies.services;

import com.netflix.movies.dtos.ReviewDTO;
import com.netflix.movies.entities.Movie;
import com.netflix.movies.entities.MovieWatchedInterest;
import com.netflix.movies.entities.User;
import com.netflix.movies.repositories.MovieRepository;
import com.netflix.movies.repositories.MovieWatchedInterestRepository;
import com.netflix.movies.repositories.UserRepository;
import com.netflix.movies.responses.StandardResponseInterest;
import com.netflix.movies.responses.StandardResponseListMovie;
import com.netflix.movies.responses.StandardResponseMovie;
import com.netflix.movies.responses.StandardResponseTopMovies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MovieWatchedInterestRepository movieWatchedInterestRepository;

    public ResponseEntity<StandardResponseListMovie> findMoviesByGenre(String genre) {
        logger.info(MessageFormat.format("Searching movies by provided genre {0}", genre));
        List<Movie> movies = movieRepository.findAll();

        List<StandardResponseListMovie.Movie> foundMoviesByGenre = new ArrayList<>();

        StandardResponseListMovie.Movie movieObject;

        for(Movie movie : movies) {
            if(movie.getGenre().contains(genre)) {
                movieObject = new StandardResponseListMovie.Movie();
                movieObject.setId(movie.getId());
                movieObject.setName(movie.getName());
                movieObject.setImage(movie.getImage());
                movieObject.setGenre(movie.getGenre());
                foundMoviesByGenre.add(movieObject);
            }
        }

        if(foundMoviesByGenre.isEmpty()) {
            logger.info("No movies found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        logger.info(MessageFormat.format("Listing {0} movies of the genre {1}", foundMoviesByGenre.size(), genre));

        return ResponseEntity.ok(new StandardResponseListMovie(foundMoviesByGenre));
    }

    public ResponseEntity<StandardResponseMovie> findById(Integer id) {
        Optional<Movie> movie = this.movieRepository.findById(id);

        if(!movie.isPresent()) {
            logger.error("Movie not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(new StandardResponseMovie(movie.get()));
    }

    public ResponseEntity<StandardResponseMovie> saveReview(ReviewDTO reviewDTO) {
        Optional<Movie> movie = this.movieRepository.findById(reviewDTO.getId());

        if(!movie.isPresent()) {
            logger.error("Movie not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        movie.get().setReviews(movie.get().getReviews() + 1);
        movie.get().setReviewsScore(movie.get().getReviewsScore() + reviewDTO.getReviewScore());

        try {
            return new ResponseEntity<>(new StandardResponseMovie(this.movieRepository.save(movie.get())), HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error(MessageFormat.format("Oops, something happened while saving review, cause: {0}", e.getMessage()));
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public ResponseEntity<StandardResponseListMovie> findMoviesByKeyword(String keyword) {
        logger.info(MessageFormat.format("Searching movies by provided keyword {0}", keyword));
        List<Movie> movies = movieRepository.findAll();

        List<StandardResponseListMovie.Movie> foundMoviesByKeyword = new ArrayList<>();

        StandardResponseListMovie.Movie movieObject;

        for(Movie movie : movies) {
            if(clearString(movie.getName().toLowerCase()).contains(clearString(keyword.toLowerCase()))) {
                movieObject = new StandardResponseListMovie.Movie();
                movieObject.setId(movie.getId());
                movieObject.setName(movie.getName());
                movieObject.setImage(movie.getImage());
                movieObject.setGenre(movie.getGenre());
                movieObject.setWatchedTimes(movie.getWatchedTimes());
                foundMoviesByKeyword.add(movieObject);
            }
        }

        if(foundMoviesByKeyword.isEmpty()) {
            logger.error("No movies found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        logger.info(MessageFormat.format("Listing {0} movies of the keyword {1}", foundMoviesByKeyword.size(), keyword));

        return ResponseEntity.ok(new StandardResponseListMovie(foundMoviesByKeyword));
    }

    public ResponseEntity<StandardResponseTopMovies> findTopMoviesByGenre() {
        List<Movie> movies = movieRepository.findAll(Sort.by(Sort.Direction.DESC, "watchedTimes", "genre"));

        if(movies.isEmpty()) {
            logger.error("No movies found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<StandardResponseListMovie.Movie> movieList = new ArrayList<>();
        List<List<StandardResponseListMovie.Movie>> responseList = new ArrayList<>();
        StandardResponseListMovie.Movie currentMovie;

        String genre = movies.get(0).getGenre();

        for(Movie movie : movies) {
            if(movie.getGenre().equalsIgnoreCase(genre)) {
                currentMovie = new StandardResponseListMovie.Movie();
                currentMovie.setId(movie.getId());
                currentMovie.setName(movie.getName());
                currentMovie.setGenre(movie.getGenre());
                currentMovie.setImage(movie.getImage());
                currentMovie.setWatchedTimes(movie.getWatchedTimes());
                movieList.add(currentMovie);
            } else {
                responseList.add(movieList);
                genre = movie.getGenre();
                movieList = new ArrayList<>();
                currentMovie = new StandardResponseListMovie.Movie();
                currentMovie.setId(movie.getId());
                currentMovie.setName(movie.getName());
                currentMovie.setGenre(movie.getGenre());
                currentMovie.setImage(movie.getImage());
                currentMovie.setWatchedTimes(movie.getWatchedTimes());
                movieList.add(currentMovie);
            }
        }

        responseList.add(movieList);

        return ResponseEntity.ok(new StandardResponseTopMovies(responseList));
    }

    public ResponseEntity<StandardResponseListMovie> findWatchedMovies(Integer id) {

        Optional<User> user = userRepository.findById(id);

        if(!user.isPresent()) {
            logger.error("User not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<StandardResponseListMovie.Movie> foundWatchedMovies = new ArrayList<>();
        List<Long> listIds = movieWatchedInterestRepository.findWatchedMovies(id);
        List<Movie> watchedMovies = movieRepository.findMoviesByIdList(listIds);
        StandardResponseListMovie.Movie movieObject;

        for(Movie movie : watchedMovies) {
            movieObject = new StandardResponseListMovie.Movie();
            movieObject.setId(movie.getId());
            movieObject.setName(movie.getName());
            movieObject.setImage(movie.getImage());
            movieObject.setGenre(movie.getGenre());
            movieObject.setWatchedTimes(movie.getWatchedTimes());
            foundWatchedMovies.add(movieObject);
        }

        return ResponseEntity.ok(new StandardResponseListMovie(foundWatchedMovies));
    }

    public ResponseEntity<StandardResponseInterest> saveMovieInterest(Integer idMovie, Integer idUser) {

        Optional<Movie> movie = movieRepository.findById(idMovie);
        Optional<User> user = userRepository.findById(idUser);

        if(!movie.isPresent())
            logger.error("Movie not found!");

        if(!user.isPresent())
            logger.error("User not found");

        if(!movie.isPresent() || !user.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Optional<MovieWatchedInterest> movieWatchedInterest = movieWatchedInterestRepository.findByMovieAndUser(idMovie, idUser);

        if(movieWatchedInterest.isPresent()) {
            movieWatchedInterest.get().setInterest(true);
            movieWatchedInterestRepository.save(movieWatchedInterest.get());
        } else {
            MovieWatchedInterest movieInterest = new MovieWatchedInterest();
            movieInterest.setIdMovie(movie.get().getId());
            movieInterest.setIdUser(user.get().getId());
            movieInterest.setInterest(true);
        }

        movieWatchedInterest = movieWatchedInterestRepository.findByMovieAndUser(idMovie, idUser);

        return ResponseEntity.ok(new StandardResponseInterest(movieWatchedInterest.get()));
    }

    private String clearString(String string)     {
        string = string.replaceAll(" ","_");
        string = Normalizer.normalize(string, Normalizer.Form.NFD);
        return  string.replaceAll("[^\\p{ASCII}]", "");
    }

}