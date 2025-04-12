package com.algaworks.junit.desafios;

import com.algaworks.junit.utilidade.ContaBancaria;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Conta Bancaria")
class ContaBancariaBDDTest {

    //Given <-> Dado
    @Nested
    @DisplayName("Dado uma conta bancária com saldo de R$10,00")
    class ContaBancariaComSaldo {

        private ContaBancaria conta;

        @BeforeEach
        void beforeEach() {
            conta = new ContaBancaria(BigDecimal.TEN);
        }

        // When <-> Quando
        @Nested
        @DisplayName("Quando efetuar saque com valor menor")
        class SaqueComValorMenor {
            private BigDecimal valorSaque = new BigDecimal("9.0");

            // Then <-> Então
            @Test
            @DisplayName("Então não deve lançar exception")
            void naoDeveLancarSaqueSemException() {
                assertDoesNotThrow(() -> conta.saque(valorSaque));
            }

            @Test
            @DisplayName("E deve subtrair do saldo")
            void deveSubtrairDoSaldo() {
                conta.saque(valorSaque);
                assertEquals(new BigDecimal("1.0"), conta.saldo());
            }
        }

        @Nested
        @DisplayName("Quando efetuar o saque com valor maior")
        class saqueComValorMaior {
            private BigDecimal valorSaque = new BigDecimal("20.0");

            @Test
            @DisplayName("Então deve lançar exception")
            void deveFalhar() {
                assertThrows(RuntimeException.class, () -> conta.saque(valorSaque));
            }

            @Test
            @DisplayName("E não deve alterar saldo")
            void naoDeveAlterarSaldo() {
                try {
                    conta.saque(valorSaque);
                } catch (Exception e) {
                    // Ignorado propositalmente, teste verifica saldo
                }
                assertEquals(BigDecimal.TEN, conta.saldo());
            }
        }

    }

    //Given <-> Dado
    @Nested
    @DisplayName("Dado uma conta bancária com saldo de R$0,00")
    class ContaBancariaComSaldoZerado {

        private ContaBancaria conta;

        @BeforeEach
        void beforeEach() {
            conta = new ContaBancaria(BigDecimal.ZERO);
        }

        // When <-> Quando
        @Nested
        @DisplayName("Quando efetuar o saque com valor maior")
        class SaqueComValorMaior {

            private BigDecimal valorSaque = new BigDecimal("1.0");

            // Then <-> Então
            @Test
            @DisplayName("Então deve lançar exception")
            void deveFalhar() {
                assertThrows(RuntimeException.class, () -> conta.saque(valorSaque));
            }
        }

        // When <-> Quando
        @Nested
        @DisplayName("Quando efetuar um deposito de R$8,00")
        class DepositoComDezReais {

            private BigDecimal valorDeposito = new BigDecimal("8.0");

            @Test
            @DisplayName("Deve somar ao saldo")
            void deveSomarOsSaldo() {
                conta.deposito(valorDeposito);
                assertEquals(new BigDecimal("8.0"), conta.saldo());
            }

        }

    }



}