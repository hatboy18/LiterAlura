package com.example.literalura.main;

import com.example.literalura.model.*;
import com.example.literalura.repository.RepositorioAutor;
import com.example.literalura.repository.RepositorioLivro;
import com.example.literalura.service.ConversorDados;
import com.example.literalura.service.GetApi;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class Main {

    private Scanner sc = new Scanner(System.in);
    private GetApi consumir = new GetApi();
    private ConversorDados conversor = new ConversorDados();
    private final String ENDERECO = "https://gutendex.com/books/?search=";
    private final RepositorioLivro repositorioLivro;
    private final RepositorioAutor repositorioAutor;

    public Main(RepositorioLivro repositorioLivro, RepositorioAutor repositorioAutor) {
        this.repositorioLivro = repositorioLivro;
        this.repositorioAutor = repositorioAutor;
    }

    public void menu(){
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    1) Buscar Livros
                    2) Listar Livros Registrados
                    3) Listar Autores Registrados
                    4) Listar Autores vivos em um ano
                    5) Listar Livros por Idioma
                    """;
            System.out.println(menu);
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivro();
                    break;
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 3:
                    listaAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosEmAno();
                    break;
                case 5:
                    ListagemPorLinguagem();
                    break;
                case 0:
                    System.out.println("Saindo");
                    break;
                default:
                    System.out.println("Opção Inválida");
            }
        }
    }




    private void buscarLivro() {
        System.out.println("Digite o nome do livro: ");
        var nomeLivro = sc.nextLine().trim();
        var json = consumir.obterDados(ENDERECO + nomeLivro.replace(" ", "+").toLowerCase());
        var dados = conversor.obterDados(json, DadosApi.class);
        Optional<DadosLivro> livro = dados.results()
                .stream()
                .filter(b -> b.titulo().toLowerCase().contains(nomeLivro.toLowerCase()))
                .findFirst();
        System.out.println(livro);
        if (livro.isPresent()){
            DadosLivro dadosLivro = livro.get();

            if (repositorioLivro.existsByTitulo(String.valueOf(dadosLivro.titulo()))) {
                System.out.println("\n Livro ja existente");
            } else {
                Livro livro2 = new Livro(dadosLivro);
                AutorDados autorDados = dadosLivro.autor().get(0);
                Optional<Autor> autorOptional = repositorioAutor.findByName(autorDados.name());

                if(autorOptional.isPresent()) {
                    Autor autorExistente = autorOptional.get();
                    livro2.setAutor(autorExistente);
                    autorExistente.getLivros().add(livro2);
                    repositorioAutor.save(autorExistente);
                } else {
                    Autor novoAutor = new Autor(autorDados);
                    livro2.setAutor(novoAutor);
                    novoAutor.getLivros().add(livro2);
                    repositorioAutor.save(novoAutor);
                }

                repositorioLivro.save(livro2);

            }

        } else {
            System.out.println("\n Livro não encontrado");
        }

    }
    private void listarLivrosRegistrados (){
        List<Livro> livros = repositorioLivro.findAll();
        livros.forEach(System.out::println);
    }
    private void listaAutoresRegistrados() {
        List<Autor> autores = repositorioAutor.findAll();
        autores.forEach(System.out::println);
    }
    private void listarAutoresVivosEmAno() {
        System.out.println("\n Digite o ano da consulta");
        var ano = sc.nextInt();
        List<Autor> autores = repositorioAutor.findAutorVivo(ano);
        if(!autores.isEmpty()){
            System.out.println("Autores vivos em " + ano);
            autores.forEach(System.out::println);
        }else {
            System.out.println("Sem Resultados");
        }
    }
    private void ListagemPorLinguagem(){
        var opcao = -1;
        String linguagem = " ";

        System.out.println("\nSelecione a linguagem que deseja consultar");
        var linguagemMenu = """
               \n
               1 - Ingles
               2 - Fracces
               3 - Alemão
               4 - Portugues
               5 - Espanhol
               """;
        System.out.println(linguagemMenu);

        opcao = sc.nextInt();

        switch (opcao){
            case 1:
                linguagem = "en";
                break;
            case 2:
                linguagem = "fr";
                break;
            case 3:
                linguagem = "de";
                break;
            case 4:
                linguagem = "pt";
                break;
            case 5:
                linguagem = "es";
                break;
            default:
                System.out.println("invalido");
        }
        System.out.println("LIVROS REGISTRADOS: ");
        List<Livro> livros = repositorioLivro.acharLivroLinguagem(linguagem);
        livros.forEach(System.out::println);
    }
}
