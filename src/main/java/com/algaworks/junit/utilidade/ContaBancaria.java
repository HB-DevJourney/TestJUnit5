package com.algaworks.junit.utilidade;

import java.math.BigDecimal;

public class ContaBancaria {

    private BigDecimal saldo;

    public BigDecimal getSaldo() {
        return saldo;
    }

    public ContaBancaria(BigDecimal saldo) {
        //TODO 1 - validar saldo: não pode ser nulo, caso seja, deve lançar uma IllegalArgumentException
        //TODO 2 - pode ser zero ou negativo
        if (saldo == null) {
            throw new IllegalArgumentException("Saldo nulo");
        } else {
            this.saldo = saldo;
        }

    }

    public void saque(BigDecimal valor) {
        //TODO 1 - validar valor: não pode ser nulo, zero ou menor que zero, caso seja, deve lançar uma IllegalArgumentException
        //TODO 2 - Deve subtrair o valor do saldo
        //TODO 3 - Se o saldo for insuficiente deve lançar uma RuntimeException
        if (valor == null) {
            throw new IllegalArgumentException("Valor do saque é nulo");
        }
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor do saque é menor que zero");
        }
        if (valor.compareTo(saldo) > 0) {
            throw new RuntimeException("Saldo insuficiente");
        }
        saldo = saldo.subtract(valor);

    }

    public void deposito(BigDecimal valor) {
        //TODO 1 - validar valor: não pode ser nulo, zero ou menor que zero, caso seja, deve lançar uma IllegalArgumentException
        //TODO 2 - Deve adicionar o valor ao saldo
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor deve ser maior que zero");
        } else {
            saldo = saldo.add(valor);
        }
    }

    public BigDecimal saldo() {
        return saldo;
    }

}
