package com.marianapetrolini.fastfood.infrastructure.adapters.persistence;

import com.marianapetrolini.fastfood.domain.entities.Produto;
import com.marianapetrolini.fastfood.domain.repositories.ProdutoRepository;
import com.marianapetrolini.fastfood.domain.valueobjects.CategoriaProduto;
import com.marianapetrolini.fastfood.infrastructure.adapters.persistence.jpa.ProdutoJpaEntity;
import com.marianapetrolini.fastfood.infrastructure.adapters.persistence.jpa.ProdutoJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adapter que implementa o repositório de produtos usando JPA.
 * Converte entre entidades de domínio e entidades JPA.
 */
@Component
public class ProdutoRepositoryAdapter implements ProdutoRepository {
    
    private final ProdutoJpaRepository jpaRepository;
    
    public ProdutoRepositoryAdapter(ProdutoJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }
    
    @Override
    public Produto salvar(Produto produto) {
        ProdutoJpaEntity jpaEntity;
        
        if (produto.getId() == null) {
            // Novo produto
            jpaEntity = ProdutoJpaEntity.fromDomainEntity(produto);
        } else {
            // Produto existente - buscar e atualizar
            jpaEntity = jpaRepository.findById(produto.getId())
                .orElse(ProdutoJpaEntity.fromDomainEntity(produto));
            jpaEntity.updateFromDomainEntity(produto);
        }
        
        ProdutoJpaEntity savedEntity = jpaRepository.save(jpaEntity);
        return savedEntity.toDomainEntity();
    }
    
    @Override
    public Optional<Produto> buscarPorId(Long id) {
        return jpaRepository.findById(id)
            .map(ProdutoJpaEntity::toDomainEntity);
    }
    
    @Override
    public List<Produto> buscarTodos() {
        return jpaRepository.findAllByOrderByCategoriaAscNomeAsc().stream()
            .map(ProdutoJpaEntity::toDomainEntity)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Produto> buscarPorCategoria(CategoriaProduto categoria) {
        return jpaRepository.findByCategoria(categoria).stream()
            .map(ProdutoJpaEntity::toDomainEntity)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Produto> buscarDisponiveis() {
        return jpaRepository.findByDisponivelTrueOrderByCategoriaAscNomeAsc().stream()
            .map(ProdutoJpaEntity::toDomainEntity)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Produto> buscarDisponiveisPorCategoria(CategoriaProduto categoria) {
        return jpaRepository.findByCategoriaAndDisponivelTrue(categoria).stream()
            .map(ProdutoJpaEntity::toDomainEntity)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Produto> buscarPorNome(String nome) {
        return jpaRepository.findByNomeContainingIgnoreCase(nome).stream()
            .map(ProdutoJpaEntity::toDomainEntity)
            .collect(Collectors.toList());
    }
    
    @Override
    public boolean existePorNome(String nome) {
        return jpaRepository.existsByNome(nome);
    }
    
    @Override
    public boolean existePorNomeExcluindoId(String nome, Long idExcluir) {
        return jpaRepository.existsByNomeAndIdNot(nome, idExcluir);
    }
    
    @Override
    public boolean remover(Long id) {
        if (jpaRepository.existsById(id)) {
            jpaRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    @Override
    public long contar() {
        return jpaRepository.count();
    }
    
    @Override
    public long contarPorCategoria(CategoriaProduto categoria) {
        return jpaRepository.countByCategoria(categoria);
    }
    
    @Override
    public long contarDisponiveis() {
        return jpaRepository.countByDisponivelTrue();
    }
}

