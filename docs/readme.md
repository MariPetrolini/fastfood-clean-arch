# 📚 GUIA COMPLETO DE EXECUÇÃO - FASTFOOD CLEAN ARCHITECTURE

## 🎯 **VISÃO GERAL**

Este guia fornece instruções para executar o projeto FastFood com Clean Architecture e Kubernetes. O sistema implementa uma solução autoatendimento para lanchonetes, incluindo gerenciamento de produtos, clientes, pedidos e sistema de notificações.



---

## 📋 **ÍNDICE**

1. [Pré-requisitos](#pré-requisitos)
2. [Instalação e Configuração](#instalação-e-configuração)
3. [Execução Local](#execução-local)
4. [Execução com Kubernetes](#execução-com-kubernetes)
5. [Testes das APIs](#testes-das-apis)
6. [Monitoramento e Observabilidade](#monitoramento-e-observabilidade)
7. [Solução de Problemas](#solução-de-problemas)



## 🔧 **PRÉ-REQUISITOS**

### **Requisitos de Sistema**

**Sistemas Operacionais Suportados:**
- Windows 
- macOS 
- Linux Ubuntu 

### **Ferramentas Obrigatórias**

#### **Java Development Kit (JDK) 17+**

O projeto utiliza Java 17 com recursos modernos da linguagem. A instalação pode ser feita através de diferentes métodos:

**Windows:**
```bash
# Usando Chocolatey
choco install openjdk17

# Ou baixar diretamente do site da Oracle/OpenJDK
# https://jdk.java.net/17/
```

**macOS:**
```bash
# Usando Homebrew
brew install openjdk@17

# Configurar JAVA_HOME
echo 'export JAVA_HOME="/opt/homebrew/opt/openjdk@17"' >> ~/.zshrc
echo 'export PATH="$JAVA_HOME/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc
```

**Linux (Ubuntu/Debian):**
```bash
sudo apt update
sudo apt install openjdk-17-jdk

# Verificar instalação
java -version
javac -version
```

#### **Docker e Docker Compose**

O Docker é essencial para containerização da aplicação e execução do PostgreSQL. A instalação varia conforme o sistema operacional:

**Windows:**
1. Baixar Docker Desktop do site oficial: https://www.docker.com/products/docker-desktop
2. Executar o instalador e seguir as instruções
3. Reiniciar o sistema se necessário
4. Verificar instalação: `docker --version`

**macOS:**
```bash
# Usando Homebrew
brew install --cask docker

# Ou baixar Docker Desktop do site oficial
# Iniciar Docker Desktop após instalação
```

**Linux:**
```bash
# Ubuntu/Debian
curl -fsSL https://get.docker.com -o get-docker.sh
sudo sh get-docker.sh

# Adicionar usuário ao grupo docker
sudo usermod -aG docker $USER
newgrp docker

# Instalar Docker Compose
sudo curl -L "https://github.com/docker/compose/releases/download/v2.20.0/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
```

#### **Kubernetes (kubectl e cluster)**

Para execução em Kubernetes, você precisa de um cluster funcional e a ferramenta kubectl:

**kubectl:**
```bash
# Windows (Chocolatey)
choco install kubernetes-cli

# macOS (Homebrew)
brew install kubectl

# Linux
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
sudo install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl
```

**Opções de Cluster Kubernetes:**

1. **Minikube:**
```bash
# Windows
choco install minikube

# macOS
brew install minikube

# Linux
curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64
sudo install minikube-linux-amd64 /usr/local/bin/minikube

# Iniciar cluster
minikube start --memory=4096 --cpus=4
```

2. **Docker Desktop Kubernetes:**
- Habilitar Kubernetes nas configurações do Docker Desktop
- Aguardar inicialização completa


#### **Postman**

Para testar APIs de forma interativa:

- **Postman:** https://www.postman.com/downloads/

### **Verificação dos Pré-requisitos**

Após instalar todas as ferramentas, execute os seguintes comandos para verificar se tudo está funcionando corretamente:

```bash
# Verificar Java
java -version
# Saída esperada: openjdk version "17.x.x"

# Verificar Docker
docker --version
docker-compose --version
# Saída esperada: Docker version 20.x.x, Docker Compose version v2.x.x

# Verificar Kubernetes
kubectl version --client
# Saída esperada: Client Version com versão recente

# Verificar cluster (se usando minikube)
minikube status
# Saída esperada: host, kubelet, apiserver Running

# Verificar Maven (se instalado)
mvn --version
# Saída esperada: Apache Maven 3.x.x

# Verificar Git
git --version
# Saída esperada: git version 2.x.x
```

### **Configurações de Rede**

Certifique-se de que as seguintes portas estejam disponíveis:

- **8080:** Aplicação FastFood
- **5432:** PostgreSQL
- **80/443:** Ingress Kubernetes (se aplicável)
- **8443:** Kubernetes API Server (minikube)

### **Variáveis de Ambiente**

Configure as seguintes variáveis de ambiente se necessário:

```bash
# JAVA_HOME (se não configurado automaticamente)
export JAVA_HOME=/path/to/java17

# DOCKER_HOST (se usando Docker remoto)
export DOCKER_HOST=tcp://localhost:2376

# KUBECONFIG (se usando múltiplos clusters)
export KUBECONFIG=~/.kube/config
```

---


## 🚀 **INSTALAÇÃO E CONFIGURAÇÃO**

### **Obtenção do Código Fonte**

O projeto FastFood está disponível em repositório Git privado. Para obter acesso ao código fonte, siga os procedimentos estabelecidos pela equipe de desenvolvimento:

```bash
# Clonar o repositório (substitua pela URL real)
git clone https://github.com/MariPetrolini/fastfood-clean-arch.git

# Navegar para o diretório do projeto
cd fastfood-clean-arch

# Verificar estrutura do projeto
ls -la
```

A estrutura do projeto segue as convenções do Maven e Clean Architecture:

```
fastfood-clean-arch/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/marianapetrolini/fastfood/
│   │   │       ├── application/          # Camada de Aplicação
│   │   │       ├── domain/               # Camada de Domínio
│   │   │       └── infrastructure/       # Camada de Infraestrutura
│   │   └── resources/
│   │       ├── application.yml
│   │       └── application-prod.yml
│   └── test/                             # Testes Unitários
├── k8s/                                  # Manifestos Kubernetes
│   └── base/
├── docs/                                 # Documentação
├── docker-compose.yml                    # Ambiente local
├── Dockerfile                            # Imagem da aplicação
├── pom.xml                              # Configuração Maven
└── README.md                            # Documentação principal
```

### **Configuração do Ambiente de Desenvolvimento**

#### **Configuração do Banco de Dados**

O projeto utiliza PostgreSQL como banco de dados principal. Para desenvolvimento local, recomenda-se usar Docker Compose:

```bash
# Iniciar PostgreSQL via Docker Compose
docker-compose up -d postgres

# Verificar se o container está rodando
docker ps | grep postgres

# Conectar ao banco para verificação (opcional)
docker exec -it fastfood-postgres psql -U fastfood_user -d fastfood
```

As configurações do banco estão definidas no arquivo `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/fastfood
    username: fastfood_user
    password: fastfood_password
    driver-class-name: org.postgresql.Driver
  
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: false
    database-platform: org.hibernate.dialect.PostgreSQLDialect
```

#### **Configuração de Profiles**

O projeto suporta múltiplos profiles para diferentes ambientes:

**Profile de Desenvolvimento (default):**
```yaml
# application.yml
spring:
  profiles:
    active: dev
  
logging:
  level:
    com.marianapetrolini.fastfood: DEBUG
    org.springframework.web: DEBUG
```

**Profile de Produção:**
```yaml
# application-prod.yml
spring:
  profiles:
    active: prod

logging:
  level:
    root: INFO
    com.marianapetrolini.fastfood: INFO

server:
  port: 8080
```

#### **Configuração de Integrações Externas**

**Mercado Pago (Opcional):**

Para habilitar a integração real com Mercado Pago, configure as seguintes variáveis:

```yaml
mercadopago:
  enabled: true
  access-token: ${MERCADOPAGO_ACCESS_TOKEN:}
  api-base-url: https://api.mercadopago.com
  timeout:
    connection: 5000
    read: 10000
```

**Sistema de Notificações:**

As configurações de notificação permitem diferentes canais:

```yaml
notification:
  enabled: true
  channels:
    email:
      enabled: true
      smtp:
        host: ${SMTP_HOST:localhost}
        port: ${SMTP_PORT:587}
        username: ${SMTP_USERNAME:}
        password: ${SMTP_PASSWORD:}
    sms:
      enabled: false
      provider: ${SMS_PROVIDER:mock}
```

### **Compilação do Projeto**

#### **Compilação com Maven Wrapper**

O projeto inclui Maven Wrapper, eliminando a necessidade de instalação global do Maven:

```bash
# Limpar e compilar o projeto
./mvnw clean compile

# Executar testes unitários
./mvnw test

# Gerar pacote JAR
./mvnw clean package

# Pular testes durante o build (se necessário)
./mvnw clean package -DskipTests
```

#### **Compilação com Maven Global**

Se preferir usar Maven instalado globalmente:

```bash
# Limpar e compilar
mvn clean compile

# Executar testes
mvn test

# Gerar pacote
mvn clean package

# Executar com profile específico
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### **Construção da Imagem Docker**

#### **Build da Imagem**

O projeto inclui Dockerfile otimizado com multi-stage build:

```bash
# Construir imagem Docker
docker build -t fastfood-clean-arch:latest .

# Verificar imagem criada
docker images | grep fastfood

# Executar container localmente
docker run -d \
  --name fastfood-app \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/fastfood \
  fastfood-clean-arch:latest
```

#### **Docker Compose Completo**

Para executar todo o stack (aplicação + banco):

```bash
# Iniciar todos os serviços
docker-compose up -d

# Verificar logs
docker-compose logs -f fastfood-app

# Parar todos os serviços
docker-compose down

# Parar e remover volumes
docker-compose down -v
```

### **Configuração do Kubernetes**

#### **Preparação do Cluster**

**Para Minikube:**
```bash
# Iniciar cluster com recursos adequados
minikube start --memory=4096 --cpus=4 --disk-size=20g

# Habilitar addons úteis
minikube addons enable ingress
minikube addons enable metrics-server

# Verificar status
minikube status
kubectl get nodes
```

**Para Docker Desktop:**
```bash
# Verificar contexto
kubectl config current-context

# Deve retornar: docker-desktop
```

#### **Carregamento da Imagem**

**Para Minikube:**
```bash
# Carregar imagem no minikube
minikube image load fastfood-clean-arch:latest

# Verificar imagem carregada
minikube ssh docker images | grep fastfood
```

**Para Docker Desktop:**
A imagem já está disponível automaticamente.

#### **Criação do Namespace**

```bash
# Criar namespace dedicado
kubectl create namespace fastfood

# Verificar namespace
kubectl get namespaces
```

#### **Configuração de Secrets e ConfigMaps**

**Secrets para dados sensíveis:**
```bash
# Criar secret para banco de dados
kubectl create secret generic fastfood-secrets \
  --from-literal=SPRING_DATASOURCE_USERNAME=fastfood_user \
  --from-literal=SPRING_DATASOURCE_PASSWORD=fastfood_password \
  --namespace=fastfood

# Verificar secret
kubectl get secrets -n fastfood
```

**ConfigMaps para configurações:**
```bash
# Aplicar ConfigMap (arquivo k8s/base/configmap.yaml)
kubectl apply -f k8s/base/configmap.yaml

# Verificar ConfigMap
kubectl get configmaps -n fastfood
kubectl describe configmap fastfood-config -n fastfood
```

### **Validação da Configuração**

#### **Checklist de Validação**


#### **Comandos de Verificação**

```bash
# Verificar compilação
./mvnw clean test -q

# Verificar Docker
docker run --rm fastfood-clean-arch:latest java -version

# Verificar Kubernetes
kubectl cluster-info
kubectl get all -n fastfood

# Verificar conectividade do banco
docker exec fastfood-postgres pg_isready -U fastfood_user
```

### **Configurações Avançadas**

#### **Monitoramento e Observabilidade**

**Actuator Endpoints:**
```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      show-details: always
  metrics:
    export:
      prometheus:
        enabled: true
```

**Logging Estruturado:**
```yaml
logging:
  pattern:
    console: "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
    file: "%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
  file:
    name: logs/fastfood.log
  level:
    org.springframework.security: DEBUG
    org.hibernate.SQL: DEBUG
```

#### **Segurança**

**CORS Configuration:**
```yaml
security:
  cors:
    allowed-origins: "*"
    allowed-methods: "GET,POST,PUT,DELETE,OPTIONS"
    allowed-headers: "*"
    allow-credentials: true
```

**SSL/TLS (Produção):**
```yaml
server:
  ssl:
    enabled: true
    key-store: classpath:keystore.p12
    key-store-password: ${SSL_KEYSTORE_PASSWORD}
    key-store-type: PKCS12
```

---


## 💻 **EXECUÇÃO LOCAL**

### **Execução com Docker Compose (Recomendado)**

A forma mais simples de executar o projeto localmente é utilizando Docker Compose, que orquestra todos os serviços necessários:

```bash
# Navegar para o diretório do projeto
cd fastfood-clean-arch

# Iniciar todos os serviços em background
docker-compose up -d

# Acompanhar logs da aplicação
docker-compose logs -f fastfood-app

# Verificar status dos containers
docker-compose ps
```

**Serviços Disponíveis:**
- **FastFood API:** http://localhost:8080
- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **PostgreSQL:** localhost:5432
- **Actuator Health:** http://localhost:8080/actuator/health

### **Execução Manual (Desenvolvimento)**

Para desenvolvimento ativo com hot-reload:

```bash
# Terminal 1: Iniciar PostgreSQL
docker-compose up -d postgres

# Terminal 2: Executar aplicação em modo desenvolvimento
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

# Ou com Maven global
mvn spring-boot:run -Dspring.profiles.active=dev
```

### **Verificação da Execução Local**

```bash
# Verificar saúde da aplicação
curl http://localhost:8080/actuator/health

# Resposta esperada:
# {"status":"UP","components":{"db":{"status":"UP"}}}

# Testar endpoint básico
curl http://localhost:8080/api/produtos/categorias

# Resposta esperada:
# ["LANCHE","ACOMPANHAMENTO","BEBIDA","SOBREMESA"]
```

---

## ☸️ **EXECUÇÃO COM KUBERNETES**

### **Deploy Completo no Kubernetes**

#### **Passo 1: Preparar o Ambiente**

```bash
# Navegar para diretório Kubernetes
cd k8s/base

# Verificar arquivos de manifesto
ls -la
# deployment.yaml, service.yaml, postgres.yaml, configmap.yaml, hpa.yaml, etc.
```

#### **Passo 2: Deploy do PostgreSQL**

```bash
# Aplicar manifesto do PostgreSQL
kubectl apply -f postgres.yaml

# Verificar deployment do PostgreSQL
kubectl get pods -n fastfood -l app=postgres

# Aguardar até o pod estar Running
kubectl wait --for=condition=Ready pod -l app=postgres -n fastfood --timeout=300s
```

#### **Passo 3: Aplicar Configurações**

```bash
# Aplicar ConfigMap
kubectl apply -f configmap.yaml

# Criar Secrets (se não existir)
kubectl create secret generic fastfood-secrets \
  --from-literal=SPRING_DATASOURCE_USERNAME=fastfood_user \
  --from-literal=SPRING_DATASOURCE_PASSWORD=fastfood_password \
  --namespace=fastfood

# Verificar configurações
kubectl get configmaps,secrets -n fastfood
```

#### **Passo 4: Deploy da Aplicação**

```bash
# Aplicar deployment da aplicação
kubectl apply -f deployment.yaml

# Aplicar service
kubectl apply -f service.yaml

# Verificar pods da aplicação
kubectl get pods -n fastfood -l app=fastfood-api
```

#### **Passo 5: Configurar HPA (Horizontal Pod Autoscaler)**

```bash
# Aplicar HPA
kubectl apply -f hpa.yaml

# Verificar HPA
kubectl get hpa -n fastfood

# Verificar métricas
kubectl describe hpa fastfood-api-hpa -n fastfood
```

### **Acesso à Aplicação no Kubernetes**

#### **Port Forward (Desenvolvimento)**

```bash
# Fazer port-forward do service
kubectl port-forward -n fastfood svc/fastfood-api-service 8080:80

# Aplicação disponível em: http://localhost:8080
```

#### **Ingress (Produção)**

```bash
# Aplicar Ingress (se disponível)
kubectl apply -f ingress.yaml

# Verificar Ingress
kubectl get ingress -n fastfood

# Para Minikube, obter IP
minikube ip
```

### **Monitoramento do Kubernetes**

#### **Verificação de Status**

```bash
# Status geral do namespace
kubectl get all -n fastfood

# Logs da aplicação
kubectl logs -f deployment/fastfood-api -n fastfood

# Logs do PostgreSQL
kubectl logs -f deployment/postgres -n fastfood

# Eventos do namespace
kubectl get events -n fastfood --sort-by='.lastTimestamp'
```

#### **Debugging**

```bash
# Descrever pod com problemas
kubectl describe pod <pod-name> -n fastfood

# Executar shell no pod
kubectl exec -it <pod-name> -n fastfood -- /bin/bash

# Verificar conectividade
kubectl exec -it <pod-name> -n fastfood -- curl http://postgres-service:5432
```

---

## 🧪 **TESTES DAS APIs**

### **Swagger UI (Interface Interativa)**

A forma mais fácil de testar as APIs é através do Swagger UI:

1. Acesse: http://localhost:8080/swagger-ui.html
2. Explore os endpoints organizados por categoria
3. Execute testes diretamente na interface

### **Collection Postman**

#### **Importação da Collection**

1. Baixar arquivo: `FastFood-API-Collection-COMPLETA.postman_collection.json`
2. Abrir Postman
3. Clicar em "Import" → "Upload Files"
4. Selecionar o arquivo da collection
5. Configurar variável `base_url` para `http://localhost:8080`

#### **Sequência de Testes Recomendada**

**Fase 1: Configuração Básica**
```bash
# 1. Listar categorias
GET {{base_url}}/api/produtos/categorias

# 2. Criar produtos (executar 4 requests)
POST {{base_url}}/api/produtos
# Body: dados do X-Bacon Especial, Batata Frita, Refrigerante, Milk Shake
```

**Fase 2: Gerenciamento de Clientes**
```bash
# 3. Criar clientes (executar 3 requests)
POST {{base_url}}/api/clientes
# Body: João Silva, Ana Costa, Pedro Santos

# 4. Listar clientes para campanhas
GET {{base_url}}/api/clientes/campanhas
```

**Fase 3: Sistema de Notificações**
```bash
# 5. Verificar status do serviço
GET {{base_url}}/api/notificacoes/status

# 6. Enviar campanha promocional
POST {{base_url}}/api/notificacoes/campanhas
# Body: título e mensagem da promoção
```

### **Testes com cURL**

#### **Exemplos de Comandos cURL**

**Criar Produto:**
```bash
curl -X POST "http://localhost:8080/api/produtos" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "X-Bacon Especial",
    "descricao": "Hambúrguer artesanal com bacon crocante",
    "preco": 22.90,
    "categoria": "LANCHE"
  }'
```

**Listar Produtos:**
```bash
curl -X GET "http://localhost:8080/api/produtos" \
  -H "Accept: application/json"
```

**Criar Cliente:**
```bash
curl -X POST "http://localhost:8080/api/clientes" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "cpf": "12345678901",
    "email": "joao@email.com",
    "aceitaCampanhas": true
  }'
```

**Enviar Notificação:**
```bash
curl -X POST "http://localhost:8080/api/notificacoes/enviar" \
  -H "Content-Type: application/json" \
  -d '{
    "destinatario": "cliente@email.com",
    "titulo": "Pedido Pronto!",
    "mensagem": "Seu pedido está pronto para retirada",
    "tipo": "PEDIDO_PRONTO"
  }'
```

### **Testes Automatizados**

#### **Executar Testes Unitários**

```bash
# Executar todos os testes
./mvnw test

# Executar testes com relatório
./mvnw test jacoco:report

# Executar testes específicos
./mvnw test -Dtest=ProdutoUseCaseTest

# Executar testes de integração
./mvnw test -Dtest=*IntegrationTest
```

#### **Testes de Performance**

```bash
# Teste de carga com Apache Bench
ab -n 1000 -c 10 http://localhost:8080/api/produtos

# Teste com curl em loop
for i in {1..100}; do
  curl -s http://localhost:8080/actuator/health > /dev/null
  echo "Request $i completed"
done
```

---

## 📊 **MONITORAMENTO E OBSERVABILIDADE**

### **Endpoints de Monitoramento**

**Health Checks:**
```bash
# Saúde geral
curl http://localhost:8080/actuator/health

# Readiness probe
curl http://localhost:8080/actuator/health/readiness

# Liveness probe
curl http://localhost:8080/actuator/health/liveness
```

**Métricas:**
```bash
# Métricas gerais
curl http://localhost:8080/actuator/metrics

# Métricas específicas
curl http://localhost:8080/actuator/metrics/jvm.memory.used
curl http://localhost:8080/actuator/metrics/http.server.requests
```

### **Logs da Aplicação**

**Visualização de Logs:**
```bash
# Docker Compose
docker-compose logs -f fastfood-app

# Kubernetes
kubectl logs -f deployment/fastfood-api -n fastfood

# Logs com filtro
kubectl logs -f deployment/fastfood-api -n fastfood | grep ERROR
```

