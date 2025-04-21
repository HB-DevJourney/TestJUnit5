package com.algaworks.junit.desafios;

import com.algaworks.junit.ecommerce.CarrinhoCompra;
import com.algaworks.junit.ecommerce.Cliente;
import com.algaworks.junit.ecommerce.ItemCarrinhoCompra;
import com.algaworks.junit.ecommerce.Produto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Carrinho de Compra")
class CarrinhoCompraTest {

    private CarrinhoCompra carrinhoCompra;
    private Cliente cliente;
    private List<ItemCarrinhoCompra> itens;
    private Produto pcGamer;
    private Produto livro;
    private Produto headset;

    @Nested
    @DisplayName("Dado um carrinho com três itens")
    class CarrinhoComDoisItens {

        @BeforeEach
        void beforeEach() {

            cliente = new Cliente(1L, "Mateus");

            pcGamer = new Produto(1L, "Pc Gamer", "Pc Gamer", new BigDecimal("1000.0"));
            livro = new Produto(2L, "Livro", "Livro", new BigDecimal("20.0"));
            headset = new Produto(3L, "Headset", "Headset", new BigDecimal("100.0"));

            itens = new ArrayList<>();
            itens.add(new ItemCarrinhoCompra(livro, 2));
            itens.add(new ItemCarrinhoCompra(pcGamer, 1));

            carrinhoCompra = new CarrinhoCompra(cliente, itens);
        }

        // When <-> Quando
        @Nested
        @DisplayName("Quando retornar itens")
        class QuandoRetornarItens {

            @Test
            @DisplayName("Então deve retornar dois itens")
            void deveRetornarDoisItens() {
                assertEquals(2, carrinhoCompra.getItens().size());
            }

            @Test
            @DisplayName("E deve retornar uma nova instância de itens")
            void deveRetornarUmaNovaLista() {
                carrinhoCompra.getItens().clear();
                assertEquals(2, carrinhoCompra.getItens().size());
            }
        }

        @Nested
        @DisplayName("Quando remover um pcgamer")
        class QuandoRemoverUmPcGamer {

            @BeforeEach
            void beforeEach() {
                carrinhoCompra.removerProduto(pcGamer);
            }

            @Test
            @DisplayName("Então deve diminuir a quantidade total de itens")
            void deveDiminuirAQuantidadeTotalItens() {
                assertEquals(1, carrinhoCompra.getItens().size());
            }

            @Test
            @DisplayName("E não deve remover os demais itens")
            void naoDeveRemoverOsDemaisItens() {
                assertEquals(livro, carrinhoCompra.getItens().get(0).getProduto());
            }

        }

        @Nested
        @DisplayName("Quando aumentar a quantidade de um livro")
        class QuandoAumentarQuantidadeLivro {

            @BeforeEach
            void beforeEach() {
                carrinhoCompra.aumentarQuantidadeProduto(livro);
            }

            @Test
            @DisplayName("Então deve somar na quantidade dos itens iguais")
            void deveAumentarUmaUnidadeDeLivro() {
                assertEquals(3, carrinhoCompra.getItens().get(0).getQuantidade());
                assertEquals(1, carrinhoCompra.getItens().get(1).getQuantidade());
            }

            @Test
            @DisplayName("Então deve retornar quatro de quantidade total de itens")
            void deveRetornarQuantidadeTotalItens() {
                assertEquals(4, carrinhoCompra.getQuantidadeTotalDeProdutos());
            }

            @Test
            @DisplayName("Então deve retornar o valor total correto de itens")
            void deveRetornarValorTotalItens() {
                assertEquals(new BigDecimal("1060.0"), carrinhoCompra.getValorTotal());
            }

        }
        
        @Nested
        @DisplayName("Quando diminuir a quantidade de um livro")
        class QuandoDiminuirQuantidadeLivro {

            @BeforeEach
            void beforeEach() {
                carrinhoCompra.diminuirQuantidadeProduto(livro);
            }

            @Test
            @DisplayName("Então deve diminuir a quantidade dos itens iguais")
            void deveDiminuirUmaUnidadeDeLivro() {
                assertEquals(1, carrinhoCompra.getItens().get(0).getQuantidade());
                assertEquals(1, carrinhoCompra.getItens().get(1).getQuantidade());
            }

            @Test
            @DisplayName("Então deve retornar dois de quantidade total de itens")
            void deveRetornarQuantidadeTotalItens() {
                assertEquals(2, carrinhoCompra.getQuantidadeTotalDeProdutos());
            }

            @Test
            @DisplayName("Então deve retornar o valor total correto de itens")
            void deveRetornarValorTotalItens() {
                assertEquals(new BigDecimal("1020.0"), carrinhoCompra.getValorTotal());
            }

        }

        @Nested
        @DisplayName("Quando remover quantidade de um item com apenas um de quantidade")
        class QuandoRemoverQuantidadeDeItemUnico {

            @BeforeEach
            void beforeEach() {
                carrinhoCompra.diminuirQuantidadeProduto(pcGamer);
            }

            @Test
            @DisplayName("Então deve remover")
            void deveRemoverItem() {
                assertNotEquals(carrinhoCompra.getItens().get(0).getProduto(), pcGamer);
            }

        }

        @Nested
        @DisplayName("Quando adicionar item com quantidade inválida")
        class QuandoAdicionarItemComQuantidadeInvalida {

            @Test
            @DisplayName("Então deve lançar exception")
            void deveLancarException() {
                assertThrows(IllegalArgumentException.class,
                        () -> carrinhoCompra.adicionarProduto(pcGamer, -1));
            }
        }

        @Nested
        @DisplayName("Quando esvaziar carrinho")
        class QuandoEsvaziarCarrinho {

            @BeforeEach
            void beforeEach() {
                carrinhoCompra.esvaziar();
            }

            @Test
            @DisplayName("Então carrinho deve ficar vazio")
            void deveFicarVazio() {
                assertTrue(carrinhoCompra.getItens().isEmpty());
            }

            @Test
            @DisplayName("Então deve retornar zero na quantidade de itens")
            void deveRetornarZeroNaQuantidadeItens() {
                assertEquals(0, carrinhoCompra.getQuantidadeTotalDeProdutos());
            }

            @Test
            @DisplayName("Então soma dos produtos deve ser zero")
            void deveSomarDosProdutosItens() {
                assertEquals(0, carrinhoCompra.getValorTotal().compareTo(BigDecimal.ZERO));
            }
        }
    }

    @Nested
    @DisplayName("Dado um carrinho vazio")
    class DadoUmCarrinhoVazio {

        @BeforeEach
        public void beforeEach() {

            cliente = new Cliente(1L, "Mateus");

            pcGamer = new Produto(1L, "Pc Gamer", "Pc Gamer", new BigDecimal("1000.0"));
            livro = new Produto(2L, "Livro", "Livro", new BigDecimal("20.0"));
            headset = new Produto(3L, "Headset", "Headset", new BigDecimal("100.0"));

            itens = new ArrayList<>();

            carrinhoCompra = new CarrinhoCompra(cliente, itens);

        }

        @Nested
        @DisplayName("Quando adicionar dois pc gamer e um livro")
        class QuandoAdicionarDoisItensIguais {

            @BeforeEach
            void beforeEach() {
                carrinhoCompra.adicionarProduto(pcGamer, 1);
                carrinhoCompra.adicionarProduto(pcGamer, 1);
                carrinhoCompra.adicionarProduto(livro, 1);
            }

            // Fazer iterações de listas
            @Test
            @DisplayName("Então deve conter apenas produtos adicionados")
            void entaoDeveConterApenasProdutosAdicionados() {
                Assertions.assertThat(carrinhoCompra.getItens())
                        .flatMap(ItemCarrinhoCompra::getProduto)
                        .contains(pcGamer, livro)
                        .doesNotContain(headset);
            }

            @Test
            @DisplayName("Então deve somar na quantidade dos itens iguais")
            void deveSomarDosItensIguais() {
                assertEquals(2, carrinhoCompra.getItens().get(0).getQuantidade());
                assertEquals(1, carrinhoCompra.getItens().get(1).getQuantidade());
            }

            @Test
            @DisplayName("E retornar três de quantidade total de itens")
            void retornarQuantidadeDEItens() {
                assertEquals(3, carrinhoCompra.getQuantidadeTotalDeProdutos());
            }

            @Test
            @DisplayName("E retornar valor total corretoro de itens")
            void retornarValorTotalDEItens() {
                assertEquals(new BigDecimal("2020.0"), carrinhoCompra.getValorTotal());
            }
        }
    }
}