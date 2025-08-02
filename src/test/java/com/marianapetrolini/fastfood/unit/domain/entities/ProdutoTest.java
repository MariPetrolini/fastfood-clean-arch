package com.marianapetrolini.fastfood.unit.domain.entities;

import com.marianapetrolini.fastfood.domain.entities.Produto;
import com.marianapetrolini.fastfood.domain.exceptions.DomainException;
import com.marianapetrolini.fastfood.domain.valueobjects.CategoriaProduto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a entidade Produto.
 */
class ProdutoTest {
    
    @Test
    void deveCriarProdutoComDadosValidos() {
        // Given
        String nome = "Hambúrguer Clássico";
        String descricao = "Hambúrguer com carne, queijo e salada";
        BigDecimal preco = new BigDecimal("25.90");
        CategoriaProduto categoria = CategoriaProduto.LANCHE;
        
        // When
        Produto produto = new Produto(nome, descricao, preco, categoria);
        
        // Then
        assertNotNull(produto);
        assertEquals(nome, produto.getNome());
        assertEquals(descricao, produto.getDescricao());
        assertEquals(preco, produto.getPreco());
        assertEquals(categoria, produto.getCategoria());
        assertTrue(produto.isDisponivel());
        assertNotNull(produto.getCriadoEm());
        assertNotNull(produto.getAtualizadoEm());
    }
    
    @Test
    void deveLancarExcecaoQuandoNomeForNulo() {
        // Given
        String nome = null;
        String descricao = "Descrição válida";
        BigDecimal preco = new BigDecimal("25.90");
        CategoriaProduto categoria = CategoriaProduto.LANCHE;
        
        // When & Then
        DomainException exception = assertThrows(DomainException.class, () -> {
            new Produto(nome, descricao, preco, categoria);
        });
        
        assertEquals("Nome do produto não pode ser nulo ou vazio", exception.getMessage());
    }
    
    @Test
    void deveLancarExcecaoQuandoNomeForVazio() {
        // Given
        String nome = "";
        String descricao = "Descrição válida";
        BigDecimal preco = new BigDecimal("25.90");
        CategoriaProduto categoria = CategoriaProduto.LANCHE;
        
        // When & Then
        DomainException exception = assertThrows(DomainException.class, () -> {
            new Produto(nome, descricao, preco, categoria);
        });
        
        assertEquals("Nome do produto não pode ser nulo ou vazio", exception.getMessage());
    }
    
    @Test
    void deveLancarExcecaoQuandoPrecoForNulo() {
        // Given
        String nome = "Produto Válido";
        String descricao = "Descrição válida";
        BigDecimal preco = null;
        CategoriaProduto categoria = CategoriaProduto.LANCHE;
        
        // When & Then
        DomainException exception = assertThrows(DomainException.class, () -> {
            new Produto(nome, descricao, preco, categoria);
        });
        
        assertEquals("Preço do produto não pode ser nulo", exception.getMessage());
    }
    
    @Test
    void deveLancarExcecaoQuandoPrecoForZeroOuNegativo() {
        // Given
        String nome = "Produto Válido";
        String descricao = "Descrição válida";
        BigDecimal precoZero = BigDecimal.ZERO;
        BigDecimal precoNegativo = new BigDecimal("-10.00");
        CategoriaProduto categoria = CategoriaProduto.LANCHE;
        
        // When & Then
        DomainException exceptionZero = assertThrows(DomainException.class, () -> {
            new Produto(nome, descricao, precoZero, categoria);
        });
        assertEquals("Preço do produto deve ser maior que zero", exceptionZero.getMessage());
        
        DomainException exceptionNegativo = assertThrows(DomainException.class, () -> {
            new Produto(nome, descricao, precoNegativo, categoria);
        });
        assertEquals("Preço do produto deve ser maior que zero", exceptionNegativo.getMessage());
    }
    
    @Test
    void deveLancarExcecaoQuandoCategoriaForNula() {
        // Given
        String nome = "Produto Válido";
        String descricao = "Descrição válida";
        BigDecimal preco = new BigDecimal("25.90");
        CategoriaProduto categoria = null;
        
        // When & Then
        DomainException exception = assertThrows(DomainException.class, () -> {
            new Produto(nome, descricao, preco, categoria);
        });
        
        assertEquals("Categoria do produto não pode ser nula", exception.getMessage());
    }
    
    @Test
    void deveCalcularValorTotalCorretamente() {
        // Given
        Produto produto = new Produto(
            "Hambúrguer",
            "Descrição",
            new BigDecimal("25.90"),
            CategoriaProduto.LANCHE
        );
        int quantidade = 3;
        
        // When
        BigDecimal valorTotal = produto.calcularValorTotal(quantidade);
        
        // Then
        assertEquals(new BigDecimal("77.70"), valorTotal);
    }
    
    @Test
    void deveLancarExcecaoQuandoQuantidadeForZeroOuNegativa() {
        // Given
        Produto produto = new Produto(
            "Hambúrguer",
            "Descrição",
            new BigDecimal("25.90"),
            CategoriaProduto.LANCHE
        );
        
        // When & Then
        DomainException exceptionZero = assertThrows(DomainException.class, () -> {
            produto.calcularValorTotal(0);
        });
        assertEquals("Quantidade deve ser maior que zero", exceptionZero.getMessage());
        
        DomainException exceptionNegativo = assertThrows(DomainException.class, () -> {
            produto.calcularValorTotal(-1);
        });
        assertEquals("Quantidade deve ser maior que zero", exceptionNegativo.getMessage());
    }
    
    @Test
    void deveAtivarEDesativarProduto() {
        // Given
        Produto produto = new Produto(
            "Hambúrguer",
            "Descrição",
            new BigDecimal("25.90"),
            CategoriaProduto.LANCHE
        );
        
        // When & Then - Produto inicia ativo
        assertTrue(produto.isDisponivel());
        assertTrue(produto.podeSerVendido());
        
        // When - Desativar
        produto.desativar();
        
        // Then
        assertFalse(produto.isDisponivel());
        assertFalse(produto.podeSerVendido());
        
        // When - Ativar novamente
        produto.ativar();
        
        // Then
        assertTrue(produto.isDisponivel());
        assertTrue(produto.podeSerVendido());
    }
    
    @Test
    void deveAtualizarProdutoCorretamente() {
        // Given
        Produto produto = new Produto(
            "Nome Original",
            "Descrição Original",
            new BigDecimal("10.00"),
            CategoriaProduto.LANCHE
        );
        
        String novoNome = "Nome Atualizado";
        String novaDescricao = "Descrição Atualizada";
        BigDecimal novoPreco = new BigDecimal("15.00");
        CategoriaProduto novaCategoria = CategoriaProduto.BEBIDA;
        
        // When
        produto.atualizar(novoNome, novaDescricao, novoPreco, novaCategoria);
        
        // Then
        assertEquals(novoNome, produto.getNome());
        assertEquals(novaDescricao, produto.getDescricao());
        assertEquals(novoPreco, produto.getPreco());
        assertEquals(novaCategoria, produto.getCategoria());
    }
    
    @Test
    void deveManterConsistenciaDeEquals() {
        // Given
        Produto produto1 = new Produto("Nome", "Desc", new BigDecimal("10.00"), CategoriaProduto.LANCHE);
        Produto produto2 = new Produto("Nome", "Desc", new BigDecimal("10.00"), CategoriaProduto.LANCHE);
        produto1.setId(1L);
        produto2.setId(1L);
        
        Produto produto3 = new Produto("Nome", "Desc", new BigDecimal("10.00"), CategoriaProduto.LANCHE);
        produto3.setId(2L);
        
        // When & Then
        assertEquals(produto1, produto2);
        assertNotEquals(produto1, produto3);
        assertEquals(produto1.hashCode(), produto2.hashCode());
        assertNotEquals(produto1.hashCode(), produto3.hashCode());
    }
}

