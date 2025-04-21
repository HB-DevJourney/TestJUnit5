package com.algaworks.junit.desafios;

import com.algaworks.junit.blog.armazenamento.ArmazenamentoPost;
import com.algaworks.junit.blog.exception.PostNaoEncontradoException;
import com.algaworks.junit.blog.exception.RegraNegocioException;
import com.algaworks.junit.blog.modelo.Editor;
import com.algaworks.junit.blog.modelo.Notificacao;
import com.algaworks.junit.blog.modelo.Post;
import com.algaworks.junit.blog.negocio.CadastroPost;
import com.algaworks.junit.blog.negocio.CalculadoraGanhos;
import com.algaworks.junit.blog.negocio.GerenciadorNotificacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cadastro de Post")
@ExtendWith(MockitoExtension.class)
class CadastroPostTest {

    Editor autor = new Editor(1L, "Mateus", "mateus@email.com", BigDecimal.TEN, true);

    @Captor
    ArgumentCaptor<Notificacao> notificacaoCaptor;

    @Mock
    ArmazenamentoPost armazenamentoPost;

    @Mock
    CalculadoraGanhos calculadoraGanhos;

    @Mock
    GerenciadorNotificacao gerenciadorNotificacao;

    @InjectMocks
    CadastroPost cadastroPost;

    @Nested
    @DisplayName("Dado um Post válido")
    class DadoUmPostValido {

        Post post = new Post(null, "Ecossistema Java", "O ecossistema do java é muito maduro", autor,
                "ecossistema-java-abc123", null, false, false);

        @Nested
        @DisplayName("Quando criar")
        class QuandoCriar {

            @BeforeEach
            void init() {

                Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class)))
                        .thenAnswer(invocacao -> {
                            Post post = invocacao.getArgument(0, Post.class);
                            post.setId(1L);
                            return post;
                        });

            }

            @Test
            @DisplayName("Então retornar um ID de cadastro")
            void deveRetornarIdDeCadastro() {
                Post postSalvo = cadastroPost.criar(post);
                assertEquals(1L, postSalvo.getId());
                Mockito.verify(armazenamentoPost, Mockito.times(1)).salvar(Mockito.any(Post.class));
            }

            @Test
            @DisplayName("Então deve verificar se o metodo calcular ganhos do calculadora ganhos foi chamado")
            void deveVerificarSeCalcularGanhosFoiChamado() {
                Post postSalvo = cadastroPost.criar(post);
                Mockito.verify(calculadoraGanhos, Mockito.times(1)).calcular(postSalvo);
            }

            @Test
            @DisplayName("Então deve verificar se o metodo salvar do armazenamento foi chamado")
            void deveVerificarSeOMetodoSalvarFoiChamado() {
                Post postSalvo = cadastroPost.criar(post);
                Mockito.verify(armazenamentoPost, Mockito.times(1)).salvar(Mockito.any(Post.class));
            }

            @Test
            @DisplayName("E enviar a notificação")
            void deveEnviarNotificacao() {
                Post postSalvo = cadastroPost.criar(post);

                InOrder inOrder = Mockito.inOrder(armazenamentoPost, gerenciadorNotificacao);
                inOrder.verify(armazenamentoPost, Mockito.times(1))
                        .salvar(post);
                inOrder.verify(gerenciadorNotificacao, Mockito.times(1))
                        .enviar(Mockito.any(Notificacao.class));
            }

            @Test
            @DisplayName("E exibir a notificação")
            void deveExibirNotificacao() {
                Post postSalvo = cadastroPost.criar(post);
                Mockito.verify(gerenciadorNotificacao).enviar(notificacaoCaptor.capture());
                Notificacao notificacao = notificacaoCaptor.getValue();
                assertEquals("Novo post criado -> Ecossistema Java", notificacao.getConteudo());
            }

            @Test
            @DisplayName("Verificar ordem de execução")
            void deveVerificarOrdem() {
                InOrder inOrder = Mockito.inOrder(calculadoraGanhos, armazenamentoPost, gerenciadorNotificacao);

                Post postSalvo = cadastroPost.criar(post);

                inOrder.verify(calculadoraGanhos, Mockito.times(1)).calcular(postSalvo);
                inOrder.verify(armazenamentoPost, Mockito.times(1)).salvar(Mockito.any(Post.class));
                inOrder.verify(gerenciadorNotificacao, Mockito.times(1)).enviar(notificacaoCaptor.capture());

            }
        }

        @Nested
        @DisplayName("Quando editar")
        class QuandoEditar {

            Post post;

            @BeforeEach
            void setUp() {
                post = Mockito.spy(new Post(1L, "Ecossistema Java", "Conteúdo antigo", autor,
                        "ecossistema-java-abc123", null, false, false));
                Mockito.when(armazenamentoPost.salvar(post)).thenAnswer(invocacao -> invocacao.getArgument(0, Post.class));
                Mockito.when(armazenamentoPost.encontrarPorId(1L)).thenReturn(Optional.of(post));
            }

            @Test
            @DisplayName("Então deve atualizar o post salvo com os novos dados")
            void deveAlterarPostSalvo() {
                Post postAtualizado = new Post(1L, "Ecossistema Java Spring", "O ecossistema do SpringBoot é o melhor", autor,
                        "ecossistema-java-abc123", null, false, false);

                cadastroPost.editar(postAtualizado);
                Mockito.verify(post, Mockito.times(1)).atualizarComDados(postAtualizado);

                InOrder inOrder = Mockito.inOrder(post, armazenamentoPost);
                inOrder.verify(post).atualizarComDados(postAtualizado);
                inOrder.verify(armazenamentoPost).salvar(post);
            }

            @Test
            @DisplayName("Então deve calcular ganhos se o post alterado não foi pago")
            void deveCalcularGanhos() {
                Mockito.doReturn(false).when(post).isPago();
                Post postNaoPago = new Post(1L, "Ecossistema Java Spring", "O ecossistema do SpringBoot é o melhor", autor,
                        "ecossistema-java-abc123", null, false, false);
                cadastroPost.editar(postNaoPago);
                Mockito.verify(calculadoraGanhos).calcular(postNaoPago);
            }

            @Test
            @DisplayName("Então não deve calcular ganhos se o post alterado foi pago")
            void naoDeveCalcularGanhos() {
                Mockito.doReturn(true).when(post).isPago();

                Post postAtualizado = new Post(1L, "Ecossistema Java Spring", "O ecossistema do SpringBoot é o melhor", autor,
                        "ecossistema-java-abc123", null, true, false);

                cadastroPost.editar(postAtualizado);
                Mockito.verify(calculadoraGanhos, Mockito.never()).calcular(Mockito.any());
            }


        }

        @Nested
        @DisplayName("Quando remover")
        class QuandoRemover {

            @Nested
            @DisplayName("Deve verificar se o post foi publicado:")
            class verificarPublicado {

                Post post;

                @BeforeEach
                void setUp() {
                    post = Mockito.spy(new Post(1L, "Título", "Conteúdo", autor, "slug", null, false, false));
                    Mockito.when(armazenamentoPost.encontrarPorId(1L)).thenReturn(Optional.of(post));
                }

                @Test
                @DisplayName("Então deve remover post não publicado e não pago")
                void deveRemoverPostValido() {
                    cadastroPost.remover(1L);
                    Mockito.verify(armazenamentoPost).remover(1L);
                }

            }
        }

    }

    @Nested
    @DisplayName("Dado um Post invalido")
    class DadoUmPostInvalido {

        @Nested
        @DisplayName("Deve lancar exceção")
        class deveLancarExcecao {

            Post post;

            @BeforeEach
            void setUp() {
                post = Mockito.spy(new Post(1L, "Título", "Conteúdo", autor, "slug", null, false, false));
            }

            @Test
            @DisplayName("Quando tentar criar um post nulo")
            void deveLancarExcecaoQuandoCriarUmPostNulo() {
                NullPointerException exception = assertThrows(NullPointerException.class,
                        () -> cadastroPost.criar(null));
            }

            @Test
            @DisplayName("Quando tentar editar um post nulo")
            void deveLancarExcecaoQuandoEditarUmPostNulo() {
                NullPointerException exception = assertThrows(NullPointerException.class,
                        () -> cadastroPost.editar(null));
            }

            @Test
            @DisplayName("Quando o post for inexistente")
            void deveLancarExcecaoQuandoForInexistente() {
                Post postAtualizado = new Post(5L, "Novo título", "Novo conteúdo", autor, "", null, false, false);

                Mockito.when(armazenamentoPost.encontrarPorId(5L)).thenReturn(Optional.empty());

                assertThrows(PostNaoEncontradoException.class,
                        () -> cadastroPost.editar(postAtualizado));
            }

            @Test
            @DisplayName("Então deve lançar exceção se post for publicado")
            void deveLancarExcecaoSePostForPublicado() {
                Mockito.doReturn(true).when(post).isPublicado();
                Mockito.when(armazenamentoPost.encontrarPorId(1L))
                        .thenReturn(Optional.of(post));
                RegraNegocioException exception = assertThrows(RegraNegocioException.class,
                        () -> cadastroPost.remover(1L));
                assertEquals("Um post publicado não pode ser removido", exception.getMessage());
                Mockito.verify(armazenamentoPost, Mockito.never()).remover(Mockito.any());
            }

            @Test
            @DisplayName("Então deve lançar exceção se post for pago")
            void deveLancarExcecaoSePostForPago() {
                Mockito.doReturn(true).when(post).isPago();
                Mockito.when(armazenamentoPost.encontrarPorId(1L))
                        .thenReturn(Optional.of(post));
                RegraNegocioException exception = assertThrows(RegraNegocioException.class,
                        () -> cadastroPost.remover(1L));
                assertEquals("Um post pago não pode ser removido", exception.getMessage());
                Mockito.verify(armazenamentoPost, Mockito.never()).remover(Mockito.any());
            }

            @Test
            @DisplayName("Então deve lançar exceção se post não for encontrado")
            void deveLancarExcecaoSePostNaoForEncontrado() {
                Mockito.when(armazenamentoPost.encontrarPorId(2L)).thenReturn(Optional.empty());
                PostNaoEncontradoException exception = assertThrows(PostNaoEncontradoException.class,
                        () -> cadastroPost.remover(2L));
            }

            @Test
            @DisplayName("Então deve lançar exceção se o ID for nulo")
            void deveLancarExcecaoSeIdForNulo() {
                NullPointerException exception = assertThrows(NullPointerException.class,
                        () -> cadastroPost.remover(null));
            }
        }
    }
}