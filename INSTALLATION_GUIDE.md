# 🚀 Guia Completo de Instalação - FastFood API

## 📋 Pré-requisitos

### **Opção 1: Ambiente Local (Desenvolvimento)**
```bash
# Ferramentas necessárias
- Docker Desktop 4.0+
- Minikube 1.25+ ou Kind 0.17+
- kubectl 1.25+
- Java 11+
- Maven 3.8+
```

### **Opção 2: Ambiente Cloud (Produção)**
```bash
# Escolha uma das opções:
- AWS EKS 1.25+
- Google GKE 1.25+
- Azure AKS 1.25+
- DigitalOcean DOKS 1.25+
```

## 🛠️ Instalação Passo a Passo

### **PASSO 1: Preparar o Ambiente Local**

#### **1.1 - Instalar Docker Desktop**
```bash
# Windows/Mac: Baixar do site oficial
https://www.docker.com/products/docker-desktop

# Linux (Ubuntu/Debian)
curl -fsSL https://get.docker.com -o get-docker.sh
sudo sh get-docker.sh
sudo usermod -aG docker $USER
```

#### **1.2 - Instalar kubectl**
```bash
# Linux
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
sudo install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl

# macOS
brew install kubectl

# Windows
choco install kubernetes-cli
```

#### **1.3 - Instalar Minikube**
```bash
# Linux
curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64
sudo install minikube-linux-amd64 /usr/local/bin/minikube

# macOS
brew install minikube

# Windows
choco install minikube
```

### **PASSO 2: Configurar Cluster Kubernetes**

#### **2.1 - Iniciar Minikube**
```bash
# Iniciar com recursos adequados
minikube start --cpus=4 --memory=8192 --disk-size=20g

# Habilitar addons necessários
minikube addons enable ingress
minikube addons enable metrics-server
minikube addons enable dashboard
```

#### **2.2 - Verificar Cluster**
```bash
# Verificar status
kubectl cluster-info
kubectl get nodes

# Deve mostrar:
# NAME       STATUS   ROLES           AGE   VERSION
# minikube   Ready    control-plane   1m    v1.25.x
```

### **PASSO 3: Preparar a Aplicação**

#### **3.1 - Clonar o Repositório**
```bash
git clone <URL_DO_REPOSITORIO>
cd fastfood-backend
```

#### **3.2 - Construir a Imagem Docker**
```bash
# Construir a aplicação
./mvnw clean package -DskipTests

# Construir imagem Docker
docker build -t fastfood-api:latest .

# Para Minikube, carregar a imagem
minikube image load fastfood-api:latest
```

### **PASSO 4: Deploy no Kubernetes**

#### **4.1 - Criar Namespaces**
```bash
kubectl apply -f k8s/base/namespace.yaml
```

#### **4.2 - Aplicar Configurações (Desenvolvimento)**
```bash
# Aplicar ConfigMaps e Secrets
kubectl apply -f k8s/base/configmap.yaml
kubectl apply -f k8s/base/secret.yaml

# Deploy da aplicação
kubectl apply -f k8s/base/deployment.yaml
kubectl apply -f k8s/base/service.yaml
kubectl apply -f k8s/base/hpa.yaml
kubectl apply -f k8s/base/ingress.yaml
```

#### **4.3 - Verificar Deploy**
```bash
# Verificar pods
kubectl get pods -n fastfood-dev
kubectl get pods -n fastfood

# Verificar services
kubectl get svc -n fastfood-dev
kubectl get svc -n fastfood

# Verificar HPA
kubectl get hpa -n fastfood
```

### **PASSO 5: Configurar Acesso**

#### **5.1 - Configurar Ingress (Minikube)**
```bash
# Obter IP do Minikube
minikube ip

# Adicionar ao /etc/hosts (Linux/Mac) ou C:\Windows\System32\drivers\etc\hosts (Windows)
echo "$(minikube ip) fastfood-api-dev.example.com" | sudo tee -a /etc/hosts
echo "$(minikube ip) fastfood-api.example.com" | sudo tee -a /etc/hosts
```

#### **5.2 - Testar Acesso**
```bash
# Testar API
curl http://fastfood-api-dev.example.com/api/produtos/categorias

# Ou usar port-forward
kubectl port-forward -n fastfood-dev svc/fastfood-api-service-dev 8080:80
curl http://localhost:8080/api/produtos/categorias
```

## 🌐 Deploy em Ambiente Cloud

### **AWS EKS**

#### **1. Criar Cluster EKS**
```bash
# Instalar eksctl
curl --silent --location "https://github.com/weaveworks/eksctl/releases/latest/download/eksctl_$(uname -s)_amd64.tar.gz" | tar xz -C /tmp
sudo mv /tmp/eksctl /usr/local/bin

# Criar cluster
eksctl create cluster --name fastfood-cluster --region us-west-2 --nodes 3 --node-type t3.medium

# Configurar kubectl
aws eks update-kubeconfig --region us-west-2 --name fastfood-cluster
```

#### **2. Instalar Ingress Controller**
```bash
# NGINX Ingress Controller
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-v1.8.1/deploy/static/provider/aws/deploy.yaml
```

#### **3. Configurar ALB (Alternativa)**
```bash
# AWS Load Balancer Controller
curl -o iam_policy.json https://raw.githubusercontent.com/kubernetes-sigs/aws-load-balancer-controller/v2.5.4/docs/install/iam_policy.json

aws iam create-policy \
    --policy-name AWSLoadBalancerControllerIAMPolicy \
    --policy-document file://iam_policy.json
```

### **Google GKE**

#### **1. Criar Cluster GKE**
```bash
# Instalar gcloud CLI
curl https://sdk.cloud.google.com | bash
exec -l $SHELL

# Autenticar
gcloud auth login
gcloud config set project YOUR_PROJECT_ID

# Criar cluster
gcloud container clusters create fastfood-cluster \
    --zone us-central1-a \
    --num-nodes 3 \
    --machine-type e2-medium \
    --enable-autoscaling \
    --min-nodes 1 \
    --max-nodes 10

# Configurar kubectl
gcloud container clusters get-credentials fastfood-cluster --zone us-central1-a
```

## 📊 Configurar Monitoramento

### **1. Instalar Prometheus Stack**
```bash
# Adicionar Helm repository
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo update

# Instalar kube-prometheus-stack
helm install prometheus prometheus-community/kube-prometheus-stack \
    --namespace monitoring \
    --create-namespace \
    --set grafana.adminPassword=admin123
```

### **2. Configurar Dashboards**
```bash
# Aplicar ServiceMonitor
kubectl apply -f k8s/base/monitoring.yaml

# Port-forward Grafana
kubectl port-forward -n monitoring svc/prometheus-grafana 3000:80

# Acessar: http://localhost:3000 (admin/admin123)
```

## 🧪 Testes e Validação

### **1. Testes de Funcionalidade**
```bash
# Testar APIs principais
curl -X GET "http://fastfood-api.example.com/api/produtos/categorias"
curl -X GET "http://fastfood-api.example.com/api/produtos/disponiveis"

# Testar checkout
curl -X POST "http://fastfood-api.example.com/api/pedidos/checkout" \
  -H "Content-Type: application/json" \
  -d '{
    "cliente": {"nome": "Teste", "email": "teste@email.com"},
    "itens": [{"produtoId": 1, "quantidade": 2}],
    "metodoPagamento": "PIX"
  }'
```

### **2. Testes de Escalabilidade**
```bash
# Instalar Apache Bench
sudo apt-get install apache2-utils

# Teste de carga
ab -n 1000 -c 50 http://fastfood-api.example.com/api/produtos/categorias

# Verificar HPA funcionando
kubectl get hpa -n fastfood -w
```

### **3. Testes de Resiliência**
```bash
# Simular falha de pod
kubectl delete pod -n fastfood -l app=fastfood-api --force

# Verificar recuperação
kubectl get pods -n fastfood -w
```

## 🔧 Troubleshooting

### **Problemas Comuns**

#### **1. Pods não iniciam**
```bash
# Verificar logs
kubectl logs -n fastfood deployment/fastfood-api

# Verificar eventos
kubectl describe pod -n fastfood <POD_NAME>

# Verificar recursos
kubectl top pods -n fastfood
```

#### **2. Ingress não funciona**
```bash
# Verificar ingress controller
kubectl get pods -n ingress-nginx

# Verificar configuração
kubectl describe ingress -n fastfood fastfood-api-ingress

# Testar com port-forward
kubectl port-forward -n fastfood svc/fastfood-api-service 8080:80
```

#### **3. HPA não escala**
```bash
# Verificar metrics server
kubectl get apiservice v1beta1.metrics.k8s.io -o yaml

# Verificar métricas
kubectl top pods -n fastfood

# Forçar carga
kubectl run -i --tty load-generator --rm --image=busybox --restart=Never -- /bin/sh
# Dentro do container:
while true; do wget -q -O- http://fastfood-api-service.fastfood.svc.cluster.local/api/produtos/categorias; done
```

## 📝 Configurações de Produção

### **1. Secrets Reais**
```bash
# Atualizar secrets com valores reais
kubectl create secret generic fastfood-secrets \
  --from-literal=SPRING_DATASOURCE_PASSWORD='sua_senha_real' \
  --from-literal=MERCADOPAGO_ACCESS_TOKEN='seu_token_real' \
  --namespace=fastfood
```

### **2. SSL/TLS**
```bash
# Instalar cert-manager
kubectl apply -f https://github.com/cert-manager/cert-manager/releases/download/v1.13.0/cert-manager.yaml

# Configurar Let's Encrypt
kubectl apply -f - <<EOF
apiVersion: cert-manager.io/v1
kind: ClusterIssuer
metadata:
  name: letsencrypt-prod
spec:
  acme:
    server: https://acme-v02.api.letsencrypt.org/directory
    email: seu-email@exemplo.com
    privateKeySecretRef:
      name: letsencrypt-prod
    solvers:
    - http01:
        ingress:
          class: nginx
EOF
```

### **3. Backup e Disaster Recovery**
```bash
# Instalar Velero para backups
velero install \
    --provider aws \
    --plugins velero/velero-plugin-for-aws:v1.7.0 \
    --bucket fastfood-backups \
    --secret-file ./credentials-velero

# Configurar backup automático
velero schedule create daily-backup --schedule="0 2 * * *"
```

## ✅ Checklist de Deploy

### **Pré-Deploy**
- [ ] Cluster Kubernetes funcionando
- [ ] kubectl configurado
- [ ] Imagem Docker construída
- [ ] Secrets configurados
- [ ] DNS configurado

### **Deploy**
- [ ] Namespaces criados
- [ ] ConfigMaps aplicados
- [ ] Secrets aplicados
- [ ] Database deployado
- [ ] Aplicação deployada
- [ ] Services criados
- [ ] Ingress configurado

### **Pós-Deploy**
- [ ] Pods rodando (3/3)
- [ ] Health checks passando
- [ ] APIs respondendo
- [ ] HPA funcionando
- [ ] Monitoramento ativo
- [ ] Alertas configurados
- [ ] Backup configurado

## 🎯 Próximos Passos

1. **Configurar CI/CD** com GitHub Actions
2. **Implementar Service Mesh** (Istio)
3. **Adicionar Cache** (Redis)
4. **Configurar CDN** para assets
5. **Implementar Blue/Green** deployment
6. **Adicionar Chaos Engineering** (Chaos Monkey)

Este guia garante uma instalação completa e robusta do sistema FastFood em Kubernetes! 🚀

