package com.marianapetrolini.fastfood.unit.application.usecases.produto;

import com.marianapetrolini.fastfood.application.dtos.produto.CriarProdutoRequest;
import com.marianapetrolini.fastfood.application.dtos.produto.ProdutoResponse;
import com.marianapetrolini.fastfood.application.usecases.produto.ProdutoUseCaseImpl;
import com.marianapetrolini.fastfood.domain.entities.Produto;
import com.marianapetrolini.fastfood.domain.exceptions.DomainException;
import com.marianapetrolini.fastfood.domain.repositories.ProdutoRepository;
import com.marianapetrolini.fastfood.domain.valueobjects.CategoriaProduto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para o use case de produtos.
 */
@ExtendWith(MockitoExtension.class)
class ProdutoUseCaseTest {
    
    @Mock
    private ProdutoRepository produtoRepository;
    
    private ProdutoUseCaseImpl produtoUseCase;
    
    @BeforeEach
    void setUp() {
        produtoUseCase = new ProdutoUseCaseImpl(produtoRepository);
    }
    
    @Test
    void deveCriarProdutoComSucesso() {
        // Given
        CriarProdutoRequest request = new CriarProdutoRequest(
            "Hambúrguer Clássico",
            "Hambúrguer com carne, queijo e salada",
            new BigDecimal("25.90"),
            "LANCHE"
        );
        
        Produto produtoSalvo = new Produto(
            1L,
            request.getNome(),
            request.getDescricao(),
            request.getPreco(),
            CategoriaProduto.LANCHE,
            true,
            LocalDateTime.now(),
            LocalDateTime.now()
        );
        
        when(produtoRepository.existePorNome(request.getNome())).thenReturn(false);
        when(produtoRepository.salvar(any(Produto.class))).thenReturn(produtoSalvo);
        
        // When
        ProdutoResponse response = produtoUseCase.criarProduto(request);
        
        // Then
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(request.getNome(), response.getNome());
        assertEquals(request.getDescricao(), response.getDescricao());
        assertEquals(request.getPreco(), response.getPreco());
        assertEquals("LANCHE", response.getCategoria());
        assertTrue(response.isDisponivel());
        
        verify(produtoRepository).existePorNome(request.getNome());
        verify(produtoRepository).salvar(any(Produto.class));
    }
    
    @Test
    void deveLancarExcecaoQuandoTentarCriarProdutoComNomeExistente() {
        // Given
        CriarProdutoRequest request = new CriarProdutoRequest(
            "Hambúrguer Existente",
            "Descrição",
            new BigDecimal("25.90"),
            "LANCHE"
        );
        
        when(produtoRepository.existePorNome(request.getNome())).thenReturn(true);
        
        // When & Then
        DomainException exception = assertThrows(DomainException.class, () -> {
            produtoUseCase.criarProduto(request);
        });
        
        assertEquals("Já existe um produto com o nome: " + request.getNome(), exception.getMessage());
        verify(produtoRepository).existePorNome(request.getNome());
        verify(produtoRepository, never()).salvar(any(Produto.class));
    }
    
    @Test
    void deveLancarExcecaoQuandoRequestForNulo() {
        // Given
        CriarProdutoRequest request = null;
        
        // When & Then
        DomainException exception = assertThrows(DomainException.class, () -> {
            produtoUseCase.criarProduto(request);
        });
        
        assertEquals("Request de criação não pode ser nulo", exception.getMessage());
        verify(produtoRepository, never()).existePorNome(anyString());
        verify(produtoRepository, never()).salvar(any(Produto.class));
    }
    
    @Test
    void deveLancarExcecaoQuandoNomeForVazio() {
        // Given
        CriarProdutoRequest request = new CriarProdutoRequest(
            "",
            "Descrição",
            new BigDecimal("25.90"),
            "LANCHE"
        );
        
        // When & Then
        DomainException exception = assertThrows(DomainException.class, () -> {
            produtoUseCase.criarProduto(request);
        });
        
        assertEquals("Nome do produto é obrigatório", exception.getMessage());
    }
    
    @Test
    void deveLancarExcecaoQuandoCategoriaForInvalida() {
        // Given
        CriarProdutoRequest request = new CriarProdutoRequest(
            "Produto Válido",
            "Descrição",
            new BigDecimal("25.90"),
            "CATEGORIA_INVALIDA"
        );
        
        // When & Then
        DomainException exception = assertThrows(DomainException.class, () -> {
            produtoUseCase.criarProduto(request);
        });
        
        assertTrue(exception.getMessage().contains("Categoria inválida"));
    }
    
    @Test
    void deveBuscarProdutoPorIdComSucesso() {
        // Given
        Long id = 1L;
        Produto produto = new Produto(
            id,
            "Hambúrguer",
            "Descrição",
            new BigDecimal("25.90"),
            CategoriaProduto.LANCHE,
            true,
            LocalDateTime.now(),
            LocalDateTime.now()
        );
        
        when(produtoRepository.buscarPorId(id)).thenReturn(Optional.of(produto));
        
        // When
        ProdutoResponse response = produtoUseCase.buscarProdutoPorId(id);
        
        // Then
        assertNotNull(response);
        assertEquals(id, response.getId());
        assertEquals(produto.getNome(), response.getNome());
        
        verify(produtoRepository).buscarPorId(id);
    }
    
    @Test
    void deveLancarExcecaoQuandoProdutoNaoForEncontrado() {
        // Given
        Long id = 999L;
        
        when(produtoRepository.buscarPorId(id)).thenReturn(Optional.empty());
        
        // When & Then
        DomainException exception = assertThrows(DomainException.class, () -> {
            produtoUseCase.buscarProdutoPorId(id);
        });
        
        assertEquals("Produto com ID 999 não encontrado(a)", exception.getMessage());
        verify(produtoRepository).buscarPorId(id);
    }
    
    @Test
    void deveListarTodosProdutos() {
        // Given
        List<Produto> produtos = Arrays.asList(
            new Produto(1L, "Produto 1", "Desc 1", new BigDecimal("10.00"), 
                       CategoriaProduto.LANCHE, true, LocalDateTime.now(), LocalDateTime.now()),
            new Produto(2L, "Produto 2", "Desc 2", new BigDecimal("15.00"), 
                       CategoriaProduto.BEBIDA, true, LocalDateTime.now(), LocalDateTime.now())
        );
        
        when(produtoRepository.buscarTodos()).thenReturn(produtos);
        
        // When
        List<ProdutoResponse> responses = produtoUseCase.listarTodosProdutos();
        
        // Then
        assertNotNull(responses);
        assertEquals(2, responses.size());
        assertEquals("Produto 1", responses.get(0).getNome());
        assertEquals("Produto 2", responses.get(1).getNome());
        
        verify(produtoRepository).buscarTodos();
    }
    
    @Test
    void deveListarProdutosPorCategoria() {
        // Given
        CategoriaProduto categoria = CategoriaProduto.LANCHE;
        List<Produto> produtos = Arrays.asList(
            new Produto(1L, "Hambúrguer", "Desc", new BigDecimal("25.90"), 
                       categoria, true, LocalDateTime.now(), LocalDateTime.now())
        );
        
        when(produtoRepository.buscarPorCategoria(categoria)).thenReturn(produtos);
        
        // When
        List<ProdutoResponse> responses = produtoUseCase.listarProdutosPorCategoria(categoria);
        
        // Then
        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals("LANCHE", responses.get(0).getCategoria());
        
        verify(produtoRepository).buscarPorCategoria(categoria);
    }
    
    @Test
    void deveRemoverProdutoComSucesso() {
        // Given
        Long id = 1L;
        Produto produto = new Produto(
            id,
            "Produto",
            "Descrição",
            new BigDecimal("25.90"),
            CategoriaProduto.LANCHE,
            true,
            LocalDateTime.now(),
            LocalDateTime.now()
        );
        
        when(produtoRepository.buscarPorId(id)).thenReturn(Optional.of(produto));
        when(produtoRepository.remover(id)).thenReturn(true);
        
        // When & Then
        assertDoesNotThrow(() -> produtoUseCase.removerProduto(id));
        
        verify(produtoRepository).buscarPorId(id);
        verify(produtoRepository).remover(id);
    }
    
    @Test
    void deveLancarExcecaoQuandoTentarRemoverProdutoInexistente() {
        // Given
        Long id = 999L;
        
        when(produtoRepository.buscarPorId(id)).thenReturn(Optional.empty());
        
        // When & Then
        DomainException exception = assertThrows(DomainException.class, () -> {
            produtoUseCase.removerProduto(id);
        });
        
        assertEquals("Produto com ID 999 não encontrado(a)", exception.getMessage());
        verify(produtoRepository).buscarPorId(id);
        verify(produtoRepository, never()).remover(id);
    }
    
    @Test
    void deveListarCategorias() {
        // When
        List<String> categorias = produtoUseCase.listarCategorias();
        
        // Then
        assertNotNull(categorias);
        assertEquals(4, categorias.size());
        assertTrue(categorias.contains("LANCHE"));
        assertTrue(categorias.contains("ACOMPANHAMENTO"));
        assertTrue(categorias.contains("BEBIDA"));
        assertTrue(categorias.contains("SOBREMESA"));
    }
}

