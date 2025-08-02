package com.marianapetrolini.fastfood.application.dtos.categoria;

import java.math.BigDecimal;

/**
 * DTO para estatísticas detalhadas de categoria.
 */
public class EstatisticasCategoriaResponse {
    
    private String categoria;
    private String descricao;
    private int totalProdutos;
    private int produtosDisponiveis;
    private int produtosIndisponiveis;
    private BigDecimal precoMedio;
    private BigDecimal precoMinimo;
    private BigDecimal precoMaximo;
    private int totalVendas;
    private BigDecimal faturamentoTotal;
    private double percentualVendas;
    private int posicaoPopularidade;
    
    public EstatisticasCategoriaResponse() {
    }
    
    public EstatisticasCategoriaResponse(String categoria, String descricao, int totalProdutos,
                                       int produtosDisponiveis, int produtosIndisponiveis,
                                       BigDecimal precoMedio, BigDecimal precoMinimo, BigDecimal precoMaximo,
                                       int totalVendas, BigDecimal faturamentoTotal,
                                       double percentualVendas, int posicaoPopularidade) {
        this.categoria = categoria;
        this.descricao = descricao;
        this.totalProdutos = totalProdutos;
        this.produtosDisponiveis = produtosDisponiveis;
        this.produtosIndisponiveis = produtosIndisponiveis;
        this.precoMedio = precoMedio;
        this.precoMinimo = precoMinimo;
        this.precoMaximo = precoMaximo;
        this.totalVendas = totalVendas;
        this.faturamentoTotal = faturamentoTotal;
        this.percentualVendas = percentualVendas;
        this.posicaoPopularidade = posicaoPopularidade;
    }
    
    public String getCategoria() {
        return categoria;
    }
    
    public void setCategoria(String categoria) {
        this.categoria = categoria;
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
    }
    
    public int getProdutosDisponiveis() {
        return produtosDisponiveis;
    }
    
    public void setProdutosDisponiveis(int produtosDisponiveis) {
        this.produtosDisponiveis = produtosDisponiveis;
    }
    
    public int getProdutosIndisponiveis() {
        return produtosIndisponiveis;
    }
    
    public void setProdutosIndisponiveis(int produtosIndisponiveis) {
        this.produtosIndisponiveis = produtosIndisponiveis;
    }
    
    public BigDecimal getPrecoMedio() {
        return precoMedio;
    }
    
    public void setPrecoMedio(BigDecimal precoMedio) {
        this.precoMedio = precoMedio;
    }
    
    public BigDecimal getPrecoMinimo() {
        return precoMinimo;
    }
    
    public void setPrecoMinimo(BigDecimal precoMinimo) {
        this.precoMinimo = precoMinimo;
    }
    
    public BigDecimal getPrecoMaximo() {
        return precoMaximo;
    }
    
    public void setPrecoMaximo(BigDecimal precoMaximo) {
        this.precoMaximo = precoMaximo;
    }
    
    public int getTotalVendas() {
        return totalVendas;
    }
    
    public void setTotalVendas(int totalVendas) {
        this.totalVendas = totalVendas;
    }
    
    public BigDecimal getFaturamentoTotal() {
        return faturamentoTotal;
    }
    
    public void setFaturamentoTotal(BigDecimal faturamentoTotal) {
        this.faturamentoTotal = faturamentoTotal;
    }
    
    public double getPercentualVendas() {
        return percentualVendas;
    }
    
    public void setPercentualVendas(double percentualVendas) {
        this.percentualVendas = percentualVendas;
    }
    
    public int getPosicaoPopularidade() {
        return posicaoPopularidade;
    }
    
    public void setPosicaoPopularidade(int posicaoPopularidade) {
        this.posicaoPopularidade = posicaoPopularidade;
    }
    
    @Override
    public String toString() {
        return String.format("EstatisticasCategoriaResponse{categoria='%s', totalProdutos=%d, totalVendas=%d, posicao=%d}", 
                           categoria, totalProdutos, totalVendas, posicaoPopularidade);
    }
}

