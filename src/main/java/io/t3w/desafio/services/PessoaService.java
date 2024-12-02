package io.t3w.desafio.services;

import io.t3w.desafio.data.dao.PessoaDAO;
import io.t3w.desafio.data.dao.ProdutoDAO;
import io.t3w.desafio.data.entity.Pessoa;
import io.t3w.desafio.data.entity.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
public class PessoaService {

    @Autowired
    private DataSource dataSource;

    public List<Pessoa> findPessoas() {
        try (final var connection = dataSource.getConnection()) {
            return new PessoaDAO(connection).findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Pessoa save(final Pessoa pessoa) {
        // TODO: Implementar update e insert da pessoa - Finalizado
        try (final var connection = dataSource.getConnection()) {
            PessoaDAO pessoaDAO = new PessoaDAO(connection);
            if (pessoaDAO.findById(pessoa.getId()) != null) {
                pessoaDAO.update(pessoa);
            } else {
                pessoaDAO.insert(pessoa);
            }
            return pessoa;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Pessoa pessoa) {
        try (final var connection = dataSource.getConnection()) {
            PessoaDAO pessoaDAO = new PessoaDAO(connection);
            pessoaDAO.delete((int) pessoa.getId());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao remover o produto", e);
        }
    }
}
