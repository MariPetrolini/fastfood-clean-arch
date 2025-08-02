package com.marianapetrolini.fastfood.application.ports.output;

import com.marianapetrolini.fastfood.domain.valueobjects.MetodoPagamento;

import java.math.BigDecimal;

/**
 * Port de saída para integração com Mercado Pago.
 * Define as operações necessárias para integração com gateway de pagamento.
 */
public interface MercadoPagoPort {
    
    /**
     * Dados de resposta da criação de pagamento no Mercado Pago.
     */
    class PagamentoMercadoPago {
        private final String transacaoId;
        private final String qrCode;
        private final String linkPagamento;
        
        public PagamentoMercadoPago(String transacaoId, String qrCode, String linkPagamento) {
            this.transacaoId = transacaoId;
            this.qrCode = qrCode;
            this.linkPagamento = linkPagamento;
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
    }
    
    /**
     * Status de pagamento retornado pelo Mercado Pago.
     */
    class StatusMercadoPago {
        private final String status;
        private final String transacaoId;
        private final BigDecimal valor;
        private final String motivo;
        
        public StatusMercadoPago(String status, String transacaoId, BigDecimal valor, String motivo) {
            this.status = status;
            this.transacaoId = transacaoId;
            this.valor = valor;
            this.motivo = motivo;
        }
        
        public String getStatus() {
            return status;
        }
        
        public String getTransacaoId() {
            return transacaoId;
        }
        
        public BigDecimal getValor() {
            return valor;
        }
        
        public String getMotivo() {
            return motivo;
        }
    }
    
    /**
     * Cria um pagamento no Mercado Pago.
     * 
     * @param pedidoId ID do pedido
     * @param valor Valor do pagamento
     * @param metodo Método de pagamento
     * @param descricao Descrição do pagamento
     * @return Dados do pagamento criado
     */
    PagamentoMercadoPago criarPagamento(Long pedidoId, BigDecimal valor, 
                                       MetodoPagamento metodo, String descricao);
    
    /**
     * Consulta o status de um pagamento no Mercado Pago.
     * 
     * @param transacaoId ID da transação
     * @return Status do pagamento
     */
    StatusMercadoPago consultarStatusPagamento(String transacaoId);
    
    /**
     * Cancela um pagamento no Mercado Pago.
     * 
     * @param transacaoId ID da transação
     * @return true se cancelado com sucesso
     */
    boolean cancelarPagamento(String transacaoId);
    
    /**
     * Estorna um pagamento no Mercado Pago.
     * 
     * @param transacaoId ID da transação
     * @param valor Valor a ser estornado (pode ser parcial)
     * @return true se estornado com sucesso
     */
    boolean estornarPagamento(String transacaoId, BigDecimal valor);
    
    /**
     * Verifica se a integração com Mercado Pago está disponível.
     * 
     * @return true se a integração está disponível
     */
    boolean isDisponivel();
    
    /**
     * Valida a assinatura de um webhook do Mercado Pago.
     * 
     * @param payload Payload do webhook
     * @param assinatura Assinatura recebida
     * @return true se a assinatura é válida
     */
    boolean validarAssinaturaWebhook(String payload, String assinatura);
}

