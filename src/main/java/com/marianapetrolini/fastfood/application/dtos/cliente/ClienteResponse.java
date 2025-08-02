package com.marianapetrolini.fastfood.application.dtos.cliente;

import com.marianapetrolini.fastfood.domain.entities.Cliente;

import java.time.LocalDateTime;

/**
 * DTO para resposta de cliente.
 */
public class ClienteResponse {
    
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private boolean ativo;
    private boolean aceitaCampanhas;
    private int totalPedidos;
    private LocalDateTime ultimoPedido;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
    
    public ClienteResponse() {
    }
    
    public ClienteResponse(Long id, String nome, String cpf, String email, String telefone,
                          boolean ativo, boolean aceitaCampanhas, int totalPedidos,
                          LocalDateTime ultimoPedido, LocalDateTime criadoEm, LocalDateTime atualizadoEm) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.ativo = ativo;
        this.aceitaCampanhas = aceitaCampanhas;
        this.totalPedidos = totalPedidos;
        this.ultimoPedido = ultimoPedido;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
    }
    
    /**
     * Cria um ClienteResponse a partir de uma entidade Cliente.
     * 
     * @param cliente Entidade cliente
     * @return DTO de resposta
     */
    public static ClienteResponse fromEntity(Cliente cliente) {
        return new ClienteResponse(
            cliente.getId(),
            cliente.getNome(),
            cliente.getCpf() != null ? cliente.getCpf().getValorFormatado() : null,
            cliente.getEmail().getValor(),
            cliente.getTelefone(),
            cliente.isAtivo(),
            cliente.isAceitaCampanhas(),
            cliente.getTotalPedidos(),
            cliente.getUltimoPedido(),
            cliente.getCriadoEm(),
            cliente.getAtualizadoEm()
        );
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getCpf() {
        return cpf;
    }
    
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getTelefone() {
        return telefone;
    }
    
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    public boolean isAtivo() {
        return ativo;
    }
    
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    
    public boolean isAceitaCampanhas() {
        return aceitaCampanhas;
    }
    
    public void setAceitaCampanhas(boolean aceitaCampanhas) {
        this.aceitaCampanhas = aceitaCampanhas;
    }
    
    public int getTotalPedidos() {
        return totalPedidos;
    }
    
    public void setTotalPedidos(int totalPedidos) {
        this.totalPedidos = totalPedidos;
    }
    
    public LocalDateTime getUltimoPedido() {
        return ultimoPedido;
    }
    
    public void setUltimoPedido(LocalDateTime ultimoPedido) {
        this.ultimoPedido = ultimoPedido;
    }
    
    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }
    
    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }
    
    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }
    
    public void setAtualizadoEm(LocalDateTime atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }
    
    @Override
    public String toString() {
        return String.format("ClienteResponse{id=%d, nome='%s', email='%s', ativo=%s, totalPedidos=%d}", 
                           id, nome, email, ativo, totalPedidos);
    }
}

