package com.marianapetrolini.fastfood.application.ports.output;

import com.marianapetrolini.fastfood.application.dtos.notificacao.NotificacaoRequest;
import com.marianapetrolini.fastfood.application.dtos.notificacao.NotificacaoResponse;
import com.marianapetrolini.fastfood.domain.valueobjects.StatusPedido;

import java.util.List;

/**
 * Port de saída para sistema de notificações.
 * Define as operações de notificação disponíveis.
 */
public interface NotificacaoPort {
    
    /**
     * Envia notificação quando pedido está pronto para retirada.
     * 
     * @param pedidoId ID do pedido
     * @param clienteEmail Email do cliente (se identificado)
     * @param numeroPedido Número do pedido para exibição
     * @return Resposta da notificação
     */
    NotificacaoResponse notificarPedidoPronto(Long pedidoId, String clienteEmail, String numeroPedido);
    
    /**
     * Envia notificação de mudança de status do pedido.
     * 
     * @param pedidoId ID do pedido
     * @param clienteEmail Email do cliente (se identificado)
     * @param statusAnterior Status anterior do pedido
     * @param novoStatus Novo status do pedido
     * @param numeroPedido Número do pedido para exibição
     * @return Resposta da notificação
     */
    NotificacaoResponse notificarMudancaStatus(Long pedidoId, String clienteEmail, 
                                             StatusPedido statusAnterior, StatusPedido novoStatus, 
                                             String numeroPedido);
    
    /**
     * Envia notificação de pagamento aprovado.
     * 
     * @param pedidoId ID do pedido
     * @param clienteEmail Email do cliente (se identificado)
     * @param numeroPedido Número do pedido para exibição
     * @param valorPago Valor pago
     * @return Resposta da notificação
     */
    NotificacaoResponse notificarPagamentoAprovado(Long pedidoId, String clienteEmail, 
                                                  String numeroPedido, String valorPago);
    
    /**
     * Envia notificação de pagamento recusado.
     * 
     * @param pedidoId ID do pedido
     * @param clienteEmail Email do cliente (se identificado)
     * @param numeroPedido Número do pedido para exibição
     * @param motivo Motivo da recusa
     * @return Resposta da notificação
     */
    NotificacaoResponse notificarPagamentoRecusado(Long pedidoId, String clienteEmail, 
                                                  String numeroPedido, String motivo);
    
    /**
     * Envia notificação personalizada.
     * 
     * @param request Dados da notificação
     * @return Resposta da notificação
     */
    NotificacaoResponse enviarNotificacao(NotificacaoRequest request);
    
    /**
     * Envia notificação para múltiplos destinatários.
     * 
     * @param request Dados da notificação
     * @param destinatarios Lista de emails dos destinatários
     * @return Lista de respostas das notificações
     */
    List<NotificacaoResponse> enviarNotificacaoEmMassa(NotificacaoRequest request, List<String> destinatarios);
    
    /**
     * Envia campanhas promocionais para clientes elegíveis.
     * 
     * @param titulo Título da campanha
     * @param mensagem Mensagem da campanha
     * @param destinatarios Lista de emails dos clientes
     * @return Lista de respostas das notificações
     */
    List<NotificacaoResponse> enviarCampanhaPromocional(String titulo, String mensagem, List<String> destinatarios);
    
    /**
     * Notifica a cozinha sobre novo pedido.
     * 
     * @param pedidoId ID do pedido
     * @param numeroPedido Número do pedido
     * @param itens Descrição dos itens do pedido
     * @return Resposta da notificação
     */
    NotificacaoResponse notificarCozinhaNovoPedido(Long pedidoId, String numeroPedido, String itens);
    
    /**
     * Alerta sobre pedidos com tempo de espera elevado.
     * 
     * @param pedidoId ID do pedido
     * @param numeroPedido Número do pedido
     * @param tempoEspera Tempo de espera em minutos
     * @return Resposta da notificação
     */
    NotificacaoResponse alertarTempoEsperaElevado(Long pedidoId, String numeroPedido, int tempoEspera);
    
    /**
     * Verifica se o serviço de notificação está disponível.
     * 
     * @return true se o serviço está disponível
     */
    boolean isServicoDisponivel();
    
    /**
     * Obtém estatísticas de notificações enviadas.
     * 
     * @return Estatísticas de notificações
     */
    NotificacaoResponse obterEstatisticas();
}

