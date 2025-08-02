package com.marianapetrolini.fastfood.application.dtos.produto;

import java.math.BigDecimal;

/**
 * DTO para requisição de criação de produto.
 */
public class CriarProdutoRequest {
    
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String categoria;
    
    public CriarProdutoRequest() {
    }
    
    public CriarProdutoRequest(String nome, String descricao, BigDecimal preco, String categoria) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.categoria = categoria;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public BigDecimal getPreco() {
        return preco;
    }
    
    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
    
    public String getCategoria() {
        return categoria;
    }
    
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    @Override
    public String toString() {
        return String.format("CriarProdutoRequest{nome='%s', categoria='%s', preco=%s}", 
                           nome, categoria, preco);
    }
}

