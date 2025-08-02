package com.marianapetrolini.fastfood.infrastructure.adapters.web.controllers;

import com.marianapetrolini.fastfood.application.dtos.cliente.AtualizarClienteRequest;
import com.marianapetrolini.fastfood.application.dtos.cliente.ClienteResponse;
import com.marianapetrolini.fastfood.application.dtos.cliente.CriarClienteRequest;
import com.marianapetrolini.fastfood.application.ports.input.ClienteUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Controller REST para operações relacionadas a clientes.
 * Adapter entre a camada web e os use cases de cliente.
 */
@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "Operações de gerenciamento de clientes")
public class ClienteController {
    
    private final ClienteUseCase clienteUseCase;
    
    public ClienteController(ClienteUseCase clienteUseCase) {
        this.clienteUseCase = clienteUseCase;
    }
    
    @PostMapping
    @Operation(summary = "Criar cliente", description = "Cria um novo cliente no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "409", description = "Cliente já existe")
    })
    public ResponseEntity<ClienteResponse> criarCliente(
            @Valid @RequestBody CriarClienteRequest request) {
        ClienteResponse cliente = clienteUseCase.criarCliente(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID", description = "Busca um cliente específico pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity<ClienteResponse> buscarCliente(
            @Parameter(description = "ID do cliente") @PathVariable Long id) {
        ClienteResponse cliente = clienteUseCase.buscarClientePorId(id);
        return ResponseEntity.ok(cliente);
    }
    
    @GetMapping("/cpf/{cpf}")
    @Operation(summary = "Buscar cliente por CPF", description = "Busca um cliente pelo CPF")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity<ClienteResponse> buscarClientePorCpf(
            @Parameter(description = "CPF do cliente") @PathVariable String cpf) {
        ClienteResponse cliente = clienteUseCase.buscarClientePorCpf(cpf);
        return ResponseEntity.ok(cliente);
    }
    
    @GetMapping("/email/{email}")
    @Operation(summary = "Buscar cliente por email", description = "Busca um cliente pelo email")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity<ClienteResponse> buscarClientePorEmail(
            @Parameter(description = "Email do cliente") @PathVariable String email) {
        ClienteResponse cliente = clienteUseCase.buscarClientePorEmail(email);
        return ResponseEntity.ok(cliente);
    }
    
    @GetMapping
    @Operation(summary = "Listar todos os clientes", description = "Lista todos os clientes cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de clientes")
    public ResponseEntity<List<ClienteResponse>> listarTodosClientes() {
        List<ClienteResponse> clientes = clienteUseCase.listarTodosClientes();
        return ResponseEntity.ok(clientes);
    }
    
    @GetMapping("/ativos")
    @Operation(summary = "Listar clientes ativos", description = "Lista clientes que fizeram pedidos")
    @ApiResponse(responseCode = "200", description = "Lista de clientes ativos")
    public ResponseEntity<List<ClienteResponse>> listarClientesAtivos() {
        List<ClienteResponse> clientes = clienteUseCase.listarClientesAtivos();
        return ResponseEntity.ok(clientes);
    }
    
    @GetMapping("/campanhas")
    @Operation(summary = "Listar clientes para campanhas", description = "Lista clientes elegíveis para campanhas promocionais")
    @ApiResponse(responseCode = "200", description = "Lista de clientes para campanhas")
    public ResponseEntity<List<ClienteResponse>> listarClientesParaCampanhas() {
        List<ClienteResponse> clientes = clienteUseCase.listarClientesParaCampanhas();
        return ResponseEntity.ok(clientes);
    }
    
    @GetMapping("/buscar")
    @Operation(summary = "Buscar clientes por nome", description = "Busca clientes que contenham o nome especificado")
    @ApiResponse(responseCode = "200", description = "Lista de clientes encontrados")
    public ResponseEntity<List<ClienteResponse>> buscarClientesPorNome(
            @Parameter(description = "Nome ou parte do nome do cliente") @RequestParam String nome) {
        List<ClienteResponse> clientes = clienteUseCase.buscarClientesPorNome(nome);
        return ResponseEntity.ok(clientes);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cliente", description = "Atualiza os dados de um cliente existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
        @ApiResponse(responseCode = "409", description = "Email já existe")
    })
    public ResponseEntity<ClienteResponse> atualizarCliente(
            @Parameter(description = "ID do cliente") @PathVariable Long id,
            @Valid @RequestBody AtualizarClienteRequest request) {
        ClienteResponse cliente = clienteUseCase.atualizarCliente(id, request);
        return ResponseEntity.ok(cliente);
    }
    
    @PatchMapping("/{id}/ativar")
    @Operation(summary = "Ativar cliente", description = "Ativa um cliente (permite receber campanhas)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente ativado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity<ClienteResponse> ativarCliente(
            @Parameter(description = "ID do cliente") @PathVariable Long id) {
        ClienteResponse cliente = clienteUseCase.ativarCliente(id);
        return ResponseEntity.ok(cliente);
    }
    
    @PatchMapping("/{id}/desativar")
    @Operation(summary = "Desativar cliente", description = "Desativa um cliente (não recebe campanhas)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente desativado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity<ClienteResponse> desativarCliente(
            @Parameter(description = "ID do cliente") @PathVariable Long id) {
        ClienteResponse cliente = clienteUseCase.desativarCliente(id);
        return ResponseEntity.ok(cliente);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Remover cliente", description = "Remove um cliente do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Cliente removido com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity<Void> removerCliente(
            @Parameter(description = "ID do cliente") @PathVariable Long id) {
        clienteUseCase.removerCliente(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/estatisticas")
    @Operation(summary = "Estatísticas de clientes", description = "Retorna estatísticas gerais dos clientes")
    @ApiResponse(responseCode = "200", description = "Estatísticas dos clientes")
    public ResponseEntity<Map<String, Object>> obterEstatisticas() {
        long totalClientes = clienteUseCase.contarClientes();
        long clientesAtivos = clienteUseCase.contarClientesAtivos();
        long clientesParaCampanhas = clienteUseCase.listarClientesParaCampanhas().size();
        
        Map<String, Object> estatisticas = Map.of(
            "totalClientes", totalClientes,
            "clientesAtivos", clientesAtivos,
            "clientesParaCampanhas", clientesParaCampanhas,
            "percentualAtivos", totalClientes > 0 ? (clientesAtivos * 100.0 / totalClientes) : 0.0
        );
        
        return ResponseEntity.ok(estatisticas);
    }
}

