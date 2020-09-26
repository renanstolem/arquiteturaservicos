package com.netflix.movies.repositories;

import com.netflix.movies.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
