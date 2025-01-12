package com.example.literalura.repository;

import com.example.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioAutor extends JpaRepository<Autor, Long> {
    Optional<Autor> findByName(@Param("name") String name);

    @Query(value = "SELECT * FROM autores a WHERE :year BETWEEN CAST(a.birth_year AS INTEGER) AND CAST(a.death_year AS INTEGER)", nativeQuery = true)
    List<Autor> findAutorVivo(@Param("year") int year);

}
