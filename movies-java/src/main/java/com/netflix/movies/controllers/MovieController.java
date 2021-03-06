package com.netflix.movies.controllers;

import com.netflix.movies.dtos.ReviewDTO;
import com.netflix.movies.responses.StandardResponseInterest;
import com.netflix.movies.responses.StandardResponseListMovie;
import com.netflix.movies.responses.StandardResponseMovie;
import com.netflix.movies.responses.StandardResponseTopMovies;
import com.netflix.movies.services.MovieService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "Movies API",tags = { "Movies" })
@RequestMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
@ApiResponses({
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 422, message = "Unprocessable Entity"),
        @ApiResponse(code = 500, message = "Internal Server Error") })
public class MovieController {

    @Autowired
    MovieService service;

    @ApiOperation("Search movies by genre")
    @ApiResponse(code = 200, message = "OK", response = StandardResponseListMovie.class)
    @GetMapping(value = "/movies/genres/{genre}", produces = "application/json")
    public ResponseEntity<StandardResponseListMovie> findMoviesByGenre(@PathVariable("genre") @ApiParam(required = true) String genre) {
            return service.findMoviesByGenre(genre);
    }

    @ApiOperation("Search movies by keyword")
    @ApiResponse(code = 200, message = "OK", response = StandardResponseListMovie.class)
    @GetMapping(value = "/movies", produces = "application/json")
    public ResponseEntity<StandardResponseListMovie> findMoviesByKeyword(@RequestParam("keyword") @ApiParam(required = true) String keyword) {
        return service.findMoviesByKeyword(keyword);
    }

    @ApiOperation("Get movie details by identification")
    @ApiResponse(code = 200, message = "OK", response = StandardResponseMovie.class)
    @GetMapping(value = "/movies/{id}", produces = "application/json")
    public ResponseEntity<StandardResponseMovie> getMovieDetails(@PathVariable @ApiParam(required = true) Integer id) {
        return service.findById(id);
    }

    @ApiOperation("Review a movie")
    @ApiResponse(code = 201, message = "OK", response = StandardResponseMovie.class)
    @PostMapping(value = "/movies/reviews/", produces = "application/json")
    public ResponseEntity<StandardResponseMovie> reviewMovie(@RequestBody @ApiParam(name = "Review", value = "Review") ReviewDTO reviewDTO) {
        return service.saveReview(reviewDTO);
    }

    @ApiOperation("Get the most watched movies grouped by category")
    @ApiResponse(code = 200, message = "OK", response = StandardResponseTopMovies.class)
    @GetMapping(value = "/movies/top", produces = "application/json")
    public ResponseEntity<StandardResponseTopMovies> getTopMoviesByGenre() {
        return service.findTopMoviesByGenre();
    }

    @ApiOperation("Get the watched movies")
    @ApiResponse(code = 200, message = "OK", response = StandardResponseListMovie.class)
    @GetMapping(value = "/movies/users/{id}/watched", produces = "application/json")
    public ResponseEntity<StandardResponseListMovie> getWatchedMovies(@PathVariable("id") @ApiParam(required = true) Integer id) {
        return service.findWatchedMovies(id);
    }

    @ApiOperation("Save movie to interest list")
    @ApiResponse(code = 201, message = "OK", response = StandardResponseInterest.class)
    @PostMapping(value = "/movies/{id}/users/{idUser}/interest", produces = "application/json")
        public ResponseEntity<StandardResponseInterest> saveMovieInterest(@PathVariable("id") @ApiParam(required = true) Integer id, @PathVariable("idUser") @ApiParam(required = true) Integer idUser) {
        return service.saveMovieInterest(id, idUser);
    }
}
