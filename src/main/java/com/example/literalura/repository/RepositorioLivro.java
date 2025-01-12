package com.example.literalura.repository;

import com.example.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@EnableJpaRepositories
@Repository
public interface RepositorioLivro extends JpaRepository<Livro, Long> {

    Boolean existsByTitulo(@Param("titulo") String title);

    @Query(value = "SELECT * FROM livros WHERE :linguagem = ANY (livros.idioma)", nativeQuery = true)
    List<Livro> acharLivroLinguagem(@Param("linguagem") String linguagem);

}
