package com.marianapetrolini.fastfood.application.ports.input;

import com.marianapetrolini.fastfood.application.dtos.categoria.CategoriaResponse;
import com.marianapetrolini.fastfood.application.dtos.categoria.EstatisticasCategoriaResponse;

import java.util.List;

/**
 * Port de entrada para use cases relacionados a categorias.
 * Define as operações disponíveis para gerenciamento de categorias.
 */
public interface CategoriaUseCase {
    
    /**
     * Lista todas as categorias disponíveis.
     * 
     * @return Lista de categorias
     */
    List<CategoriaResponse> listarTodasCategorias();
    
    /**
     * Lista categorias que possuem produtos.
     * 
     * @return Lista de categorias com produtos
     */
    List<CategoriaResponse> listarCategoriasComProdutos();
    
    /**
     * Lista categorias que possuem produtos disponíveis.
     * 
     * @return Lista de categorias com produtos disponíveis
     */
    List<CategoriaResponse> listarCategoriasComProdutosDisponiveis();
    
    /**
     * Busca uma categoria por nome.
     * 
     * @param nome Nome da categoria
     * @return Categoria encontrada
     */
    CategoriaResponse buscarCategoriaPorNome(String nome);
    
    /**
     * Obtém estatísticas de uma categoria específica.
     * 
     * @param nome Nome da categoria
     * @return Estatísticas da categoria
     */
    EstatisticasCategoriaResponse obterEstatisticasCategoria(String nome);
    
    /**
     * Obtém estatísticas de todas as categorias.
     * 
     * @return Lista de estatísticas por categoria
     */
    List<EstatisticasCategoriaResponse> obterEstatisticasTodasCategorias();
    
    /**
     * Verifica se uma categoria existe.
     * 
     * @param nome Nome da categoria
     * @return true se a categoria existe
     */
    boolean categoriaExiste(String nome);
    
    /**
     * Lista categorias ordenadas por popularidade (mais produtos vendidos).
     * 
     * @return Lista de categorias ordenadas por popularidade
     */
    List<CategoriaResponse> listarCategoriasPorPopularidade();
}

