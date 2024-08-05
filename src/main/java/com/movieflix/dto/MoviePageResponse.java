package com.movieflix.dto;

import java.util.List;

//Paginacion

public record MoviePageResponse (List<MovieDto> movieDtoList,
                                 Integer pageNumber,
                                 Integer pageSize,
                                 long totalElements,
                                 int totalPages,

                                 boolean isLast) {



}
