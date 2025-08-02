package com.marianapetrolini.fastfood.application.dtos.pedido;

import com.marianapetrolini.fastfood.domain.entities.Pedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO para resposta de lista de pedidos (especialmente para cozinha).
 * Versão simplificada com informações essenciais.
 */
public class PedidoListaResponse {
    
    private Long id;
    private String clienteNome;
    private String status;
    private String descricaoItens;
    private BigDecimal valorTotal;
    private int totalItens;
    private LocalDateTime criadoEm;
    private int prioridade;
    
    public PedidoListaResponse() {
    }
    
    public PedidoListaResponse(Long id, String clienteNome, String status, String descricaoItens,
                              BigDecimal valorTotal, int totalItens, LocalDateTime criadoEm, int prioridade) {
        this.id = id;
        this.clienteNome = clienteNome;
        this.status = status;
        this.descricaoItens = descricaoItens;
        this.valorTotal = valorTotal;
        this.totalItens = totalItens;
        this.criadoEm = criadoEm;
        this.prioridade = prioridade;
    }
    
    /**
     * Cria um PedidoListaResponse a partir de uma entidade Pedido.
     * 
     * @param pedido Entidade pedido
     * @return DTO de resposta
     */
    public static PedidoListaResponse fromEntity(Pedido pedido) {
        String descricaoItens = pedido.getItens().stream()
            .map(item -> String.format("%dx %s", item.getQuantidade(), item.getProduto().getNome()))
            .collect(Collectors.joining(", "));
        
        return new PedidoListaResponse(
            pedido.getId(),
            pedido.getCliente().getNome(),
            pedido.getStatus().name(),
            descricaoItens,
            pedido.getValorTotal(),
            pedido.getTotalItens(),
            pedido.getCriadoEm(),
            pedido.getPrioridadeCozinha()
        );
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getClienteNome() {
        return clienteNome;
    }
    
    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getDescricaoItens() {
        return descricaoItens;
    }
    
    public void setDescricaoItens(String descricaoItens) {
        this.descricaoItens = descricaoItens;
    }
    
    public BigDecimal getValorTotal() {
        return valorTotal;
    }
    
    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
    
    public int getTotalItens() {
        return totalItens;
    }
    
    public void setTotalItens(int totalItens) {
        this.totalItens = totalItens;
    }
    
    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }
    
    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }
    
    public int getPrioridade() {
        return prioridade;
    }
    
    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }
    
    @Override
    public String toString() {
        return String.format("PedidoListaResponse{id=%d, cliente='%s', status='%s', itens=%d, prioridade=%d}", 
                           id, clienteNome, status, totalItens, prioridade);
    }
}

