package com.marianapetrolini.fastfood.infrastructure.adapters.external;

import com.marianapetrolini.fastfood.application.dtos.notificacao.NotificacaoRequest;
import com.marianapetrolini.fastfood.application.dtos.notificacao.NotificacaoResponse;
import com.marianapetrolini.fastfood.application.ports.output.NotificacaoPort;
import com.marianapetrolini.fastfood.domain.valueobjects.StatusPedido;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Adapter para sistema de notificações.
 * Implementa a integração com serviços de notificação (email, SMS, push).
 */
@Component
public class NotificacaoAdapter implements NotificacaoPort {
    
    private static final Logger logger = LoggerFactory.getLogger(NotificacaoAdapter.class);
    
    private boolean servicoDisponivel = true;
    private int totalNotificacoesEnviadas = 0;
    private int totalNotificacoesFalha = 0;
    
    @Override
    public NotificacaoResponse notificarPedidoPronto(Long pedidoId, String clienteEmail, String numeroPedido) {
        logger.info("Notificando pedido pronto - Pedido: {}, Cliente: {}", numeroPedido, clienteEmail);
        
        String titulo = "🍔 Seu pedido está pronto!";
        String mensagem = String.format(
            "Olá! Seu pedido #%s está pronto para retirada. " +
            "Dirija-se ao balcão de retirada com este número. " +
            "Obrigado pela preferência!",
            numeroPedido
        );
        
        NotificacaoRequest request = new NotificacaoRequest(clienteEmail, titulo, mensagem, 
                                                          "PEDIDO_PRONTO", pedidoId, numeroPedido);
        request.setUrgente(true);
        
        return enviarNotificacao(request);
    }
    
    @Override
    public NotificacaoResponse notificarMudancaStatus(Long pedidoId, String clienteEmail, 
                                                    StatusPedido statusAnterior, StatusPedido novoStatus, 
                                                    String numeroPedido) {
        logger.info("Notificando mudança de status - Pedido: {}, Status: {} -> {}", 
                   numeroPedido, statusAnterior, novoStatus);
        
        String titulo = "📋 Status do seu pedido atualizado";
        String mensagem = String.format(
            "Seu pedido #%s teve o status atualizado para: %s. " +
            "Acompanhe o progresso pelo painel de pedidos.",
            numeroPedido, novoStatus.getDescricao()
        );
        
        NotificacaoRequest request = new NotificacaoRequest(clienteEmail, titulo, mensagem, 
                                                          "MUDANCA_STATUS", pedidoId, numeroPedido);
        
        return enviarNotificacao(request);
    }
    
    @Override
    public NotificacaoResponse notificarPagamentoAprovado(Long pedidoId, String clienteEmail, 
                                                        String numeroPedido, String valorPago) {
        logger.info("Notificando pagamento aprovado - Pedido: {}, Valor: {}", numeroPedido, valorPago);
        
        String titulo = "✅ Pagamento aprovado!";
        String mensagem = String.format(
            "Seu pagamento de R$ %s foi aprovado com sucesso! " +
            "Pedido #%s confirmado e enviado para a cozinha. " +
            "Tempo estimado: 15-20 minutos.",
            valorPago, numeroPedido
        );
        
        NotificacaoRequest request = new NotificacaoRequest(clienteEmail, titulo, mensagem, 
                                                          "PAGAMENTO_APROVADO", pedidoId, numeroPedido);
        request.setUrgente(true);
        
        return enviarNotificacao(request);
    }
    
    @Override
    public NotificacaoResponse notificarPagamentoRecusado(Long pedidoId, String clienteEmail, 
                                                        String numeroPedido, String motivo) {
        logger.info("Notificando pagamento recusado - Pedido: {}, Motivo: {}", numeroPedido, motivo);
        
        String titulo = "❌ Pagamento não aprovado";
        String mensagem = String.format(
            "Infelizmente seu pagamento para o pedido #%s não foi aprovado. " +
            "Motivo: %s. " +
            "Tente novamente ou escolha outra forma de pagamento.",
            numeroPedido, motivo
        );
        
        NotificacaoRequest request = new NotificacaoRequest(clienteEmail, titulo, mensagem, 
                                                          "PAGAMENTO_RECUSADO", pedidoId, numeroPedido);
        request.setUrgente(true);
        
        return enviarNotificacao(request);
    }
    
    @Override
    public NotificacaoResponse enviarNotificacao(NotificacaoRequest request) {
        try {
            if (!servicoDisponivel) {
                logger.warn("Serviço de notificação indisponível");
                totalNotificacoesFalha++;
                return NotificacaoResponse.falha(request.getDestinatario(), request.getTitulo(), 
                                               "Serviço de notificação temporariamente indisponível");
            }
            
            // Simular envio de notificação
            String notificacaoId = UUID.randomUUID().toString();
            
            // Log da notificação
            logger.info("Enviando notificação - ID: {}, Destinatário: {}, Tipo: {}, Urgente: {}", 
                       notificacaoId, request.getDestinatario(), request.getTipo(), request.isUrgente());
            
            // Simular tempo de processamento
            Thread.sleep(100);
            
            totalNotificacoesEnviadas++;
            
            return NotificacaoResponse.sucesso(notificacaoId, request.getDestinatario(), 
                                             request.getTitulo(), request.getTipo(), 
                                             request.getPedidoId(), request.getNumeroPedido());
            
        } catch (Exception e) {
            logger.error("Erro ao enviar notificação: {}", e.getMessage(), e);
            totalNotificacoesFalha++;
            return NotificacaoResponse.falha(request.getDestinatario(), request.getTitulo(), 
                                           "Erro interno: " + e.getMessage());
        }
    }
    
    @Override
    public List<NotificacaoResponse> enviarNotificacaoEmMassa(NotificacaoRequest request, List<String> destinatarios) {
        logger.info("Enviando notificação em massa para {} destinatários", destinatarios.size());
        
        return destinatarios.stream()
            .map(destinatario -> {
                NotificacaoRequest requestIndividual = new NotificacaoRequest(
                    destinatario, request.getTitulo(), request.getMensagem()
                );
                requestIndividual.setTipo(request.getTipo());
                requestIndividual.setCanal(request.getCanal());
                return enviarNotificacao(requestIndividual);
            })
            .collect(Collectors.toList());
    }
    
    @Override
    public List<NotificacaoResponse> enviarCampanhaPromocional(String titulo, String mensagem, List<String> destinatarios) {
        logger.info("Enviando campanha promocional para {} clientes", destinatarios.size());
        
        NotificacaoRequest request = new NotificacaoRequest("", titulo, mensagem);
        request.setTipo("CAMPANHA_PROMOCIONAL");
        request.setCanal("EMAIL");
        
        return enviarNotificacaoEmMassa(request, destinatarios);
    }
    
    @Override
    public NotificacaoResponse notificarCozinhaNovoPedido(Long pedidoId, String numeroPedido, String itens) {
        logger.info("Notificando cozinha sobre novo pedido: {}", numeroPedido);
        
        String titulo = "🍳 Novo pedido na cozinha";
        String mensagem = String.format(
            "Novo pedido #%s recebido:\n%s\n\nInicie a preparação!",
            numeroPedido, itens
        );
        
        NotificacaoRequest request = new NotificacaoRequest("cozinha@fastfood.com", titulo, mensagem, 
                                                          "NOVO_PEDIDO_COZINHA", pedidoId, numeroPedido);
        request.setUrgente(true);
        request.setCanal("SISTEMA");
        
        return enviarNotificacao(request);
    }
    
    @Override
    public NotificacaoResponse alertarTempoEsperaElevado(Long pedidoId, String numeroPedido, int tempoEspera) {
        logger.warn("Alerta de tempo de espera elevado - Pedido: {}, Tempo: {} min", numeroPedido, tempoEspera);
        
        String titulo = "⚠️ Alerta: Tempo de espera elevado";
        String mensagem = String.format(
            "ATENÇÃO: Pedido #%s está com tempo de espera de %d minutos. " +
            "Verificar status na cozinha e priorizar se necessário.",
            numeroPedido, tempoEspera
        );
        
        NotificacaoRequest request = new NotificacaoRequest("gerencia@fastfood.com", titulo, mensagem, 
                                                          "ALERTA_TEMPO_ESPERA", pedidoId, numeroPedido);
        request.setUrgente(true);
        request.setCanal("SISTEMA");
        
        return enviarNotificacao(request);
    }
    
    @Override
    public boolean isServicoDisponivel() {
        return servicoDisponivel;
    }
    
    @Override
    public NotificacaoResponse obterEstatisticas() {
        String titulo = "Estatísticas de Notificações";
        String mensagem = String.format(
            "Total enviadas: %d\nTotal falhas: %d\nTaxa de sucesso: %.2f%%\nServiço disponível: %s",
            totalNotificacoesEnviadas,
            totalNotificacoesFalha,
            totalNotificacoesEnviadas > 0 ? 
                (totalNotificacoesEnviadas * 100.0 / (totalNotificacoesEnviadas + totalNotificacoesFalha)) : 0.0,
            servicoDisponivel ? "Sim" : "Não"
        );
        
        NotificacaoResponse response = new NotificacaoResponse();
        response.setId("STATS-" + UUID.randomUUID().toString());
        response.setTitulo(titulo);
        response.setMensagem(mensagem);
        response.setStatus("ESTATISTICAS");
        response.setSucesso(true);
        response.setEnviadaEm(LocalDateTime.now());
        
        return response;
    }
    
    /**
     * Método para simular indisponibilidade do serviço (para testes).
     */
    public void simularIndisponibilidade() {
        this.servicoDisponivel = false;
        logger.warn("Serviço de notificação marcado como indisponível");
    }
    
    /**
     * Método para restaurar disponibilidade do serviço.
     */
    public void restaurarDisponibilidade() {
        this.servicoDisponivel = true;
        logger.info("Serviço de notificação restaurado");
    }
}

