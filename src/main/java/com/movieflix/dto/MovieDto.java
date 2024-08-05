package com.movieflix.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.annotation.Order;

import java.util.Set;
@Order(3)

// en mi Dto  sera utilizado para transferir datos a diferentes pertes de la aplicacion

@Data
@NoArgsConstructor//un constructor que no aceptan argumentos especificos
@AllArgsConstructor// si acepta argumentos

public class MovieDto {

    private Integer moviesId;


    @NotBlank(message = "Please provide movie´s studio!")
    private String title;


    @NotBlank(message = "Please provide movie´s director!")
    private String director;


    @NotBlank(message = "Please provide movie´s studio!")
    private String studio;


    private Set<String> moviesCast;



    private Integer releaseYear;


    @NotBlank(message = "Please provide movie´s release poster!")
    private String poster;

    @NotBlank(message = "Please provide poster´s url!")
    private String posterUrl;

    public MovieDto(String moviesId, String title, String director, String studio, Set<String> moviesCast, Integer releaseYear, String poster, String posterUrl) {
        this.moviesId = Integer.parseInt(moviesId);
        this.title = title;
        this.director = director;
        this.studio = studio;
        this.moviesCast = moviesCast;
        this.releaseYear = releaseYear;
        this.poster = poster;
        this.posterUrl = posterUrl;
    }
}
