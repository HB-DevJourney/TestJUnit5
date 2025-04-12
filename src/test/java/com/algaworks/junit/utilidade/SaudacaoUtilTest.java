package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static com.algaworks.junit.utilidade.SaudacaoUtil.saudar;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes no utilitário de saudação")
class SaudacaoUtilTest {

    @Test
    @DisplayName("Deve saudar com bom dia")
    public void saudarBomDia() {
        int horaValida = 9;
        String saudacao = saudar(horaValida);
        assertEquals("Bom dia", saudacao, "Saudação incorreta");
    }

    @Test
    @DisplayName("Deve saudar com boa tarde")
    public void saudarBoaTarde() {
        int horaValida = 15;
        String saudacao = saudar(horaValida);
        assertEquals("Boa tarde", saudacao, "Saudação incorreta");
    }

    @Test
    @DisplayName("Deve saudar com boa noite")
    public void saudarBoaNoite() {
        int horaValida = 23;
        String saudacao = saudar(horaValida);
        assertEquals("Boa noite", saudacao, "Saudação incorreta");
    }

    @Test
    @DisplayName("Deve saudar com bom dia a partir das 5 horas")
    public void saudarComBomDiaAPartirDas5h() {
        int horaValida = 5;
        String saudacao = saudar(horaValida);
        assertEquals("Bom dia", saudacao, "Saudação incorreta");
    }

    @Test
    @DisplayName("Deve saudar com boa noite a partir até 4 horas")
    public void saudarComBoaNoiteAteAs4h() {
        int horaValida = 4;
        String saudacao = saudar(horaValida);
        assertEquals("Boa noite", saudacao, "Saudação incorreta");
    }

    @Test
    @DisplayName("Deve saudar com bom dia até as 11 horas")
    public void saudarLimiteBomDia() {
        int horaValida = 11;
        String saudacao = saudar(horaValida);
        assertEquals("Bom dia", saudacao);
    }

    @Test
    @DisplayName("Deve saudar com boa tarde até as 17 horas")
    public void saudarLimiteBoaTarde() {
        int horaValida = 17;
        String saudacao = saudar(horaValida);
        assertEquals("Boa tarde", saudacao);
    }

    @Test
    @DisplayName("Deve saudar com boa noite até as 4 horas")
    public void saudarLimiteBoaNoite() {
        int horaValida = 4;
        String saudacao = saudar(horaValida);
        assertEquals("Boa noite", saudacao);
    }


    @Test
    @DisplayName("Deve lançar exception por hora muito alta")
    public void deveLancarExceptionHoraAlta() {
        int horaInvalida = 30;
        Executable chamadaInvalidaDeMetodo = () -> saudar(horaInvalida);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, chamadaInvalidaDeMetodo);
        assertEquals("Hora inválida", e.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exception por hora negativa")
    public void deveLancarException() {
        int horaInvalida = -10;
        Executable chamadaInvalidaDeMetodo = () -> saudar(horaInvalida);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, chamadaInvalidaDeMetodo);
        assertEquals("Hora inválida", e.getMessage());
    }

    @Test
    @DisplayName("Não deve lançar exception")
    public void naoDeveLancarException() {
        int horaValida = 0;
        Executable chamadaValidaDeMetodo = () -> saudar(horaValida);
        assertDoesNotThrow(chamadaValidaDeMetodo);
    }

}