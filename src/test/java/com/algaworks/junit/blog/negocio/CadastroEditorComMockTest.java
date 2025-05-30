package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.armazenamento.ArmazenamentoEditor;
import com.algaworks.junit.blog.exception.EditorNaoEncontradoException;
import com.algaworks.junit.blog.exception.RegraNegocioException;
import com.algaworks.junit.blog.modelo.Editor;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.internal.verification.Times;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
public class CadastroEditorComMockTest {

    @Spy
    Editor editor = new Editor(null, "Mateus", "mateus@email.com", BigDecimal.TEN, true);

    @Captor
    ArgumentCaptor<Mensagem> mensagemArgumentCaptor;

    @Mock
    ArmazenamentoEditor armazenamentoEditor;

    @Mock
    GerenciadorEnvioEmail gerenciadorEnvioEmail;

    @InjectMocks
    CadastroEditor cadastroEditor;

    @Nested
    class CadastroComEditorValido {

        @Spy
        Editor editor = EditorTestData.umEditorNovo().build();

        @BeforeEach
        void init() {
            Mockito.when(armazenamentoEditor.salvar(Mockito.any(Editor.class)))
                    .thenAnswer(invocacao -> {
                        Editor editorPassado = invocacao.getArgument(0, Editor.class);
                        editorPassado.setId(1L);
                        return editorPassado;
                    });
        }

        @Test
        void Dado_um_editor_valido_Quando_criar_Entao_deve_retornar_um_id_de_cadastro() {
            Editor editorSalvo = cadastroEditor.criar(editor);
            assertEquals(1L, editorSalvo.getId());
        }

        @Test
        void Dado_editor_valido_Quando_criar_Entao_deve_chamar_metodo_salvar_do_armazenamento() {
            cadastroEditor.criar(editor);
            Mockito.verify(armazenamentoEditor, Mockito.times(1))
                    .salvar(Mockito.eq(editor));
        }

        @Test
        void Dado_um_editor_valido_Quando_criar_e_lancar_exception_ao_salvar_Entao_nao_deve_enviar_email() {
            Mockito.when(armazenamentoEditor.salvar(editor)).thenThrow(RuntimeException.class);
            assertAll("Não deve enviar email, quando lançar Exception do armazenamento",
                    () -> assertThrows(RuntimeException.class, () -> cadastroEditor.criar(editor)),
                    () -> Mockito.verify(gerenciadorEnvioEmail, never()).enviarEmail(Mockito.any())
            );
        }

        @Test
        void Dado_um_editor_valido_Quando_cadastrar_Entao_deve_enviar_email_com_destino_do_editor() {

            Editor editorSalvo = cadastroEditor.criar(editor);

            Mockito.verify(gerenciadorEnvioEmail).enviarEmail(mensagemArgumentCaptor.capture());

            Mensagem mensagem = mensagemArgumentCaptor.getValue();

            assertEquals(editorSalvo.getEmail(), mensagem.getDestinatario());

        }

        @Test
        void Dado_um_editor_valido_Quando_cadastrar_Entao_deve_verificar_o_email() {
            cadastroEditor.criar(editor);
            Mockito.verify(editor, Mockito.atLeast(1)).getEmail();
        }

        @Test
        void Dado_um_editor_com_email_existente_Quando_cadastrar_Entao_deve_lancar_exception() {
            Mockito.when(armazenamentoEditor.encontrarPorEmail("mateus@email.com"))
                    .thenReturn(Optional.empty())
                    .thenReturn(Optional.of(editor));

            Editor editorComEmailExistente = EditorTestData.umEditorExistente().build();
            cadastroEditor.criar(editor);
            assertThrows(RegraNegocioException.class, () -> cadastroEditor.criar(editorComEmailExistente));
        }

        @Test
        void Dado_um_editor_valido_Quando_cadastrar_Entao_deve_enviar_email_apos_salvar() {
            cadastroEditor.criar(editor);

            InOrder inOrder = Mockito.inOrder(armazenamentoEditor, gerenciadorEnvioEmail);
            inOrder.verify(armazenamentoEditor, Mockito.times(1)).salvar(editor);
            inOrder.verify(gerenciadorEnvioEmail, Mockito.times(1)).enviarEmail(Mockito.any(Mensagem.class));
        }

    }

    @Nested
    class CadastroComEditorNull {

        @Test
        void Dado_um_editor_null_Quando_cadastrar_Entao_deve_lancar_exception() {
            assertThrows(NullPointerException.class, () -> cadastroEditor.criar(null));
            Mockito.verify(armazenamentoEditor, never()).salvar(Mockito.any());
            Mockito.verify(gerenciadorEnvioEmail, never()).enviarEmail(Mockito.any(Mensagem.class));
        }

    }

    @Nested
    class EdicaoComEditorValido {

        @Spy
        Editor editor = new Editor(1L, "Mateus", "mateus@email.com", BigDecimal.TEN, true);

        @BeforeEach
        void init() {
            Mockito.when(armazenamentoEditor.salvar(editor)).thenAnswer(invocacao -> invocacao.getArgument(0, Editor.class));
            Mockito.when(armazenamentoEditor.encontrarPorId(1L)).thenReturn(Optional.of(editor));
        }

        @Test
        void Dado_um_editor_valido_Quando_editar_Entao_deve_alterar_editor_salvo() {
            Editor editorAtualizado = EditorTestData.umEditorExistente()
                    .ComEmail("Mateus.silva@email.com")
                    .ComNome("Mateus Silva")
                    .build();

                    //new Editor(1L, "Mateus Silva", "mateus.silva@email.com", BigDecimal.ZERO, false);
            cadastroEditor.editar(editorAtualizado);
            Mockito.verify(editor, Mockito.times(1)).atualizarComDados(editorAtualizado);

            InOrder inOrder = Mockito.inOrder(editor, armazenamentoEditor);
            inOrder.verify(editor).atualizarComDados(editorAtualizado);
            inOrder.verify(armazenamentoEditor).salvar(editor);
        }

    }

    @Nested
    class EdicaoComEditorInexistente {

        Editor editor = EditorTestData.umEditorInexistente().build();

        @BeforeEach
        void init() {
            Mockito.when(armazenamentoEditor.encontrarPorId(99L)). thenReturn(Optional.empty());
        }

        @Test
        void Dado_um_editor_que_nao_exista_Quando_editar_Entao_deve_lancar_exception() {
            assertThrows(EditorNaoEncontradoException.class, ()-> cadastroEditor.editar(editor));
            Mockito.verify(armazenamentoEditor, never()).salvar(Mockito.any(Editor.class));
        }

    }
}
