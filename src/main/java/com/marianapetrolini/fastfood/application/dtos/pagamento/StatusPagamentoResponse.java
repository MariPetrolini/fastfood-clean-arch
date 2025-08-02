package com.marianapetrolini.fastfood.application.dtos.pagamento;

import com.marianapetrolini.fastfood.domain.entities.Pagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO para resposta de status de pagamento.
 */
public class StatusPagamentoResponse {
    
    private Long pedidoId;
    private Long pagamentoId;
    private String status;
    private String metodo;
    private BigDecimal valor;
    private String transacaoId;
    private String qrCode;
    private String linkPagamento;
    private String motivoRecusa;
    private LocalDateTime criadoEm;
    private LocalDateTime processadoEm;
    private Long tempoProcessamentoMinutos;
    private String mensagem;
    
    public StatusPagamentoResponse() {
    }
    
    public StatusPagamentoResponse(Long pedidoId, Long pagamentoId, String status, String metodo,
                                  BigDecimal valor, String transacaoId, String qrCode, String linkPagamento,
                                  String motivoRecusa, LocalDateTime criadoEm, LocalDateTime processadoEm,
                                  Long tempoProcessamentoMinutos, String mensagem) {
        this.pedidoId = pedidoId;
        this.pagamentoId = pagamentoId;
        this.status = status;
        this.metodo = metodo;
        this.valor = valor;
        this.transacaoId = transacaoId;
        this.qrCode = qrCode;
        this.linkPagamento = linkPagamento;
        this.motivoRecusa = motivoRecusa;
        this.criadoEm = criadoEm;
        this.processadoEm = processadoEm;
        this.tempoProcessamentoMinutos = tempoProcessamentoMinutos;
        this.mensagem = mensagem;
    }
    
    /**
     * Cria um StatusPagamentoResponse a partir de uma entidade Pagamento.
     * 
     * @param pagamento Entidade pagamento
     * @return DTO de resposta
     */
    public static StatusPagamentoResponse fromEntity(Pagamento pagamento) {
        String mensagem = criarMensagem(pagamento);
        
        return new StatusPagamentoResponse(
            pagamento.getPedido().getId(),
            pagamento.getId(),
            pagamento.getStatus().name(),
            pagamento.getMetodo().name(),
            pagamento.getValor(),
            pagamento.getTransacaoId(),
            pagamento.getQrCode(),
            pagamento.getLinkPagamento(),
            pagamento.getMotivoRecusa(),
            pagamento.getCriadoEm(),
            pagamento.getProcessadoEm(),
            pagamento.getTempoProcessamentoMinutos(),
            mensagem
        );
    }
    
    private static String criarMensagem(Pagamento pagamento) {
        switch (pagamento.getStatus()) {
            case PENDENTE:
                return "Pagamento aguardando processamento";
            case PROCESSANDO:
                return "Pagamento sendo processado";
            case APROVADO:
                return "Pagamento aprovado com sucesso";
            case RECUSADO:
                return "Pagamento recusado: " + (pagamento.getMotivoRecusa() != null ? 
                    pagamento.getMotivoRecusa() : "Motivo não informado");
            case CANCELADO:
                return "Pagamento cancelado: " + (pagamento.getMotivoRecusa() != null ? 
                    pagamento.getMotivoRecusa() : "Motivo não informado");
            case ESTORNADO:
                return "Pagamento estornado: " + (pagamento.getMotivoRecusa() != null ? 
                    pagamento.getMotivoRecusa() : "Motivo não informado");
            default:
                return "Status desconhecido";
        }
    }
    
    public Long getPedidoId() {
        return pedidoId;
    }
    
    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }
    
    public Long getPagamentoId() {
        return pagamentoId;
    }
    
    public void setPagamentoId(Long pagamentoId) {
        this.pagamentoId = pagamentoId;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getMetodo() {
        return metodo;
    }
    
    public void setMetodo(String metodo) {
        this.metodo = metodo;
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
    
    public String getMensagem() {
        return mensagem;
    }
    
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    
    @Override
    public String toString() {
        return String.format("StatusPagamentoResponse{pedidoId=%d, status='%s', metodo='%s', valor=%s}", 
                           pedidoId, status, metodo, valor);
    }
}

