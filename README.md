# HubSpot Integration API

This is a Spring Boot REST API that integrates with HubSpot's API, implementing OAuth 2.0 authorization code flow, contact creation, and webhook handling.

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- HubSpot Developer Account
- HubSpot App with OAuth 2.0 credentials

## Setup

1. Clone the repository:
```bash
git clone <repository-url>
cd hubspotintegration
```

2. Configure HubSpot OAuth credentials:
   - Go to your HubSpot Developer account
   - Create a new app or select an existing one
   - Configure OAuth 2.0 settings:
     - Set the redirect URI to: `http://localhost:8080/api/oauth/callback`
     - Add the required scopes: `crm.objects.contacts.write`
   - Copy your Client ID and Client Secret

3. Update the application.properties file:
   - Replace `your-client-id` with your HubSpot Client ID
   - Replace `your-client-secret` with your HubSpot Client Secret

4. Build the application:
```bash
mvn clean install
```

5. Run the application:
```bash
mvn spring:boot run
```

## API Endpoints

### 1. Generate Authorization URL
```
GET /api/oauth/authorize
```
Returns the URL to initiate the OAuth flow with HubSpot.

### 2. OAuth Callback
```
GET /api/oauth/callback?code={code}
```
Handles the OAuth callback from HubSpot and exchanges the authorization code for an access token.

### 3. Create Contact
```
POST /api/contacts
Headers:
  Authorization: Bearer {access_token}
Body:
  {
    "properties": {
      "email": "example@email.com",
      "firstname": "John",
      "lastname": "Doe"
    }
  }
```
Creates a new contact in HubSpot.

### 4. Webhook Endpoint
```
POST /api/webhook
Headers:
  X-HubSpot-Event-Type: contact.creation
Body:
  {
    "subscriptionType": "contact.creation",
    "portalId": 123456,
    "objectId": 789,
    "propertyName": "email",
    "propertyValue": "example@email.com",
    "changeSource": "CRM_UI",
    "eventId": 123,
    "subscriptionId": 456,
    "attemptNumber": 1,
    "objectType": "CONTACT",
    "timestamp": 1234567890
  }
```
Receives and processes webhook events from HubSpot.

## Security Considerations

1. The application uses OAuth 2.0 for secure authentication with HubSpot
2. Access tokens are required for protected endpoints
3. Webhook endpoints should be secured in production
4. Store sensitive credentials securely (e.g., using environment variables or a secure configuration management system)

## Rate Limiting

The application respects HubSpot's API rate limits. When creating contacts, it's recommended to implement appropriate throttling mechanisms in production.

## Error Handling

The application includes comprehensive error handling for:
- OAuth authentication failures
- API rate limiting
- Invalid requests
- Network issues
- Server errors

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details. 