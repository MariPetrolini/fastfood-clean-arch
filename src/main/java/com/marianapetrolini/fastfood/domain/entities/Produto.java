package com.marianapetrolini.fastfood.domain.entities;

import com.marianapetrolini.fastfood.domain.exceptions.DomainException;
import com.marianapetrolini.fastfood.domain.valueobjects.CategoriaProduto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Entidade que representa um produto do sistema.
 * Contém as regras de negócio relacionadas aos produtos.
 */
public class Produto {
    
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private CategoriaProduto categoria;
    private boolean disponivel;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
    
    // Construtor para criação de novos produtos
    public Produto(String nome, String descricao, BigDecimal preco, CategoriaProduto categoria) {
        this.setNome(nome);
        this.setDescricao(descricao);
        this.setPreco(preco);
        this.setCategoria(categoria);
        this.disponivel = true;
        this.criadoEm = LocalDateTime.now();
        this.atualizadoEm = LocalDateTime.now();
    }
    
    // Construtor para reconstrução (usado pelos adapters)
    public Produto(Long id, String nome, String descricao, BigDecimal preco, 
                   CategoriaProduto categoria, boolean disponivel, 
                   LocalDateTime criadoEm, LocalDateTime atualizadoEm) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.categoria = categoria;
        this.disponivel = disponivel;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
    }
    
    // Getters
    public Long getId() {
        return id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public BigDecimal getPreco() {
        return preco;
    }
    
    public CategoriaProduto getCategoria() {
        return categoria;
    }
    
    public boolean isDisponivel() {
        return disponivel;
    }
    
    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }
    
    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }
    
    // Setters com validações de domínio
    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new DomainException("Nome do produto não pode ser nulo ou vazio");
        }
        if (nome.trim().length() > 100) {
            throw new DomainException("Nome do produto não pode ter mais que 100 caracteres");
        }
        this.nome = nome.trim();
        this.atualizarTimestamp();
    }
    
    public void setDescricao(String descricao) {
        if (descricao != null && descricao.trim().length() > 500) {
            throw new DomainException("Descrição do produto não pode ter mais que 500 caracteres");
        }
        this.descricao = descricao != null ? descricao.trim() : null;
        this.atualizarTimestamp();
    }
    
    public void setPreco(BigDecimal preco) {
        if (preco == null) {
            throw new DomainException("Preço do produto não pode ser nulo");
        }
        if (preco.compareTo(BigDecimal.ZERO) <= 0) {
            throw new DomainException("Preço do produto deve ser maior que zero");
        }
        if (preco.scale() > 2) {
            throw new DomainException("Preço do produto não pode ter mais que 2 casas decimais");
        }
        this.preco = preco;
        this.atualizarTimestamp();
    }
    
    public void setCategoria(CategoriaProduto categoria) {
        if (categoria == null) {
            throw new DomainException("Categoria do produto não pode ser nula");
        }
        this.categoria = categoria;
        this.atualizarTimestamp();
    }
    
    // Métodos de negócio
    public void ativar() {
        this.disponivel = true;
        this.atualizarTimestamp();
    }
    
    public void desativar() {
        this.disponivel = false;
        this.atualizarTimestamp();
    }
    
    public void atualizar(String nome, String descricao, BigDecimal preco, CategoriaProduto categoria) {
        this.setNome(nome);
        this.setDescricao(descricao);
        this.setPreco(preco);
        this.setCategoria(categoria);
    }
    
    /**
     * Calcula o valor total para uma quantidade específica.
     * 
     * @param quantidade Quantidade do produto
     * @return Valor total
     */
    public BigDecimal calcularValorTotal(int quantidade) {
        if (quantidade <= 0) {
            throw new DomainException("Quantidade deve ser maior que zero");
        }
        return preco.multiply(BigDecimal.valueOf(quantidade));
    }
    
    /**
     * Verifica se o produto está disponível para venda.
     * 
     * @return true se disponível
     */
    public boolean podeSerVendido() {
        return disponivel;
    }
    
    private void atualizarTimestamp() {
        this.atualizadoEm = LocalDateTime.now();
    }
    
    // Método para definir ID (usado pelos adapters)
    public void setId(Long id) {
        this.id = id;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Objects.equals(id, produto.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return String.format("Produto{id=%d, nome='%s', categoria=%s, preco=%s, disponivel=%s}", 
                           id, nome, categoria, preco, disponivel);
    }
}

