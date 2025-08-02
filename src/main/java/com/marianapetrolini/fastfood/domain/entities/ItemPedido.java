package com.marianapetrolini.fastfood.domain.entities;

import com.marianapetrolini.fastfood.domain.exceptions.DomainException;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Entidade que representa um item de pedido.
 * Contém as regras de negócio relacionadas aos itens de pedido.
 */
public class ItemPedido {
    
    private Long id;
    private Produto produto;
    private int quantidade;
    private BigDecimal precoUnitario;
    private String observacoes;
    
    // Construtor para criação de novos itens
    public ItemPedido(Produto produto, int quantidade) {
        this.setProduto(produto);
        this.setQuantidade(quantidade);
        this.precoUnitario = produto.getPreco();
        this.observacoes = null;
    }
    
    // Construtor com observações
    public ItemPedido(Produto produto, int quantidade, String observacoes) {
        this.setProduto(produto);
        this.setQuantidade(quantidade);
        this.precoUnitario = produto.getPreco();
        this.setObservacoes(observacoes);
    }
    
    // Construtor para reconstrução (usado pelos adapters)
    public ItemPedido(Long id, Produto produto, int quantidade, 
                      BigDecimal precoUnitario, String observacoes) {
        this.id = id;
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.observacoes = observacoes;
    }
    
    // Getters
    public Long getId() {
        return id;
    }
    
    public Produto getProduto() {
        return produto;
    }
    
    public int getQuantidade() {
        return quantidade;
    }
    
    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }
    
    public String getObservacoes() {
        return observacoes;
    }
    
    // Setters com validações de domínio
    public void setProduto(Produto produto) {
        if (produto == null) {
            throw new DomainException("Produto do item não pode ser nulo");
        }
        if (!produto.podeSerVendido()) {
            throw new DomainException("Produto não está disponível para venda: " + produto.getNome());
        }
        this.produto = produto;
    }
    
    public void setQuantidade(int quantidade) {
        if (quantidade <= 0) {
            throw new DomainException("Quantidade do item deve ser maior que zero");
        }
        if (quantidade > 99) {
            throw new DomainException("Quantidade do item não pode ser maior que 99");
        }
        this.quantidade = quantidade;
    }
    
    public void setObservacoes(String observacoes) {
        if (observacoes != null && observacoes.trim().length() > 200) {
            throw new DomainException("Observações do item não podem ter mais que 200 caracteres");
        }
        this.observacoes = observacoes != null ? observacoes.trim() : null;
    }
    
    // Métodos de negócio
    
    /**
     * Calcula o valor total do item (preço unitário × quantidade).
     * 
     * @return Valor total do item
     */
    public BigDecimal calcularValorTotal() {
        return precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }
    
    /**
     * Atualiza a quantidade do item.
     * 
     * @param novaQuantidade Nova quantidade
     */
    public void atualizarQuantidade(int novaQuantidade) {
        this.setQuantidade(novaQuantidade);
    }
    
    /**
     * Adiciona observações ao item.
     * 
     * @param observacoes Observações do item
     */
    public void adicionarObservacoes(String observacoes) {
        this.setObservacoes(observacoes);
    }
    
    /**
     * Verifica se o item possui observações.
     * 
     * @return true se possui observações
     */
    public boolean possuiObservacoes() {
        return observacoes != null && !observacoes.trim().isEmpty();
    }
    
    /**
     * Verifica se o produto do item ainda está disponível.
     * 
     * @return true se o produto está disponível
     */
    public boolean produtoDisponivel() {
        return produto != null && produto.podeSerVendido();
    }
    
    /**
     * Cria uma descrição completa do item.
     * 
     * @return Descrição do item
     */
    public String getDescricaoCompleta() {
        StringBuilder descricao = new StringBuilder();
        descricao.append(quantidade).append("x ").append(produto.getNome());
        
        if (possuiObservacoes()) {
            descricao.append(" (").append(observacoes).append(")");
        }
        
        return descricao.toString();
    }
    
    // Método para definir ID (usado pelos adapters)
    public void setId(Long id) {
        this.id = id;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemPedido that = (ItemPedido) o;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return String.format("ItemPedido{id=%d, produto=%s, quantidade=%d, precoUnitario=%s, valorTotal=%s}", 
                           id, produto != null ? produto.getNome() : "null", 
                           quantidade, precoUnitario, calcularValorTotal());
    }
}

