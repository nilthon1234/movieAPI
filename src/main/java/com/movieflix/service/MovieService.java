package com.movieflix.service;

import com.movieflix.dto.MovieDto;
import com.movieflix.dto.MoviePageResponse;
import org.springframework.core.annotation.Order;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
// aremos la transferencia de datos
@Order(7)
public interface MovieService {

    MovieDto addMovieDto(MovieDto movieDto, MultipartFile file) throws IOException;

    MovieDto getMovie(Integer movieId);
    List<MovieDto> getAllMovies();

    MovieDto updateMovie(Integer movieId, MovieDto movieDto, MultipartFile file) throws  IOException;

    String deleteMovie(Integer movieId) throws IOException;

    MoviePageResponse getAllMoviesWithPagination(Integer pageNumber, Integer pageSize);
    MoviePageResponse getAllMoviesWithPaginationAndSorting(Integer pageNumber, Integer pageSize,
                                                           String sortBy, String dir);


}
