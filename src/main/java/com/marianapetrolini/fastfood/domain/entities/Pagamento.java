package com.marianapetrolini.fastfood.domain.entities;

import com.marianapetrolini.fastfood.domain.exceptions.DomainException;
import com.marianapetrolini.fastfood.domain.valueobjects.MetodoPagamento;
import com.marianapetrolini.fastfood.domain.valueobjects.StatusPagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Entidade que representa um pagamento do sistema.
 * Contém as regras de negócio relacionadas aos pagamentos.
 */
public class Pagamento {
    
    private Long id;
    private Pedido pedido;
    private MetodoPagamento metodo;
    private StatusPagamento status;
    private BigDecimal valor;
    private String transacaoId;
    private String qrCode;
    private String linkPagamento;
    private String motivoRecusa;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
    private LocalDateTime processadoEm;
    
    // Construtor para criação de novos pagamentos
    public Pagamento(Pedido pedido, MetodoPagamento metodo, BigDecimal valor) {
        this.setPedido(pedido);
        this.setMetodo(metodo);
        this.setValor(valor);
        this.status = StatusPagamento.PENDENTE;
        this.criadoEm = LocalDateTime.now();
        this.atualizadoEm = LocalDateTime.now();
    }
    
    // Construtor para reconstrução (usado pelos adapters)
    public Pagamento(Long id, Pedido pedido, MetodoPagamento metodo, StatusPagamento status,
                     BigDecimal valor, String transacaoId, String qrCode, String linkPagamento,
                     String motivoRecusa, LocalDateTime criadoEm, LocalDateTime atualizadoEm,
                     LocalDateTime processadoEm) {
        this.id = id;
        this.pedido = pedido;
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
    }
    
    // Getters
    public Long getId() {
        return id;
    }
    
    public Pedido getPedido() {
        return pedido;
    }
    
    public MetodoPagamento getMetodo() {
        return metodo;
    }
    
    public StatusPagamento getStatus() {
        return status;
    }
    
    public BigDecimal getValor() {
        return valor;
    }
    
    public String getTransacaoId() {
        return transacaoId;
    }
    
    public String getQrCode() {
        return qrCode;
    }
    
    public String getLinkPagamento() {
        return linkPagamento;
    }
    
    public String getMotivoRecusa() {
        return motivoRecusa;
    }
    
    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }
    
    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }
    
    public LocalDateTime getProcessadoEm() {
        return processadoEm;
    }
    
    // Setters com validações de domínio
    public void setPedido(Pedido pedido) {
        if (pedido == null) {
            throw new DomainException("Pedido do pagamento não pode ser nulo");
        }
        this.pedido = pedido;
        this.atualizarTimestamp();
    }
    
    public void setMetodo(MetodoPagamento metodo) {
        if (metodo == null) {
            throw new DomainException("Método de pagamento não pode ser nulo");
        }
        this.metodo = metodo;
        this.atualizarTimestamp();
    }
    
    public void setValor(BigDecimal valor) {
        if (valor == null) {
            throw new DomainException("Valor do pagamento não pode ser nulo");
        }
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new DomainException("Valor do pagamento deve ser maior que zero");
        }
        this.valor = valor;
        this.atualizarTimestamp();
    }
    
    // Métodos de negócio
    
    /**
     * Atualiza o status do pagamento validando as transições.
     * 
     * @param novoStatus Novo status
     */
    public void atualizarStatus(StatusPagamento novoStatus) {
        if (novoStatus == null) {
            throw new DomainException("Novo status não pode ser nulo");
        }
        
        if (!this.status.podeTransicionarPara(novoStatus)) {
            throw new DomainException(
                String.format("Transição inválida de %s para %s", 
                    this.status.getNome(), novoStatus.getNome())
            );
        }
        
        this.status = novoStatus;
        this.atualizarTimestamp();
        
        if (novoStatus.isFinalizado()) {
            this.processadoEm = LocalDateTime.now();
        }
    }
    
    /**
     * Aprova o pagamento.
     * 
     * @param transacaoId ID da transação do gateway
     */
    public void aprovar(String transacaoId) {
        this.transacaoId = transacaoId;
        this.atualizarStatus(StatusPagamento.APROVADO);
    }
    
    /**
     * Recusa o pagamento.
     * 
     * @param motivo Motivo da recusa
     */
    public void recusar(String motivo) {
        this.motivoRecusa = motivo;
        this.atualizarStatus(StatusPagamento.RECUSADO);
    }
    
    /**
     * Cancela o pagamento.
     * 
     * @param motivo Motivo do cancelamento
     */
    public void cancelar(String motivo) {
        this.motivoRecusa = motivo;
        this.atualizarStatus(StatusPagamento.CANCELADO);
    }
    
    /**
     * Inicia o processamento do pagamento.
     * 
     * @param transacaoId ID da transação do gateway
     */
    public void iniciarProcessamento(String transacaoId) {
        this.transacaoId = transacaoId;
        this.atualizarStatus(StatusPagamento.PROCESSANDO);
    }
    
    /**
     * Estorna o pagamento.
     * 
     * @param motivo Motivo do estorno
     */
    public void estornar(String motivo) {
        if (!this.status.isAprovado()) {
            throw new DomainException("Só é possível estornar pagamentos aprovados");
        }
        this.motivoRecusa = motivo;
        this.atualizarStatus(StatusPagamento.ESTORNADO);
    }
    
    /**
     * Define os dados de integração com gateway de pagamento.
     * 
     * @param transacaoId ID da transação
     * @param qrCode Código QR para pagamento
     * @param linkPagamento Link para pagamento
     */
    public void definirDadosIntegracao(String transacaoId, String qrCode, String linkPagamento) {
        this.transacaoId = transacaoId;
        this.qrCode = qrCode;
        this.linkPagamento = linkPagamento;
        this.atualizarTimestamp();
    }
    
    /**
     * Verifica se o pagamento foi aprovado.
     * 
     * @return true se aprovado
     */
    public boolean isAprovado() {
        return status.isAprovado();
    }
    
    /**
     * Verifica se o pagamento foi negado.
     * 
     * @return true se negado
     */
    public boolean isNegado() {
        return status.isNegado();
    }
    
    /**
     * Verifica se o pagamento está pendente.
     * 
     * @return true se pendente
     */
    public boolean isPendente() {
        return status.isPendente();
    }
    
    /**
     * Verifica se o pagamento está finalizado.
     * 
     * @return true se finalizado
     */
    public boolean isFinalizado() {
        return status.isFinalizado();
    }
    
    /**
     * Verifica se o método de pagamento requer integração.
     * 
     * @return true se requer integração
     */
    public boolean requerIntegracao() {
        return metodo.requerIntegracao();
    }
    
    /**
     * Verifica se o pagamento possui QR Code.
     * 
     * @return true se possui QR Code
     */
    public boolean possuiQrCode() {
        return qrCode != null && !qrCode.trim().isEmpty();
    }
    
    /**
     * Verifica se o pagamento possui link de pagamento.
     * 
     * @return true se possui link
     */
    public boolean possuiLinkPagamento() {
        return linkPagamento != null && !linkPagamento.trim().isEmpty();
    }
    
    /**
     * Calcula o tempo de processamento em minutos.
     * 
     * @return Tempo de processamento em minutos, ou null se não processado
     */
    public Long getTempoProcessamentoMinutos() {
        if (processadoEm == null) {
            return null;
        }
        return java.time.Duration.between(criadoEm, processadoEm).toMinutes();
    }
    
    /**
     * Cria uma descrição resumida do pagamento.
     * 
     * @return Descrição do pagamento
     */
    public String getDescricaoResumida() {
        StringBuilder descricao = new StringBuilder();
        descricao.append("Pagamento #").append(id != null ? id : "novo");
        descricao.append(" - ").append(metodo.getNome());
        descricao.append(" - R$ ").append(valor);
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
        Pagamento pagamento = (Pagamento) o;
        return Objects.equals(id, pagamento.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return String.format("Pagamento{id=%d, pedidoId=%d, metodo=%s, status=%s, valor=%s, transacaoId=%s}", 
                           id, pedido != null ? pedido.getId() : null, 
                           metodo, status, valor, transacaoId);
    }
}

