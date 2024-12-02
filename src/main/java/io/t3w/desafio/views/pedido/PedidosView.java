package io.t3w.desafio.views.pedido;


import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.*;
import io.t3w.desafio.components.T3WButton;
import io.t3w.desafio.components.T3WContentLayout;
import io.t3w.desafio.data.entity.Pedido;
import io.t3w.desafio.services.PedidoService;
import org.vaadin.firitin.components.formlayout.VFormLayout;
import org.vaadin.firitin.components.grid.VGrid;
import org.vaadin.firitin.components.html.VDiv;

import java.math.BigDecimal;

@Route("pedidos")
@PageTitle("Pedidos")
@Menu(order = 0, title = "Pedidos")
public class PedidosView extends T3WContentLayout implements BeforeEnterObserver {

    private final VGrid<Pedido> gridPedidos;

    public PedidosView(final PedidoService pedidoService) {
        gridPedidos = new VGrid<>();
        gridPedidos.withThemeVariants(GridVariant.LUMO_NO_BORDER);
        gridPedidos.addColumn(new ComponentRenderer<>(pedido -> new RouterLink(PedidoView.class, new RouteParameters(new RouteParam("id", pedido.getId()))))).setHeader("ID");
        gridPedidos.addColumn(pedido -> pedido.getPessoa().getNome()).setHeader("Cliente");
        gridPedidos.addColumn(pedido -> pedido.getItens().size()).setHeader("Qt. itens");
        gridPedidos.addColumn(pedido -> {
            // TODO: Implementar cálculo do valor total dos itens do pedido e formatá-lo no padrão brasileiro (1.000,00)
            return BigDecimal.ZERO;
        }).setHeader("Valor total");

        final var btnAdicionar = new T3WButton("Adicionar").themeTertiaryInline().themeSmall().withClassName("grid-actions")
            .withClickListener(ev -> new PedidoDialog(new Pedido(), pedidoService, pedido -> {
                pedidoService.salvar(pedido); // Salva no banco de dados
                gridPedidos.getListDataView().addItem(pedido); // Adiciona no grid
            }).open());



        gridPedidos.addColumn(new ComponentRenderer<>(pedido -> {
            final var btnEditar = new T3WButton("Editar").themeTertiaryInline().themeSmall()
                .withClickListener(ev -> new PedidoDialog(pedido, pedidoService, pedidoAtualizado -> {
                    pedidoService.salvar(pedidoAtualizado);
                    gridPedidos.getListDataView().refreshItem(pedidoAtualizado);
                }).open());

            final var btnRemover = new T3WButton("Excluir")
                .themeTertiaryInline().themeError().themeSmall()
                .withClickListener(ev -> {
                    // TODO: Remover pedido excluido
                    gridPedidos.getListDataView().removeItem(pedido);
                });
            return new VDiv(btnEditar, btnRemover).withClassName("grid-actions");
        })).setHeader(btnAdicionar);

        final var options = new VFormLayout().withFullWidth();

        this.add(options, gridPedidos);
    }

    @Override
    public void beforeEnter(final BeforeEnterEvent beforeEnterEvent) {
        // TODO: Adicionar itens ao grid
        gridPedidos.withItems();
    }
}