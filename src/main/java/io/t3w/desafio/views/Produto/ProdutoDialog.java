package io.t3w.desafio.views.Produto;

import io.t3w.desafio.components.T3WBinder;
import io.t3w.desafio.components.T3WButton;
import io.t3w.desafio.components.T3WFormLayout;
import io.t3w.desafio.data.entity.Produto;
import io.t3w.desafio.services.ProdutoService;
import org.vaadin.firitin.components.button.VButton;
import org.vaadin.firitin.components.dialog.VDialog;
import org.vaadin.firitin.components.html.VDiv;
import org.vaadin.firitin.components.textfield.VBigDecimalField;
import org.vaadin.firitin.components.textfield.VTextField;

import java.util.function.Consumer;

public class ProdutoDialog extends VDialog {

    public ProdutoDialog(final Produto produto, final ProdutoService produtoService, final Consumer<Produto> consumer) {
        setHeaderTitle("Produto");

        final var form = new T3WFormLayout();
        this.add(form);
        final var binder = new T3WBinder<>(produto);

        final var tfID = new VTextField("ID").withReadOnly(true);
        binder.forField(tfID).bindReadOnly(p -> String.valueOf(p.getId()));
        form.add(tfID);

        final var tfDescricao = new VTextField("Descricao");
        binder.forField(tfDescricao).bind(Produto::getDescricao, Produto::setDescricao);
        form.add(tfDescricao);

        final var bfValorUnitario = new VBigDecimalField("Valor unitario");
        binder.forField(bfValorUnitario).bind(Produto::getValorUnitario, Produto::setValorUnitario);
        form.add(bfValorUnitario, 2);

        final var btnSalvar = new T3WButton("Salvar").themePrimary()
            .withClickListener(ev -> {
                // TODO: Implementar update e insert do produto - Finalizado
                final var produtoSalvo = binder.getObject();
                consumer.accept(produtoSalvo);
                this.close();
            });

        final var btnCancelar = new VButton("Cancelar").withClickListener(ev -> this.close());

        getFooter().add(new VDiv(btnSalvar, btnCancelar).withClassName("dialog-footer"));
    }


}
