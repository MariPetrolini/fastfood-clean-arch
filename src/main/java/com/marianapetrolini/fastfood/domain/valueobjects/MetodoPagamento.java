package com.marianapetrolini.fastfood.domain.valueobjects;

/**
 * Enum que representa os métodos de pagamento disponíveis.
 * Define os métodos suportados pelo sistema.
 */
public enum MetodoPagamento {
    
    PIX("PIX", "Pagamento via PIX", true),
    CARTAO_CREDITO("Cartão de Crédito", "Pagamento via cartão de crédito", true),
    CARTAO_DEBITO("Cartão de Débito", "Pagamento via cartão de débito", true),
    DINHEIRO("Dinheiro", "Pagamento em dinheiro", false);
    
    private final String nome;
    private final String descricao;
    private final boolean requerIntegracao;
    
    MetodoPagamento(String nome, String descricao, boolean requerIntegracao) {
        this.nome = nome;
        this.descricao = descricao;
        this.requerIntegracao = requerIntegracao;
    }
    
    public String getNome() {
        return nome;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    /**
     * Verifica se o método de pagamento requer integração com gateway.
     * 
     * @return true se requer integração externa
     */
    public boolean requerIntegracao() {
        return requerIntegracao;
    }
    
    /**
     * Verifica se o método de pagamento é eletrônico.
     * 
     * @return true se for eletrônico
     */
    public boolean isEletronico() {
        return this != DINHEIRO;
    }
    
    /**
     * Verifica se o método de pagamento é instantâneo.
     * 
     * @return true se for instantâneo
     */
    public boolean isInstantaneo() {
        return this == PIX || this == DINHEIRO;
    }
    
    /**
     * Converte uma string para MetodoPagamento de forma case-insensitive.
     * 
     * @param metodo String representando o método
     * @return MetodoPagamento correspondente
     * @throws IllegalArgumentException se o método não for válido
     */
    public static MetodoPagamento fromString(String metodo) {
        if (metodo == null || metodo.trim().isEmpty()) {
            throw new IllegalArgumentException("Método de pagamento não pode ser nulo ou vazio");
        }
        
        try {
            return MetodoPagamento.valueOf(metodo.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Método de pagamento inválido: " + metodo + 
                ". Métodos válidos: PIX, CARTAO_CREDITO, CARTAO_DEBITO, DINHEIRO");
        }
    }
    
    /**
     * Retorna o código do método para integração com Mercado Pago.
     * 
     * @return Código do método para o Mercado Pago
     */
    public String getCodigoMercadoPago() {
        switch (this) {
            case PIX:
                return "pix";
            case CARTAO_CREDITO:
                return "credit_card";
            case CARTAO_DEBITO:
                return "debit_card";
            default:
                return null;
        }
    }
}

