package com.marianapetrolini.fastfood.application.dtos.pedido;

import java.time.LocalDateTime;

/**
 * DTO para resposta de tempo de espera de pedidos.
 */
public class TempoEsperaResponse {
    
    private Long pedidoId;
    private String numeroPedido;
    private String status;
    private LocalDateTime criadoEm;
    private LocalDateTime iniciadoPreparacaoEm;
    private LocalDateTime prontoEm;
    private LocalDateTime finalizadoEm;
    private int tempoEsperaMinutos;
    private int tempoPreparacaoMinutos;
    private int tempoTotalMinutos;
    private boolean tempoElevado;
    private String alertaTempoEspera;
    
    public TempoEsperaResponse() {
    }
    
    public TempoEsperaResponse(Long pedidoId, String numeroPedido, String status,
                             LocalDateTime criadoEm, LocalDateTime iniciadoPreparacaoEm,
                             LocalDateTime prontoEm, LocalDateTime finalizadoEm,
                             int tempoEsperaMinutos, int tempoPreparacaoMinutos, int tempoTotalMinutos) {
        this.pedidoId = pedidoId;
        this.numeroPedido = numeroPedido;
        this.status = status;
        this.criadoEm = criadoEm;
        this.iniciadoPreparacaoEm = iniciadoPreparacaoEm;
        this.prontoEm = prontoEm;
        this.finalizadoEm = finalizadoEm;
        this.tempoEsperaMinutos = tempoEsperaMinutos;
        this.tempoPreparacaoMinutos = tempoPreparacaoMinutos;
        this.tempoTotalMinutos = tempoTotalMinutos;
        this.tempoElevado = tempoTotalMinutos > 30; // Considera elevado acima de 30 minutos
        this.alertaTempoEspera = determinarAlerta(tempoTotalMinutos);
    }
    
    private String determinarAlerta(int tempoTotal) {
        if (tempoTotal <= 15) {
            return "NORMAL";
        } else if (tempoTotal <= 30) {
            return "ATENCAO";
        } else if (tempoTotal <= 45) {
            return "ELEVADO";
        } else {
            return "CRITICO";
        }
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
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }
    
    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }
    
    public LocalDateTime getIniciadoPreparacaoEm() {
        return iniciadoPreparacaoEm;
    }
    
    public void setIniciadoPreparacaoEm(LocalDateTime iniciadoPreparacaoEm) {
        this.iniciadoPreparacaoEm = iniciadoPreparacaoEm;
    }
    
    public LocalDateTime getProntoEm() {
        return prontoEm;
    }
    
    public void setProntoEm(LocalDateTime prontoEm) {
        this.prontoEm = prontoEm;
    }
    
    public LocalDateTime getFinalizadoEm() {
        return finalizadoEm;
    }
    
    public void setFinalizadoEm(LocalDateTime finalizadoEm) {
        this.finalizadoEm = finalizadoEm;
    }
    
    public int getTempoEsperaMinutos() {
        return tempoEsperaMinutos;
    }
    
    public void setTempoEsperaMinutos(int tempoEsperaMinutos) {
        this.tempoEsperaMinutos = tempoEsperaMinutos;
    }
    
    public int getTempoPreparacaoMinutos() {
        return tempoPreparacaoMinutos;
    }
    
    public void setTempoPreparacaoMinutos(int tempoPreparacaoMinutos) {
        this.tempoPreparacaoMinutos = tempoPreparacaoMinutos;
    }
    
    public int getTempoTotalMinutos() {
        return tempoTotalMinutos;
    }
    
    public void setTempoTotalMinutos(int tempoTotalMinutos) {
        this.tempoTotalMinutos = tempoTotalMinutos;
        this.tempoElevado = tempoTotalMinutos > 30;
        this.alertaTempoEspera = determinarAlerta(tempoTotalMinutos);
    }
    
    public boolean isTempoElevado() {
        return tempoElevado;
    }
    
    public void setTempoElevado(boolean tempoElevado) {
        this.tempoElevado = tempoElevado;
    }
    
    public String getAlertaTempoEspera() {
        return alertaTempoEspera;
    }
    
    public void setAlertaTempoEspera(String alertaTempoEspera) {
        this.alertaTempoEspera = alertaTempoEspera;
    }
    
    @Override
    public String toString() {
        return String.format("TempoEsperaResponse{pedidoId=%d, numeroPedido='%s', tempoTotal=%d min, alerta='%s'}", 
                           pedidoId, numeroPedido, tempoTotalMinutos, alertaTempoEspera);
    }
}

