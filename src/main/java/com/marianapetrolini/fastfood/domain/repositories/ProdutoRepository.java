package com.marianapetrolini.fastfood.domain.repositories;

import com.marianapetrolini.fastfood.domain.entities.Produto;
import com.marianapetrolini.fastfood.domain.valueobjects.CategoriaProduto;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída para persistência de produtos.
 * Define as operações de repositório para a entidade Produto.
 */
public interface ProdutoRepository {
    
    /**
     * Salva um produto no repositório.
     * 
     * @param produto Produto a ser salvo
     * @return Produto salvo com ID gerado
     */
    Produto salvar(Produto produto);
    
    /**
     * Busca um produto por ID.
     * 
     * @param id ID do produto
     * @return Optional contendo o produto se encontrado
     */
    Optional<Produto> buscarPorId(Long id);
    
    /**
     * Busca todos os produtos.
     * 
     * @return Lista de todos os produtos
     */
    List<Produto> buscarTodos();
    
    /**
     * Busca produtos por categoria.
     * 
     * @param categoria Categoria dos produtos
     * @return Lista de produtos da categoria
     */
    List<Produto> buscarPorCategoria(CategoriaProduto categoria);
    
    /**
     * Busca produtos disponíveis.
     * 
     * @return Lista de produtos disponíveis
     */
    List<Produto> buscarDisponiveis();
    
    /**
     * Busca produtos disponíveis por categoria.
     * 
     * @param categoria Categoria dos produtos
     * @return Lista de produtos disponíveis da categoria
     */
    List<Produto> buscarDisponiveisPorCategoria(CategoriaProduto categoria);
    
    /**
     * Busca produtos por nome (busca parcial, case-insensitive).
     * 
     * @param nome Nome ou parte do nome do produto
     * @return Lista de produtos que contêm o nome
     */
    List<Produto> buscarPorNome(String nome);
    
    /**
     * Verifica se existe um produto com o nome especificado.
     * 
     * @param nome Nome do produto
     * @return true se existe produto com o nome
     */
    boolean existePorNome(String nome);
    
    /**
     * Verifica se existe um produto com o nome especificado, excluindo um ID.
     * Útil para validações de atualização.
     * 
     * @param nome Nome do produto
     * @param idExcluir ID a ser excluído da verificação
     * @return true se existe outro produto com o nome
     */
    boolean existePorNomeExcluindoId(String nome, Long idExcluir);
    
    /**
     * Remove um produto do repositório.
     * 
     * @param id ID do produto a ser removido
     * @return true se o produto foi removido, false se não foi encontrado
     */
    boolean remover(Long id);
    
    /**
     * Conta o total de produtos.
     * 
     * @return Número total de produtos
     */
    long contar();
    
    /**
     * Conta produtos por categoria.
     * 
     * @param categoria Categoria dos produtos
     * @return Número de produtos da categoria
     */
    long contarPorCategoria(CategoriaProduto categoria);
    
    /**
     * Conta produtos disponíveis.
     * 
     * @return Número de produtos disponíveis
     */
    long contarDisponiveis();
}

