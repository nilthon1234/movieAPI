package com.movieflix.service;

import com.movieflix.Excepciones.FileExistsException;
import com.movieflix.Excepciones.MovieNotFoundException;
import com.movieflix.dto.MovieDto;
import com.movieflix.dto.MoviePageResponse;
import com.movieflix.entities.Movie;
import com.movieflix.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Order(8)


@Service
public class MovieServiceImpl  implements  MovieService{

    private final MovieRepository movieRepository;
    private final FileService fileService;

    // extraemos la variable de entorno del File controller
    @Value("${project.poster}")
    private String path;

    @Value("${base.url}")
    private  String baseUrl;


    //agregamos un parametro de constructor

    public MovieServiceImpl(MovieRepository movieRepository, FileService fileService) {
        this.movieRepository = movieRepository;
        this.fileService = fileService;
    }


    @Override
    public MovieDto addMovieDto(MovieDto movieDto, MultipartFile file) throws IOException {

        //1. Upload the file
        if (Files.exists(Paths.get(path + File.separator + file.getOriginalFilename()))) {
            throw  new FileExistsException("¡El archivo ya existe! Por favor ingrese otro nombre de archivo!");
        }
        String uploadFileName = fileService.uploadFile(path, file);
        //2. establecer  el valor del campo  "POST" como nombre de archhivo
        movieDto.setPoster(uploadFileName);
        //3.asignar dto al objeto de película
        Movie movie = new Movie(
                null,
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMoviesCast(),
                movieDto.getReleaseYear(),
                movieDto.getPoster()
        );
        //4. guarde el objeto de película -> objeto de película guardado
        Movie saveMovie = movieRepository.save(movie);
        //5. generar la URL del poster
        String posterUrl = baseUrl + "/file/" + uploadFileName;
        //6. map Mover el objeto al objeto DTO y devolverlo
        MovieDto response = new MovieDto(
                saveMovie.getMoviesId(),
                saveMovie.getTitle(),
                saveMovie.getDirector(),
                saveMovie.getStudio(),
                saveMovie.getMoviesCast(),
                saveMovie.getReleaseYear(),
                saveMovie.getPoster(),
                posterUrl
        );

        return response;
    }

    @Override
    public MovieDto getMovie(Integer movieId) {
        //1. verifique los datos en la base de datos y, si existen, obtenga los datos de la identificación

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException("¡Película con id: "+ movieId +" no encontrada!"));

        //2. generar posterUrl
        String posterUrl = baseUrl + "/file/" + movie.getPoster();

        //3. asignar al objeto MovieDto y devolverlo
        MovieDto response = new MovieDto(
                movie.getMoviesId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getStudio(),
                movie.getMoviesCast(),
                movie.getReleaseYear(),
                movie.getPoster(),
                posterUrl
        );

        return response;
    }

    @Override
    public List<MovieDto> getAllMovies() {
        //1. recuperar todos los datos de la base de datos
        List<Movie> movies = movieRepository.findAll();

        List<MovieDto> movieDtos = new ArrayList<>();
        //2. iiterar a través de la lista, generar la URL de publicación para cada objeto de película,
        // y asignar a MovieDto obj
        for (Movie movie : movies) {
            String posterUrl = baseUrl + "/file/" + movie.getPoster();

            MovieDto movieDto = new MovieDto(
                    movie.getMoviesId(),
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getStudio(),
                    movie.getMoviesCast(),
                    movie.getReleaseYear(),
                    movie.getPoster(),
                    posterUrl
            );
            movieDtos.add(movieDto);
        }

        return movieDtos;
    }

    @Override
    public MovieDto updateMovie(Integer movieId, MovieDto movieDto, MultipartFile file) throws IOException {
        //1. check if movie object exists with given movieId
        Movie mv = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException("¡Película con id: "+ movieId +" no encontrada!"));

        /*2.si el archivo es nulo, no haga nada si el archivo es nulo, elimine
        * archivo existente asociado con el registro y actualizar el nuevo archivo */
        String fileName = mv.getPoster();
        if (file != null){
            Files.deleteIfExists(Paths.get(path + File.separator + fileName));
            fileName = fileService.uploadFile(path, file);
        }

//        3. establezca el valor del póster de movieDto s, según el paso 2
        movieDto.setPoster(fileName);
//        4. asignarlo al objeto Película
        Movie movie = new Movie(
                mv.getMoviesId(),
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMoviesCast(),
                movieDto.getReleaseYear(),
                movieDto.getPoster()

        );
//        5. GUARDAR EL OBJETO DE PELÍCULA -> DEVOLVER OBJETO DE PELÍCULA GUARDADO
        Movie updatedMovie = movieRepository.save(movie);

//        6. generar posterUrl para ello
        String posterUrl = baseUrl + "/file/" + fileName;

//        7. asignar a MovieDto y devolverlo
        MovieDto response = new MovieDto(
                movie.getMoviesId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getStudio(),
                movie.getMoviesCast(),
                movie.getReleaseYear(),
                movie.getPoster(),
                posterUrl
        );
        return response;
    }

    @Override
    public String deleteMovie(Integer movieId) throws IOException {

//        1. CHECK IF MOVIE OBJECT EXISTS IN DB
        Movie mv = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException("¡Película con id: "+ movieId +" no encontrada!"));
        Integer id = mv.getMoviesId();
//        2. DELETE THE FILE ASSOCIATED WITH THIS OBJECT
        Files.deleteIfExists(Paths.get(path + File.separator + mv.getPoster()));

//        3. DELETE THE MOVIE OBJECT
        movieRepository.delete(mv);

        return "Movie deleted with id = " + movieId;
    }

    @Override
    public MoviePageResponse getAllMoviesWithPagination(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Movie> moviePages = movieRepository.findAll(pageable);
        List<Movie> movies = moviePages.getContent();

        List<MovieDto> movieDtos = new ArrayList<>();

        //2. iiterar a través de la lista, generar la URL de publicación para cada objeto de película,
        // y asignar a MovieDto obj
        for (Movie movie : movies) {
            String posterUrl = baseUrl + "/file/" + movie.getPoster();

            MovieDto movieDto = new MovieDto(
                    movie.getMoviesId(),
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getStudio(),
                    movie.getMoviesCast(),
                    movie.getReleaseYear(),
                    movie.getPoster(),
                    posterUrl
            );
            movieDtos.add(movieDto);
        }


        return new MoviePageResponse(movieDtos, pageNumber, pageSize,
                moviePages.getTotalElements(),
                moviePages.getTotalPages(),
                moviePages.isLast());

    }

    @Override
    public MoviePageResponse getAllMoviesWithPaginationAndSorting(Integer pageNumber, Integer pageSize,
                                                                  String sortBy, String dir) {
        Sort sort = sortBy.equalsIgnoreCase("asc")? Sort.by(dir).ascending()
                                                                : Sort.by(dir).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Movie> moviePages = movieRepository.findAll(pageable);
        List<Movie> movies = moviePages.getContent();

        List<MovieDto> movieDtos = new ArrayList<>();

        //2. iiterar a través de la lista, generar la URL de publicación para cada objeto de película,
        // y asignar a MovieDto obj
        for (Movie movie : movies) {
            String posterUrl = baseUrl + "/file/" + movie.getPoster();

            MovieDto movieDto = new MovieDto(
                    movie.getMoviesId(),
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getStudio(),
                    movie.getMoviesCast(),
                    movie.getReleaseYear(),
                    movie.getPoster(),
                    posterUrl
            );
            movieDtos.add(movieDto);
        }


        return new MoviePageResponse(movieDtos, pageNumber, pageSize,

                moviePages.getTotalPages(),
                (int) moviePages.getTotalElements(),
                moviePages.isLast());
    }

}
