package io.t3w.desafio.views.Produto;

import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.*;
import io.t3w.desafio.components.T3WButton;
import io.t3w.desafio.components.T3WFormLayout;
import io.t3w.desafio.data.entity.Pessoa;
import io.t3w.desafio.data.entity.Produto;
import io.t3w.desafio.services.ProdutoService;
import org.vaadin.firitin.components.grid.VGrid;
import org.vaadin.firitin.components.html.VDiv;
import org.vaadin.firitin.components.orderedlayout.VVerticalLayout;
import com.vaadin.flow.component.notification.Notification;

import java.util.List;

@Route("produtos")
@PageTitle("Produtos")
@Menu(order = 2, title = "Produtos")
public class ProdutosView extends VVerticalLayout implements BeforeEnterObserver {

    private final VGrid<Produto> gridProdutos;

    public ProdutosView(final ProdutoService produtoService) {
        this.withFullWidth();
        this.withPadding(false);

        gridProdutos = new VGrid<>();
        gridProdutos.withThemeVariants(GridVariant.LUMO_NO_BORDER);
        gridProdutos.addColumn(Produto::getId).setHeader("ID");
        gridProdutos.addColumn(Produto::getDescricao).setHeader("Descrição");
        gridProdutos.addColumn(Produto::getValorUnitario).setHeader("Valor");

        final var btnAdicionar = new T3WButton("Adicionar").themeTertiaryInline().themeSmall().withClassName("grid-actions")
            .withClickListener(ev -> new ProdutoDialog(new Produto(), produtoService, consumerProduto -> {
                // TODO: Produto deve ser adicionado na base de dados e no `gridProdutos` - Finalizado
                try {
                    Notification.show("Produto adicionado com sucesso!", 3000, Notification.Position.MIDDLE);
                    gridProdutos.setItems(produtoService.findProdutos());
                } catch (Exception e) {
                    Notification.show("Erro ao adicionar produto: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
                }
            }).open());

        gridProdutos.addColumn(new ComponentRenderer<>(produto -> {
            final var btnEditar = new T3WButton("Editar").themeTertiaryInline().themeSmall()
                .withClickListener(ev -> new ProdutoDialog(produto, produtoService, consumerProduto -> {
                    try {
                        // TODO: Produto deve ser editado da base de dados e do gridProdutos - Finalizado
                        produtoService.save(produto);
                        gridProdutos.setItems(produtoService.findProdutos());
                        Notification.show("Produto editado com sucesso!", 3000, Notification.Position.MIDDLE);
                    } catch (Exception e) {
                        Notification.show("Erro ao editar o produto: " + e.getMessage(), 5000, Notification.Position.MIDDLE);
                    }
                }).open());

            final var btnRemover = new T3WButton("Excluir").themeTertiaryInline().themeError().themeSmall()
                .withClickListener(ev -> {
                    try {
                        // TODO: Produto deve ser removido da base de dados e do gridProdutos - Finalizado
                        produtoService.delete(produto);
                        gridProdutos.setItems(produtoService.findProdutos());
                        Notification.show("Produto excluído com sucesso!", 3000, Notification.Position.MIDDLE);
                    } catch (Exception e) {
                        Notification.show("Erro ao excluir o produto: " + e.getMessage(), 5000, Notification.Position.MIDDLE);
                    }
                });

            return new VDiv(btnEditar, btnRemover).withClassName("grid-actions");
        })).setHeader(btnAdicionar);

        final var options = new T3WFormLayout().withFullWidth();

        this.add(options, gridProdutos);
    }

    @Override
    public void beforeEnter(final BeforeEnterEvent beforeEnterEvent) {
         // TODO: deve buscar os produtos na base de dados e expor os dados no grid
        gridProdutos.setItems( List.of(
            new Produto().setId(1),
            new Produto().setId(2)
        ));
    }
}