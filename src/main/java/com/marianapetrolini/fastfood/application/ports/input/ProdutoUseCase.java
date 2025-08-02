package com.marianapetrolini.fastfood.application.ports.input;

import com.marianapetrolini.fastfood.application.dtos.produto.CriarProdutoRequest;
import com.marianapetrolini.fastfood.application.dtos.produto.AtualizarProdutoRequest;
import com.marianapetrolini.fastfood.application.dtos.produto.ProdutoResponse;
import com.marianapetrolini.fastfood.domain.valueobjects.CategoriaProduto;

import java.util.List;

/**
 * Port de entrada para use cases relacionados a produtos.
 * Define as operações disponíveis para gerenciamento de produtos.
 */
public interface ProdutoUseCase {
    
    /**
     * Cria um novo produto.
     * 
     * @param request Dados do produto a ser criado
     * @return Produto criado
     */
    ProdutoResponse criarProduto(CriarProdutoRequest request);
    
    /**
     * Busca um produto por ID.
     * 
     * @param id ID do produto
     * @return Produto encontrado
     */
    ProdutoResponse buscarProdutoPorId(Long id);
    
    /**
     * Lista todos os produtos.
     * 
     * @return Lista de produtos
     */
    List<ProdutoResponse> listarTodosProdutos();
    
    /**
     * Lista produtos por categoria.
     * 
     * @param categoria Categoria dos produtos
     * @return Lista de produtos da categoria
     */
    List<ProdutoResponse> listarProdutosPorCategoria(CategoriaProduto categoria);
    
    /**
     * Lista produtos disponíveis.
     * 
     * @return Lista de produtos disponíveis
     */
    List<ProdutoResponse> listarProdutosDisponiveis();
    
    /**
     * Lista produtos disponíveis por categoria.
     * 
     * @param categoria Categoria dos produtos
     * @return Lista de produtos disponíveis da categoria
     */
    List<ProdutoResponse> listarProdutosDisponiveisPorCategoria(CategoriaProduto categoria);
    
    /**
     * Busca produtos por nome.
     * 
     * @param nome Nome ou parte do nome do produto
     * @return Lista de produtos que contêm o nome
     */
    List<ProdutoResponse> buscarProdutosPorNome(String nome);
    
    /**
     * Atualiza um produto existente.
     * 
     * @param id ID do produto a ser atualizado
     * @param request Novos dados do produto
     * @return Produto atualizado
     */
    ProdutoResponse atualizarProduto(Long id, AtualizarProdutoRequest request);
    
    /**
     * Ativa um produto (torna disponível).
     * 
     * @param id ID do produto
     * @return Produto ativado
     */
    ProdutoResponse ativarProduto(Long id);
    
    /**
     * Desativa um produto (torna indisponível).
     * 
     * @param id ID do produto
     * @return Produto desativado
     */
    ProdutoResponse desativarProduto(Long id);
    
    /**
     * Remove um produto.
     * 
     * @param id ID do produto a ser removido
     */
    void removerProduto(Long id);
    
    /**
     * Lista todas as categorias de produtos disponíveis.
     * 
     * @return Lista de categorias
     */
    List<String> listarCategorias();
}

