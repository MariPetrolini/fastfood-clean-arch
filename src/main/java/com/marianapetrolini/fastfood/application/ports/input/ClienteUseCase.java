package com.marianapetrolini.fastfood.application.ports.input;

import com.marianapetrolini.fastfood.application.dtos.cliente.CriarClienteRequest;
import com.marianapetrolini.fastfood.application.dtos.cliente.AtualizarClienteRequest;
import com.marianapetrolini.fastfood.application.dtos.cliente.ClienteResponse;

import java.util.List;
import java.util.Map;

/**
 * Port de entrada para use cases relacionados a clientes.
 * Define as operações disponíveis para gerenciamento de clientes.
 */
public interface ClienteUseCase {
    
    /**
     * Cria um novo cliente no sistema.
     * 
     * @param request Dados do cliente a ser criado
     * @return Cliente criado
     */
    ClienteResponse criarCliente(CriarClienteRequest request);
    
    /**
     * Busca um cliente por ID.
     * 
     * @param id ID do cliente
     * @return Cliente encontrado
     */
    ClienteResponse buscarClientePorId(Long id);
    
    /**
     * Busca um cliente por CPF.
     * 
     * @param cpf CPF do cliente
     * @return Cliente encontrado
     */
    ClienteResponse buscarClientePorCpf(String cpf);
    
    /**
     * Busca um cliente por email.
     * 
     * @param email Email do cliente
     * @return Cliente encontrado
     */
    ClienteResponse buscarClientePorEmail(String email);
    
    /**
     * Lista todos os clientes cadastrados.
     * 
     * @return Lista de clientes
     */
    List<ClienteResponse> listarTodosClientes();
    
    /**
     * Lista clientes ativos (que fizeram pedidos).
     * 
     * @return Lista de clientes ativos
     */
    List<ClienteResponse> listarClientesAtivos();
    
    /**
     * Busca clientes por nome (busca parcial).
     * 
     * @param nome Nome ou parte do nome
     * @return Lista de clientes encontrados
     */
    List<ClienteResponse> buscarClientesPorNome(String nome);
    
    /**
     * Atualiza os dados de um cliente.
     * 
     * @param id ID do cliente
     * @param request Novos dados do cliente
     * @return Cliente atualizado
     */
    ClienteResponse atualizarCliente(Long id, AtualizarClienteRequest request);
    
    /**
     * Ativa um cliente (permite receber campanhas).
     * 
     * @param id ID do cliente
     * @return Cliente ativado
     */
    ClienteResponse ativarCliente(Long id);
    
    /**
     * Desativa um cliente (não recebe campanhas).
     * 
     * @param id ID do cliente
     * @return Cliente desativado
     */
    ClienteResponse desativarCliente(Long id);
    
    /**
     * Remove um cliente do sistema.
     * 
     * @param id ID do cliente
     */
    void removerCliente(Long id);
    
    /**
     * Conta o total de clientes cadastrados.
     * 
     * @return Número total de clientes
     */
    long contarClientes();
    
    /**
     * Conta clientes ativos (que fizeram pedidos).
     * 
     * @return Número de clientes ativos
     */
    long contarClientesAtivos();
    
    /**
     * Lista clientes elegíveis para campanhas promocionais.
     * 
     * @return Lista de clientes para campanhas
     */
    List<ClienteResponse> listarClientesParaCampanhas();
    
    /**
     * Obtém estatísticas gerais dos clientes.
     * 
     * @return Mapa com estatísticas dos clientes
     */
    Map<String, Object> obterEstatisticasClientes();
}

