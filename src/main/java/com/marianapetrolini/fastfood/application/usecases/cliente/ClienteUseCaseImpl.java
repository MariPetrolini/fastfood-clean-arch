package com.marianapetrolini.fastfood.application.usecases.cliente;

import com.marianapetrolini.fastfood.application.dtos.cliente.AtualizarClienteRequest;
import com.marianapetrolini.fastfood.application.dtos.cliente.ClienteResponse;
import com.marianapetrolini.fastfood.application.dtos.cliente.CriarClienteRequest;
import com.marianapetrolini.fastfood.application.ports.input.ClienteUseCase;
import com.marianapetrolini.fastfood.domain.entities.Cliente;
import com.marianapetrolini.fastfood.domain.exceptions.DomainException;
import com.marianapetrolini.fastfood.domain.repositories.ClienteRepository;
import com.marianapetrolini.fastfood.domain.valueobjects.CPF;
import com.marianapetrolini.fastfood.domain.valueobjects.Email;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementação dos casos de uso relacionados a clientes.
 */
@Service
public class ClienteUseCaseImpl implements ClienteUseCase {
    
    private final ClienteRepository clienteRepository;
    
    public ClienteUseCaseImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
    
    @Override
    public ClienteResponse criarCliente(CriarClienteRequest request) {
        if (request == null) {
            throw new DomainException("Dados do cliente não podem ser nulos");
        }
        
        // Verificar se já existe cliente com mesmo CPF ou email
        if (request.getCpf() != null && !request.getCpf().trim().isEmpty()) {
            clienteRepository.buscarPorCpf(request.getCpf())
                .ifPresent(cliente -> {
                    throw new DomainException("Já existe um cliente com o CPF informado");
                });
        }
        
        clienteRepository.buscarPorEmail(request.getEmail())
            .ifPresent(cliente -> {
                throw new DomainException("Já existe um cliente com o email informado");
            });
        
        CPF cpf = null;
        if (request.getCpf() != null && !request.getCpf().trim().isEmpty()) {
            cpf = new CPF(request.getCpf());
        }
        
        Email email = new Email(request.getEmail());
        
        Cliente cliente = new Cliente(
            request.getNome(),
            cpf,
            email,
            request.getTelefone(),
            request.isAceitaCampanhas()
        );
        
        Cliente clienteSalvo = clienteRepository.salvar(cliente);
        return ClienteResponse.fromEntity(clienteSalvo);
    }
    
    @Override
    public ClienteResponse buscarClientePorId(Long id) {
        if (id == null) {
            throw new DomainException("ID do cliente não pode ser nulo");
        }
        
        Cliente cliente = clienteRepository.buscarPorId(id)
            .orElseThrow(() -> DomainException.entidadeNaoEncontrada("Cliente", id));
        
        return ClienteResponse.fromEntity(cliente);
    }
    
    @Override
    public ClienteResponse buscarClientePorCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new DomainException("CPF não pode ser nulo ou vazio");
        }
        
        Cliente cliente = clienteRepository.buscarPorCpf(cpf)
            .orElseThrow(() -> new DomainException("Cliente com CPF " + cpf + " não encontrado"));
        
        return ClienteResponse.fromEntity(cliente);
    }
    
    @Override
    public ClienteResponse buscarClientePorEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new DomainException("Email não pode ser nulo ou vazio");
        }
        
        Cliente cliente = clienteRepository.buscarPorEmail(email)
            .orElseThrow(() -> new DomainException("Cliente com email " + email + " não encontrado"));
        
        return ClienteResponse.fromEntity(cliente);
    }
    
    @Override
    public List<ClienteResponse> listarTodosClientes() {
        return clienteRepository.buscarTodos().stream()
            .map(ClienteResponse::fromEntity)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<ClienteResponse> listarClientesAtivos() {
        return clienteRepository.buscarTodos().stream()
            .filter(Cliente::isClienteAtivo)
            .map(ClienteResponse::fromEntity)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<ClienteResponse> buscarClientesPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new DomainException("Nome não pode ser nulo ou vazio");
        }
        
        return clienteRepository.buscarPorNome(nome).stream()
            .map(ClienteResponse::fromEntity)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<ClienteResponse> listarClientesParaCampanhas() {
        return clienteRepository.buscarTodos().stream()
            .filter(Cliente::isElegivelParaCampanhas)
            .map(ClienteResponse::fromEntity)
            .collect(Collectors.toList());
    }
    
    @Override
    public ClienteResponse atualizarCliente(Long id, AtualizarClienteRequest request) {
        if (id == null) {
            throw new DomainException("ID do cliente não pode ser nulo");
        }
        if (request == null) {
            throw new DomainException("Dados para atualização não podem ser nulos");
        }
        
        Cliente cliente = clienteRepository.buscarPorId(id)
            .orElseThrow(() -> DomainException.entidadeNaoEncontrada("Cliente", id));
        
        Email email = new Email(request.getEmail());
        
        cliente.atualizar(request.getNome(), email, request.getTelefone(), request.isAceitaCampanhas());
        
        Cliente clienteAtualizado = clienteRepository.salvar(cliente);
        return ClienteResponse.fromEntity(clienteAtualizado);
    }
    
    @Override
    public ClienteResponse ativarCliente(Long id) {
        if (id == null) {
            throw new DomainException("ID do cliente não pode ser nulo");
        }
        
        Cliente cliente = clienteRepository.buscarPorId(id)
            .orElseThrow(() -> DomainException.entidadeNaoEncontrada("Cliente", id));
        
        cliente.ativar();
        
        Cliente clienteAtualizado = clienteRepository.salvar(cliente);
        return ClienteResponse.fromEntity(clienteAtualizado);
    }
    
    @Override
    public ClienteResponse desativarCliente(Long id) {
        if (id == null) {
            throw new DomainException("ID do cliente não pode ser nulo");
        }
        
        Cliente cliente = clienteRepository.buscarPorId(id)
            .orElseThrow(() -> DomainException.entidadeNaoEncontrada("Cliente", id));
        
        cliente.desativar();
        
        Cliente clienteAtualizado = clienteRepository.salvar(cliente);
        return ClienteResponse.fromEntity(clienteAtualizado);
    }
    
    @Override
    public void removerCliente(Long id) {
        if (id == null) {
            throw new DomainException("ID do cliente não pode ser nulo");
        }
        
        Cliente cliente = clienteRepository.buscarPorId(id)
            .orElseThrow(() -> DomainException.entidadeNaoEncontrada("Cliente", id));
        
        clienteRepository.remover(id);
    }
    
    @Override
    public Map<String, Object> obterEstatisticasClientes() {
        List<Cliente> todosClientes = clienteRepository.buscarTodos();
        
        long totalClientes = todosClientes.size();
        long clientesAtivos = todosClientes.stream()
            .filter(Cliente::isClienteAtivo)
            .count();
        long clientesParaCampanhas = todosClientes.stream()
            .filter(Cliente::isElegivelParaCampanhas)
            .count();
        
        double percentualAtivos = totalClientes > 0 ? 
            (clientesAtivos * 100.0 / totalClientes) : 0.0;
        double percentualCampanhas = totalClientes > 0 ? 
            (clientesParaCampanhas * 100.0 / totalClientes) : 0.0;
        
        return Map.of(
            "totalClientes", totalClientes,
            "clientesAtivos", clientesAtivos,
            "clientesParaCampanhas", clientesParaCampanhas,
            "percentualAtivos", Math.round(percentualAtivos * 100.0) / 100.0,
            "percentualCampanhas", Math.round(percentualCampanhas * 100.0) / 100.0
        );
    }
    
    @Override
    public long contarClientes() {
        return clienteRepository.buscarTodos().size();
    }
    
    @Override
    public long contarClientesAtivos() {
        return clienteRepository.buscarTodos().stream()
            .filter(Cliente::isClienteAtivo)
            .count();
    }
}

