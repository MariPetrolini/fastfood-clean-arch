package com.marianapetrolini.fastfood.application.ports.input;

import com.marianapetrolini.fastfood.application.dtos.pedido.CheckoutRequest;
import com.marianapetrolini.fastfood.application.dtos.pedido.CheckoutResponse;
import com.marianapetrolini.fastfood.application.dtos.pedido.PedidoResponse;
import com.marianapetrolini.fastfood.application.dtos.pedido.PedidoListaResponse;

import java.util.List;

/**
 * Port de entrada para use cases relacionados a pedidos.
 * Define as operações disponíveis para gerenciamento de pedidos.
 */
public interface PedidoUseCase {
    
    /**
     * Realiza o checkout de um pedido.
     * 
     * @param request Dados do checkout
     * @return Resposta do checkout com ID do pedido
     */
    CheckoutResponse realizarCheckout(CheckoutRequest request);
    
    /**
     * Busca um pedido por ID.
     * 
     * @param id ID do pedido
     * @return Pedido encontrado
     */
    PedidoResponse buscarPedidoPorId(Long id);
    
    /**
     * Lista todos os pedidos.
     * 
     * @return Lista de pedidos
     */
    List<PedidoResponse> listarTodosPedidos();
    
    /**
     * Lista pedidos para a cozinha ordenados por prioridade.
     * Regras de ordenação:
     * 1. Pronto > Em Preparação > Recebido
     * 2. Pedidos mais antigos primeiro
     * 3. Pedidos finalizados não aparecem
     * 
     * @return Lista de pedidos para a cozinha
     */
    List<PedidoListaResponse> listarPedidosParaCozinha();
    
    /**
     * Lista pedidos por status.
     * 
     * @param status Status dos pedidos
     * @return Lista de pedidos com o status
     */
    List<PedidoResponse> listarPedidosPorStatus(String status);
    
    /**
     * Lista pedidos de um cliente.
     * 
     * @param clienteId ID do cliente
     * @return Lista de pedidos do cliente
     */
    List<PedidoResponse> listarPedidosDoCliente(Long clienteId);
    
    /**
     * Lista pedidos ativos (não finalizados).
     * 
     * @return Lista de pedidos ativos
     */
    List<PedidoResponse> listarPedidosAtivos();
    
    /**
     * Lista pedidos de hoje.
     * 
     * @return Lista de pedidos de hoje
     */
    List<PedidoResponse> listarPedidosDeHoje();
    
    /**
     * Inicia a preparação de um pedido.
     * 
     * @param id ID do pedido
     * @return Pedido atualizado
     */
    PedidoResponse iniciarPreparacaoPedido(Long id);
    
    /**
     * Marca um pedido como pronto.
     * 
     * @param id ID do pedido
     * @return Pedido atualizado
     */
    PedidoResponse marcarPedidoComoPronto(Long id);
    
    /**
     * Finaliza um pedido.
     * 
     * @param id ID do pedido
     * @return Pedido atualizado
     */
    PedidoResponse finalizarPedido(Long id);
    
    /**
     * Atualiza o status de um pedido.
     * 
     * @param id ID do pedido
     * @param novoStatus Novo status
     * @return Pedido atualizado
     */
    PedidoResponse atualizarStatusPedido(Long id, String novoStatus);
}

