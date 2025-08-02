# 🏗️ Arquitetura do Sistema FastFood

## 📋 Visão Geral

O sistema FastFood foi projetado seguindo os princípios de **Clean Architecture** e **Microserviços**, implementado em **Kubernetes** com foco em **escalabilidade**, **disponibilidade** e **performance** para atender aos requisitos de negócio de um restaurante fast food.

## 🎯 Requisitos de Negócio Atendidos

### 1. **Problema de Performance no Totem**
**Solução**: Implementação de **HPA (Horizontal Pod Autoscaler)** que monitora:
- **CPU > 70%**: Escala automaticamente de 3 para até 20 pods
- **Memória > 80%**: Adiciona pods conforme demanda
- **Requisições/segundo > 100**: Escala baseado em carga de trabalho
- **Tempo de resposta**: Mantém latência baixa mesmo com picos de demanda

### 2. **Gestão de Pedidos da Cozinha**
**Solução**: API otimizada com ordenação inteligente:
- **Prioridade**: Pronto > Em Preparação > Recebido
- **Tempo**: Pedidos mais antigos primeiro
- **Filtragem**: Exclui pedidos finalizados automaticamente
- **Cache**: Redis para consultas frequentes (implementável)

### 3. **Integração de Pagamentos**
**Solução**: Integração robusta com Mercado Pago:
- **QR Code PIX**: Geração automática
- **Webhook**: Processamento em tempo real
- **Fallback**: Sistema mock para desenvolvimento
- **Segurança**: Secrets para tokens sensíveis

## 🏛️ Desenho da Arquitetura

```
┌─────────────────────────────────────────────────────────────────────────────────┐
│                                INTERNET                                         │
└─────────────────────────┬───────────────────────────────────────────────────────┘
                          │
┌─────────────────────────▼───────────────────────────────────────────────────────┐
│                        LOAD BALANCER                                            │
│                    (AWS ALB / GCP LB)                                          │
└─────────────────────────┬───────────────────────────────────────────────────────┘
                          │
┌─────────────────────────▼───────────────────────────────────────────────────────┐
│                    KUBERNETES CLUSTER                                           │
│  ┌─────────────────────────────────────────────────────────────────────────┐   │
│  │                        INGRESS CONTROLLER                               │   │
│  │                         (NGINX)                                         │   │
│  │  • Rate Limiting (100 req/min)                                         │   │
│  │  • SSL Termination                                                     │   │
│  │  • CORS Headers                                                        │   │
│  └─────────────────────┬───────────────────────────────────────────────────┘   │
│                        │                                                       │
│  ┌─────────────────────▼───────────────────────────────────────────────────┐   │
│  │                    FASTFOOD API SERVICE                                 │   │
│  │                      (LoadBalancer)                                     │   │
│  └─────────────────────┬───────────────────────────────────────────────────┘   │
│                        │                                                       │
│  ┌─────────────────────▼───────────────────────────────────────────────────┐   │
│  │                 HPA (HORIZONTAL POD AUTOSCALER)                         │   │
│  │  • Min Replicas: 3                                                     │   │
│  │  • Max Replicas: 20                                                    │   │
│  │  • CPU Target: 70%                                                     │   │
│  │  • Memory Target: 80%                                                  │   │
│  │  • Custom Metrics: HTTP req/sec > 100                                  │   │
│  └─────────────────────┬───────────────────────────────────────────────────┘   │
│                        │                                                       │
│  ┌─────────────────────▼───────────────────────────────────────────────────┐   │
│  │                    FASTFOOD API PODS                                    │   │
│  │  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐    │   │
│  │  │   POD 1     │  │   POD 2     │  │   POD 3     │  │   POD N     │    │   │
│  │  │             │  │             │  │             │  │             │    │   │
│  │  │ FastFood    │  │ FastFood    │  │ FastFood    │  │ FastFood    │    │   │
│  │  │ API         │  │ API         │  │ API         │  │ API         │    │   │
│  │  │             │  │             │  │             │  │             │    │   │
│  │  │ Resources:  │  │ Resources:  │  │ Resources:  │  │ Resources:  │    │   │
│  │  │ CPU: 250m   │  │ CPU: 250m   │  │ CPU: 250m   │  │ CPU: 250m   │    │   │
│  │  │ Mem: 512Mi  │  │ Mem: 512Mi  │  │ Mem: 512Mi  │  │ Mem: 512Mi  │    │   │
│  │  │             │  │             │  │             │  │             │    │   │
│  │  │ Health:     │  │ Health:     │  │ Health:     │  │ Health:     │    │   │
│  │  │ ✓ Liveness  │  │ ✓ Liveness  │  │ ✓ Liveness  │  │ ✓ Liveness  │    │   │
│  │  │ ✓ Readiness │  │ ✓ Readiness │  │ ✓ Readiness │  │ ✓ Readiness │    │   │
│  │  │ ✓ Startup   │  │ ✓ Startup   │  │ ✓ Startup   │  │ ✓ Startup   │    │   │
│  │  └─────────────┘  └─────────────┘  └─────────────┘  └─────────────┘    │   │
│  └─────────────────────┬───────────────────────────────────────────────────┘   │
│                        │                                                       │
│  ┌─────────────────────▼───────────────────────────────────────────────────┐   │
│  │                    CONFIGURATION LAYER                                  │   │
│  │  ┌─────────────────┐              ┌─────────────────┐                  │   │
│  │  │   CONFIGMAP     │              │     SECRETS     │                  │   │
│  │  │                 │              │                 │                  │   │
│  │  │ • DB Config     │              │ • DB Password   │                  │   │
│  │  │ • App Settings  │              │ • MP Token      │                  │   │
│  │  │ • CORS Settings │              │ • JWT Secret    │                  │   │
│  │  │ • Timeouts      │              │ • SSL Certs     │                  │   │
│  │  └─────────────────┘              └─────────────────┘                  │   │
│  └─────────────────────┬───────────────────────────────────────────────────┘   │
│                        │                                                       │
│  ┌─────────────────────▼───────────────────────────────────────────────────┐   │
│  │                    DATABASE LAYER                                       │   │
│  │  ┌─────────────────────────────────────────────────────────────────┐   │   │
│  │  │                    POSTGRESQL                                   │   │   │
│  │  │                                                                 │   │   │
│  │  │ • Persistent Volume: 10Gi                                      │   │   │
│  │  │ • Resources: CPU 250m, Memory 256Mi                            │   │   │
│  │  │ • Health Checks: pg_isready                                    │   │   │
│  │  │ • Backup Strategy: Daily snapshots                             │   │   │
│  │  │                                                                 │   │   │
│  │  │ Tables:                                                         │   │   │
│  │  │ ├── produtos                                                    │   │   │
│  │  │ ├── clientes                                                    │   │   │
│  │  │ ├── pedidos                                                     │   │   │
│  │  │ ├── itens_pedido                                                │   │   │
│  │  │ └── pagamentos                                                  │   │   │
│  │  └─────────────────────────────────────────────────────────────────┘   │   │
│  └─────────────────────────────────────────────────────────────────────────┘   │
│                                                                               │
│  ┌─────────────────────────────────────────────────────────────────────────┐   │
│  │                    MONITORING & OBSERVABILITY                           │   │
│  │  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐    │   │
│  │  │ PROMETHEUS  │  │  GRAFANA    │  │ ALERTMANAGER│  │   JAEGER    │    │   │
│  │  │             │  │             │  │             │  │             │    │   │
│  │  │ • Metrics   │  │ • Dashboard │  │ • Alerts    │  │ • Tracing   │    │   │
│  │  │ • Scraping  │  │ • Graphs    │  │ • PagerDuty │  │ • APM       │    │   │
│  │  │ • Storage   │  │ • Reports   │  │ • Slack     │  │ • Debugging │    │   │
│  │  └─────────────┘  └─────────────┘  └─────────────┘  └─────────────┘    │   │
│  └─────────────────────────────────────────────────────────────────────────┘   │
└───────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────────┐
│                            EXTERNAL SERVICES                                    │
│  ┌─────────────────┐              ┌─────────────────┐                          │
│  │  MERCADO PAGO   │              │   NOTIFICATION  │                          │
│  │                 │              │    SERVICES     │                          │
│  │ • PIX QR Code   │              │                 │                          │
│  │ • Webhook       │              │ • Email         │                          │
│  │ • Payment API   │              │ • SMS           │                          │
│  │ • Validation    │              │ • Push          │                          │
│  └─────────────────┘              └─────────────────┘                          │
└─────────────────────────────────────────────────────────────────────────────────┘
```

## 🔧 Componentes e Configurações

### **1. Ingress Controller (NGINX)**
```yaml
Funcionalidades:
- Rate Limiting: 100 req/min por IP
- SSL Termination com Let's Encrypt
- CORS Headers automáticos
- Health Check: /actuator/health
- Timeout: 30s para conexões
```

### **2. HPA (Horizontal Pod Autoscaler)**
```yaml
Configuração para Performance do Totem:
- Min Replicas: 3 (sempre disponível)
- Max Replicas: 20 (picos de demanda)
- CPU Target: 70% (escala antes de saturar)
- Memory Target: 80% (previne OOM)
- Scale Up: +100% ou +5 pods (máximo)
- Scale Down: -50% ou -2 pods (conservador)
- Stabilization: 60s up, 300s down
```

### **3. VPA (Vertical Pod Autoscaler)**
```yaml
Otimização de Recursos:
- Min: CPU 100m, Memory 256Mi
- Max: CPU 2000m, Memory 2Gi
- Update Mode: Auto
- Controlled: Requests + Limits
```

### **4. Pod Disruption Budget**
```yaml
Disponibilidade:
- Min Available: 2 pods
- Garante disponibilidade durante updates
- Previne downtime total
```

### **5. Network Policy**
```yaml
Segurança de Rede:
- Ingress: Apenas porta 8080
- Egress: PostgreSQL (5432), HTTPS (443), DNS (53)
- Isolamento entre namespaces
```

### **6. ConfigMap & Secrets**
```yaml
ConfigMap (não sensível):
- Configurações de aplicação
- URLs de APIs externas
- Timeouts e limites
- Configurações de CORS

Secrets (sensível):
- Credenciais do banco
- Tokens do Mercado Pago
- Chaves JWT
- Certificados SSL
```

## 📊 Monitoramento e Alertas

### **Métricas Coletadas**
- **Request Rate**: Requisições por segundo
- **Response Time**: Latência P95, P99
- **Error Rate**: Taxa de erro 4xx/5xx
- **Resource Usage**: CPU, Memória, Disk I/O
- **Database**: Conexões, Query time, Locks
- **Business**: Pedidos/min, Conversão, Revenue

### **Alertas Configurados**
- **Critical**: Error rate > 10%, Pods < 2
- **Warning**: Latency > 2s, CPU > 80%, Memory > 85%
- **Info**: Scale events, Deployments

## 🚀 Estratégia de Deployment

### **Ambientes**
```
Development (fastfood-dev):
- 1 replica mínima
- H2 database
- Recursos reduzidos
- Logs verbosos

Production (fastfood):
- 3 replicas mínimas
- PostgreSQL
- Recursos otimizados
- Monitoramento completo
```

### **CI/CD Pipeline**
```
1. Code Push → GitHub
2. Tests → JUnit, Integration
3. Build → Docker Image
4. Security Scan → Trivy, Snyk
5. Deploy Dev → Automatic
6. Tests E2E → Automated
7. Deploy Prod → Manual Approval
8. Health Check → Automatic
```

## 🔒 Segurança

### **Implementações**
- **RBAC**: Role-based access control
- **Network Policies**: Isolamento de rede
- **Pod Security**: Non-root user, read-only filesystem
- **Secrets Management**: Encrypted at rest
- **Image Security**: Distroless images, vulnerability scanning
- **TLS**: End-to-end encryption

### **Compliance**
- **PCI DSS**: Para dados de pagamento
- **LGPD**: Para dados pessoais
- **SOC 2**: Para auditoria de segurança

## 📈 Escalabilidade

### **Horizontal Scaling**
- **HPA**: Baseado em CPU, Memory, Custom Metrics
- **Cluster Autoscaler**: Adiciona nodes conforme demanda
- **Multi-AZ**: Distribuição em zonas de disponibilidade

### **Vertical Scaling**
- **VPA**: Otimiza recursos por pod
- **Resource Quotas**: Limita uso por namespace
- **QoS Classes**: Guaranteed, Burstable, BestEffort

## 🎯 Resolução do Problema de Performance

### **Cenário**: Totem com alta demanda
```
1. Detecção: HPA monitora métricas
2. Trigger: CPU > 70% por 60s
3. Ação: Escala de 3 para 6 pods
4. Resultado: Latência reduzida
5. Monitoramento: Grafana mostra melhoria
6. Scale Down: Após demanda reduzir
```

### **Benefícios**
- **Disponibilidade**: 99.9% uptime
- **Performance**: < 200ms response time
- **Custo**: Pay-per-use scaling
- **Manutenção**: Zero-downtime deployments

Esta arquitetura garante que o sistema FastFood seja **resiliente**, **escalável** e **performático**, atendendo a todos os requisitos de negócio com as melhores práticas de DevOps e Cloud Native.

