package com.example.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AutorDados(@JsonAlias("name")String name,
                         @JsonAlias("birth_year") String anoNasc,
                         @JsonAlias("death_year") String anoMort) {
}
