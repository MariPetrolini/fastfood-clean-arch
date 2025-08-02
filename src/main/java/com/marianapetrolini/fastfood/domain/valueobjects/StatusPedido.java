package com.marianapetrolini.fastfood.domain.valueobjects;

import com.marianapetrolini.fastfood.domain.exceptions.DomainException;

import java.util.Arrays;
import java.util.List;

/**
 * Enum que representa os possíveis status de um pedido.
 * Define as transições válidas conforme regras de negócio.
 */
public enum StatusPedido {
    
    RECEBIDO("Recebido", "Pedido foi recebido e aguarda pagamento"),
    EM_PREPARACAO("Em Preparação", "Pagamento aprovado, pedido em preparação"),
    PRONTO("Pronto", "Pedido finalizado e pronto para retirada"),
    FINALIZADO("Finalizado", "Pedido foi entregue ao cliente");
    
    private final String nome;
    private final String descricao;
    
    StatusPedido(String nome, String descricao) {
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
     * Verifica se é possível transicionar do status atual para o novo status.
     * 
     * @param novoStatus Status de destino
     * @return true se a transição for válida, false caso contrário
     */
    public boolean podeTransicionarPara(StatusPedido novoStatus) {
        if (novoStatus == null) {
            return false;
        }
        
        switch (this) {
            case RECEBIDO:
                return novoStatus == EM_PREPARACAO;
            case EM_PREPARACAO:
                return novoStatus == PRONTO;
            case PRONTO:
                return novoStatus == FINALIZADO;
            case FINALIZADO:
                return false; // Status final, não permite transições
            default:
                return false;
        }
    }
    
    /**
     * Valida uma transição de status e lança exceção se inválida.
     * 
     * @param novoStatus Status de destino
     * @throws DomainException se a transição for inválida
     */
    public void validarTransicao(StatusPedido novoStatus) {
        if (!podeTransicionarPara(novoStatus)) {
            throw new DomainException(
                String.format("Transição inválida de %s para %s", this.nome, 
                    novoStatus != null ? novoStatus.nome : "null")
            );
        }
    }
    
    /**
     * Retorna os próximos status possíveis a partir do status atual.
     * 
     * @return Lista de status possíveis
     */
    public List<StatusPedido> getProximosStatusPossiveis() {
        switch (this) {
            case RECEBIDO:
                return Arrays.asList(EM_PREPARACAO);
            case EM_PREPARACAO:
                return Arrays.asList(PRONTO);
            case PRONTO:
                return Arrays.asList(FINALIZADO);
            case FINALIZADO:
                return Arrays.asList(); // Nenhuma transição possível
            default:
                return Arrays.asList();
        }
    }
    
    /**
     * Verifica se o status é visível na lista da cozinha.
     * Pedidos finalizados não devem aparecer na lista da cozinha.
     * 
     * @return true se deve aparecer na lista da cozinha
     */
    public boolean isVisivelNaCozinha() {
        return this != FINALIZADO;
    }
    
    /**
     * Retorna a prioridade do status para ordenação na cozinha.
     * Menor número = maior prioridade.
     * 
     * @return Prioridade numérica
     */
    public int getPrioridadeCozinha() {
        switch (this) {
            case PRONTO:
                return 1; // Maior prioridade
            case EM_PREPARACAO:
                return 2;
            case RECEBIDO:
                return 3; // Menor prioridade
            case FINALIZADO:
                return 4; // Não deve aparecer na cozinha
            default:
                return 999;
        }
    }
    
    /**
     * Converte uma string para StatusPedido de forma case-insensitive.
     * 
     * @param status String representando o status
     * @return StatusPedido correspondente
     * @throws IllegalArgumentException se o status não for válido
     */
    public static StatusPedido fromString(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status não pode ser nulo ou vazio");
        }
        
        try {
            return StatusPedido.valueOf(status.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Status inválido: " + status + 
                ". Status válidos: RECEBIDO, EM_PREPARACAO, PRONTO, FINALIZADO");
        }
    }
}

