package com.marianapetrolini.fastfood.application.usecases.produto;

import com.marianapetrolini.fastfood.application.dtos.produto.AtualizarProdutoRequest;
import com.marianapetrolini.fastfood.application.dtos.produto.CriarProdutoRequest;
import com.marianapetrolini.fastfood.application.dtos.produto.ProdutoResponse;
import com.marianapetrolini.fastfood.application.ports.input.ProdutoUseCase;
import com.marianapetrolini.fastfood.domain.entities.Produto;
import com.marianapetrolini.fastfood.domain.exceptions.DomainException;
import com.marianapetrolini.fastfood.domain.repositories.ProdutoRepository;
import com.marianapetrolini.fastfood.domain.valueobjects.CategoriaProduto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementação dos use cases relacionados a produtos.
 */
public class ProdutoUseCaseImpl implements ProdutoUseCase {
    
    private final ProdutoRepository produtoRepository;
    
    public ProdutoUseCaseImpl(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }
    
    @Override
    public ProdutoResponse criarProduto(CriarProdutoRequest request) {
        validarRequestCriacao(request);
        
        // Verificar se já existe produto com o mesmo nome
        if (produtoRepository.existePorNome(request.getNome())) {
            throw new DomainException("Já existe um produto com o nome: " + request.getNome());
        }
        
        CategoriaProduto categoria = CategoriaProduto.fromString(request.getCategoria());
        
        Produto produto = new Produto(
            request.getNome(),
            request.getDescricao(),
            request.getPreco(),
            categoria
        );
        
        Produto produtoSalvo = produtoRepository.salvar(produto);
        return ProdutoResponse.fromEntity(produtoSalvo);
    }
    
    @Override
    public ProdutoResponse buscarProdutoPorId(Long id) {
        if (id == null) {
            throw new DomainException("ID do produto não pode ser nulo");
        }
        
        Produto produto = produtoRepository.buscarPorId(id)
            .orElseThrow(() -> DomainException.entidadeNaoEncontrada("Produto", id));
        
        return ProdutoResponse.fromEntity(produto);
    }
    
    @Override
    public List<ProdutoResponse> listarTodosProdutos() {
        return produtoRepository.buscarTodos().stream()
            .map(ProdutoResponse::fromEntity)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<ProdutoResponse> listarProdutosPorCategoria(CategoriaProduto categoria) {
        if (categoria == null) {
            throw new DomainException("Categoria não pode ser nula");
        }
        
        return produtoRepository.buscarPorCategoria(categoria).stream()
            .map(ProdutoResponse::fromEntity)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<ProdutoResponse> listarProdutosDisponiveis() {
        return produtoRepository.buscarDisponiveis().stream()
            .map(ProdutoResponse::fromEntity)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<ProdutoResponse> listarProdutosDisponiveisPorCategoria(CategoriaProduto categoria) {
        if (categoria == null) {
            throw new DomainException("Categoria não pode ser nula");
        }
        
        return produtoRepository.buscarDisponiveisPorCategoria(categoria).stream()
            .map(ProdutoResponse::fromEntity)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<ProdutoResponse> buscarProdutosPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new DomainException("Nome para busca não pode ser nulo ou vazio");
        }
        
        return produtoRepository.buscarPorNome(nome).stream()
            .map(ProdutoResponse::fromEntity)
            .collect(Collectors.toList());
    }
    
    @Override
    public ProdutoResponse atualizarProduto(Long id, AtualizarProdutoRequest request) {
        if (id == null) {
            throw new DomainException("ID do produto não pode ser nulo");
        }
        
        validarRequestAtualizacao(request);
        
        Produto produto = produtoRepository.buscarPorId(id)
            .orElseThrow(() -> DomainException.entidadeNaoEncontrada("Produto", id));
        
        // Verificar se já existe outro produto com o mesmo nome
        if (produtoRepository.existePorNomeExcluindoId(request.getNome(), id)) {
            throw new DomainException("Já existe outro produto com o nome: " + request.getNome());
        }
        
        CategoriaProduto categoria = CategoriaProduto.fromString(request.getCategoria());
        
        produto.atualizar(
            request.getNome(),
            request.getDescricao(),
            request.getPreco(),
            categoria
        );
        
        Produto produtoAtualizado = produtoRepository.salvar(produto);
        return ProdutoResponse.fromEntity(produtoAtualizado);
    }
    
    @Override
    public ProdutoResponse ativarProduto(Long id) {
        if (id == null) {
            throw new DomainException("ID do produto não pode ser nulo");
        }
        
        Produto produto = produtoRepository.buscarPorId(id)
            .orElseThrow(() -> DomainException.entidadeNaoEncontrada("Produto", id));
        
        produto.ativar();
        
        Produto produtoAtualizado = produtoRepository.salvar(produto);
        return ProdutoResponse.fromEntity(produtoAtualizado);
    }
    
    @Override
    public ProdutoResponse desativarProduto(Long id) {
        if (id == null) {
            throw new DomainException("ID do produto não pode ser nulo");
        }
        
        Produto produto = produtoRepository.buscarPorId(id)
            .orElseThrow(() -> DomainException.entidadeNaoEncontrada("Produto", id));
        
        produto.desativar();
        
        Produto produtoAtualizado = produtoRepository.salvar(produto);
        return ProdutoResponse.fromEntity(produtoAtualizado);
    }
    
    @Override
    public void removerProduto(Long id) {
        if (id == null) {
            throw new DomainException("ID do produto não pode ser nulo");
        }
        
        if (!produtoRepository.buscarPorId(id).isPresent()) {
            throw DomainException.entidadeNaoEncontrada("Produto", id);
        }
        
        boolean removido = produtoRepository.remover(id);
        if (!removido) {
            throw new DomainException("Erro ao remover produto com ID: " + id);
        }
    }
    
    @Override
    public List<String> listarCategorias() {
        return Arrays.stream(CategoriaProduto.values())
            .map(Enum::name)
            .collect(Collectors.toList());
    }
    
    private void validarRequestCriacao(CriarProdutoRequest request) {
        if (request == null) {
            throw new DomainException("Request de criação não pode ser nulo");
        }
        
        if (request.getNome() == null || request.getNome().trim().isEmpty()) {
            throw new DomainException("Nome do produto é obrigatório");
        }
        
        if (request.getPreco() == null) {
            throw new DomainException("Preço do produto é obrigatório");
        }
        
        if (request.getCategoria() == null || request.getCategoria().trim().isEmpty()) {
            throw new DomainException("Categoria do produto é obrigatória");
        }
        
        // Validar se a categoria é válida
        try {
            CategoriaProduto.fromString(request.getCategoria());
        } catch (IllegalArgumentException e) {
            throw new DomainException("Categoria inválida: " + request.getCategoria());
        }
    }
    
    private void validarRequestAtualizacao(AtualizarProdutoRequest request) {
        if (request == null) {
            throw new DomainException("Request de atualização não pode ser nulo");
        }
        
        if (request.getNome() == null || request.getNome().trim().isEmpty()) {
            throw new DomainException("Nome do produto é obrigatório");
        }
        
        if (request.getPreco() == null) {
            throw new DomainException("Preço do produto é obrigatório");
        }
        
        if (request.getCategoria() == null || request.getCategoria().trim().isEmpty()) {
            throw new DomainException("Categoria do produto é obrigatória");
        }
        
        // Validar se a categoria é válida
        try {
            CategoriaProduto.fromString(request.getCategoria());
        } catch (IllegalArgumentException e) {
            throw new DomainException("Categoria inválida: " + request.getCategoria());
        }
    }
}

