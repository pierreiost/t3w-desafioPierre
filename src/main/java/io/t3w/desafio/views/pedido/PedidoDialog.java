package io.t3w.desafio.views.pedido;

import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import io.t3w.desafio.components.T3WBinder;
import io.t3w.desafio.components.T3WButton;
import io.t3w.desafio.components.T3WFormLayout;
import io.t3w.desafio.data.entity.Pedido;
import io.t3w.desafio.data.entity.PedidoItem;
import io.t3w.desafio.data.entity.Pessoa;
import io.t3w.desafio.services.PedidoService;
import io.t3w.desafio.services.ProdutoService;
import org.vaadin.firitin.components.button.VButton;
import org.vaadin.firitin.components.combobox.VComboBox;
import org.vaadin.firitin.components.dialog.VDialog;
import org.vaadin.firitin.components.grid.VGrid;
import org.vaadin.firitin.components.html.VDiv;
import org.vaadin.firitin.components.textfield.VTextField;

import java.math.BigDecimal;
import java.util.function.Consumer;

public class PedidoDialog extends VDialog {

    public PedidoDialog(final Pedido pedido, final PedidoService pedidoService, final Consumer<Pedido> consumer) {
        setHeaderTitle("Pedido");
        setWidthFull();

        final var form = new T3WFormLayout();
        final var binder = new T3WBinder<>(pedido);

        // TODO: Nao renderize o id caso seja uma inclusao
        final var tfID = new VTextField("ID").withReadOnly(true);
        binder.forField(tfID).bindReadOnly(p -> String.valueOf(p.getId()));
        form.add(tfID);

        // TODO: Preencher combobox com pessoas para o usuario selecionar
        final var cbPessoa = new VComboBox<Pessoa>("Pessoa");
        binder.forField(cbPessoa).bind(Pedido::getPessoa, Pedido::setPessoa);
        form.add(cbPessoa, 2);

        final var gridItens = new VGrid<PedidoItem>().withThemeVariants(GridVariant.LUMO_NO_BORDER);
        gridItens.addColumn(PedidoItem::getId).setHeader("ID");
        gridItens.addColumn(p -> p.getProduto().getDescricao()).setHeader("Produto");
        gridItens.addColumn(PedidoItem::getQuantidade).setHeader("Quantidade");
        gridItens.addColumn(p -> p.getProduto().getValorUnitario().multiply(BigDecimal.valueOf(p.getQuantidade()))).setHeader("Valor");

        final var btnAdicionar = new T3WButton("Adicionar").themeTertiaryInline().themeSmall().withClassName("grid-actions")
            .withClickListener(ev -> {
                // TODO: Abrir um Dialog para seleção do item e quantidade
            });

        gridItens.addColumn(new ComponentRenderer<>(pessoa -> {
            final var btnEditar = new T3WButton("Editar").themeTertiaryInline().themeSmall()
                .withClickListener(ev -> {
                    // TODO: Permita editar o item através do dialog de seleção
                });

            final var btnRemover = new T3WButton("Excluir")
                .themeTertiaryInline().themeError().themeSmall()
                .withClickListener(ev -> {
                    // TODO: Remova o item do grid
                });

            return new VDiv(btnEditar, btnRemover).withClassName("grid-actions");
        })).setHeader(btnAdicionar);

        final var btnSalvar = new T3WButton("Salvar").themePrimary()
            .withClickListener(ev -> {
                // TODO: Salve o pedido
                this.close();
            });

        this.add(form, gridItens);

        final var btnCancelar = new VButton("Cancelar").withClickListener(ev -> this.close());

        getFooter().add(new VDiv(btnSalvar, btnCancelar).withClassName("dialog-footer"));
    }


}
