package com.example.literalura;

import com.example.literalura.main.Main;
import com.example.literalura.repository.RepositorioAutor;
import com.example.literalura.repository.RepositorioLivro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private RepositorioLivro repositorioLivro1;
	@Autowired
	private RepositorioAutor repositorioAutor1;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{
		Main menu = new Main(repositorioLivro1, repositorioAutor1);
		menu.menu();
	}

}
