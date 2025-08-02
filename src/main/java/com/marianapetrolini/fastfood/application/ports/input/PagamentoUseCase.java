package com.marianapetrolini.fastfood.application.ports.input;

import com.marianapetrolini.fastfood.application.dtos.pagamento.StatusPagamentoResponse;
import com.marianapetrolini.fastfood.application.dtos.pagamento.WebhookPagamentoRequest;
import com.marianapetrolini.fastfood.application.dtos.pagamento.PagamentoResponse;

import java.util.List;

/**
 * Port de entrada para use cases relacionados a pagamentos.
 * Define as operações disponíveis para gerenciamento de pagamentos.
 */
public interface PagamentoUseCase {
    
    /**
     * Consulta o status de pagamento de um pedido.
     * 
     * @param pedidoId ID do pedido
     * @return Status do pagamento
     */
    StatusPagamentoResponse consultarStatusPagamento(Long pedidoId);
    
    /**
     * Processa webhook de confirmação de pagamento.
     * 
     * @param request Dados do webhook
     */
    void processarWebhookPagamento(WebhookPagamentoRequest request);
    
    /**
     * Busca um pagamento por ID.
     * 
     * @param id ID do pagamento
     * @return Pagamento encontrado
     */
    PagamentoResponse buscarPagamentoPorId(Long id);
    
    /**
     * Busca pagamento por ID do pedido.
     * 
     * @param pedidoId ID do pedido
     * @return Pagamento do pedido
     */
    PagamentoResponse buscarPagamentoPorPedidoId(Long pedidoId);
    
    /**
     * Busca pagamento por ID de transação.
     * 
     * @param transacaoId ID da transação
     * @return Pagamento da transação
     */
    PagamentoResponse buscarPagamentoPorTransacaoId(String transacaoId);
    
    /**
     * Lista todos os pagamentos.
     * 
     * @return Lista de pagamentos
     */
    List<PagamentoResponse> listarTodosPagamentos();
    
    /**
     * Lista pagamentos por status.
     * 
     * @param status Status dos pagamentos
     * @return Lista de pagamentos com o status
     */
    List<PagamentoResponse> listarPagamentosPorStatus(String status);
    
    /**
     * Lista pagamentos por método.
     * 
     * @param metodo Método de pagamento
     * @return Lista de pagamentos com o método
     */
    List<PagamentoResponse> listarPagamentosPorMetodo(String metodo);
    
    /**
     * Lista pagamentos aprovados.
     * 
     * @return Lista de pagamentos aprovados
     */
    List<PagamentoResponse> listarPagamentosAprovados();
    
    /**
     * Lista pagamentos pendentes.
     * 
     * @return Lista de pagamentos pendentes
     */
    List<PagamentoResponse> listarPagamentosPendentes();
    
    /**
     * Lista pagamentos de hoje.
     * 
     * @return Lista de pagamentos de hoje
     */
    List<PagamentoResponse> listarPagamentosDeHoje();
    
    /**
     * Aprova um pagamento manualmente.
     * 
     * @param id ID do pagamento
     * @param transacaoId ID da transação
     * @return Pagamento aprovado
     */
    PagamentoResponse aprovarPagamento(Long id, String transacaoId);
    
    /**
     * Recusa um pagamento manualmente.
     * 
     * @param id ID do pagamento
     * @param motivo Motivo da recusa
     * @return Pagamento recusado
     */
    PagamentoResponse recusarPagamento(Long id, String motivo);
    
    /**
     * Cancela um pagamento.
     * 
     * @param id ID do pagamento
     * @param motivo Motivo do cancelamento
     * @return Pagamento cancelado
     */
    PagamentoResponse cancelarPagamento(Long id, String motivo);
    
    /**
     * Estorna um pagamento.
     * 
     * @param id ID do pagamento
     * @param motivo Motivo do estorno
     * @return Pagamento estornado
     */
    PagamentoResponse estornarPagamento(Long id, String motivo);
}

