package com.marianapetrolini.fastfood.domain.valueobjects;

/**
 * Enum que representa os possíveis status de um pagamento.
 * Define os estados do ciclo de vida de um pagamento.
 */
public enum StatusPagamento {
    
    PENDENTE("Pendente", "Pagamento aguardando processamento"),
    PROCESSANDO("Processando", "Pagamento sendo processado pelo gateway"),
    APROVADO("Aprovado", "Pagamento foi aprovado com sucesso"),
    RECUSADO("Recusado", "Pagamento foi recusado"),
    CANCELADO("Cancelado", "Pagamento foi cancelado"),
    ESTORNADO("Estornado", "Pagamento foi estornado");
    
    private final String nome;
    private final String descricao;
    
    StatusPagamento(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }
    
    public String getNome() {
        return nome;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    /**
     * Verifica se o pagamento foi aprovado.
     * 
     * @return true se o pagamento foi aprovado
     */
    public boolean isAprovado() {
        return this == APROVADO;
    }
    
    /**
     * Verifica se o pagamento foi recusado ou cancelado.
     * 
     * @return true se o pagamento foi negado
     */
    public boolean isNegado() {
        return this == RECUSADO || this == CANCELADO;
    }
    
    /**
     * Verifica se o pagamento ainda está pendente de processamento.
     * 
     * @return true se o pagamento está pendente
     */
    public boolean isPendente() {
        return this == PENDENTE || this == PROCESSANDO;
    }
    
    /**
     * Verifica se o pagamento está em um estado final.
     * 
     * @return true se o pagamento está finalizado
     */
    public boolean isFinalizado() {
        return this == APROVADO || this == RECUSADO || 
               this == CANCELADO || this == ESTORNADO;
    }
    
    /**
     * Verifica se é possível transicionar do status atual para o novo status.
     * 
     * @param novoStatus Status de destino
     * @return true se a transição for válida
     */
    public boolean podeTransicionarPara(StatusPagamento novoStatus) {
        if (novoStatus == null) {
            return false;
        }
        
        switch (this) {
            case PENDENTE:
                return novoStatus == PROCESSANDO || novoStatus == CANCELADO;
            case PROCESSANDO:
                return novoStatus == APROVADO || novoStatus == RECUSADO || novoStatus == CANCELADO;
            case APROVADO:
                return novoStatus == ESTORNADO;
            case RECUSADO:
            case CANCELADO:
            case ESTORNADO:
                return false; // Estados finais
            default:
                return false;
        }
    }
    
    /**
     * Converte uma string para StatusPagamento de forma case-insensitive.
     * 
     * @param status String representando o status
     * @return StatusPagamento correspondente
     * @throws IllegalArgumentException se o status não for válido
     */
    public static StatusPagamento fromString(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status não pode ser nulo ou vazio");
        }
        
        try {
            return StatusPagamento.valueOf(status.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Status inválido: " + status + 
                ". Status válidos: PENDENTE, PROCESSANDO, APROVADO, RECUSADO, CANCELADO, ESTORNADO");
        }
    }
    
    /**
     * Converte status do webhook do Mercado Pago para StatusPagamento.
     * 
     * @param statusMercadoPago Status recebido do Mercado Pago
     * @return StatusPagamento correspondente
     */
    public static StatusPagamento fromMercadoPago(String statusMercadoPago) {
        if (statusMercadoPago == null) {
            return PENDENTE;
        }
        
        switch (statusMercadoPago.toLowerCase()) {
            case "approved":
                return APROVADO;
            case "rejected":
            case "cancelled":
                return RECUSADO;
            case "pending":
            case "in_process":
                return PROCESSANDO;
            case "refunded":
                return ESTORNADO;
            default:
                return PENDENTE;
        }
    }
}

