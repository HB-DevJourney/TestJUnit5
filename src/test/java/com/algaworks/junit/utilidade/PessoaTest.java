package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PessoaTest {

    @Test
    void assercao_agrupada_() {
        Pessoa pessoa = new Pessoa("Mateus", "Silva");

        // Verifica ambas as falhas
        assertAll("Asserções de pessoa",
                () -> assertEquals("Mateus", pessoa.getNome()),
                () -> assertEquals("Silva", pessoa.getSobrenome())
                );
    }

}