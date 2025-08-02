package com.marianapetrolini.fastfood.domain.valueobjects;

import com.marianapetrolini.fastfood.domain.exceptions.DomainException;

import java.util.Objects;

/**
 * Value Object que representa um CPF válido.
 * Garante que apenas CPFs válidos sejam criados no domínio.
 */
public final class CPF {
    
    private final String valor;
    
    public CPF(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new DomainException("CPF não pode ser nulo ou vazio");
        }
        
        String cpfLimpo = limparCPF(valor);
        if (!isValido(cpfLimpo)) {
            throw new DomainException("CPF inválido: " + valor);
        }
        
        this.valor = cpfLimpo;
    }
    
    public String getValor() {
        return valor;
    }
    
    public String getValorFormatado() {
        return valor.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }
    
    private String limparCPF(String cpf) {
        return cpf.replaceAll("[^0-9]", "");
    }
    
    private boolean isValido(String cpf) {
        if (cpf.length() != 11) {
            return false;
        }
        
        // Verifica se todos os dígitos são iguais
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }
        
        // Validação dos dígitos verificadores
        try {
            int[] digitos = cpf.chars().map(c -> c - '0').toArray();
            
            // Primeiro dígito verificador
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += digitos[i] * (10 - i);
            }
            int primeiroDigito = 11 - (soma % 11);
            if (primeiroDigito >= 10) primeiroDigito = 0;
            
            if (digitos[9] != primeiroDigito) {
                return false;
            }
            
            // Segundo dígito verificador
            soma = 0;
            for (int i = 0; i < 10; i++) {
                soma += digitos[i] * (11 - i);
            }
            int segundoDigito = 11 - (soma % 11);
            if (segundoDigito >= 10) segundoDigito = 0;
            
            return digitos[10] == segundoDigito;
            
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CPF cpf = (CPF) o;
        return Objects.equals(valor, cpf.valor);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
    
    @Override
    public String toString() {
        return getValorFormatado();
    }
}

