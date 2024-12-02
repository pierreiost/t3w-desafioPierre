package io.t3w.desafio.data.dao;

import io.t3w.desafio.data.entity.Pessoa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PessoaDAO extends DAO {

    private static Pessoa parse(final ResultSet rs) throws SQLException {
        return new Pessoa()
            .setId(rs.getLong("id"))
            .setNome(rs.getString("nome"))
            .setCpf(rs.getString("cpf"));
    }

    //Método para atualizar cadastro da pessoa
    public void update(Pessoa pessoa) throws SQLException {
        String sql = "UPDATE pessoa SET nome = ?, cpf = ? WHERE id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getCpf());
            stmt.setLong(3, pessoa.getId());
            stmt.executeUpdate();
        }
    }

    //Método para criar cadastro da pessoa
    public void insert(Pessoa pessoa) throws SQLException {
        String sql = "INSERT INTO pessoa (nome, cpf) VALUES (?, ?)";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getCpf());
            stmt.executeUpdate();
        }
    }

    public PessoaDAO(final Connection connection) {
        super(connection);
    }

    public List<Pessoa> findAll() throws SQLException {
        try (PreparedStatement psmt = getConnection().prepareStatement("SELECT * FROM pessoa")) {
            try (ResultSet rs = psmt.executeQuery()) {
                final List<Pessoa> pessoas = new ArrayList<>();
                while (rs.next()) {
                    final var pessoa = new Pessoa()
                        .setId(rs.getLong("id"))
                        .setNome(rs.getString("nome"))
                        .setCpf(rs.getString("cpf"));
                    pessoas.add(pessoa);
                }
                return pessoas;
            }
        }
    }

    // Método para procurar pessoas já existentes no banco pelo ID
    public Pessoa findById(Long id) throws SQLException {
        String sql = "SELECT id, nome, cpf FROM pessoa WHERE id = ?";
        try (PreparedStatement psmt = getConnection().prepareStatement(sql)) {
            psmt.setLong(1, id);
            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    return new Pessoa(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getString("cpf")
                    );
                }
            }
        }
        return null;
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM pessoa WHERE id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
}
