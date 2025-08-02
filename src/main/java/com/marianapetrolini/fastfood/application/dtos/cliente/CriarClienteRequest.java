package com.marianapetrolini.fastfood.application.dtos.cliente;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * DTO para requisição de criação de cliente.
 */
public class CriarClienteRequest {
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String nome;
    
    @Size(min = 11, max = 11, message = "CPF deve ter 11 dígitos")
    private String cpf;
    
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ter formato válido")
    @Size(max = 150, message = "Email deve ter no máximo 150 caracteres")
    private String email;
    
    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
    private String telefone;
    
    private boolean aceitaCampanhas = true;
    
    public CriarClienteRequest() {
    }
    
    public CriarClienteRequest(String nome, String cpf, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
    }
    
    public CriarClienteRequest(String nome, String cpf, String email, String telefone, boolean aceitaCampanhas) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.aceitaCampanhas = aceitaCampanhas;
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
    
    public boolean isAceitaCampanhas() {
        return aceitaCampanhas;
    }
    
    public void setAceitaCampanhas(boolean aceitaCampanhas) {
        this.aceitaCampanhas = aceitaCampanhas;
    }
    
    @Override
    public String toString() {
        return String.format("CriarClienteRequest{nome='%s', email='%s', aceitaCampanhas=%s}", 
                           nome, email, aceitaCampanhas);
    }
}

