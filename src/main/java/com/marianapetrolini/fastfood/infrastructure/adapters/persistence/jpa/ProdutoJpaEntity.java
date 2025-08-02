package com.marianapetrolini.fastfood.infrastructure.adapters.persistence.jpa;

import com.marianapetrolini.fastfood.domain.entities.Produto;
import com.marianapetrolini.fastfood.domain.valueobjects.CategoriaProduto;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidade JPA para persistência de produtos.
 * Adapter entre o domínio e a camada de persistência.
 */
@Entity
@Table(name = "produtos")
public class ProdutoJpaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;
    
    @Column(name = "descricao", length = 500)
    private String descricao;
    
    @Column(name = "preco", nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false, length = 20)
    private CategoriaProduto categoria;
    
    @Column(name = "disponivel", nullable = false)
    private Boolean disponivel;
    
    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm;
    
    @Column(name = "atualizado_em", nullable = false)
    private LocalDateTime atualizadoEm;
    
    public ProdutoJpaEntity() {
    }
    
    public ProdutoJpaEntity(String nome, String descricao, BigDecimal preco, 
                           CategoriaProduto categoria, Boolean disponivel, 
                           LocalDateTime criadoEm, LocalDateTime atualizadoEm) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.categoria = categoria;
        this.disponivel = disponivel;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
    }
    
    /**
     * Converte a entidade JPA para entidade de domínio.
     * 
     * @return Entidade de domínio
     */
    public Produto toDomainEntity() {
        return new Produto(
            this.id,
            this.nome,
            this.descricao,
            this.preco,
            this.categoria,
            this.disponivel,
            this.criadoEm,
            this.atualizadoEm
        );
    }
    
    /**
     * Cria uma entidade JPA a partir de uma entidade de domínio.
     * 
     * @param produto Entidade de domínio
     * @return Entidade JPA
     */
    public static ProdutoJpaEntity fromDomainEntity(Produto produto) {
        ProdutoJpaEntity jpaEntity = new ProdutoJpaEntity(
            produto.getNome(),
            produto.getDescricao(),
            produto.getPreco(),
            produto.getCategoria(),
            produto.isDisponivel(),
            produto.getCriadoEm(),
            produto.getAtualizadoEm()
        );
        jpaEntity.setId(produto.getId());
        return jpaEntity;
    }
    
    /**
     * Atualiza a entidade JPA com dados da entidade de domínio.
     * 
     * @param produto Entidade de domínio
     */
    public void updateFromDomainEntity(Produto produto) {
        this.nome = produto.getNome();
        this.descricao = produto.getDescricao();
        this.preco = produto.getPreco();
        this.categoria = produto.getCategoria();
        this.disponivel = produto.isDisponivel();
        this.atualizadoEm = produto.getAtualizadoEm();
    }
    
    // Getters e Setters
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
    
    public CategoriaProduto getCategoria() {
        return categoria;
    }
    
    public void setCategoria(CategoriaProduto categoria) {
        this.categoria = categoria;
    }
    
    public Boolean getDisponivel() {
        return disponivel;
    }
    
    public void setDisponivel(Boolean disponivel) {
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
}

