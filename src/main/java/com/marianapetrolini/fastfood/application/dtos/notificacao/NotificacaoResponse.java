package com.marianapetrolini.fastfood.application.dtos.notificacao;

import java.time.LocalDateTime;

/**
 * DTO para resposta de notificação.
 */
public class NotificacaoResponse {
    
    private String id;
    private String destinatario;
    private String titulo;
    private String mensagem;
    private String tipo;
    private String canal;
    private String status; // ENVIADA, FALHA, PENDENTE, AGENDADA
    private LocalDateTime enviadaEm;
    private LocalDateTime agendadaPara;
    private String erro;
    private boolean sucesso;
    private Long pedidoId;
    private String numeroPedido;
    
    public NotificacaoResponse() {
    }
    
    public NotificacaoResponse(String id, String destinatario, String titulo, String status, boolean sucesso) {
        this.id = id;
        this.destinatario = destinatario;
        this.titulo = titulo;
        this.status = status;
        this.sucesso = sucesso;
        this.enviadaEm = LocalDateTime.now();
    }
    
    public NotificacaoResponse(String id, String destinatario, String titulo, String mensagem,
                             String tipo, String canal, String status, boolean sucesso,
                             Long pedidoId, String numeroPedido) {
        this.id = id;
        this.destinatario = destinatario;
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.tipo = tipo;
        this.canal = canal;
        this.status = status;
        this.sucesso = sucesso;
        this.pedidoId = pedidoId;
        this.numeroPedido = numeroPedido;
        this.enviadaEm = LocalDateTime.now();
    }
    
    /**
     * Cria uma resposta de sucesso.
     */
    public static NotificacaoResponse sucesso(String id, String destinatario, String titulo, 
                                            String tipo, Long pedidoId, String numeroPedido) {
        return new NotificacaoResponse(id, destinatario, titulo, null, tipo, "EMAIL", 
                                     "ENVIADA", true, pedidoId, numeroPedido);
    }
    
    /**
     * Cria uma resposta de falha.
     */
    public static NotificacaoResponse falha(String destinatario, String titulo, String erro) {
        NotificacaoResponse response = new NotificacaoResponse();
        response.setDestinatario(destinatario);
        response.setTitulo(titulo);
        response.setStatus("FALHA");
        response.setErro(erro);
        response.setSucesso(false);
        response.setEnviadaEm(LocalDateTime.now());
        return response;
    }
    
    /**
     * Cria uma resposta de notificação agendada.
     */
    public static NotificacaoResponse agendada(String id, String destinatario, String titulo, 
                                             LocalDateTime agendadaPara) {
        NotificacaoResponse response = new NotificacaoResponse();
        response.setId(id);
        response.setDestinatario(destinatario);
        response.setTitulo(titulo);
        response.setStatus("AGENDADA");
        response.setAgendadaPara(agendadaPara);
        response.setSucesso(true);
        return response;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getDestinatario() {
        return destinatario;
    }
    
    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getMensagem() {
        return mensagem;
    }
    
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public String getCanal() {
        return canal;
    }
    
    public void setCanal(String canal) {
        this.canal = canal;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public LocalDateTime getEnviadaEm() {
        return enviadaEm;
    }
    
    public void setEnviadaEm(LocalDateTime enviadaEm) {
        this.enviadaEm = enviadaEm;
    }
    
    public LocalDateTime getAgendadaPara() {
        return agendadaPara;
    }
    
    public void setAgendadaPara(LocalDateTime agendadaPara) {
        this.agendadaPara = agendadaPara;
    }
    
    public String getErro() {
        return erro;
    }
    
    public void setErro(String erro) {
        this.erro = erro;
    }
    
    public boolean isSucesso() {
        return sucesso;
    }
    
    public void setSucesso(boolean sucesso) {
        this.sucesso = sucesso;
    }
    
    public Long getPedidoId() {
        return pedidoId;
    }
    
    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }
    
    public String getNumeroPedido() {
        return numeroPedido;
    }
    
    public void setNumeroPedido(String numeroPedido) {
        this.numeroPedido = numeroPedido;
    }
    
    @Override
    public String toString() {
        return String.format("NotificacaoResponse{id='%s', destinatario='%s', status='%s', sucesso=%s}", 
                           id, destinatario, status, sucesso);
    }
}

