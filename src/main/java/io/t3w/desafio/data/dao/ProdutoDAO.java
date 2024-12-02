package io.t3w.desafio.data.dao;

import io.t3w.desafio.data.entity.Produto;

import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ProdutoDAO extends DAO {

    public ProdutoDAO(Connection connection) {
        super(connection);
    }

    public List<Produto> findAll() throws SQLException {
        try (PreparedStatement psmt = getConnection().prepareStatement("SELECT * FROM produto")) {
            try (ResultSet rs = psmt.executeQuery()) {
                final List<Produto> produtos = new ArrayList<>();
                while (rs.next()) {
                    final var produto = new Produto()
                        .setId(rs.getInt("id"))
                        .setDescricao(rs.getString("descricao"))
                        .setValorUnitario(rs.getBigDecimal("valor_unitario"));
                    produtos.add(produto);
                }
                return produtos;
            }
        }
    }

    public void insert(Produto produto) throws SQLException {
        String sql = "INSERT INTO produto (descricao, valor_unitario) VALUES (?, ?)";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, produto.getDescricao());
            stmt.setBigDecimal(2, produto.getValorUnitario());
            stmt.executeUpdate();
        }
    }

    public void update(Produto produto) throws SQLException {
        String sql = "UPDATE produto SET descricao = ?, valor_unitario = ? WHERE id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, produto.getDescricao());
            stmt.setBigDecimal(2, produto.getValorUnitario());
            stmt.setLong(4, produto.getId());
            stmt.executeUpdate();
        }
    }

    public Produto findById(int id) throws SQLException {
        String sql = "SELECT id, descricao, valor_unitario FROM produto WHERE id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Produto(
                        rs.getLong("id"),
                        rs.getString("descricao"),
                        rs.getBigDecimal("valor_unitario")
                    );
                }
            }
        }
        return null;
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM produto WHERE id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

}
