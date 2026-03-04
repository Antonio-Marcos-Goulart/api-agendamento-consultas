# Sistema de Agendamento de Consultas

Este projeto foi desenvolvido visando estudar, praticar e aperfeiçoar o desenvolvimento de software utilizando tecnologias modernas e APIs REST.
O sistema simula um ambiente de agendamento de consultas médicas, incluindo o cadastro de pacientes, médicos e controle de horários.

## Funcionalidades Principais

- **Agendamento de Consultas**: Permite aos usuários cadastrar consultas, selecionando médico, paciente, data/hora, local, tipo e status.
- **Cadastro e Gerenciamento**
    - **Pacientes**: Registro, atualização e exclusão de pacientes.
    - **Médicos**: Registro, escalas e vínculo com especialidades.
- **Validações**: Garante que datas estejam no futuro/presente, campos obrigatórios preenchidos, etc.
- **Enumeração de Status**: Situação do agendamento pode ser `PENDENTE`, `CONFIRMADO`, `CANCELADO` ou `CONCLUIDO`.
- **Notificações por E-mail**: Confirmação do agendamento enviada automaticamente ao paciente.
- **Exceções e Erros**: Mensagens detalhadas para erros de validação e recursos não encontrados via API REST.
- **API RESTful**: Endpoints seguros para CRUD de agendamentos.
- **DTOs**: Transferência de dados simplificada entre camadas.

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot**: Estrutura principal do backend, com dependências Spring Web, Spring Data JPA e Spring Scheduling.
- **Hibernate/JPA**: ORM para persistência e consulta de dados em banco relacional.
- **Bean Validation (javax.validation/jakarta.validation)**: Annotações para validações automáticas dos dados.
- **Lombok**: Anotações para reduzir repetição de código em entidades e DTOs.
- **Jackson**: Serialização e deserialização dos objetos JSON.
- **Arquitetura MVC**: Controllers, Services, Models/Entities, DTOs e Repositories.
- **Testes Unitários**: Estrutura para testes com JUnit no diretório `src/test/`.
- **Spring Email**: Envio automatizado de e-mails de confirmação.
- **Configuração de Serialização**: Datas configuradas no padrão brasileiro via Jackson.

## Exemplo de Fluxo de Agendamento

1. Usuário realiza requisição POST `/agendamentos` com os dados do agendamento.
2. Backend verifica existência do paciente e médico.
3. Realiza validações automáticas.
4. Grava o agendamento e dispara e-mail de confirmação para o paciente.

## Autor

Desenvolvido por [Antonio Marcos Goulart](https://github.com/Antonio-Marcos-Goulart)
