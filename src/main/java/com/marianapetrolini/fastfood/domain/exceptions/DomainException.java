package com.marianapetrolini.fastfood.domain.exceptions;

/**
 * Exceção base para violações de regras de domínio.
 * Representa erros que ocorrem quando regras de negócio são violadas.
 */
public class DomainException extends RuntimeException {
    
    public DomainException(String message) {
        super(message);
    }
    
    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * Cria uma exceção de domínio para entidade não encontrada.
     * 
     * @param entidade Nome da entidade
     * @param id Identificador da entidade
     * @return DomainException configurada
     */
    public static DomainException entidadeNaoEncontrada(String entidade, Object id) {
        return new DomainException(String.format("%s com ID %s não encontrado(a)", entidade, id));
    }
    
    /**
     * Cria uma exceção de domínio para violação de regra de negócio.
     * 
     * @param regra Descrição da regra violada
     * @return DomainException configurada
     */
    public static DomainException regraViolada(String regra) {
        return new DomainException("Regra de negócio violada: " + regra);
    }
    
    /**
     * Cria uma exceção de domínio para estado inválido.
     * 
     * @param estado Descrição do estado inválido
     * @return DomainException configurada
     */
    public static DomainException estadoInvalido(String estado) {
        return new DomainException("Estado inválido: " + estado);
    }
    
    /**
     * Cria uma exceção de domínio para operação não permitida.
     * 
     * @param operacao Descrição da operação
     * @param motivo Motivo da não permissão
     * @return DomainException configurada
     */
    public static DomainException operacaoNaoPermitida(String operacao, String motivo) {
        return new DomainException(String.format("Operação '%s' não permitida: %s", operacao, motivo));
    }
}

