
# 🚀 API de Integração com HubSpot

Esta é uma API REST desenvolvida com **Spring Boot** que integra com a API do **HubSpot**, utilizando o fluxo de autorização **OAuth 2.0 (Authorization Code Flow)**. A aplicação permite autenticação segura, criação de contatos no CRM e tratamento de eventos via webhooks.

---

# 👀 Visualizar
Clique para utilizar a integração pode visualizar na prática [aqui](https://hubspotintegration-portal.vercel.app/auth)


## ✅ Pré-requisitos

- Java 17 ou superior
- Maven 3.6 ou superior
- Conta de Desenvolvedor no HubSpot
- Um App no HubSpot com credenciais OAuth 2.0 configuradas

---

## ⚙️ Configuração do Projeto

### 1. Clone o repositório
```bash
git clone https://github.com/IgorBavand/hubspotintegration.api
cd hubspotintegration
```

### 2. Configure o App no HubSpot
- Acesse sua conta de desenvolvedor HubSpot
- Crie um novo aplicativo ou selecione um existente
- Nas configurações de OAuth 2.0:
    - Defina o **Redirect URI** como:  
      `http://localhost:8080/api/oauth/callback`
    - Adicione os escopos necessários:  
      `crm.objects.contacts.write`
- Copie o **Client ID** e o **Client Secret**

### 3. Configure o `application.properties`
Edite o arquivo `src/main/resources/application.properties` com suas credenciais:
```properties
hubspot.client.id=your-client-id
hubspot.client.secret=your-client-secret
hubspot.redirect.uri=your-callback-url
```

### 4. Compile o projeto
```bash
mvn clean install
```

### 5. Inicie a aplicação
```bash
mvn spring-boot:run
```

---

## 📌 Endpoints Disponíveis

### 🔐 1. Gerar URL de Autorização
**GET** `/api/oauth/authorize`  
Gera e retorna a URL para iniciar o processo de autenticação com o HubSpot.

---

### 🔄 2. Callback OAuth
**GET** `/api/oauth/callback?code={code}`  
Recebe o código de autorização do HubSpot e troca por um token de acesso.

---

### ➕ 3. Criar Contato
**POST** `/api/contacts`  
**Headers:**
```http
Authorization: Bearer {access_token}
```
**Body (JSON):**
```json
{
  "properties": {
    "email": "exemplo@email.com",
    "firstname": "João",
    "lastname": "Silva"
  }
}
```
Cria um novo contato no HubSpot.

---

### 📩 4. Receber Webhook
**POST** `/api/webhook`  
**Headers:**
```http
X-HubSpot-Event-Type: contact.creation
```
**Body (JSON):**
```json
{
  "subscriptionType": "contact.creation",
  "portalId": 123456,
  "objectId": 789,
  "propertyName": "email",
  "propertyValue": "exemplo@email.com",
  "changeSource": "CRM_UI",
  "eventId": 123,
  "subscriptionId": 456,
  "attemptNumber": 1,
  "objectType": "CONTACT",
  "timestamp": 1234567890
}
```
Recebe e processa eventos de criação de contatos enviados pelo HubSpot via webhook.

---

## 🔐 Segurança

- Utiliza OAuth 2.0 para autenticação segura com o HubSpot
- Tokens de acesso são obrigatórios para endpoints protegidos
- Recomenda-se proteger o endpoint de webhook em produção
- Armazene credenciais de forma segura (variáveis de ambiente, ferramentas de gestão segura de configurações, etc.)

---

## 📉 Limites de Requisição

A aplicação respeita os limites de requisição da API do HubSpot.  
É recomendável implementar mecanismos de controle de taxa (rate limiting) em produção.

---

## ❗ Tratamento de Erros

A API trata falhas comuns como:
- Erros na autenticação OAuth
- Limites excedidos da API
- Requisições inválidas
- Problemas de rede
- Erros internos do servidor

---

