package com.marianapetrolini.fastfood.domain.entities;

import com.marianapetrolini.fastfood.domain.exceptions.DomainException;
import com.marianapetrolini.fastfood.domain.valueobjects.CPF;
import com.marianapetrolini.fastfood.domain.valueobjects.Email;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Entidade que representa um cliente do sistema.
 * Contém as regras de negócio relacionadas aos clientes.
 */
public class Cliente {
    
    private Long id;
    private String nome;
    private CPF cpf;
    private Email email;
    private String telefone;
    private boolean ativo;
    private boolean aceitaCampanhas;
    private int totalPedidos;
    private LocalDateTime ultimoPedido;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
    
    // Construtor para criação de novos clientes completo
    public Cliente(String nome, CPF cpf, Email email, String telefone, boolean aceitaCampanhas) {
        this.setNome(nome);
        this.setCpf(cpf);
        this.setEmail(email);
        this.setTelefone(telefone);
        this.aceitaCampanhas = aceitaCampanhas;
        this.ativo = true;
        this.totalPedidos = 0;
        this.criadoEm = LocalDateTime.now();
        this.atualizadoEm = LocalDateTime.now();
    }
    
    // Construtor para criação de novos clientes
    public Cliente(String nome, CPF cpf, Email email) {
        this(nome, cpf, email, null, true);
    }
    
    // Construtor para cliente sem CPF (cliente anônimo)
    public Cliente(String nome, Email email) {
        this(nome, null, email, null, false);
    }
    
    // Construtor para reconstrução (usado pelos adapters)
    public Cliente(Long id, String nome, CPF cpf, Email email, 
                   LocalDateTime criadoEm, LocalDateTime atualizadoEm) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.ativo = true;
        this.aceitaCampanhas = true;
        this.totalPedidos = 0;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
    }
    
    // Construtor completo para reconstrução
    public Cliente(Long id, String nome, CPF cpf, Email email, String telefone,
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
     * Atualiza os dados do cliente.
     */
    public void atualizar(String nome, Email email, String telefone, boolean aceitaCampanhas) {
        this.setNome(nome);
        this.setEmail(email);
        this.setTelefone(telefone);
        this.aceitaCampanhas = aceitaCampanhas;
        this.atualizadoEm = LocalDateTime.now();
    }
    
    /**
     * Ativa o cliente.
     */
    public void ativar() {
        this.ativo = true;
        this.atualizadoEm = LocalDateTime.now();
    }
    
    /**
     * Desativa o cliente.
     */
    public void desativar() {
        this.ativo = false;
        this.atualizadoEm = LocalDateTime.now();
    }
    
    /**
     * Incrementa o contador de pedidos.
     */
    public void incrementarPedidos() {
        this.totalPedidos++;
        this.ultimoPedido = LocalDateTime.now();
        this.atualizadoEm = LocalDateTime.now();
    }
    
    /**
     * Verifica se o cliente é identificado (possui CPF).
     */
    public boolean isIdentificado() {
        return this.cpf != null;
    }
    
    /**
     * Verifica se o cliente é elegível para campanhas.
     */
    public boolean isElegivelParaCampanhas() {
        return this.ativo && this.aceitaCampanhas && this.email != null;
    }
    
    /**
     * Verifica se o cliente é ativo (fez pelo menos um pedido).
     */
    public boolean isClienteAtivo() {
        return this.totalPedidos > 0;
    }
    
    private void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new DomainException("Nome do cliente não pode ser nulo ou vazio");
        }
        if (nome.length() > 100) {
            throw new DomainException("Nome do cliente não pode ter mais de 100 caracteres");
        }
        this.nome = nome.trim();
    }
    
    private void setCpf(CPF cpf) {
        this.cpf = cpf;
    }
    
    private void setEmail(Email email) {
        if (email == null) {
            throw new DomainException("Email do cliente não pode ser nulo");
        }
        this.email = email;
    }
    
    private void setTelefone(String telefone) {
        if (telefone != null && telefone.length() > 20) {
            throw new DomainException("Telefone não pode ter mais de 20 caracteres");
        }
        this.telefone = telefone;
    }
    
    // Getters
    public Long getId() {
        return id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public CPF getCpf() {
        return cpf;
    }
    
    public Email getEmail() {
        return email;
    }
    
    public String getTelefone() {
        return telefone;
    }
    
    public boolean isAtivo() {
        return ativo;
    }
    
    public boolean isAceitaCampanhas() {
        return aceitaCampanhas;
    }
    
    public int getTotalPedidos() {
        return totalPedidos;
    }
    
    public LocalDateTime getUltimoPedido() {
        return ultimoPedido;
    }
    
    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }
    
    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id) ||
               (Objects.equals(email, cliente.email)) ||
               (cpf != null && Objects.equals(cpf, cliente.cpf));
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, email, cpf);
    }
    
    @Override
    public String toString() {
        return String.format("Cliente{id=%d, nome='%s', email='%s', ativo=%s, totalPedidos=%d}", 
                           id, nome, email != null ? email.getValor() : null, ativo, totalPedidos);
    }
}

