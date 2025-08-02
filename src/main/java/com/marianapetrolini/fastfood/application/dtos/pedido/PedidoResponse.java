package com.marianapetrolini.fastfood.application.dtos.pedido;

import com.marianapetrolini.fastfood.domain.entities.Pedido;
import com.marianapetrolini.fastfood.domain.entities.ItemPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO para resposta de pedido.
 */
public class PedidoResponse {
    
    private Long id;
    private ClienteResponse cliente;
    private List<ItemPedidoResponse> itens;
    private String status;
    private BigDecimal valorTotal;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
    
    public PedidoResponse() {
    }
    
    public PedidoResponse(Long id, ClienteResponse cliente, List<ItemPedidoResponse> itens,
                         String status, BigDecimal valorTotal, LocalDateTime criadoEm,
                         LocalDateTime atualizadoEm) {
        this.id = id;
        this.cliente = cliente;
        this.itens = itens;
        this.status = status;
        this.valorTotal = valorTotal;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
    }
    
    /**
     * Cria um PedidoResponse a partir de uma entidade Pedido.
     * 
     * @param pedido Entidade pedido
     * @return DTO de resposta
     */
    public static PedidoResponse fromEntity(Pedido pedido) {
        return new PedidoResponse(
            pedido.getId(),
            ClienteResponse.fromEntity(pedido.getCliente()),
            pedido.getItens().stream()
                .map(ItemPedidoResponse::fromEntity)
                .collect(Collectors.toList()),
            pedido.getStatus().name(),
            pedido.getValorTotal(),
            pedido.getCriadoEm(),
            pedido.getAtualizadoEm()
        );
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public ClienteResponse getCliente() {
        return cliente;
    }
    
    public void setCliente(ClienteResponse cliente) {
        this.cliente = cliente;
    }
    
    public List<ItemPedidoResponse> getItens() {
        return itens;
    }
    
    public void setItens(List<ItemPedidoResponse> itens) {
        this.itens = itens;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public BigDecimal getValorTotal() {
        return valorTotal;
    }
    
    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
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
    
    /**
     * DTO para resposta de cliente.
     */
    public static class ClienteResponse {
        private Long id;
        private String nome;
        private String cpf;
        private String email;
        
        public ClienteResponse() {
        }
        
        public ClienteResponse(Long id, String nome, String cpf, String email) {
            this.id = id;
            this.nome = nome;
            this.cpf = cpf;
            this.email = email;
        }
        
        public static ClienteResponse fromEntity(com.marianapetrolini.fastfood.domain.entities.Cliente cliente) {
            return new ClienteResponse(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf() != null ? cliente.getCpf().getValorFormatado() : null,
                cliente.getEmail().getValor()
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
        
        public String getCpf() {
            return cpf;
        }
        
        public void setCpf(String cpf) {
            this.cpf = cpf;
        }
        
        public String getEmail() {
            return email;
        }
        
        public void setEmail(String email) {
            this.email = email;
        }
    }
    
    /**
     * DTO para resposta de item do pedido.
     */
    public static class ItemPedidoResponse {
        private Long id;
        private String produtoNome;
        private int quantidade;
        private BigDecimal precoUnitario;
        private BigDecimal valorTotal;
        private String observacoes;
        
        public ItemPedidoResponse() {
        }
        
        public ItemPedidoResponse(Long id, String produtoNome, int quantidade,
                                 BigDecimal precoUnitario, BigDecimal valorTotal, String observacoes) {
            this.id = id;
            this.produtoNome = produtoNome;
            this.quantidade = quantidade;
            this.precoUnitario = precoUnitario;
            this.valorTotal = valorTotal;
            this.observacoes = observacoes;
        }
        
        public static ItemPedidoResponse fromEntity(ItemPedido item) {
            return new ItemPedidoResponse(
                item.getId(),
                item.getProduto().getNome(),
                item.getQuantidade(),
                item.getPrecoUnitario(),
                item.calcularValorTotal(),
                item.getObservacoes()
            );
        }
        
        public Long getId() {
            return id;
        }
        
        public void setId(Long id) {
            this.id = id;
        }
        
        public String getProdutoNome() {
            return produtoNome;
        }
        
        public void setProdutoNome(String produtoNome) {
            this.produtoNome = produtoNome;
        }
        
        public int getQuantidade() {
            return quantidade;
        }
        
        public void setQuantidade(int quantidade) {
            this.quantidade = quantidade;
        }
        
        public BigDecimal getPrecoUnitario() {
            return precoUnitario;
        }
        
        public void setPrecoUnitario(BigDecimal precoUnitario) {
            this.precoUnitario = precoUnitario;
        }
        
        public BigDecimal getValorTotal() {
            return valorTotal;
        }
        
        public void setValorTotal(BigDecimal valorTotal) {
            this.valorTotal = valorTotal;
        }
        
        public String getObservacoes() {
            return observacoes;
        }
        
        public void setObservacoes(String observacoes) {
            this.observacoes = observacoes;
        }
    }
    
    @Override
    public String toString() {
        return String.format("PedidoResponse{id=%d, status='%s', valorTotal=%s, itens=%d}", 
                           id, status, valorTotal, itens != null ? itens.size() : 0);
    }
}

