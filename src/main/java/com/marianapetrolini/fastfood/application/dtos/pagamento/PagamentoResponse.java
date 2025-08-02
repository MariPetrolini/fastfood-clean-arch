package com.marianapetrolini.fastfood.application.dtos.pagamento;

import com.marianapetrolini.fastfood.domain.entities.Pagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO para resposta de pagamento.
 */
public class PagamentoResponse {
    
    private Long id;
    private Long pedidoId;
    private String metodo;
    private String status;
    private BigDecimal valor;
    private String transacaoId;
    private String qrCode;
    private String linkPagamento;
    private String motivoRecusa;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
    private LocalDateTime processadoEm;
    private Long tempoProcessamentoMinutos;
    
    public PagamentoResponse() {
    }
    
    public PagamentoResponse(Long id, Long pedidoId, String metodo, String status, BigDecimal valor,
                            String transacaoId, String qrCode, String linkPagamento, String motivoRecusa,
                            LocalDateTime criadoEm, LocalDateTime atualizadoEm, LocalDateTime processadoEm,
                            Long tempoProcessamentoMinutos) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.metodo = metodo;
        this.status = status;
        this.valor = valor;
        this.transacaoId = transacaoId;
        this.qrCode = qrCode;
        this.linkPagamento = linkPagamento;
        this.motivoRecusa = motivoRecusa;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
        this.processadoEm = processadoEm;
        this.tempoProcessamentoMinutos = tempoProcessamentoMinutos;
    }
    
    /**
     * Cria um PagamentoResponse a partir de uma entidade Pagamento.
     * 
     * @param pagamento Entidade pagamento
     * @return DTO de resposta
     */
    public static PagamentoResponse fromEntity(Pagamento pagamento) {
        return new PagamentoResponse(
            pagamento.getId(),
            pagamento.getPedido().getId(),
            pagamento.getMetodo().name(),
            pagamento.getStatus().name(),
            pagamento.getValor(),
            pagamento.getTransacaoId(),
            pagamento.getQrCode(),
            pagamento.getLinkPagamento(),
            pagamento.getMotivoRecusa(),
            pagamento.getCriadoEm(),
            pagamento.getAtualizadoEm(),
            pagamento.getProcessadoEm(),
            pagamento.getTempoProcessamentoMinutos()
        );
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getPedidoId() {
        return pedidoId;
    }
    
    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }
    
    public String getMetodo() {
        return metodo;
    }
    
    public void setMetodo(String metodo) {
        this.metodo = metodo;
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
    
    public String getTransacaoId() {
        return transacaoId;
    }
    
    public void setTransacaoId(String transacaoId) {
        this.transacaoId = transacaoId;
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
    
    public String getMotivoRecusa() {
        return motivoRecusa;
    }
    
    public void setMotivoRecusa(String motivoRecusa) {
        this.motivoRecusa = motivoRecusa;
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
    
    public LocalDateTime getProcessadoEm() {
        return processadoEm;
    }
    
    public void setProcessadoEm(LocalDateTime processadoEm) {
        this.processadoEm = processadoEm;
    }
    
    public Long getTempoProcessamentoMinutos() {
        return tempoProcessamentoMinutos;
    }
    
    public void setTempoProcessamentoMinutos(Long tempoProcessamentoMinutos) {
        this.tempoProcessamentoMinutos = tempoProcessamentoMinutos;
    }
    
    @Override
    public String toString() {
        return String.format("PagamentoResponse{id=%d, pedidoId=%d, metodo='%s', status='%s', valor=%s}", 
                           id, pedidoId, metodo, status, valor);
    }
}

