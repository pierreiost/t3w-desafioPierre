package io.t3w.desafio.data.entity;

public class Pessoa {

    private long id;
    private String cpf;
    private String nome;

    // Construtores da entidade pessoa.
    public Pessoa(long id, String nome, String cpf) {
    }

    public Pessoa() {
    }

    public long getId() {
        return id;
    }

    public Pessoa setId(long id) {
        this.id = id;
        return this;
    }

    public String getCpf() {
        return cpf;
    }

    public Pessoa setCpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public Pessoa setNome(String nome) {
        this.nome = nome;
        return this;
    }
}
