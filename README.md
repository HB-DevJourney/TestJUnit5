# 🧪 Projeto de Testes Unitários com JUnit 5

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
- [`Cadastro de Post`](https://github.com/HB-DevJourney/TestJUnit5/blob/main/src/test/java/com/algaworks/junit/desafios/CadastroPostTest.java) - Este teste cobre os principais fluxos da classe CadastroPost, responsável pela criação, edição e remoção de posts em um sistema de blog.

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

## ⚠️ Observação

> Este projeto faz parte do meu aprendizado, por isso alguns testes exploram variações de estrutura, assertivas e organização de código como forma de prática. O foco é a **evolução contínua na qualidade de testes**.
