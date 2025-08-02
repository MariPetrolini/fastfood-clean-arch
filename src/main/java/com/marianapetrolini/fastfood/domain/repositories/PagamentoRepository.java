package com.marianapetrolini.fastfood.domain.repositories;

import com.marianapetrolini.fastfood.domain.entities.Pagamento;
import com.marianapetrolini.fastfood.domain.entities.Pedido;
import com.marianapetrolini.fastfood.domain.valueobjects.MetodoPagamento;
import com.marianapetrolini.fastfood.domain.valueobjects.StatusPagamento;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Port de saída para persistência de pagamentos.
 * Define as operações de repositório para a entidade Pagamento.
 */
public interface PagamentoRepository {
    
    /**
     * Salva um pagamento no repositório.
     * 
     * @param pagamento Pagamento a ser salvo
     * @return Pagamento salvo com ID gerado
     */
    Pagamento salvar(Pagamento pagamento);
    
    /**
     * Busca um pagamento por ID.
     * 
     * @param id ID do pagamento
     * @return Optional contendo o pagamento se encontrado
     */
    Optional<Pagamento> buscarPorId(Long id);
    
    /**
     * Busca um pagamento por pedido.
     * 
     * @param pedido Pedido do pagamento
     * @return Optional contendo o pagamento se encontrado
     */
    Optional<Pagamento> buscarPorPedido(Pedido pedido);
    
    /**
     * Busca um pagamento por pedido ID.
     * 
     * @param pedidoId ID do pedido
     * @return Optional contendo o pagamento se encontrado
     */
    Optional<Pagamento> buscarPorPedidoId(Long pedidoId);
    
    /**
     * Busca um pagamento por ID de transação.
     * 
     * @param transacaoId ID da transação
     * @return Optional contendo o pagamento se encontrado
     */
    Optional<Pagamento> buscarPorTransacaoId(String transacaoId);
    
    /**
     * Busca todos os pagamentos.
     * 
     * @return Lista de todos os pagamentos
     */
    List<Pagamento> buscarTodos();
    
    /**
     * Busca pagamentos por status.
     * 
     * @param status Status dos pagamentos
     * @return Lista de pagamentos com o status
     */
    List<Pagamento> buscarPorStatus(StatusPagamento status);
    
    /**
     * Busca pagamentos por método.
     * 
     * @param metodo Método de pagamento
     * @return Lista de pagamentos com o método
     */
    List<Pagamento> buscarPorMetodo(MetodoPagamento metodo);
    
    /**
     * Busca pagamentos aprovados.
     * 
     * @return Lista de pagamentos aprovados
     */
    List<Pagamento> buscarAprovados();
    
    /**
     * Busca pagamentos negados (recusados ou cancelados).
     * 
     * @return Lista de pagamentos negados
     */
    List<Pagamento> buscarNegados();
    
    /**
     * Busca pagamentos pendentes.
     * 
     * @return Lista de pagamentos pendentes
     */
    List<Pagamento> buscarPendentes();
    
    /**
     * Busca pagamentos processados em um período.
     * 
     * @param inicio Data/hora de início
     * @param fim Data/hora de fim
     * @return Lista de pagamentos processados no período
     */
    List<Pagamento> buscarProcessadosNoPeriodo(LocalDateTime inicio, LocalDateTime fim);
    
    /**
     * Busca pagamentos criados hoje.
     * 
     * @return Lista de pagamentos de hoje
     */
    List<Pagamento> buscarDeHoje();
    
    /**
     * Busca pagamentos que precisam ser verificados (pendentes há muito tempo).
     * 
     * @param minutosLimite Limite em minutos para considerar pendente há muito tempo
     * @return Lista de pagamentos para verificação
     */
    List<Pagamento> buscarParaVerificacao(int minutosLimite);
    
    /**
     * Remove um pagamento do repositório.
     * 
     * @param id ID do pagamento a ser removido
     * @return true se o pagamento foi removido, false se não foi encontrado
     */
    boolean remover(Long id);
    
    /**
     * Conta o total de pagamentos.
     * 
     * @return Número total de pagamentos
     */
    long contar();
    
    /**
     * Conta pagamentos por status.
     * 
     * @param status Status dos pagamentos
     * @return Número de pagamentos com o status
     */
    long contarPorStatus(StatusPagamento status);
    
    /**
     * Conta pagamentos por método.
     * 
     * @param metodo Método de pagamento
     * @return Número de pagamentos com o método
     */
    long contarPorMetodo(MetodoPagamento metodo);
    
    /**
     * Conta pagamentos aprovados.
     * 
     * @return Número de pagamentos aprovados
     */
    long contarAprovados();
    
    /**
     * Conta pagamentos negados.
     * 
     * @return Número de pagamentos negados
     */
    long contarNegados();
    
    /**
     * Conta pagamentos pendentes.
     * 
     * @return Número de pagamentos pendentes
     */
    long contarPendentes();
    
    /**
     * Conta pagamentos criados hoje.
     * 
     * @return Número de pagamentos de hoje
     */
    long contarDeHoje();
}

