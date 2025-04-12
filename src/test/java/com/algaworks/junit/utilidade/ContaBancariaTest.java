package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Teste na class Conta Bancária")
class ContaBancariaTest {

    @Nested
    class CriarContaBancaria {

        @Test
        @DisplayName("Deve criar conta com saldo R$0,00")
        public void deveCriarContaBancaria_ComSaldoZero() {
            BigDecimal valor = BigDecimal.valueOf(0);
            ContaBancaria conta = new ContaBancaria(valor);
            assertEquals(BigDecimal.valueOf(0), conta.getSaldo(), "Valor incorreto");
        }

        @Test
        @DisplayName("Lançar exceção quando criar conta com saldo nulo")
        public void deveLancarExcecao_quandoSaldoForNulo() {
            IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                    () -> new ContaBancaria(null));
            assertEquals("Saldo nulo", illegalArgumentException.getMessage());
        }

        @Test
        @DisplayName("Deve criar conta quando o saldo for maior que R$0,00")
        public void deveCriarConta_ComSaldoPositivo() {
            BigDecimal valor = BigDecimal.valueOf(100);
            ContaBancaria conta = new ContaBancaria(valor);
            assertEquals(BigDecimal.valueOf(100), conta.getSaldo(), "Valor incorreto");
        }

        @Test
        @DisplayName("Deve criar conta quando saldo for minimo positivo R$0.01")
        public void deveCriarConta_ComSaldoPositivoMinimo() {
            BigDecimal valor = BigDecimal.valueOf(0.01);
            ContaBancaria conta = new ContaBancaria(valor);
            assertEquals(BigDecimal.valueOf(0.01), conta.getSaldo(), "Valor incorreto");
        }

        @Test
        @DisplayName("Deve criar conta quando saldo for menor que R$0,00")
        public void deveCriarConta_ComSaldoNegativo() {
            BigDecimal valor = BigDecimal.valueOf(-100);
            ContaBancaria conta = new ContaBancaria(valor);
            assertEquals(BigDecimal.valueOf(-100), conta.getSaldo(), "Valor incorreto");
        }

        @Test
        @DisplayName("Deve criar conta quando saldo for minimo negativo -R$0.01")
        public void deveCriarConta_ComSaldoNegativoMinimo() {
            BigDecimal valor = BigDecimal.valueOf(-0.01);
            ContaBancaria conta = new ContaBancaria(valor);
            assertEquals(BigDecimal.valueOf(-0.01), conta.getSaldo(), "Valor incorreto");
        }

    }

    @Nested
    class Deposito {

        @Test
        @DisplayName("Deve depositar quando o valor for maior que R$0,00")
        public void deveDepositar_ComValorMaiorQueZero() {
            ContaBancaria conta = new ContaBancaria(BigDecimal.valueOf(100));
            conta.deposito(BigDecimal.valueOf(50));
            assertEquals(BigDecimal.valueOf(150), conta.getSaldo(), "Valor incorreto");
        }

        @Test
        @DisplayName("Lançar exceção quando valor for R$0,00")
        public void deveLancarExcecao_QuandoDepositoForZero() {
            ContaBancaria conta = new ContaBancaria(BigDecimal.valueOf(100));
            IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                    () -> conta.deposito(BigDecimal.valueOf(0)));
            assertEquals("Valor deve ser maior que zero", illegalArgumentException.getMessage(), "Valor incorreto");
        }

        @Test
        @DisplayName("Lançar exceção quando valor for negativo")
        public void deveLancarExcecao_QuandoDepositoForNegativo() {
            ContaBancaria conta = new ContaBancaria(BigDecimal.valueOf(100));
            IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                    () -> conta.deposito(BigDecimal.valueOf(-50)));
            assertEquals("Valor deve ser maior que zero", illegalArgumentException.getMessage(),  "Valor incorreto");
        }

        @Test
        @DisplayName("Lançar exceção quando valor for nulo")
        public void deveLancarExcecao_QuandoDepositoForNulo() {
            ContaBancaria conta = new ContaBancaria(BigDecimal.valueOf(100));
            IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                    () -> conta.deposito(null));
            assertEquals("Valor deve ser maior que zero", illegalArgumentException.getMessage(),  "Valor incorreto");
        }

    }

    @Nested
    class Saque {

        @Test
        @DisplayName("Deve sacar quando tiver saldo suficiente")
        public void deveSacar_QuandoSaqueForMenorQueOSaldo() {
            ContaBancaria conta = new ContaBancaria(BigDecimal.valueOf(100));
            conta.saque(BigDecimal.valueOf(50));
            assertEquals(BigDecimal.valueOf(50), conta.getSaldo(), "Valor incorreto");
        }

        @Test
        @DisplayName("Lançar exceção quando valor do saque for maior que o saldo")
        public void deveLancarExcecao_QuandoSaqueForMaiorQueOSaldo() {
            ContaBancaria conta = new ContaBancaria(BigDecimal.valueOf(100));
            RuntimeException runtimeException = assertThrows(RuntimeException.class,
                    () -> conta.saque(BigDecimal.valueOf(200)));
            assertEquals("Saldo insuficiente", runtimeException.getMessage(), "Valor incorreto");
        }

        @Test
        @DisplayName("Lancar exceção quando o valor do saque for negativo")
        public void deveLancarExcecao_QuandoSaqueForNegativo() {
            ContaBancaria conta = new ContaBancaria(BigDecimal.valueOf(100));
            IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                    () -> conta.saque(BigDecimal.valueOf(-10)));
            assertEquals("Valor do saque é menor que zero", illegalArgumentException.getMessage(), "Valor incorreto");
        }

        @Test
        @DisplayName("Lançar exceção quando o valor do saque for nulo")
        public void deveLancarExcecao_QuandoSaqueForNulo() {
            ContaBancaria conta = new ContaBancaria(BigDecimal.valueOf(100));
            IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                    () -> conta.saque(null));
            assertEquals("Valor do saque é nulo", illegalArgumentException.getMessage(), "Valor incorreto");
        }

    }

    @Nested
    class Saldo {
        @Test
        @DisplayName("Verifica o saldo da conta")
        public void deveVerificarSaldo() {
            ContaBancaria conta = new ContaBancaria(BigDecimal.valueOf(100));
            assertEquals(BigDecimal.valueOf(100), conta.getSaldo(), "Valor incorreto");
        }
    }
}