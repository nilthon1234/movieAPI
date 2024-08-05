package com.movieflix.repositories;

import com.movieflix.entities.Movie;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.JpaRepository;

@Order(2)


/* este repositoriode interfaz nos servira para realizar operaciones CRUD*/
public interface MovieRepository  extends JpaRepository<Movie, Integer> {
}
