package com.marianapetrolini.fastfood.application.dtos.produto;

import com.marianapetrolini.fastfood.domain.entities.Produto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO para resposta de produto.
 */
public class ProdutoResponse {
    
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String categoria;
    private boolean disponivel;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
    
    public ProdutoResponse() {
    }
    
    public ProdutoResponse(Long id, String nome, String descricao, BigDecimal preco, 
                          String categoria, boolean disponivel, LocalDateTime criadoEm, 
                          LocalDateTime atualizadoEm) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.categoria = categoria;
        this.disponivel = disponivel;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
    }
    
    /**
     * Cria um ProdutoResponse a partir de uma entidade Produto.
     * 
     * @param produto Entidade produto
     * @return DTO de resposta
     */
    public static ProdutoResponse fromEntity(Produto produto) {
        return new ProdutoResponse(
            produto.getId(),
            produto.getNome(),
            produto.getDescricao(),
            produto.getPreco(),
            produto.getCategoria().name(),
            produto.isDisponivel(),
            produto.getCriadoEm(),
            produto.getAtualizadoEm()
        );
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
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
    
    public boolean isDisponivel() {
        return disponivel;
    }
    
    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }
    
    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }
    
    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }
    
    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }
    
    public void setAtualizadoEm(LocalDateTime atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }
    
    @Override
    public String toString() {
        return String.format("ProdutoResponse{id=%d, nome='%s', categoria='%s', preco=%s, disponivel=%s}", 
                           id, nome, categoria, preco, disponivel);
    }
}

