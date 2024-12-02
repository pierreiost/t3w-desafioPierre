package io.t3w.desafio.data.entity;

import java.math.BigDecimal;

public class Produto {
    private int id;
    private String descricao;
    private BigDecimal valorUnitario;

    public Produto(long id, String descricao, BigDecimal valorUnitario) {
    }

    public Produto() {

    }

    public int getId() {
        return id;
    }

    public Produto setId(int registro) {
        this.id = registro;
        return this;
    }

    public String getDescricao() {
        return descricao;
    }

    public Produto setDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public Produto setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
        return this;
    }
}
