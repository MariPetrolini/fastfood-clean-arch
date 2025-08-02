package com.marianapetrolini.fastfood.domain.repositories;

import com.marianapetrolini.fastfood.domain.entities.Cliente;

import java.util.List;
import java.util.Optional;

/**
 * Interface do repositório de clientes.
 * Define as operações de persistência para a entidade Cliente.
 */
public interface ClienteRepository {
    
    /**
     * Salva um cliente no repositório.
     * 
     * @param cliente Cliente a ser salvo
     * @return Cliente salvo com ID gerado
     */
    Cliente salvar(Cliente cliente);
    
    /**
     * Busca um cliente por ID.
     * 
     * @param id ID do cliente
     * @return Optional contendo o cliente se encontrado
     */
    Optional<Cliente> buscarPorId(Long id);
    
    /**
     * Busca um cliente por CPF.
     * 
     * @param cpf CPF do cliente
     * @return Optional contendo o cliente se encontrado
     */
    Optional<Cliente> buscarPorCpf(String cpf);
    
    /**
     * Busca um cliente por email.
     * 
     * @param email Email do cliente
     * @return Optional contendo o cliente se encontrado
     */
    Optional<Cliente> buscarPorEmail(String email);
    
    /**
     * Busca clientes por nome (busca parcial).
     * 
     * @param nome Nome ou parte do nome do cliente
     * @return Lista de clientes encontrados
     */
    List<Cliente> buscarPorNome(String nome);
    
    /**
     * Busca todos os clientes.
     * 
     * @return Lista de todos os clientes
     */
    List<Cliente> buscarTodos();
    
    /**
     * Remove um cliente do repositório.
     * 
     * @param id ID do cliente a ser removido
     */
    void remover(Long id);
    
    /**
     * Verifica se existe um cliente com o CPF informado.
     * 
     * @param cpf CPF a ser verificado
     * @return true se existe cliente com o CPF
     */
    boolean existePorCpf(String cpf);
    
    /**
     * Verifica se existe um cliente com o email informado.
     * 
     * @param email Email a ser verificado
     * @return true se existe cliente com o email
     */
    boolean existePorEmail(String email);
}

