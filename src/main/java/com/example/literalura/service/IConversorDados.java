package com.example.literalura.service;

public interface IConversorDados {
    <T> T obterDados(String json, Class<T> classe);
}
