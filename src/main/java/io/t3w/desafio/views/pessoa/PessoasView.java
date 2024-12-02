package io.t3w.desafio.views.pessoa;

import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.*;
import io.t3w.desafio.components.T3WButton;
import io.t3w.desafio.data.entity.Pessoa;
import io.t3w.desafio.services.PessoaService;
import org.vaadin.firitin.components.formlayout.VFormLayout;
import org.vaadin.firitin.components.grid.VGrid;
import org.vaadin.firitin.components.html.VDiv;
import org.vaadin.firitin.components.orderedlayout.VVerticalLayout;
import com.vaadin.flow.component.notification.Notification;

import java.util.List;

@Route("")
@RouteAlias("pessoas")
@PageTitle("Pessoas")
@Menu(order = 1, title = "Pessoas")
public class PessoasView extends VVerticalLayout implements BeforeEnterObserver {

    private final VGrid<Pessoa> gridPessoas;

    public PessoasView(final PessoaService pessoaService) {
        this.withFullWidth();
        this.withPadding(false);

        gridPessoas = new VGrid<>();
        gridPessoas.withThemeVariants(GridVariant.LUMO_NO_BORDER);
        gridPessoas.addColumn(Pessoa::getId).setHeader("ID");
        gridPessoas.addColumn(Pessoa::getNome).setHeader("Nome");
        gridPessoas.addColumn(Pessoa::getCpf).setHeader("Cpf");

        final var btnAdicionar = new T3WButton("Adicionar").themeTertiaryInline().themeSmall().withClassName("grid-actions")
            .withClickListener(ev -> {
                new PessoaDialog(new Pessoa(), pessoaService, pessoaSalva -> {
                    try {
                        gridPessoas.setItems(pessoaService.findPessoas());
                        Notification.show("Pessoa adicionada com sucesso!", 3000, Notification.Position.MIDDLE);
                    } catch (Exception e) {
                        Notification.show("Erro ao adicionar a pessoa: " + e.getMessage(), 5000, Notification.Position.MIDDLE);
                    }
                }).open();
            });


        gridPessoas.addColumn(new ComponentRenderer<>(pessoa -> {
            final var btnEditar = new T3WButton("Editar").themeTertiaryInline().themeSmall()
                .withClickListener(ev -> new PessoaDialog(pessoa, pessoaService, pessoaAtualizada -> {
                    try {
                        pessoaService.save(pessoaAtualizada);
                        gridPessoas.setItems(pessoaService.findPessoas());
                        Notification.show("Pessoa atualizada com sucesso!", 3000, Notification.Position.MIDDLE);
                    } catch (Exception e) {
                        Notification.show("Erro ao atualizar a pessoa: " + e.getMessage(), 5000, Notification.Position.MIDDLE);
                    }
                }).open());

            final var btnExcluir = new T3WButton("Excluir").themeError().themeSmall()
                .withClickListener(ev -> {
                    try {
                        pessoaService.delete(pessoa);
                        gridPessoas.setItems(pessoaService.findPessoas());
                        Notification.show("Pessoa excluída com sucesso!", 3000, Notification.Position.MIDDLE);
                    } catch (Exception e) {
                        Notification.show("Erro ao excluir a pessoa: " + e.getMessage(), 5000, Notification.Position.MIDDLE);
                    }
                });

            return new VDiv(btnEditar, btnExcluir).withClassName("grid-actions");

        })).setHeader(btnAdicionar);

        final var options = new VFormLayout().withFullWidth();

        this.add(options, gridPessoas);
    }

    @Override
    public void beforeEnter(final BeforeEnterEvent beforeEnterEvent) {
        // TODO: Buscar pessoas na base de dados e expor no grid
        gridPessoas.setItems(
            List.of(
                new Pessoa().setId(1L),
                new Pessoa().setId(2L)
            )
        );
    }
}