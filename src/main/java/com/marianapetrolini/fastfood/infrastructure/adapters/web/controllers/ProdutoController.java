package com.marianapetrolini.fastfood.infrastructure.adapters.web.controllers;

import com.marianapetrolini.fastfood.application.dtos.produto.AtualizarProdutoRequest;
import com.marianapetrolini.fastfood.application.dtos.produto.CriarProdutoRequest;
import com.marianapetrolini.fastfood.application.dtos.produto.ProdutoResponse;
import com.marianapetrolini.fastfood.application.ports.input.ProdutoUseCase;
import com.marianapetrolini.fastfood.domain.valueobjects.CategoriaProduto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller REST para operações relacionadas a produtos.
 * Adapter entre a camada web e os use cases de produto.
 */
@RestController
@RequestMapping("/api/produtos")
@Tag(name = "Produtos", description = "Operações relacionadas a produtos")
public class ProdutoController {
    
    private final ProdutoUseCase produtoUseCase;
    
    public ProdutoController(ProdutoUseCase produtoUseCase) {
        this.produtoUseCase = produtoUseCase;
    }
    
    @PostMapping
    @Operation(summary = "Criar produto", description = "Cria um novo produto no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "409", description = "Produto já existe")
    })
    public ResponseEntity<ProdutoResponse> criarProduto(
            @Valid @RequestBody CriarProdutoRequest request) {
        ProdutoResponse produto = produtoUseCase.criarProduto(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(produto);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID", description = "Busca um produto específico pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produto encontrado"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<ProdutoResponse> buscarProduto(
            @Parameter(description = "ID do produto") @PathVariable Long id) {
        ProdutoResponse produto = produtoUseCase.buscarProdutoPorId(id);
        return ResponseEntity.ok(produto);
    }
    
    @GetMapping
    @Operation(summary = "Listar todos os produtos", description = "Lista todos os produtos cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de produtos")
    public ResponseEntity<List<ProdutoResponse>> listarTodosProdutos() {
        List<ProdutoResponse> produtos = produtoUseCase.listarTodosProdutos();
        return ResponseEntity.ok(produtos);
    }
    
    @GetMapping("/disponiveis")
    @Operation(summary = "Listar produtos disponíveis", description = "Lista apenas produtos disponíveis para venda")
    @ApiResponse(responseCode = "200", description = "Lista de produtos disponíveis")
    public ResponseEntity<List<ProdutoResponse>> listarProdutosDisponiveis() {
        List<ProdutoResponse> produtos = produtoUseCase.listarProdutosDisponiveis();
        return ResponseEntity.ok(produtos);
    }
    
    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Listar produtos por categoria", description = "Lista produtos de uma categoria específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de produtos da categoria"),
        @ApiResponse(responseCode = "400", description = "Categoria inválida")
    })
    public ResponseEntity<List<ProdutoResponse>> listarProdutosPorCategoria(
            @Parameter(description = "Categoria do produto") @PathVariable String categoria) {
        CategoriaProduto categoriaProduto = CategoriaProduto.fromString(categoria);
        List<ProdutoResponse> produtos = produtoUseCase.listarProdutosPorCategoria(categoriaProduto);
        return ResponseEntity.ok(produtos);
    }
    
    @GetMapping("/categoria/{categoria}/disponiveis")
    @Operation(summary = "Listar produtos disponíveis por categoria", 
               description = "Lista produtos disponíveis de uma categoria específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de produtos disponíveis da categoria"),
        @ApiResponse(responseCode = "400", description = "Categoria inválida")
    })
    public ResponseEntity<List<ProdutoResponse>> listarProdutosDisponiveisPorCategoria(
            @Parameter(description = "Categoria do produto") @PathVariable String categoria) {
        CategoriaProduto categoriaProduto = CategoriaProduto.fromString(categoria);
        List<ProdutoResponse> produtos = produtoUseCase.listarProdutosDisponiveisPorCategoria(categoriaProduto);
        return ResponseEntity.ok(produtos);
    }
    
    @GetMapping("/buscar")
    @Operation(summary = "Buscar produtos por nome", description = "Busca produtos que contenham o nome especificado")
    @ApiResponse(responseCode = "200", description = "Lista de produtos encontrados")
    public ResponseEntity<List<ProdutoResponse>> buscarProdutosPorNome(
            @Parameter(description = "Nome ou parte do nome do produto") @RequestParam String nome) {
        List<ProdutoResponse> produtos = produtoUseCase.buscarProdutosPorNome(nome);
        return ResponseEntity.ok(produtos);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produto", description = "Atualiza os dados de um produto existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
        @ApiResponse(responseCode = "409", description = "Nome já existe")
    })
    public ResponseEntity<ProdutoResponse> atualizarProduto(
            @Parameter(description = "ID do produto") @PathVariable Long id,
            @Valid @RequestBody AtualizarProdutoRequest request) {
        ProdutoResponse produto = produtoUseCase.atualizarProduto(id, request);
        return ResponseEntity.ok(produto);
    }
    
    @PatchMapping("/{id}/ativar")
    @Operation(summary = "Ativar produto", description = "Ativa um produto (torna disponível para venda)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produto ativado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<ProdutoResponse> ativarProduto(
            @Parameter(description = "ID do produto") @PathVariable Long id) {
        ProdutoResponse produto = produtoUseCase.ativarProduto(id);
        return ResponseEntity.ok(produto);
    }
    
    @PatchMapping("/{id}/desativar")
    @Operation(summary = "Desativar produto", description = "Desativa um produto (torna indisponível para venda)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produto desativado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<ProdutoResponse> desativarProduto(
            @Parameter(description = "ID do produto") @PathVariable Long id) {
        ProdutoResponse produto = produtoUseCase.desativarProduto(id);
        return ResponseEntity.ok(produto);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Remover produto", description = "Remove um produto do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Produto removido com sucesso"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<Void> removerProduto(
            @Parameter(description = "ID do produto") @PathVariable Long id) {
        produtoUseCase.removerProduto(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/categorias")
    @Operation(summary = "Listar categorias", description = "Lista todas as categorias de produtos disponíveis")
    @ApiResponse(responseCode = "200", description = "Lista de categorias")
    public ResponseEntity<List<String>> listarCategorias() {
        List<String> categorias = produtoUseCase.listarCategorias();
        return ResponseEntity.ok(categorias);
    }
}

