package com.marianapetrolini.fastfood.domain.entities;

import com.marianapetrolini.fastfood.domain.exceptions.DomainException;
import com.marianapetrolini.fastfood.domain.valueobjects.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Entidade que representa um pedido do sistema.
 * Contém as regras de negócio relacionadas aos pedidos.
 */
public class Pedido {
    
    private Long id;
    private Cliente cliente;
    private List<ItemPedido> itens;
    private StatusPedido status;
    private BigDecimal valorTotal;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
    
    // Construtor para criação de novos pedidos
    public Pedido(Cliente cliente, List<ItemPedido> itens) {
        this.setCliente(cliente);
        this.setItens(itens);
        this.status = StatusPedido.RECEBIDO;
        this.calcularValorTotal();
        this.criadoEm = LocalDateTime.now();
        this.atualizadoEm = LocalDateTime.now();
    }
    
    // Construtor para reconstrução (usado pelos adapters)
    public Pedido(Long id, Cliente cliente, List<ItemPedido> itens, StatusPedido status,
                  BigDecimal valorTotal, LocalDateTime criadoEm, LocalDateTime atualizadoEm) {
        this.id = id;
        this.cliente = cliente;
        this.itens = itens != null ? new ArrayList<>(itens) : new ArrayList<>();
        this.status = status;
        this.valorTotal = valorTotal;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
    }
    
    // Getters
    public Long getId() {
        return id;
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public List<ItemPedido> getItens() {
        return Collections.unmodifiableList(itens);
    }
    
    public StatusPedido getStatus() {
        return status;
    }
    
    public BigDecimal getValorTotal() {
        return valorTotal;
    }
    
    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }
    
    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }
    
    // Setters com validações de domínio
    public void setCliente(Cliente cliente) {
        if (cliente == null) {
            throw new DomainException("Cliente do pedido não pode ser nulo");
        }
        this.cliente = cliente;
        this.atualizarTimestamp();
    }
    
    public void setItens(List<ItemPedido> itens) {
        if (itens == null || itens.isEmpty()) {
            throw new DomainException("Pedido deve ter pelo menos um item");
        }
        
        // Validar se todos os produtos estão disponíveis
        for (ItemPedido item : itens) {
            if (!item.produtoDisponivel()) {
                throw new DomainException("Produto não disponível: " + item.getProduto().getNome());
            }
        }
        
        this.itens = new ArrayList<>(itens);
        this.calcularValorTotal();
        this.atualizarTimestamp();
    }
    
    // Métodos de negócio
    
    /**
     * Adiciona um item ao pedido.
     * 
     * @param item Item a ser adicionado
     */
    public void adicionarItem(ItemPedido item) {
        if (item == null) {
            throw new DomainException("Item não pode ser nulo");
        }
        
        if (!podeAdicionarItens()) {
            throw new DomainException("Não é possível adicionar itens ao pedido no status: " + status.getNome());
        }
        
        if (!item.produtoDisponivel()) {
            throw new DomainException("Produto não disponível: " + item.getProduto().getNome());
        }
        
        // Verificar se já existe item com o mesmo produto
        for (ItemPedido itemExistente : itens) {
            if (itemExistente.getProduto().getId().equals(item.getProduto().getId())) {
                // Atualizar quantidade do item existente
                itemExistente.atualizarQuantidade(itemExistente.getQuantidade() + item.getQuantidade());
                this.calcularValorTotal();
                this.atualizarTimestamp();
                return;
            }
        }
        
        // Adicionar novo item
        this.itens.add(item);
        this.calcularValorTotal();
        this.atualizarTimestamp();
    }
    
    /**
     * Remove um item do pedido.
     * 
     * @param itemId ID do item a ser removido
     */
    public void removerItem(Long itemId) {
        if (!podeAdicionarItens()) {
            throw new DomainException("Não é possível remover itens do pedido no status: " + status.getNome());
        }
        
        boolean removido = itens.removeIf(item -> item.getId().equals(itemId));
        
        if (!removido) {
            throw new DomainException("Item não encontrado no pedido: " + itemId);
        }
        
        if (itens.isEmpty()) {
            throw new DomainException("Pedido deve ter pelo menos um item");
        }
        
        this.calcularValorTotal();
        this.atualizarTimestamp();
    }
    
    /**
     * Atualiza o status do pedido validando as transições.
     * 
     * @param novoStatus Novo status
     */
    public void atualizarStatus(StatusPedido novoStatus) {
        if (novoStatus == null) {
            throw new DomainException("Novo status não pode ser nulo");
        }
        
        this.status.validarTransicao(novoStatus);
        this.status = novoStatus;
        this.atualizarTimestamp();
    }
    
    /**
     * Inicia a preparação do pedido (transição para EM_PREPARACAO).
     */
    public void iniciarPreparacao() {
        this.atualizarStatus(StatusPedido.EM_PREPARACAO);
    }
    
    /**
     * Marca o pedido como pronto (transição para PRONTO).
     */
    public void marcarComoPronto() {
        this.atualizarStatus(StatusPedido.PRONTO);
    }
    
    /**
     * Finaliza o pedido (transição para FINALIZADO).
     */
    public void finalizar() {
        this.atualizarStatus(StatusPedido.FINALIZADO);
    }
    
    /**
     * Calcula o valor total do pedido somando todos os itens.
     */
    private void calcularValorTotal() {
        this.valorTotal = itens.stream()
                .map(ItemPedido::calcularValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    /**
     * Verifica se é possível adicionar/remover itens do pedido.
     * 
     * @return true se for possível modificar itens
     */
    public boolean podeAdicionarItens() {
        return status == StatusPedido.RECEBIDO;
    }
    
    /**
     * Verifica se o pedido pode ser cancelado.
     * 
     * @return true se pode ser cancelado
     */
    public boolean podeCancelar() {
        return status == StatusPedido.RECEBIDO || status == StatusPedido.EM_PREPARACAO;
    }
    
    /**
     * Verifica se o pedido está finalizado.
     * 
     * @return true se finalizado
     */
    public boolean isFinalizado() {
        return status == StatusPedido.FINALIZADO;
    }
    
    /**
     * Verifica se o pedido deve aparecer na lista da cozinha.
     * 
     * @return true se deve aparecer na cozinha
     */
    public boolean isVisivelNaCozinha() {
        return status.isVisivelNaCozinha();
    }
    
    /**
     * Retorna a prioridade do pedido para ordenação na cozinha.
     * 
     * @return Prioridade numérica
     */
    public int getPrioridadeCozinha() {
        return status.getPrioridadeCozinha();
    }
    
    /**
     * Retorna o número total de itens no pedido.
     * 
     * @return Quantidade total de itens
     */
    public int getTotalItens() {
        return itens.stream().mapToInt(ItemPedido::getQuantidade).sum();
    }
    
    /**
     * Cria uma descrição resumida do pedido.
     * 
     * @return Descrição do pedido
     */
    public String getDescricaoResumida() {
        StringBuilder descricao = new StringBuilder();
        descricao.append("Pedido #").append(id != null ? id : "novo");
        descricao.append(" - ").append(getTotalItens()).append(" itens");
        descricao.append(" - R$ ").append(valorTotal);
        descricao.append(" - ").append(status.getNome());
        return descricao.toString();
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
        Pedido pedido = (Pedido) o;
        return Objects.equals(id, pedido.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return String.format("Pedido{id=%d, cliente=%s, status=%s, valorTotal=%s, totalItens=%d}", 
                           id, cliente != null ? cliente.getNome() : "null", 
                           status, valorTotal, getTotalItens());
    }
}

