package com.marianapetrolini.fastfood.application.dtos.pagamento;

import java.math.BigDecimal;

/**
 * DTO para requisição de webhook de pagamento.
 */
public class WebhookPagamentoRequest {
    
    private String transacaoId;
    private String status;
    private BigDecimal valor;
    private String motivo;
    private String provedor;
    private String assinatura;
    
    public WebhookPagamentoRequest() {
    }
    
    public WebhookPagamentoRequest(String transacaoId, String status, BigDecimal valor, String motivo) {
        this.transacaoId = transacaoId;
        this.status = status;
        this.valor = valor;
        this.motivo = motivo;
    }
    
    public String getTransacaoId() {
        return transacaoId;
    }
    
    public void setTransacaoId(String transacaoId) {
        this.transacaoId = transacaoId;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public BigDecimal getValor() {
        return valor;
    }
    
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    
    public String getMotivo() {
        return motivo;
    }
    
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
    
    public String getProvedor() {
        return provedor;
    }
    
    public void setProvedor(String provedor) {
        this.provedor = provedor;
    }
    
    public String getAssinatura() {
        return assinatura;
    }
    
    public void setAssinatura(String assinatura) {
        this.assinatura = assinatura;
    }
    
    @Override
    public String toString() {
        return String.format("WebhookPagamentoRequest{transacaoId='%s', status='%s', valor=%s, provedor='%s'}", 
                           transacaoId, status, valor, provedor);
    }
}

