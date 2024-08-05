package com.movieflix.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.core.annotation.Order;

import java.util.Set;
@Order(1)

//Entities representa a las entidades de domino relacionaada con la estructuras en la base de datos

@Entity // para marcar una clase como entidaad lo que permite un mapeo a una tabla BD
@NoArgsConstructor//autogenerara un contructor sin argumentos
@AllArgsConstructor// con argumentos
@Getter


public class Movie {
    @Id
    /*En particular, esta estrategia se refiere a la generación de
    valores de clave primaria utilizando una columna de identidad en la base de datos*/
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer moviesId;

    @Column(nullable = false, length = 200)
    @NotBlank(message = "Por favor proporcione el estudio de cine!")
    private String title;

    @Column(nullable = false)
    @NotBlank(message = "Por favor proporcione el director de la película!")
    private String director;

    @Column(nullable = false)
    @NotBlank(message = "¡Por favor proporcione el estudio de cine!")
    private String studio;

    @ElementCollection // especificamos que no es una entidad si no una coleccion de elementos encrustados
    @CollectionTable(name = "movie_cast")
    private Set<String> moviesCast;

    @Column(nullable = false)//  nullable si es nulo me salga un error false
    private Integer releaseYear;

    @Column(nullable = false)
    @NotBlank(message = "Please provide movie´s release poster!")
    private String poster;

}
