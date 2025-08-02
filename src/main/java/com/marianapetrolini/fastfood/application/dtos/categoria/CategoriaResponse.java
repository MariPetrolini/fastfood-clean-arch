package com.marianapetrolini.fastfood.application.dtos.categoria;

import com.marianapetrolini.fastfood.domain.valueobjects.CategoriaProduto;

/**
 * DTO para resposta de categoria.
 */
public class CategoriaResponse {
    
    private String nome;
    private String descricao;
    private int totalProdutos;
    private int produtosDisponiveis;
    private int produtosIndisponiveis;
    private boolean temProdutos;
    private boolean temProdutosDisponiveis;
    
    public CategoriaResponse() {
    }
    
    public CategoriaResponse(String nome, String descricao, int totalProdutos, 
                           int produtosDisponiveis, int produtosIndisponiveis) {
        this.nome = nome;
        this.descricao = descricao;
        this.totalProdutos = totalProdutos;
        this.produtosDisponiveis = produtosDisponiveis;
        this.produtosIndisponiveis = produtosIndisponiveis;
        this.temProdutos = totalProdutos > 0;
        this.temProdutosDisponiveis = produtosDisponiveis > 0;
    }
    
    /**
     * Cria um CategoriaResponse a partir de uma categoria e estatísticas.
     * 
     * @param categoria Categoria do produto
     * @param totalProdutos Total de produtos na categoria
     * @param produtosDisponiveis Produtos disponíveis na categoria
     * @return DTO de resposta
     */
    public static CategoriaResponse fromCategoria(CategoriaProduto categoria, 
                                                 int totalProdutos, int produtosDisponiveis) {
        return new CategoriaResponse(
            categoria.name(),
            categoria.getDescricao(),
            totalProdutos,
            produtosDisponiveis,
            totalProdutos - produtosDisponiveis
        );
    }
    
    /**
     * Cria um CategoriaResponse básico apenas com informações da categoria.
     * 
     * @param categoria Categoria do produto
     * @return DTO de resposta básico
     */
    public static CategoriaResponse fromCategoria(CategoriaProduto categoria) {
        return new CategoriaResponse(
            categoria.name(),
            categoria.getDescricao(),
            0, 0, 0
        );
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public int getTotalProdutos() {
        return totalProdutos;
    }
    
    public void setTotalProdutos(int totalProdutos) {
        this.totalProdutos = totalProdutos;
        this.temProdutos = totalProdutos > 0;
    }
    
    public int getProdutosDisponiveis() {
        return produtosDisponiveis;
    }
    
    public void setProdutosDisponiveis(int produtosDisponiveis) {
        this.produtosDisponiveis = produtosDisponiveis;
        this.temProdutosDisponiveis = produtosDisponiveis > 0;
    }
    
    public int getProdutosIndisponiveis() {
        return produtosIndisponiveis;
    }
    
    public void setProdutosIndisponiveis(int produtosIndisponiveis) {
        this.produtosIndisponiveis = produtosIndisponiveis;
    }
    
    public boolean isTemProdutos() {
        return temProdutos;
    }
    
    public void setTemProdutos(boolean temProdutos) {
        this.temProdutos = temProdutos;
    }
    
    public boolean isTemProdutosDisponiveis() {
        return temProdutosDisponiveis;
    }
    
    public void setTemProdutosDisponiveis(boolean temProdutosDisponiveis) {
        this.temProdutosDisponiveis = temProdutosDisponiveis;
    }
    
    @Override
    public String toString() {
        return String.format("CategoriaResponse{nome='%s', totalProdutos=%d, produtosDisponiveis=%d}", 
                           nome, totalProdutos, produtosDisponiveis);
    }
}

