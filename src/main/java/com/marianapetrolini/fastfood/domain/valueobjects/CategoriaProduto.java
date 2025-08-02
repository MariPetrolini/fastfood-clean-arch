package com.marianapetrolini.fastfood.domain.valueobjects;

/**
 * Enum que representa as categorias de produtos disponíveis no sistema.
 * Define as categorias fixas conforme regras de negócio.
 */
public enum CategoriaProduto {
    
    LANCHE("Lanche", "Hambúrgueres, sanduíches e similares"),
    ACOMPANHAMENTO("Acompanhamento", "Batatas fritas, onion rings e similares"),
    BEBIDA("Bebida", "Refrigerantes, sucos, águas e similares"),
    SOBREMESA("Sobremesa", "Sorvetes, tortas, doces e similares");
    
    private final String nome;
    private final String descricao;
    
    CategoriaProduto(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }
    
    public String getNome() {
        return nome;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    /**
     * Converte uma string para CategoriaProduto de forma case-insensitive.
     * 
     * @param categoria String representando a categoria
     * @return CategoriaProduto correspondente
     * @throws IllegalArgumentException se a categoria não for válida
     */
    public static CategoriaProduto fromString(String categoria) {
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new IllegalArgumentException("Categoria não pode ser nula ou vazia");
        }
        
        try {
            return CategoriaProduto.valueOf(categoria.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Categoria inválida: " + categoria + 
                ". Categorias válidas: LANCHE, ACOMPANHAMENTO, BEBIDA, SOBREMESA");
        }
    }
    
    /**
     * Verifica se uma string representa uma categoria válida.
     * 
     * @param categoria String a ser verificada
     * @return true se for uma categoria válida, false caso contrário
     */
    public static boolean isValida(String categoria) {
        if (categoria == null || categoria.trim().isEmpty()) {
            return false;
        }
        
        try {
            CategoriaProduto.valueOf(categoria.trim().toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}

