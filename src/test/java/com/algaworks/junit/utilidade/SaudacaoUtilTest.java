package com.algaworks.junit.utilidade;


import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.algaworks.junit.utilidade.SaudacaoUtil.saudar;
import static org.assertj.core.api.Assertions.*;


@DisplayName("Testes no utilitário de saudação")
class SaudacaoUtilTest {

    @ParameterizedTest
    @ValueSource(ints = {5, 6, 7, 8, 9, 10, 11})
    @DisplayName("Deve saudar com bom dia")
    public void saudarBomDia(int hora) {
        String saudacao = saudar(hora);
        assertThat(saudacao).is(SaudacoesUtilConditions.igualBomDia());
    }

    @ParameterizedTest
    @ValueSource(ints = {12, 13, 14, 15, 16, 17})
    @DisplayName("Deve saudar com boa tarde")
    public void saudarBoaTarde(int hora) {
        String saudacao = saudar(hora);
        Assertions.assertThat(saudacao).isEqualTo("Boa tarde");
    }

    @ParameterizedTest
    @ValueSource(ints = {18, 19, 20, 21, 22, 23, 0, 1, 2, 3, 4})
    @DisplayName("Deve saudar com boa noite")
    public void saudarBoaNoite(int hora) {
        String saudacao = saudar(hora);
        Assertions.assertThat(saudacao).isEqualTo("Boa noite");
    }


    @ParameterizedTest
    @ValueSource(ints = {25, 26, 27, 28, 29, 30})
    @DisplayName("Deve lançar exception por hora muito alta")
    public void deveLancarExceptionHoraAlta(int hora) {
        assertThatThrownBy(() -> saudar(hora))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Hora inválida");
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -2, -3, -4, -5})
    @DisplayName("Deve lançar exception por hora negativa")
    public void deveLancarException(int hora) {
        assertThatThrownBy(() -> saudar(hora))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Hora inválida");
    }
}