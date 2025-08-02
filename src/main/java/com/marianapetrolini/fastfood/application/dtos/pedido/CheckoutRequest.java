package com.marianapetrolini.fastfood.application.dtos.pedido;

import java.util.List;

/**
 * DTO para requisição de checkout de pedido.
 */
public class CheckoutRequest {
    
    private ClienteRequest cliente;
    private List<ItemPedidoRequest> itens;
    private String metodoPagamento;
    
    public CheckoutRequest() {
    }
    
    public CheckoutRequest(ClienteRequest cliente, List<ItemPedidoRequest> itens, String metodoPagamento) {
        this.cliente = cliente;
        this.itens = itens;
        this.metodoPagamento = metodoPagamento;
    }
    
    public ClienteRequest getCliente() {
        return cliente;
    }
    
    public void setCliente(ClienteRequest cliente) {
        this.cliente = cliente;
    }
    
    public List<ItemPedidoRequest> getItens() {
        return itens;
    }
    
    public void setItens(List<ItemPedidoRequest> itens) {
        this.itens = itens;
    }
    
    public String getMetodoPagamento() {
        return metodoPagamento;
    }
    
    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }
    
    /**
     * DTO para dados do cliente no checkout.
     */
    public static class ClienteRequest {
        private String nome;
        private String cpf;
        private String email;
        
        public ClienteRequest() {
        }
        
        public ClienteRequest(String nome, String cpf, String email) {
            this.nome = nome;
            this.cpf = cpf;
            this.email = email;
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
        
        @Override
        public String toString() {
            return String.format("ClienteRequest{nome='%s', email='%s'}", nome, email);
        }
    }
    
    /**
     * DTO para item do pedido no checkout.
     */
    public static class ItemPedidoRequest {
        private Long produtoId;
        private int quantidade;
        private String observacoes;
        
        public ItemPedidoRequest() {
        }
        
        public ItemPedidoRequest(Long produtoId, int quantidade) {
            this.produtoId = produtoId;
            this.quantidade = quantidade;
        }
        
        public ItemPedidoRequest(Long produtoId, int quantidade, String observacoes) {
            this.produtoId = produtoId;
            this.quantidade = quantidade;
            this.observacoes = observacoes;
        }
        
        public Long getProdutoId() {
            return produtoId;
        }
        
        public void setProdutoId(Long produtoId) {
            this.produtoId = produtoId;
        }
        
        public int getQuantidade() {
            return quantidade;
        }
        
        public void setQuantidade(int quantidade) {
            this.quantidade = quantidade;
        }
        
        public String getObservacoes() {
            return observacoes;
        }
        
        public void setObservacoes(String observacoes) {
            this.observacoes = observacoes;
        }
        
        @Override
        public String toString() {
            return String.format("ItemPedidoRequest{produtoId=%d, quantidade=%d}", produtoId, quantidade);
        }
    }
    
    @Override
    public String toString() {
        return String.format("CheckoutRequest{cliente=%s, itens=%d, metodoPagamento='%s'}", 
                           cliente, itens != null ? itens.size() : 0, metodoPagamento);
    }
}

