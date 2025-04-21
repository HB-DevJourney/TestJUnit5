
package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.modelo.Editor;

import java.math.BigDecimal;

public class EditorTestData {

    public EditorTestData() {
    }

    public static Editor.Builder umEditorNovo() {
        return Editor.builder()
                .ComNome("mateus")
                .ComEmail("mateus@email.com")
                .ComValorPagoPorPalavra(BigDecimal.TEN)
                .ComPremium(true);
    }

    public static Editor.Builder umEditorExistente() {
        return umEditorNovo().ComId(1L);
    }

    public static Editor.Builder umEditorInexistente() {
        return umEditorNovo().ComId(99L);
    }
}