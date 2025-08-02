package com.marianapetrolini.fastfood.application.dtos.pedido;

import java.math.BigDecimal;

/**
 * DTO para resposta do checkout de pedido.
 */
public class CheckoutResponse {
    
    private Long pedidoId;
    private String status;
    private BigDecimal valorTotal;
    private String metodoPagamento;
    private String qrCode;
    private String linkPagamento;
    private String transacaoId;
    private String mensagem;
    
    public CheckoutResponse() {
    }
    
    public CheckoutResponse(Long pedidoId, String status, BigDecimal valorTotal, 
                           String metodoPagamento, String mensagem) {
        this.pedidoId = pedidoId;
        this.status = status;
        this.valorTotal = valorTotal;
        this.metodoPagamento = metodoPagamento;
        this.mensagem = mensagem;
    }
    
    public Long getPedidoId() {
        return pedidoId;
    }
    
    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
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
    
    public String getMetodoPagamento() {
        return metodoPagamento;
    }
    
    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }
    
    public String getQrCode() {
        return qrCode;
    }
    
    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
    
    public String getLinkPagamento() {
        return linkPagamento;
    }
    
    public void setLinkPagamento(String linkPagamento) {
        this.linkPagamento = linkPagamento;
    }
    
    public String getTransacaoId() {
        return transacaoId;
    }
    
    public void setTransacaoId(String transacaoId) {
        this.transacaoId = transacaoId;
    }
    
    public String getMensagem() {
        return mensagem;
    }
    
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    
    @Override
    public String toString() {
        return String.format("CheckoutResponse{pedidoId=%d, status='%s', valorTotal=%s, metodoPagamento='%s'}", 
                           pedidoId, status, valorTotal, metodoPagamento);
    }
}

