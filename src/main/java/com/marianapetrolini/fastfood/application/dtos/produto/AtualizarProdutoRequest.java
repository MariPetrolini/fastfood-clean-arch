package com.marianapetrolini.fastfood.application.dtos.produto;

import java.math.BigDecimal;

/**
 * DTO para requisição de atualização de produto.
 */
public class AtualizarProdutoRequest {
    
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String categoria;
    
    public AtualizarProdutoRequest() {
    }
    
    public AtualizarProdutoRequest(String nome, String descricao, BigDecimal preco, String categoria) {
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
        return String.format("AtualizarProdutoRequest{nome='%s', categoria='%s', preco=%s}", 
                           nome, categoria, preco);
    }
}

