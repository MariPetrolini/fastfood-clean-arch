package com.marianapetrolini.fastfood.infrastructure.adapters.persistence.jpa;

import com.marianapetrolini.fastfood.domain.valueobjects.CategoriaProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório JPA para produtos.
 * Interface Spring Data JPA para operações de persistência.
 */
@Repository
public interface ProdutoJpaRepository extends JpaRepository<ProdutoJpaEntity, Long> {
    
    /**
     * Busca produtos por categoria.
     * 
     * @param categoria Categoria dos produtos
     * @return Lista de produtos da categoria
     */
    List<ProdutoJpaEntity> findByCategoria(CategoriaProduto categoria);
    
    /**
     * Busca produtos disponíveis.
     * 
     * @return Lista de produtos disponíveis
     */
    List<ProdutoJpaEntity> findByDisponivelTrue();
    
    /**
     * Busca produtos disponíveis por categoria.
     * 
     * @param categoria Categoria dos produtos
     * @return Lista de produtos disponíveis da categoria
     */
    List<ProdutoJpaEntity> findByCategoriaAndDisponivelTrue(CategoriaProduto categoria);
    
    /**
     * Busca produtos por nome (busca parcial, case-insensitive).
     * 
     * @param nome Nome ou parte do nome do produto
     * @return Lista de produtos que contêm o nome
     */
    @Query("SELECT p FROM ProdutoJpaEntity p WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<ProdutoJpaEntity> findByNomeContainingIgnoreCase(@Param("nome") String nome);
    
    /**
     * Verifica se existe um produto com o nome especificado.
     * 
     * @param nome Nome do produto
     * @return true se existe produto com o nome
     */
    boolean existsByNome(String nome);
    
    /**
     * Verifica se existe um produto com o nome especificado, excluindo um ID.
     * 
     * @param nome Nome do produto
     * @param id ID a ser excluído da verificação
     * @return true se existe outro produto com o nome
     */
    boolean existsByNomeAndIdNot(String nome, Long id);
    
    /**
     * Conta produtos por categoria.
     * 
     * @param categoria Categoria dos produtos
     * @return Número de produtos da categoria
     */
    long countByCategoria(CategoriaProduto categoria);
    
    /**
     * Conta produtos disponíveis.
     * 
     * @return Número de produtos disponíveis
     */
    long countByDisponivelTrue();
    
    /**
     * Busca produtos ordenados por categoria e nome.
     * 
     * @return Lista de produtos ordenados
     */
    List<ProdutoJpaEntity> findAllByOrderByCategoriaAscNomeAsc();
    
    /**
     * Busca produtos disponíveis ordenados por categoria e nome.
     * 
     * @return Lista de produtos disponíveis ordenados
     */
    List<ProdutoJpaEntity> findByDisponivelTrueOrderByCategoriaAscNomeAsc();
}

