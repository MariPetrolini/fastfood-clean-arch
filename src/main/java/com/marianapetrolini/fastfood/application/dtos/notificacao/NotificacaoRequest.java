package com.marianapetrolini.fastfood.application.dtos.notificacao;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * DTO para requisição de notificação.
 */
public class NotificacaoRequest {
    
    @NotBlank(message = "Destinatário é obrigatório")
    private String destinatario;
    
    @NotBlank(message = "Título é obrigatório")
    @Size(max = 100, message = "Título deve ter no máximo 100 caracteres")
    private String titulo;
    
    @NotBlank(message = "Mensagem é obrigatória")
    @Size(max = 500, message = "Mensagem deve ter no máximo 500 caracteres")
    private String mensagem;
    
    private String tipo;
    private String canal; // EMAIL, SMS, PUSH, etc.
    private Long pedidoId;
    private String numeroPedido;
    private LocalDateTime agendarPara;
    private Map<String, Object> parametros;
    private boolean urgente;
    
    public NotificacaoRequest() {
    }
    
    public NotificacaoRequest(String destinatario, String titulo, String mensagem) {
        this.destinatario = destinatario;
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.canal = "EMAIL";
        this.urgente = false;
    }
    
    public NotificacaoRequest(String destinatario, String titulo, String mensagem, 
                            String tipo, Long pedidoId, String numeroPedido) {
        this.destinatario = destinatario;
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.tipo = tipo;
        this.pedidoId = pedidoId;
        this.numeroPedido = numeroPedido;
        this.canal = "EMAIL";
        this.urgente = false;
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
    
    public LocalDateTime getAgendarPara() {
        return agendarPara;
    }
    
    public void setAgendarPara(LocalDateTime agendarPara) {
        this.agendarPara = agendarPara;
    }
    
    public Map<String, Object> getParametros() {
        return parametros;
    }
    
    public void setParametros(Map<String, Object> parametros) {
        this.parametros = parametros;
    }
    
    public boolean isUrgente() {
        return urgente;
    }
    
    public void setUrgente(boolean urgente) {
        this.urgente = urgente;
    }
    
    @Override
    public String toString() {
        return String.format("NotificacaoRequest{destinatario='%s', titulo='%s', tipo='%s', urgente=%s}", 
                           destinatario, titulo, tipo, urgente);
    }
}

