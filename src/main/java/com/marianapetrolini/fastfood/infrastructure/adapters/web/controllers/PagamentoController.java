package com.marianapetrolini.fastfood.infrastructure.adapters.web.controllers;

import com.marianapetrolini.fastfood.application.dtos.pagamento.StatusPagamentoResponse;
import com.marianapetrolini.fastfood.application.dtos.pagamento.WebhookPagamentoRequest;
import com.marianapetrolini.fastfood.application.dtos.pagamento.PagamentoResponse;
import com.marianapetrolini.fastfood.application.ports.input.PagamentoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller REST para operações relacionadas a pagamentos.
 * Adapter entre a camada web e os use cases de pagamento.
 */
@RestController
@RequestMapping("/api/pagamentos")
@Tag(name = "Pagamentos", description = "Operações de gerenciamento de pagamentos e webhooks")
public class PagamentoController {
    
    private final PagamentoUseCase pagamentoUseCase;
    
    public PagamentoController(PagamentoUseCase pagamentoUseCase) {
        this.pagamentoUseCase = pagamentoUseCase;
    }
    
    @GetMapping("/pedido/{pedidoId}/status")
    @Operation(summary = "Consultar status de pagamento", 
               description = "Consulta o status de pagamento de um pedido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Status encontrado"),
        @ApiResponse(responseCode = "404", description = "Pagamento não encontrado")
    })
    public ResponseEntity<StatusPagamentoResponse> consultarStatusPagamento(
            @Parameter(description = "ID do pedido") @PathVariable Long pedidoId) {
        StatusPagamentoResponse status = pagamentoUseCase.consultarStatusPagamento(pedidoId);
        return ResponseEntity.ok(status);
    }
    
    @PostMapping("/webhook")
    @Operation(summary = "Webhook de pagamento", 
               description = "Recebe confirmação de pagamento do Mercado Pago")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Webhook processado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<Void> processarWebhookPagamento(
            @Valid @RequestBody WebhookPagamentoRequest request) {
        pagamentoUseCase.processarWebhookPagamento(request);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar pagamento por ID", description = "Busca um pagamento específico pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pagamento encontrado"),
        @ApiResponse(responseCode = "404", description = "Pagamento não encontrado")
    })
    public ResponseEntity<PagamentoResponse> buscarPagamento(
            @Parameter(description = "ID do pagamento") @PathVariable Long id) {
        PagamentoResponse pagamento = pagamentoUseCase.buscarPagamentoPorId(id);
        return ResponseEntity.ok(pagamento);
    }
    
    @GetMapping("/pedido/{pedidoId}")
    @Operation(summary = "Buscar pagamento por pedido", 
               description = "Busca o pagamento de um pedido específico")
    @ApiResponse(responseCode = "200", description = "Pagamento do pedido")
    public ResponseEntity<PagamentoResponse> buscarPagamentoPorPedidoId(
            @Parameter(description = "ID do pedido") @PathVariable Long pedidoId) {
        PagamentoResponse pagamento = pagamentoUseCase.buscarPagamentoPorPedidoId(pedidoId);
        return ResponseEntity.ok(pagamento);
    }
    
    @GetMapping("/transacao/{transacaoId}")
    @Operation(summary = "Buscar pagamento por transação", 
               description = "Busca pagamento pelo ID da transação")
    @ApiResponse(responseCode = "200", description = "Pagamento da transação")
    public ResponseEntity<PagamentoResponse> buscarPagamentoPorTransacaoId(
            @Parameter(description = "ID da transação") @PathVariable String transacaoId) {
        PagamentoResponse pagamento = pagamentoUseCase.buscarPagamentoPorTransacaoId(transacaoId);
        return ResponseEntity.ok(pagamento);
    }
    
    @GetMapping
    @Operation(summary = "Listar todos os pagamentos", description = "Lista todos os pagamentos")
    @ApiResponse(responseCode = "200", description = "Lista de pagamentos")
    public ResponseEntity<List<PagamentoResponse>> listarTodosPagamentos() {
        List<PagamentoResponse> pagamentos = pagamentoUseCase.listarTodosPagamentos();
        return ResponseEntity.ok(pagamentos);
    }
    
    @GetMapping("/status/{status}")
    @Operation(summary = "Listar pagamentos por status", 
               description = "Lista pagamentos com status específico")
    @ApiResponse(responseCode = "200", description = "Lista de pagamentos com o status")
    public ResponseEntity<List<PagamentoResponse>> listarPagamentosPorStatus(
            @Parameter(description = "Status do pagamento") @PathVariable String status) {
        List<PagamentoResponse> pagamentos = pagamentoUseCase.listarPagamentosPorStatus(status);
        return ResponseEntity.ok(pagamentos);
    }
    
    @GetMapping("/metodo/{metodo}")
    @Operation(summary = "Listar pagamentos por método", 
               description = "Lista pagamentos por método de pagamento")
    @ApiResponse(responseCode = "200", description = "Lista de pagamentos com o método")
    public ResponseEntity<List<PagamentoResponse>> listarPagamentosPorMetodo(
            @Parameter(description = "Método de pagamento") @PathVariable String metodo) {
        List<PagamentoResponse> pagamentos = pagamentoUseCase.listarPagamentosPorMetodo(metodo);
        return ResponseEntity.ok(pagamentos);
    }
    
    @GetMapping("/aprovados")
    @Operation(summary = "Listar pagamentos aprovados", description = "Lista pagamentos aprovados")
    @ApiResponse(responseCode = "200", description = "Lista de pagamentos aprovados")
    public ResponseEntity<List<PagamentoResponse>> listarPagamentosAprovados() {
        List<PagamentoResponse> pagamentos = pagamentoUseCase.listarPagamentosAprovados();
        return ResponseEntity.ok(pagamentos);
    }
    
    @GetMapping("/pendentes")
    @Operation(summary = "Listar pagamentos pendentes", description = "Lista pagamentos pendentes")
    @ApiResponse(responseCode = "200", description = "Lista de pagamentos pendentes")
    public ResponseEntity<List<PagamentoResponse>> listarPagamentosPendentes() {
        List<PagamentoResponse> pagamentos = pagamentoUseCase.listarPagamentosPendentes();
        return ResponseEntity.ok(pagamentos);
    }
    
    @GetMapping("/hoje")
    @Operation(summary = "Listar pagamentos de hoje", description = "Lista pagamentos feitos hoje")
    @ApiResponse(responseCode = "200", description = "Lista de pagamentos de hoje")
    public ResponseEntity<List<PagamentoResponse>> listarPagamentosDeHoje() {
        List<PagamentoResponse> pagamentos = pagamentoUseCase.listarPagamentosDeHoje();
        return ResponseEntity.ok(pagamentos);
    }
    
    @PatchMapping("/{id}/aprovar")
    @Operation(summary = "Aprovar pagamento", description = "Aprova um pagamento manualmente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pagamento aprovado"),
        @ApiResponse(responseCode = "404", description = "Pagamento não encontrado"),
        @ApiResponse(responseCode = "400", description = "Status inválido para aprovação")
    })
    public ResponseEntity<PagamentoResponse> aprovarPagamento(
            @Parameter(description = "ID do pagamento") @PathVariable Long id,
            @Parameter(description = "ID da transação") @RequestParam String transacaoId) {
        PagamentoResponse pagamento = pagamentoUseCase.aprovarPagamento(id, transacaoId);
        return ResponseEntity.ok(pagamento);
    }
    
    @PatchMapping("/{id}/recusar")
    @Operation(summary = "Recusar pagamento", description = "Recusa um pagamento manualmente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pagamento recusado"),
        @ApiResponse(responseCode = "404", description = "Pagamento não encontrado")
    })
    public ResponseEntity<PagamentoResponse> recusarPagamento(
            @Parameter(description = "ID do pagamento") @PathVariable Long id,
            @Parameter(description = "Motivo da recusa") @RequestParam String motivo) {
        PagamentoResponse pagamento = pagamentoUseCase.recusarPagamento(id, motivo);
        return ResponseEntity.ok(pagamento);
    }
    
    @PatchMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar pagamento", description = "Cancela um pagamento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pagamento cancelado"),
        @ApiResponse(responseCode = "404", description = "Pagamento não encontrado")
    })
    public ResponseEntity<PagamentoResponse> cancelarPagamento(
            @Parameter(description = "ID do pagamento") @PathVariable Long id,
            @Parameter(description = "Motivo do cancelamento") @RequestParam String motivo) {
        PagamentoResponse pagamento = pagamentoUseCase.cancelarPagamento(id, motivo);
        return ResponseEntity.ok(pagamento);
    }
    
    @PatchMapping("/{id}/estornar")
    @Operation(summary = "Estornar pagamento", description = "Estorna um pagamento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pagamento estornado"),
        @ApiResponse(responseCode = "404", description = "Pagamento não encontrado")
    })
    public ResponseEntity<PagamentoResponse> estornarPagamento(
            @Parameter(description = "ID do pagamento") @PathVariable Long id,
            @Parameter(description = "Motivo do estorno") @RequestParam String motivo) {
        PagamentoResponse pagamento = pagamentoUseCase.estornarPagamento(id, motivo);
        return ResponseEntity.ok(pagamento);
    }
}

