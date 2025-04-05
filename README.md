# HubSpot Integration API

## 📖 Visão Geral

API de integração com o HubSpot desenvolvida em Java com Spring Boot, focada em gerenciamento de contatos e autenticação OAuth 2.0. Esta documentação detalha as decisões técnicas, arquitetura e motivações por trás das escolhas tecnológicas.

## 🚀 Tecnologias Utilizadas

### Core
- **Java 17**: Versão LTS com suporte a records e pattern matching
- **Spring Boot 3.2.0**: Framework robusto para desenvolvimento de APIs REST
- **Spring WebFlux**: Escolhido para melhor performance em operações assíncronas e não-bloqueantes

### Dependências
- **Lombok**: Redução de boilerplate code
- **WebClient**: Cliente HTTP reativo para comunicação com APIs externas
- **Validation**: Validação de dados de entrada
- **Logging**: Registro de eventos e debug

## 🏗️ Arquitetura

### Decisões de Design

1. **DDD**
   - Separação clara entre aplicação e infraestrutura
   - Facilita testes e manutenção
   - Permite troca de implementações sem afetar o core

2. **Programação Reativa**
   - Uso de WebFlux para melhor escalabilidade
   - Tratamento assíncrono de requisições
   - Melhor performance em operações I/O

3. **Tratamento de Erros**
   - Hierarquia de exceções customizadas
   - Respostas padronizadas via `ApiResponse`
   - Logging estruturado para debug

### Motivações Técnicas


1. **WebClient**
   - Suporte a programação reativa
   - Melhor performance
   - API mais moderna e flexível

2. **Lombok**
   - Redução de código boilerplate
   - Melhor legibilidade
   - Manutenção simplificada

3. **Webhooks**
   - Implementar endpoint de webhook
   - Processamento assíncrono
   - Notificações em tempo real

4. **Rate Limiting**
   - Implementar rate limiting
   - Proteção contra abusos
   - Melhor gerenciamento de recursos

## 📋 Pré-requisitos

- Java 17 ou superior
- Maven
- Conta no HubSpot
- Aplicação OAuth configurada no HubSpot

## 🔧 Configuração

1. Clone o repositório:
```bash
git clone https://github.com/IgorBavand/hubspotintegration.api
cd hubspotintegration.api
```

2. Configure as variáveis de ambiente no arquivo `application.properties`:
```properties
hubspot.client.id=seu-client-id
hubspot.client.secret=seu-client-secret
hubspot.redirect.uri=url-de-callback
hubspot.token.url=https://api.hubapi.com/oauth/v1/token
```

3. Compile o projeto:
```bash
mvn clean install
```

## 🏃 Executando o Projeto

1. Inicie a aplicação:
```bash
mvn spring-boot:run
```

2. A aplicação estará disponível em:
```
http://localhost:8080
```

## 📚 Endpoints da API

### 1. Autenticação

#### Obter URL de Autorização
```http
GET /api/oauth/authorize
```
**Resposta:**
```json
{
  "success": true,
  "message": "Success",
  "data": "https://app.hubspot.com/oauth/authorize?..."
}
```

#### Callback de Autorização
```http
GET /api/oauth/callback?code={code}
```
**Resposta:**
```json
{
  "success": true,
  "message": "Success",
  "data": "{token}"
}
```

### 2. Contatos

#### Criar Contato
```http
POST /api/contacts
Headers:
  Authorization: Bearer {token}
  Content-Type: application/json

Body:
{
  "email": "string",
  "firstname": "string",
  "lastname": "string",
  "phone": "string",
  "company": "string"
}
```

**Resposta de Sucesso (200):**
```json
{
  "success": true,
  "message": "Success",
  "data": {
    "email": "string",
    "firstname": "string",
    "lastname": "string",
    "phone": "string",
    "company": "string"
  }
}
```

## 🔍 Estrutura do Projeto

```
src/main/java/com/igorbavand/hubspotintegration/
├── api/                    # Controladores REST
│   ├── AuthController.java
│   └── ContactController.java
├── application/           # Lógica de negócio
│   ├── dto/              # Objetos de transferência de dados
│   │   ├── request/
│   │   └── response/
│   └── service/          # Serviços de aplicação
├── infrastructure/        # Infraestrutura
│   ├── client/           # Clientes HTTP
│   └── utils/            # Utilitários
├── exception/            # Tratamento de exceções
└── model/               # Modelos de domínio
```

## 🔒 Segurança

### Decisões de Segurança
1. **OAuth 2.0**
   - Padrão industrial para autenticação
   - Suporte a múltiplos fluxos


2. **Headers de Segurança**
   - CORS configurado
   - Headers de segurança HTTP
   - Proteção contra ataques comuns

