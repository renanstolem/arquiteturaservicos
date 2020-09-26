package com.netflix.movies.responses;

import com.netflix.movies.entities.MovieWatchedInterest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StandardResponseInterest {

    private MovieWatchedInterest data;
}
