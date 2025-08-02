package com.marianapetrolini.fastfood.domain.valueobjects;

import com.marianapetrolini.fastfood.domain.exceptions.DomainException;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Value Object que representa um email válido.
 * Garante que apenas emails válidos sejam criados no domínio.
 */
public final class Email {
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
    );
    
    private final String valor;
    
    public Email(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new DomainException("Email não pode ser nulo ou vazio");
        }
        
        String emailLimpo = valor.trim().toLowerCase();
        if (!isValido(emailLimpo)) {
            throw new DomainException("Email inválido: " + valor);
        }
        
        this.valor = emailLimpo;
    }
    
    public String getValor() {
        return valor;
    }
    
    private boolean isValido(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(valor, email.valor);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
    
    @Override
    public String toString() {
        return valor;
    }
}

