package com.marianapetrolini.fastfood.infrastructure.adapters.web.controllers;

import com.marianapetrolini.fastfood.application.dtos.pedido.CheckoutRequest;
import com.marianapetrolini.fastfood.application.dtos.pedido.CheckoutResponse;
import com.marianapetrolini.fastfood.application.dtos.pedido.PedidoResponse;
import com.marianapetrolini.fastfood.application.dtos.pedido.PedidoListaResponse;
import com.marianapetrolini.fastfood.application.ports.input.PedidoUseCase;
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
 * Controller REST para operações relacionadas a pedidos.
 * Adapter entre a camada web e os use cases de pedido.
 */
@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "Operações de gerenciamento de pedidos e checkout")
public class PedidoController {
    
    private final PedidoUseCase pedidoUseCase;
    
    public PedidoController(PedidoUseCase pedidoUseCase) {
        this.pedidoUseCase = pedidoUseCase;
    }
    
    @PostMapping("/checkout")
    @Operation(summary = "Realizar checkout", description = "Realiza o checkout de um pedido com produtos selecionados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Checkout realizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<CheckoutResponse> realizarCheckout(
            @Valid @RequestBody CheckoutRequest request) {
        CheckoutResponse response = pedidoUseCase.realizarCheckout(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido por ID", description = "Busca um pedido específico pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    public ResponseEntity<PedidoResponse> buscarPedido(
            @Parameter(description = "ID do pedido") @PathVariable Long id) {
        PedidoResponse pedido = pedidoUseCase.buscarPedidoPorId(id);
        return ResponseEntity.ok(pedido);
    }
    
    @GetMapping
    @Operation(summary = "Listar todos os pedidos", description = "Lista todos os pedidos cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos")
    public ResponseEntity<List<PedidoResponse>> listarTodosPedidos() {
        List<PedidoResponse> pedidos = pedidoUseCase.listarTodosPedidos();
        return ResponseEntity.ok(pedidos);
    }
    
    @GetMapping("/cozinha")
    @Operation(summary = "Lista pedidos para cozinha", 
               description = "Lista pedidos ordenados por prioridade: Pronto > Em Preparação > Recebido")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos para cozinha")
    public ResponseEntity<List<PedidoListaResponse>> listarPedidosParaCozinha() {
        List<PedidoListaResponse> pedidos = pedidoUseCase.listarPedidosParaCozinha();
        return ResponseEntity.ok(pedidos);
    }
    
    @GetMapping("/status/{status}")
    @Operation(summary = "Listar pedidos por status", description = "Lista pedidos com status específico")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos com o status")
    public ResponseEntity<List<PedidoResponse>> listarPedidosPorStatus(
            @Parameter(description = "Status do pedido") @PathVariable String status) {
        List<PedidoResponse> pedidos = pedidoUseCase.listarPedidosPorStatus(status);
        return ResponseEntity.ok(pedidos);
    }
    
    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar pedidos do cliente", description = "Lista todos os pedidos de um cliente")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos do cliente")
    public ResponseEntity<List<PedidoResponse>> listarPedidosDoCliente(
            @Parameter(description = "ID do cliente") @PathVariable Long clienteId) {
        List<PedidoResponse> pedidos = pedidoUseCase.listarPedidosDoCliente(clienteId);
        return ResponseEntity.ok(pedidos);
    }
    
    @GetMapping("/ativos")
    @Operation(summary = "Listar pedidos ativos", description = "Lista pedidos não finalizados")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos ativos")
    public ResponseEntity<List<PedidoResponse>> listarPedidosAtivos() {
        List<PedidoResponse> pedidos = pedidoUseCase.listarPedidosAtivos();
        return ResponseEntity.ok(pedidos);
    }
    
    @GetMapping("/hoje")
    @Operation(summary = "Listar pedidos de hoje", description = "Lista pedidos feitos hoje")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos de hoje")
    public ResponseEntity<List<PedidoResponse>> listarPedidosDeHoje() {
        List<PedidoResponse> pedidos = pedidoUseCase.listarPedidosDeHoje();
        return ResponseEntity.ok(pedidos);
    }
    
    @PatchMapping("/{id}/iniciar-preparacao")
    @Operation(summary = "Iniciar preparação", description = "Inicia a preparação de um pedido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Preparação iniciada"),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
        @ApiResponse(responseCode = "400", description = "Status inválido para iniciar preparação")
    })
    public ResponseEntity<PedidoResponse> iniciarPreparacaoPedido(
            @Parameter(description = "ID do pedido") @PathVariable Long id) {
        PedidoResponse pedido = pedidoUseCase.iniciarPreparacaoPedido(id);
        return ResponseEntity.ok(pedido);
    }
    
    @PatchMapping("/{id}/marcar-pronto")
    @Operation(summary = "Marcar como pronto", description = "Marca um pedido como pronto para retirada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido marcado como pronto"),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
        @ApiResponse(responseCode = "400", description = "Status inválido para marcar como pronto")
    })
    public ResponseEntity<PedidoResponse> marcarPedidoComoPronto(
            @Parameter(description = "ID do pedido") @PathVariable Long id) {
        PedidoResponse pedido = pedidoUseCase.marcarPedidoComoPronto(id);
        return ResponseEntity.ok(pedido);
    }
    
    @PatchMapping("/{id}/finalizar")
    @Operation(summary = "Finalizar pedido", description = "Finaliza um pedido (cliente retirou)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido finalizado"),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
        @ApiResponse(responseCode = "400", description = "Status inválido para finalizar")
    })
    public ResponseEntity<PedidoResponse> finalizarPedido(
            @Parameter(description = "ID do pedido") @PathVariable Long id) {
        PedidoResponse pedido = pedidoUseCase.finalizarPedido(id);
        return ResponseEntity.ok(pedido);
    }
    
    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status", description = "Atualiza o status de um pedido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Status atualizado"),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
        @ApiResponse(responseCode = "400", description = "Status inválido")
    })
    public ResponseEntity<PedidoResponse> atualizarStatusPedido(
            @Parameter(description = "ID do pedido") @PathVariable Long id,
            @Parameter(description = "Novo status") @RequestParam String novoStatus) {
        PedidoResponse pedido = pedidoUseCase.atualizarStatusPedido(id, novoStatus);
        return ResponseEntity.ok(pedido);
    }
}

