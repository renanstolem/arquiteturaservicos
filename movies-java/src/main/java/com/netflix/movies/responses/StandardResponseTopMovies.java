package com.netflix.movies.responses;

import com.netflix.movies.entities.Movie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StandardResponseTopMovies {

    private List<List<StandardResponseListMovie.Movie>> data;
}
