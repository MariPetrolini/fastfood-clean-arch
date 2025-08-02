# 📡 FastFood API Collection

## 🔗 Links Importantes

- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **API Docs**: `http://localhost:8080/v3/api-docs`
- **Health Check**: `http://localhost:8080/actuator/health`
- **Postman Collection**: [Download JSON](./postman_collection.json)

## 🏷️ Categorias de APIs

### 1. **Produtos** 🍔
### 2. **Pedidos** 📋
### 3. **Pagamentos** 💳
### 4. **Clientes** 👥
### 5. **Sistema** ⚙️

---

## 🍔 **1. PRODUTOS**

### **1.1 - Listar Categorias**
```http
GET /api/produtos/categorias
```
**Descrição**: Retorna todas as categorias de produtos disponíveis.

**Response**:
```json
[
  "LANCHE",
  "ACOMPANHAMENTO", 
  "BEBIDA",
  "SOBREMESA"
]
```

**cURL**:
```bash
curl -X GET "http://localhost:8080/api/produtos/categorias" \
  -H "Accept: application/json"
```

---

### **1.2 - Listar Produtos Disponíveis**
```http
GET /api/produtos/disponiveis
```
**Descrição**: Retorna todos os produtos disponíveis para venda.

**Response**:
```json
[
  {
    "id": 1,
    "nome": "Big Mac",
    "descricao": "Dois hambúrgueres, alface, queijo, molho especial, cebola, picles, pão com gergelim",
    "preco": 25.90,
    "categoria": "LANCHE",
    "disponivel": true,
    "tempoPreparoMinutos": 8
  }
]
```

**cURL**:
```bash
curl -X GET "http://localhost:8080/api/produtos/disponiveis" \
  -H "Accept: application/json"
```

---

### **1.3 - Buscar Produtos por Categoria**
```http
GET /api/produtos/categoria/{categoria}
```
**Parâmetros**:
- `categoria` (path): LANCHE | ACOMPANHAMENTO | BEBIDA | SOBREMESA

**Exemplo**:
```http
GET /api/produtos/categoria/LANCHE
```

**Response**:
```json
[
  {
    "id": 1,
    "nome": "Big Mac",
    "descricao": "Dois hambúrgueres, alface, queijo, molho especial, cebola, picles, pão com gergelim",
    "preco": 25.90,
    "categoria": "LANCHE",
    "disponivel": true,
    "tempoPreparoMinutos": 8
  }
]
```

**cURL**:
```bash
curl -X GET "http://localhost:8080/api/produtos/categoria/LANCHE" \
  -H "Accept: application/json"
```

---

### **1.4 - Criar Produto**
```http
POST /api/produtos
```
**Request Body**:
```json
{
  "nome": "McChicken Deluxe",
  "descricao": "Frango empanado, alface, tomate, maionese, pão brioche",
  "preco": 28.90,
  "categoria": "LANCHE",
  "tempoPreparoMinutos": 10
}
```

**Response**:
```json
{
  "id": 15,
  "nome": "McChicken Deluxe",
  "descricao": "Frango empanado, alface, tomate, maionese, pão brioche",
  "preco": 28.90,
  "categoria": "LANCHE",
  "disponivel": true,
  "tempoPreparoMinutos": 10
}
```

**cURL**:
```bash
curl -X POST "http://localhost:8080/api/produtos" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "McChicken Deluxe",
    "descricao": "Frango empanado, alface, tomate, maionese, pão brioche",
    "preco": 28.90,
    "categoria": "LANCHE",
    "tempoPreparoMinutos": 10
  }'
```

---

### **1.5 - Atualizar Produto**
```http
PUT /api/produtos/{id}
```
**Parâmetros**:
- `id` (path): ID do produto

**Request Body**:
```json
{
  "nome": "Big Mac Especial",
  "descricao": "Dois hambúrgueres, alface, queijo, molho especial, cebola, picles, pão com gergelim",
  "preco": 27.90,
  "categoria": "LANCHE",
  "tempoPreparoMinutos": 8
}
```

**cURL**:
```bash
curl -X PUT "http://localhost:8080/api/produtos/1" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Big Mac Especial",
    "descricao": "Dois hambúrgueres, alface, queijo, molho especial, cebola, picles, pão com gergelim",
    "preco": 27.90,
    "categoria": "LANCHE",
    "tempoPreparoMinutos": 8
  }'
```

---

### **1.6 - Remover Produto**
```http
DELETE /api/produtos/{id}
```
**Parâmetros**:
- `id` (path): ID do produto

**cURL**:
```bash
curl -X DELETE "http://localhost:8080/api/produtos/1"
```

---

## 📋 **2. PEDIDOS**

### **2.1 - Realizar Checkout**
```http
POST /api/pedidos/checkout
```
**Descrição**: Cria um novo pedido com os produtos solicitados e retorna a identificação do pedido.

**Request Body**:
```json
{
  "cliente": {
    "nome": "João Silva",
    "email": "joao.silva@email.com",
    "telefone": "(11) 99999-9999",
    "cpf": "12345678901"
  },
  "itens": [
    {
      "produtoId": 1,
      "quantidade": 2,
      "observacoes": "Sem cebola"
    },
    {
      "produtoId": 5,
      "quantidade": 1,
      "observacoes": "Bem gelada"
    }
  ],
  "metodoPagamento": "PIX",
  "observacoes": "Entrega rápida, por favor"
}
```

**Response**:
```json
{
  "pedidoId": 4,
  "statusPedido": "RECEBIDO",
  "valorTotal": 60.70,
  "dataCriacao": "2025-07-29T20:01:21.482783",
  "pagamento": {
    "pagamentoId": 4,
    "status": "PENDENTE",
    "metodoPagamento": "PIX",
    "transacaoId": "TXN-149C7215",
    "qrCodeData": "00020126580014BR.GOV.BCB.PIX0136e0cdae04-f01f-4a26-b0eb-a32cc2d9107852040000530398654060.705802BR6009SAO PAULO62070503***6304",
    "urlPagamento": "https://api.mercadopago.com/checkout/pro/v1/TXN-149C7215"
  }
}
```

**cURL**:
```bash
curl -X POST "http://localhost:8080/api/pedidos/checkout" \
  -H "Content-Type: application/json" \
  -d '{
    "cliente": {
      "nome": "João Silva",
      "email": "joao.silva@email.com",
      "telefone": "(11) 99999-9999",
      "cpf": "12345678901"
    },
    "itens": [
      {
        "produtoId": 1,
        "quantidade": 2,
        "observacoes": "Sem cebola"
      }
    ],
    "metodoPagamento": "PIX",
    "observacoes": "Entrega rápida"
  }'
```

---

### **2.2 - Lista de Pedidos para Cozinha**
```http
GET /api/pedidos/cozinha
```
**Descrição**: Retorna pedidos ordenados para a cozinha (Pronto > Em Preparação > Recebido, mais antigos primeiro).

**Response**:
```json
[
  {
    "id": 3,
    "nomeCliente": "Pedro Oliveira",
    "status": "PRONTO",
    "statusPagamento": "APROVADO",
    "valorTotal": 28.80,
    "dataCriacao": "2024-01-15T12:00:00",
    "observacoes": null,
    "itens": [
      {
        "nomeProduto": "McChicken",
        "categoria": "LANCHE",
        "quantidade": 1,
        "precoUnitario": 22.90,
        "subtotal": 22.90,
        "observacoes": null
      }
    ],
    "tempoEsperaMinutos": 45
  }
]
```

**cURL**:
```bash
curl -X GET "http://localhost:8080/api/pedidos/cozinha" \
  -H "Accept: application/json"
```

---

### **2.3 - Buscar Pedido por ID**
```http
GET /api/pedidos/{id}
```
**Parâmetros**:
- `id` (path): ID do pedido

**Response**:
```json
{
  "id": 1,
  "cliente": {
    "nome": "João Silva",
    "email": "joao.silva@email.com",
    "telefone": "(11) 99999-9999"
  },
  "status": "EM_PREPARACAO",
  "valorTotal": 34.80,
  "dataCriacao": "2024-01-15T10:30:00",
  "observacoes": "Sem cebola no hambúrguer",
  "itens": [
    {
      "produto": {
        "nome": "Big Mac",
        "preco": 25.90
      },
      "quantidade": 1,
      "precoUnitario": 25.90,
      "observacoes": "Sem cebola"
    }
  ]
}
```

**cURL**:
```bash
curl -X GET "http://localhost:8080/api/pedidos/1" \
  -H "Accept: application/json"
```

---

### **2.4 - Iniciar Preparação**
```http
PUT /api/pedidos/{id}/iniciar-preparacao
```
**Descrição**: Marca o pedido como "EM_PREPARACAO" (só funciona se pagamento aprovado).

**cURL**:
```bash
curl -X PUT "http://localhost:8080/api/pedidos/1/iniciar-preparacao"
```

---

### **2.5 - Marcar como Pronto**
```http
PUT /api/pedidos/{id}/marcar-pronto
```
**Descrição**: Marca o pedido como "PRONTO" para retirada.

**cURL**:
```bash
curl -X PUT "http://localhost:8080/api/pedidos/1/marcar-pronto"
```

---

### **2.6 - Finalizar Pedido**
```http
PUT /api/pedidos/{id}/finalizar
```
**Descrição**: Marca o pedido como "FINALIZADO" (entregue ao cliente).

**cURL**:
```bash
curl -X PUT "http://localhost:8080/api/pedidos/1/finalizar"
```

---

### **2.7 - Atualizar Status Genérico**
```http
PUT /api/pedidos/{id}/status
```
**Request Body**:
```json
{
  "status": "EM_PREPARACAO"
}
```

**cURL**:
```bash
curl -X PUT "http://localhost:8080/api/pedidos/1/status" \
  -H "Content-Type: application/json" \
  -d '{"status": "EM_PREPARACAO"}'
```

---

## 💳 **3. PAGAMENTOS**

### **3.1 - Consultar Status de Pagamento**
```http
GET /api/pagamentos/status/{pedidoId}
```
**Descrição**: Informa se o pagamento foi aprovado ou não.

**Parâmetros**:
- `pedidoId` (path): ID do pedido

**Response**:
```json
{
  "pedidoId": 1,
  "pagamentoId": 1,
  "status": "APROVADO",
  "metodoPagamento": "PIX",
  "valor": 34.80,
  "transacaoId": "TXN-ABC123",
  "dataCriacao": "2024-01-15T10:30:00",
  "dataProcessamento": "2024-01-15T10:32:15",
  "qrCodeData": "00020126580014BR.GOV.BCB.PIX...",
  "urlPagamento": "https://api.mercadopago.com/checkout/pro/v1/TXN-ABC123"
}
```

**cURL**:
```bash
curl -X GET "http://localhost:8080/api/pagamentos/status/1" \
  -H "Accept: application/json"
```

---

### **3.2 - Webhook de Confirmação de Pagamento**
```http
POST /api/pagamentos/webhook
```
**Descrição**: Recebe confirmação de pagamento aprovado ou recusado do provedor.

**Request Body**:
```json
{
  "transacaoId": "TXN-149C7215",
  "status": "approved",
  "valor": 34.80,
  "metodoPagamento": "pix",
  "dataProcessamento": "2025-07-29T20:01:30",
  "provedor": "mercadopago",
  "dadosAdicionais": {
    "mercadoPagoId": "12345678",
    "pixId": "E12345678202501292001301234567890"
  }
}
```

**Response**:
```json
{
  "status": "success",
  "message": "Webhook processado com sucesso",
  "transacaoId": "TXN-149C7215"
}
```

**cURL**:
```bash
curl -X POST "http://localhost:8080/api/pagamentos/webhook" \
  -H "Content-Type: application/json" \
  -d '{
    "transacaoId": "TXN-149C7215",
    "status": "approved",
    "valor": 34.80,
    "metodoPagamento": "pix",
    "dataProcessamento": "2025-07-29T20:01:30"
  }'
```

---

### **3.3 - Simular Aprovação (Teste)**
```http
POST /api/pagamentos/simular-aprovacao/{transacaoId}
```
**Descrição**: Endpoint para simular aprovação de pagamento em ambiente de teste.

**cURL**:
```bash
curl -X POST "http://localhost:8080/api/pagamentos/simular-aprovacao/TXN-149C7215"
```

---

## 👥 **4. CLIENTES**

### **4.1 - Buscar Cliente por CPF**
```http
GET /api/clientes/cpf/{cpf}
```
**Parâmetros**:
- `cpf` (path): CPF do cliente (apenas números)

**Response**:
```json
{
  "id": 1,
  "nome": "João Silva",
  "email": "joao.silva@email.com",
  "telefone": "(11) 99999-9999",
  "cpf": "12345678901",
  "dataCadastro": "2024-01-15T10:00:00"
}
```

**cURL**:
```bash
curl -X GET "http://localhost:8080/api/clientes/cpf/12345678901" \
  -H "Accept: application/json"
```

---

## ⚙️ **5. SISTEMA**

### **5.1 - Health Check**
```http
GET /actuator/health
```
**Response**:
```json
{
  "status": "UP",
  "components": {
    "db": {
      "status": "UP",
      "details": {
        "database": "H2",
        "validationQuery": "isValid()"
      }
    },
    "diskSpace": {
      "status": "UP",
      "details": {
        "total": 499963174912,
        "free": 123456789012,
        "threshold": 10485760,
        "exists": true
      }
    }
  }
}
```

**cURL**:
```bash
curl -X GET "http://localhost:8080/actuator/health" \
  -H "Accept: application/json"
```

---

### **5.2 - Métricas**
```http
GET /actuator/metrics
```
**Response**:
```json
{
  "names": [
    "jvm.memory.used",
    "jvm.gc.pause",
    "http.server.requests",
    "system.cpu.usage",
    "process.uptime"
  ]
}
```

---

### **5.3 - Info da Aplicação**
```http
GET /actuator/info
```
**Response**:
```json
{
  "app": {
    "name": "FastFood API",
    "version": "1.0.0",
    "description": "Sistema de gerenciamento de pedidos para fast food"
  },
  "build": {
    "version": "1.0.0",
    "artifact": "fastfood",
    "name": "fastfood",
    "group": "com.marianapetrolini",
    "time": "2025-07-29T20:00:00.000Z"
  }
}
```

---

## 🧪 **FLUXO COMPLETO DE TESTE**

### **Cenário: Cliente faz pedido e paga via PIX**

#### **1. Listar produtos disponíveis**
```bash
curl -X GET "http://localhost:8080/api/produtos/disponiveis"
```

#### **2. Fazer checkout**
```bash
curl -X POST "http://localhost:8080/api/pedidos/checkout" \
  -H "Content-Type: application/json" \
  -d '{
    "cliente": {
      "nome": "Maria Santos",
      "email": "maria@email.com",
      "telefone": "(11) 88888-8888"
    },
    "itens": [
      {"produtoId": 1, "quantidade": 1},
      {"produtoId": 5, "quantidade": 1}
    ],
    "metodoPagamento": "PIX"
  }'
```

#### **3. Consultar status do pagamento**
```bash
curl -X GET "http://localhost:8080/api/pagamentos/status/4"
```

#### **4. Simular webhook de aprovação**
```bash
curl -X POST "http://localhost:8080/api/pagamentos/webhook" \
  -H "Content-Type: application/json" \
  -d '{
    "transacaoId": "TXN-RETORNADO-NO-CHECKOUT",
    "status": "approved",
    "valor": 34.80
  }'
```

#### **5. Verificar lista da cozinha**
```bash
curl -X GET "http://localhost:8080/api/pedidos/cozinha"
```

#### **6. Marcar como pronto**
```bash
curl -X PUT "http://localhost:8080/api/pedidos/4/marcar-pronto"
```

#### **7. Finalizar pedido**
```bash
curl -X PUT "http://localhost:8080/api/pedidos/4/finalizar"
```

---

## 📊 **Códigos de Status HTTP**

| Código | Significado | Quando ocorre |
|--------|-------------|---------------|
| 200 | OK | Operação realizada com sucesso |
| 201 | Created | Recurso criado com sucesso |
| 400 | Bad Request | Dados inválidos na requisição |
| 404 | Not Found | Recurso não encontrado |
| 409 | Conflict | Conflito (ex: produto já existe) |
| 422 | Unprocessable Entity | Dados válidos mas regra de negócio violada |
| 500 | Internal Server Error | Erro interno do servidor |

---

## 🔐 **Autenticação**

Atualmente a API não requer autenticação para facilitar os testes. Em produção, seria implementado:

```http
Authorization: Bearer <JWT_TOKEN>
```

---

## 📱 **Postman Collection**

Para importar no Postman, use o arquivo `postman_collection.json` incluído no projeto ou acesse:

**Import URL**: `http://localhost:8080/v3/api-docs`

---

Esta collection cobre todas as funcionalidades da API FastFood! 🚀

