package io.t3w.desafio.services;

import io.t3w.desafio.data.dao.ProdutoDAO;
import io.t3w.desafio.data.entity.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private DataSource dataSource;

    public List<Produto> findProdutos() {
        try (final var connection = dataSource.getConnection()) {
            return new ProdutoDAO(connection).findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Produto save(Produto produto) {
        try (final var connection = dataSource.getConnection()) {
            ProdutoDAO produtoDAO = new ProdutoDAO(connection);
            if (produtoDAO.findById(produto.getId()) != null) {
                produtoDAO.update(produto);
            } else {
                produtoDAO.insert(produto);
            }
            return produto;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar o produto", e);
        }
    }

    public void delete(Produto produto) {
        try (final var connection = dataSource.getConnection()) {
            ProdutoDAO produtoDAO = new ProdutoDAO(connection);
            produtoDAO.delete(produto.getId());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao remover o produto", e);
        }
    }
}
