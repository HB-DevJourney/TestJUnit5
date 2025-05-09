package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class) // remove os underscore
class FiltroNumerosTest {

    // Nomenclature BDD

    // Given, When, Then
    // Dado, Quando, Então
    @Test
    public void Dado_uma_lista_de_numeros_Quando_filtrar_por_pares_Entao_deve_retornar_apenas_numeros_pares() {
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4);
        List<Integer> numerosEsperados = Arrays.asList(2, 4);
        List<Integer> resultadoFiltro = FiltroNumeros.numerosPares(numeros);
        Assertions.assertIterableEquals(numerosEsperados, resultadoFiltro);
    }

    @Test
    public void Dado_uma_lista_de_numeros_Quando_filtrar_por_impares_Entao_deve_retornar_apenas_numeros_impares() {
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4);
        List<Integer> numerosEsperados = Arrays.asList(1, 3);
        List<Integer> resultadoFiltro = FiltroNumeros.numerosImpares(numeros);
        Assertions.assertIterableEquals(numerosEsperados, resultadoFiltro);
    }

    @Test
    public void deve_verificar_numero_positivo() {
        boolean positivo = FiltroNumeros.isPositivo(10);
        Assertions.assertTrue(positivo);
    }

    @Test
    public void deve_verificar_numero_negativo() {
        boolean positivo = FiltroNumeros.isPositivo(-10);
        Assertions.assertFalse(positivo);
    }

}