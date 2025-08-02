package com.marianapetrolini.fastfood.domain.repositories;

import com.marianapetrolini.fastfood.domain.entities.Cliente;
import com.marianapetrolini.fastfood.domain.entities.Pedido;
import com.marianapetrolini.fastfood.domain.valueobjects.StatusPedido;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Port de saída para persistência de pedidos.
 * Define as operações de repositório para a entidade Pedido.
 */
public interface PedidoRepository {
    
    /**
     * Salva um pedido no repositório.
     * 
     * @param pedido Pedido a ser salvo
     * @return Pedido salvo com ID gerado
     */
    Pedido salvar(Pedido pedido);
    
    /**
     * Busca um pedido por ID.
     * 
     * @param id ID do pedido
     * @return Optional contendo o pedido se encontrado
     */
    Optional<Pedido> buscarPorId(Long id);
    
    /**
     * Busca todos os pedidos.
     * 
     * @return Lista de todos os pedidos
     */
    List<Pedido> buscarTodos();
    
    /**
     * Busca pedidos por status.
     * 
     * @param status Status dos pedidos
     * @return Lista de pedidos com o status
     */
    List<Pedido> buscarPorStatus(StatusPedido status);
    
    /**
     * Busca pedidos por cliente.
     * 
     * @param cliente Cliente dos pedidos
     * @return Lista de pedidos do cliente
     */
    List<Pedido> buscarPorCliente(Cliente cliente);
    
    /**
     * Busca pedidos por cliente ID.
     * 
     * @param clienteId ID do cliente
     * @return Lista de pedidos do cliente
     */
    List<Pedido> buscarPorClienteId(Long clienteId);
    
    /**
     * Busca pedidos visíveis na cozinha ordenados por prioridade e data.
     * Regras de ordenação:
     * 1. Pronto > Em Preparação > Recebido
     * 2. Pedidos mais antigos primeiro
     * 3. Pedidos finalizados não aparecem
     * 
     * @return Lista de pedidos ordenados para a cozinha
     */
    List<Pedido> buscarParaCozinha();
    
    /**
     * Busca pedidos criados em um período.
     * 
     * @param inicio Data/hora de início
     * @param fim Data/hora de fim
     * @return Lista de pedidos no período
     */
    List<Pedido> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim);
    
    /**
     * Busca pedidos criados hoje.
     * 
     * @return Lista de pedidos de hoje
     */
    List<Pedido> buscarDeHoje();
    
    /**
     * Busca pedidos ativos (não finalizados).
     * 
     * @return Lista de pedidos ativos
     */
    List<Pedido> buscarAtivos();
    
    /**
     * Busca pedidos finalizados.
     * 
     * @return Lista de pedidos finalizados
     */
    List<Pedido> buscarFinalizados();
    
    /**
     * Busca pedidos em preparação.
     * 
     * @return Lista de pedidos em preparação
     */
    List<Pedido> buscarEmPreparacao();
    
    /**
     * Busca pedidos prontos.
     * 
     * @return Lista de pedidos prontos
     */
    List<Pedido> buscarProntos();
    
    /**
     * Busca pedidos recebidos (aguardando pagamento).
     * 
     * @return Lista de pedidos recebidos
     */
    List<Pedido> buscarRecebidos();
    
    /**
     * Remove um pedido do repositório.
     * 
     * @param id ID do pedido a ser removido
     * @return true se o pedido foi removido, false se não foi encontrado
     */
    boolean remover(Long id);
    
    /**
     * Conta o total de pedidos.
     * 
     * @return Número total de pedidos
     */
    long contar();
    
    /**
     * Conta pedidos por status.
     * 
     * @param status Status dos pedidos
     * @return Número de pedidos com o status
     */
    long contarPorStatus(StatusPedido status);
    
    /**
     * Conta pedidos por cliente.
     * 
     * @param cliente Cliente dos pedidos
     * @return Número de pedidos do cliente
     */
    long contarPorCliente(Cliente cliente);
    
    /**
     * Conta pedidos criados hoje.
     * 
     * @return Número de pedidos de hoje
     */
    long contarDeHoje();
    
    /**
     * Conta pedidos ativos.
     * 
     * @return Número de pedidos ativos
     */
    long contarAtivos();
    
    /**
     * Conta pedidos na cozinha.
     * 
     * @return Número de pedidos visíveis na cozinha
     */
    long contarNaCozinha();
}

