package com.marianapetrolini.fastfood.infrastructure.config;

import com.marianapetrolini.fastfood.application.ports.input.ProdutoUseCase;
import com.marianapetrolini.fastfood.application.usecases.produto.ProdutoUseCaseImpl;
import com.marianapetrolini.fastfood.domain.repositories.ProdutoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração dos use cases da aplicação.
 * Define os beans dos use cases com suas dependências.
 */
@Configuration
public class UseCaseConfig {
    
    /**
     * Configura o use case de produtos.
     * 
     * @param produtoRepository Repositório de produtos
     * @return Use case de produtos
     */
    @Bean
    public ProdutoUseCase produtoUseCase(ProdutoRepository produtoRepository) {
        return new ProdutoUseCaseImpl(produtoRepository);
    }
}

