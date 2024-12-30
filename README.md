## Auth Control API

### Visão Geral
Auth Control é uma aplicação Spring Boot que fornece um sistema de autenticação e autorização robusto para gerenciar usuários. Com esta API, os usuários podem se registrar, fazer login e receber tokens JWT para autenticação em requisições subsequentes. A aplicação foi projetada para ser escalável e segura, utilizando as melhores práticas de desenvolvimento em Java, incluindo o uso de Spring Security para proteger os endpoints.

### Principais Funcionalidades:
- **Gerenciamento de Usuários**: Permite a criação, leitura e verificação de usuários registrados.
- **Autenticação Segura**: Utiliza JSON Web Tokens (JWT) para autenticação, garantindo sessões sem estado.
- **Validação de Dados**: Implementa validação de entrada usando Jakarta Bean Validation.
- **Tratamento de Exceções**: Fornece mensagens de erro claras e específicas para diferentes cenários.

## Tabela de Conteúdos
- [Recursos](#recursos)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Como Começar](#como-começar)
  - [Pré-requisitos](#pré-requisitos)
  - [Instalação](#instalação)
  - [Executando a Aplicação](#executando-a-aplicação)
  - [Criando um Usuário Admin](#criando-um-usuário-admin)
- [Endpoints da API](#endpoints-da-api)
- [Estrutura do Projeto](#estrutura-do-projeto)

## Recursos
- Criar, ler e verificar registros de usuários.
- Validar dados de entrada usando Jakarta Bean Validation.
- Autenticar usuários com base em email e senha, retornando um token JWT.
- Tratamento de exceções personalizadas para erros comuns.

## Tecnologias Utilizadas
- **Java 23**: Linguagem de programação utilizada para a aplicação.
- **Spring Boot 3.3.6**: Framework para construir a API REST.
- **Spring Security**: Framework para autenticação e autorização, garantindo a segurança da aplicação.
- **Spring Data JPA**: Para interagir com o banco de dados usando JPA.
- **MySQL**: Banco de dados para armazenar registros de usuários.
- **Maven**: Gerenciador de dependências e ferramenta de build.
- **Lombok**: Biblioteca que reduz o código boilerplate em Java.
- **Java JWT**: Biblioteca para trabalhar com JSON Web Tokens.
- **Postman**: Ferramenta utilizada para testar os endpoints da API, facilitando a verificação das funcionalidades implementadas.

## Como Começar

### Pré-requisitos
Antes de começar, certifique-se de que você atendeu aos seguintes requisitos:
- Java Development Kit (JDK) 23 ou superior instalado em sua máquina.
- Maven instalado em sua máquina.
- Servidor MySQL em execução e acessível.

### Instalação
1. Clone o repositório:
   ```bash
   git clone https://github.com/MarceloB-Junior/auth_control_api.git
   cd auth_control_api
   ```
2. Configure o banco de dados:
   - Crie um novo banco de dados no MySQL (por exemplo, `auth_control_db`).
   - Atualize o arquivo `application.properties` localizado em `src/main/resources` com suas credenciais do banco de dados:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/auth_control_db
     spring.datasource.username=seu_usuario
     spring.datasource.password=sua_senha

     spring.jpa.hibernate.ddl-auto=update
     spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true

     auth.jwt.token.secret=seu_token_secret
     auth.jwt.token.expiration=1
     auth.jwt.refresh.token.expiration=8
     ```
3. Instale as dependências:
   ```bash
   mvn install
   ```

### Executando a Aplicação
Para executar a aplicação, use o seguinte comando:
```bash
mvn spring-boot:run
```
A aplicação será iniciada em `http://localhost:8080`.

### Criando um Usuário Admin
Para criar um usuário admin diretamente no banco de dados MySQL, você pode executar o seguinte comando SQL:

```sql
USE auth_control_db;
INSERT INTO tb_user (user_id, email, name, password, role) 
VALUES (UUID_TO_BIN('24f1604d-c74c-48f4-b09f-35ca34e1a6bd'), 'john.doe@example.com', 'John Doe', '$2a$10$YrxH6L72UzuSKZSVYmnqM.Khc5ZR3YW1cTpcW/667/AjlkLoShTgK', 'ADMIN');
```

Certifique-se de que a tabela `tb_user` existe e possui as colunas apropriadas (`user_id`, `email`, `name`, `password`, `role`) antes de executar este comando.

## Endpoints da API

### Login do Usuário
**POST /auth/login**

Corpo da Requisição:
```json
{
    "email": "usuario@example.com",
    "password": "sua_senha"
}
```

Resposta:
```json
{
    "accessToken": "seu_token_jwt",
    "refreshToken": "seu_refresh_token",
    "expiresAt": "2024-12-30T21:01:49.370814300Z"
}
```

### Registro do Usuário
**POST /users/register**

Corpo da Requisição:
```json
{
    "name": "Nome do Usuário",
    "password": "sua_senha",
    "email": "usuario@example.com"
}
```
Resposta:
```json
{
    "name": "Nome do Usuário",
    "email": "usuario@example.com"
}
```

### Refresh Token do Usuário
**POST /auth/refresh-token**

Corpo da Requisição:
```json
{
    "refreshToken": "seu_refresh_token"
}
```

Resposta:
```json
{
    "accessToken": "novo_token_jwt",
    "refreshToken": "novo_refresh_token",
    "expiresAt": "2024-12-31T06:01:49.370814300Z"
}
```

### Obter Todos os Usuários
**GET /users**

### Mensagem para Administrador
**GET /users/admin-role**

Resposta:
```
Welcome, you have admin permission.
```

### Mensagem para Usuário
**GET /users/user-role**

Resposta:
```
Welcome, you have either admin or user permissions.
```

## Estrutura do Projeto

```
auth_control/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── api/
│   │   │           └── auth_control/
│   │   │               ├── configs/
│   │   │               │   └── security/
│   │   │               ├── controllers/
│   │   │               ├── dtos/
│   │   │               ├── enums/
│   │   │               ├── exceptions/          
│   │   │               ├── models/
│   │   │               ├── repositories/
│   │   │               └── services/
│   │   │                   └── impl/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
└── pom.xml
```

