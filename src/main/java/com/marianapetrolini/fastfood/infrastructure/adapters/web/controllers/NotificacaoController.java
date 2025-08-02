package com.marianapetrolini.fastfood.infrastructure.adapters.web.controllers;

import com.marianapetrolini.fastfood.application.dtos.notificacao.NotificacaoRequest;
import com.marianapetrolini.fastfood.application.dtos.notificacao.NotificacaoResponse;
import com.marianapetrolini.fastfood.application.ports.input.ClienteUseCase;
import com.marianapetrolini.fastfood.application.ports.output.NotificacaoPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controller REST para operações de notificação.
 * Adapter entre a camada web e o sistema de notificações.
 */
@RestController
@RequestMapping("/api/notificacoes")
@Tag(name = "Notificações", description = "Sistema de notificações e campanhas")
public class NotificacaoController {
    
    private final NotificacaoPort notificacaoPort;
    private final ClienteUseCase clienteUseCase;
    
    public NotificacaoController(NotificacaoPort notificacaoPort, ClienteUseCase clienteUseCase) {
        this.notificacaoPort = notificacaoPort;
        this.clienteUseCase = clienteUseCase;
    }
    
    @PostMapping("/enviar")
    @Operation(summary = "Enviar notificação", description = "Envia uma notificação personalizada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificação enviada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "500", description = "Erro no serviço de notificação")
    })
    public ResponseEntity<NotificacaoResponse> enviarNotificacao(
            @Valid @RequestBody NotificacaoRequest request) {
        NotificacaoResponse response = notificacaoPort.enviarNotificacao(request);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/campanhas")
    @Operation(summary = "Enviar campanha promocional", description = "Envia campanha para clientes elegíveis")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Campanha enviada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<List<NotificacaoResponse>> enviarCampanhaPromocional(
            @RequestBody Map<String, String> campanha) {
        
        String titulo = campanha.get("titulo");
        String mensagem = campanha.get("mensagem");
        
        if (titulo == null || mensagem == null) {
            throw new IllegalArgumentException("Título e mensagem são obrigatórios");
        }
        
        // Buscar clientes elegíveis para campanhas
        List<String> destinatarios = clienteUseCase.listarClientesParaCampanhas()
            .stream()
            .map(cliente -> cliente.getEmail())
            .collect(Collectors.toList());
        
        List<NotificacaoResponse> responses = notificacaoPort.enviarCampanhaPromocional(
            titulo, mensagem, destinatarios
        );
        
        return ResponseEntity.ok(responses);
    }
    
    @PostMapping("/massa")
    @Operation(summary = "Enviar notificação em massa", description = "Envia notificação para múltiplos destinatários")
    @ApiResponse(responseCode = "200", description = "Notificações enviadas")
    public ResponseEntity<List<NotificacaoResponse>> enviarNotificacaoEmMassa(
            @Valid @RequestBody NotificacaoRequest request,
            @RequestParam List<String> destinatarios) {
        
        List<NotificacaoResponse> responses = notificacaoPort.enviarNotificacaoEmMassa(
            request, destinatarios
        );
        
        return ResponseEntity.ok(responses);
    }
    
    @PostMapping("/pedido/{pedidoId}/pronto")
    @Operation(summary = "Notificar pedido pronto", description = "Notifica cliente que pedido está pronto")
    @ApiResponse(responseCode = "200", description = "Cliente notificado")
    public ResponseEntity<NotificacaoResponse> notificarPedidoPronto(
            @Parameter(description = "ID do pedido") @PathVariable Long pedidoId,
            @RequestParam String clienteEmail,
            @RequestParam String numeroPedido) {
        
        NotificacaoResponse response = notificacaoPort.notificarPedidoPronto(
            pedidoId, clienteEmail, numeroPedido
        );
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/cozinha/novo-pedido")
    @Operation(summary = "Notificar cozinha", description = "Notifica cozinha sobre novo pedido")
    @ApiResponse(responseCode = "200", description = "Cozinha notificada")
    public ResponseEntity<NotificacaoResponse> notificarCozinhaNovoPedido(
            @RequestParam Long pedidoId,
            @RequestParam String numeroPedido,
            @RequestParam String itens) {
        
        NotificacaoResponse response = notificacaoPort.notificarCozinhaNovoPedido(
            pedidoId, numeroPedido, itens
        );
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/alertas/tempo-espera")
    @Operation(summary = "Alerta tempo de espera", description = "Envia alerta sobre tempo de espera elevado")
    @ApiResponse(responseCode = "200", description = "Alerta enviado")
    public ResponseEntity<NotificacaoResponse> alertarTempoEsperaElevado(
            @RequestParam Long pedidoId,
            @RequestParam String numeroPedido,
            @RequestParam int tempoEspera) {
        
        NotificacaoResponse response = notificacaoPort.alertarTempoEsperaElevado(
            pedidoId, numeroPedido, tempoEspera
        );
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/status")
    @Operation(summary = "Status do serviço", description = "Verifica se o serviço de notificação está disponível")
    @ApiResponse(responseCode = "200", description = "Status do serviço")
    public ResponseEntity<Map<String, Object>> verificarStatusServico() {
        boolean disponivel = notificacaoPort.isServicoDisponivel();
        
        Map<String, Object> status = Map.of(
            "servicoDisponivel", disponivel,
            "status", disponivel ? "ONLINE" : "OFFLINE",
            "timestamp", java.time.LocalDateTime.now()
        );
        
        return ResponseEntity.ok(status);
    }
    
    @GetMapping("/estatisticas")
    @Operation(summary = "Estatísticas de notificações", description = "Obtém estatísticas do sistema de notificações")
    @ApiResponse(responseCode = "200", description = "Estatísticas do sistema")
    public ResponseEntity<NotificacaoResponse> obterEstatisticas() {
        NotificacaoResponse estatisticas = notificacaoPort.obterEstatisticas();
        return ResponseEntity.ok(estatisticas);
    }
    
    @PostMapping("/teste")
    @Operation(summary = "Teste de notificação", description = "Envia notificação de teste")
    @ApiResponse(responseCode = "200", description = "Notificação de teste enviada")
    public ResponseEntity<NotificacaoResponse> enviarNotificacaoTeste(
            @RequestParam String destinatario) {
        
        NotificacaoRequest request = new NotificacaoRequest(
            destinatario,
            "🧪 Teste de Notificação - FastFood",
            "Esta é uma notificação de teste do sistema FastFood. " +
            "Se você recebeu esta mensagem, o sistema está funcionando corretamente!"
        );
        request.setTipo("TESTE");
        
        NotificacaoResponse response = notificacaoPort.enviarNotificacao(request);
        return ResponseEntity.ok(response);
    }
}

