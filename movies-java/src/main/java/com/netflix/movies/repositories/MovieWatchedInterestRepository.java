package com.netflix.movies.repositories;

import com.netflix.movies.entities.MovieWatchedInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieWatchedInterestRepository extends JpaRepository<MovieWatchedInterest, Integer> {

    @Query(value = "SELECT * FROM bdexadatanetflix.tb_movie_watched_interest WHERE id_user = :id", nativeQuery = true)
    List<Long> findWatchedMovies(@Param("id") Integer id);

    @Query(value = "SELECT * FROM bdexadatanetflix.tb_movie_watched_interest WHERE id_movie = :idMovie and id_user = :idUser", nativeQuery = true)
    Optional<MovieWatchedInterest> findByMovieAndUser(@Param("idMovie") Integer idMovie,@Param("idUser") Integer idUser);
}
