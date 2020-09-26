package com.netflix.movies.repositories;

import com.netflix.movies.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    @Query(value = "SELECT * FROM bdexadatanetflix.tb_movies WHERE id_movie in :ids", nativeQuery = true)
    List<Movie> findMoviesByIdList(@Param("ids") List<Long> ids);
}
