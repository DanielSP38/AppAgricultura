package com.example.appagricultura;

public class Produto {

    private int id;
    private String nome;
    private String fornecedor;
    private String tipo;
    private String descricao;
    private String quantidade;
    private String preco;
    private int rating;

    public Produto() {
    }

    public Produto(int id, String nome, String fornecedor, String tipo, String descricao, String quantidade, String preco, int rating) {
        this.id = id;
        this.nome = nome;
        this.fornecedor = fornecedor;
        this.tipo = tipo;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.preco = preco;
        this.rating = rating;
    }

    public Produto(String nome, String fornecedor, String quantidade, int rating) {
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getFornecedor() {

        return fornecedor;
    }

    public String getTipo() {

        return tipo;
    }
    public String getDescricao() {
        return descricao;
    }
    public String getQuantidade() {
        return quantidade;
    }
    public String getPreco() {
        return preco;
    }
    public int getRating() {
        return rating;
    }
}
