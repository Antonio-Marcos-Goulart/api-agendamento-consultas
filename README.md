# Sistema de Agendamento de Consultas

API REST (com um pequeno frontend estático) para cadastro de pacientes e médicos e criação de agendamentos de consultas, com envio automático de e-mail de confirmação.

Projeto de estudo, focado em praticar Spring Boot, JPA/Hibernate, validação de dados, documentação de API com OpenAPI/Swagger e envio de e-mail.

> Documentação técnica completa (modelo de dados, arquitetura, pontos de atenção, roadmap): [DOCUMENTACAO_PROJETO.md](DOCUMENTACAO_PROJETO.md)

## Sumário

- [Funcionalidades](#funcionalidades)
- [Tecnologias](#tecnologias)
- [Pré-requisitos](#pré-requisitos)
- [Configuração](#configuração)
- [Como executar](#como-executar)
- [Documentação da API (Swagger)](#documentação-da-api-swagger)
- [Endpoints principais](#endpoints-principais)
- [Exemplo de payload](#exemplo-de-payload)
- [Testes](#testes)
- [Estrutura do projeto](#estrutura-do-projeto)
- [Autor](#autor)

## Funcionalidades

- Cadastro, atualização, busca e remoção de pacientes.
- Cadastro, atualização, busca e remoção de médicos.
- Criação e cancelamento de agendamentos, vinculando paciente, médico, data/hora, local, tipo e status.
- Envio automático de e-mail de confirmação ao paciente após o agendamento.
- Envio de e-mail com anexo via API.
- Validações de dados (CPF, telefone, e-mail, CRM, datas obrigatórias/futuras).
- Documentação interativa da API com Swagger UI.
- Frontend estático simples para uso básico da API.

## Tecnologias

- Java 21
- Spring Boot 3.4.5 (Web, Data JPA, Validation, Mail, Actuator)
- Hibernate + PostgreSQL
- Springdoc OpenAPI / Swagger UI
- Lombok
- Maven (Wrapper)
- Docker e Docker Compose
- HTML, CSS e JavaScript puro no frontend

## Pré-requisitos

- Java 21
- Maven Wrapper (incluído no projeto, não precisa instalar Maven)
- PostgreSQL local **ou** Docker + Docker Compose
- Uma conta SMTP para envio de e-mails (ex.: Brevo)

## Configuração

Crie um arquivo `.env` na raiz do projeto com as variáveis abaixo (usadas em `application.properties`):

```env
DB_URL=jdbc:postgresql://localhost:5432/sistemaDeAgendamento
DB_USERNAME=seu_usuario
DB_PASSWORD=sua_senha

SMTP_MAIL=smtp.seuprovedor.com
SMTP_PORT=587
SMTP_USERNAME=seu_usuario_smtp
SMTP_PASSWORD=sua_senha_smtp
```

Outras configurações relevantes já definidas em `application.properties`:

| Configuração | Valor |
|---|---|
| Porta da aplicação | `8081` |
| Banco de dados | PostgreSQL |
| Estratégia de schema | `spring.jpa.hibernate.ddl-auto=update` |
| Formato de datas na API | `dd/MM/yyyy HH:mm:ss` (agendamento) / `dd/MM/yyyy` (nascimento) |
| Upload máximo de anexo | `5MB` |

## Como executar

### Com Maven Wrapper (banco local)

Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Linux/macOS:

```bash
./mvnw spring-boot:run
```

### Com Docker Compose (aplicação + banco)

```bash
docker compose up --build
```

Sobe dois serviços: `sistema-agendamentos-app` (Spring Boot, porta `8081`) e `sistema-agendamentos-db` (PostgreSQL 16, porta `5432`), com volume persistente para os dados do banco.

Após subir, a aplicação fica disponível em:

```text
http://localhost:8081
```

## Documentação da API (Swagger)

```text
http://localhost:8081/swagger-ui/index.html
```

Especificação OpenAPI em JSON:

```text
http://localhost:8081/v3/api-docs
```

## Endpoints principais

| Recurso | Base path | Operações |
|---|---|---|
| Pacientes | `/pacientes` | `POST`, `GET`, `GET /{id}`, `PUT /{id}`, `DELETE /{id}`, `GET /search` |
| Médicos | `/medico` | `POST`, `GET`, `GET /{id}`, `PUT /{id}`, `DELETE /{id}`, `GET /search` |
| Agendamentos | `/agendamentos` | `POST`, `DELETE /{id}` |
| E-mail | `/email` | `POST /enviar` (multipart, com anexo) |

Lista completa de endpoints, parâmetros de busca e exemplos de payload de médico/agendamento estão em [DOCUMENTACAO_PROJETO.md](DOCUMENTACAO_PROJETO.md#endpoints), ou direto no Swagger UI.

## Exemplo de payload

Criar paciente (`POST /pacientes`):

```json
{
  "nome": "Maria Silva",
  "cpf": "12345678901",
  "telefone": "11999999999",
  "email": "maria@email.com",
  "dataNascimento": "10/05/1990",
  "endereco": {
    "rua": "Rua Central",
    "numero": "100",
    "complemento": "Apto 12",
    "bairro": "Centro",
    "cidade": "São Paulo",
    "estado": "SP",
    "cep": "01000-000"
  }
}
```

## Testes

```bash
./mvnw test
```

Windows:

```powershell
.\mvnw.cmd test
```

Testes unitários em `src/test/java/com/antonio/SistemadeAgendamentodeConsultas/unit`, cobrindo os services de paciente, médico e agendamento.

## Estrutura do projeto

```text
.
├── pom.xml
├── Dockerfile
├── compose.yaml
├── src
│   ├── main
│   │   ├── backend/com/antonio/SistemadeAgendamentodeConsultas
│   │   │   ├── config       # Swagger, Jackson
│   │   │   ├── controller   # Endpoints REST
│   │   │   ├── DTOs         # Objetos de entrada/saída
│   │   │   ├── enums        # SituacaoCadastro, SituacaoAgendamento
│   │   │   ├── exception
│   │   │   ├── model        # Entidades JPA
│   │   │   ├── repository
│   │   │   └── service      # Regras de negócio
│   │   └── resources
│   │       ├── application.properties
│   │       └── static       # Frontend (index.html, app.css, app.js)
│   └── test/java/com/antonio/SistemadeAgendamentodeConsultas/unit
```

> O código-fonte Java fica em `src/main/backend` (configurado via `<sourceDirectory>` no `pom.xml`), não no `src/main/java` padrão.

## Autor

Desenvolvido por [Antonio Marcos Goulart](https://github.com/Antonio-Marcos-Goulart)
