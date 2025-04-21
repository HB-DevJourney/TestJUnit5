# 🧪 Testes Unitários com JUnit 5 — Desafios práticos do curso da AlgaWorks

Este repositório contém exercícios práticos realizados durante o curso **"Testes Unitários com JUnit"** da [AlgaWorks](https://algaworks.com), com foco na criação de testes automatizados aplicando boas práticas e padrões modernos.

> 🔹 O código-fonte base das classes de domínio foi fornecido pela própria AlgaWorks.  
> 🔹 As implementações dos métodos e a criação dos testes foram desenvolvidas por mim como parte dos desafios propostos no curso.

## 📚 Objetivos

- Escrever testes com **JUnit 5**
- Aplicar o padrão **AAA (Arrange, Act, Assert)**
- Organizar os testes no estilo **BDD** com `@Nested` e `@DisplayName`
- Realizar testes de:
    - Validação de valores (`BigDecimal`, listas, etc.)
    - Exceções com `assertThrows`
    - Comportamento esperado de classes de domínio

## 🧱 Testes incluídos

- [`ContaBancariaTest`](https://github.com/HB-DevJourney/TestJUnit5/blob/main/src/test/java/com/algaworks/junit/desafios/ContaBancariaTest.java) – validações de criação, depósitos, saques e exceções
- [`CarrinhoCompraTest`](https://github.com/HB-DevJourney/TestJUnit5/blob/main/src/test/java/com/algaworks/junit/desafios/CarrinhoCompraTest.java) – simulação de um carrinho de compras, com adição, remoção e alteração de itens
- [`CadastroPostTest`](https://github.com/HB-DevJourney/TestJUnit5/blob/main/src/test/java/com/algaworks/junit/desafios/CadastroPostTest.java) – cobre os principais fluxos da classe `CadastroPost`, incluindo criação, edição, remoção e validações de regras de negócio.

## 🧪 Tecnologias

- Java 21
- JUnit 5
- Maven

## 🔎 Execução

Para rodar os testes:

```bash
mvn clean test
```

Ou use sua IDE (IntelliJ, Eclipse, VS Code) para executar diretamente os arquivos de teste.

> ⚠️ **Nota:** Este projeto faz parte do meu processo de aprendizado.  
> Por isso, alguns testes exploram diferentes estruturas, abordagens de validação e organização de código como prática intencional.  
> O foco principal é a **evolução contínua na escrita de testes de qualidade**.
