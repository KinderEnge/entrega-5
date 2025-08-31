# MindMatch Pagamento API

Este projeto é uma API REST para gerenciamento de pagamentos, desenvolvida com Spring Boot, JPA, H2 Database e Angular para o dashboard frontend.

## Sumário
- [Visão Geral](#visão-geral)
- [Tecnologias](#tecnologias)
- [Como Executar](#como-executar)
- [Endpoints](#endpoints)
- [Autenticação](#autenticação)
- [Testes](#testes)
- [Frontend Angular](#frontend-angular)
- [Git Workflow](#git-workflow)
- [Autores](#autores)

---

## Visão Geral
API para cadastro, consulta, atualização e remoção de pagamentos. Inclui autenticação básica, tratamento de exceções e seed de dados para ambiente de desenvolvimento.

## Tecnologias
- Java 17+
- Spring Boot
- Spring Security
- JPA/Hibernate
- H2 Database (dev/test)
- Maven
- Angular (dashboard)

## Como Executar
1. **Backend**
   - Requisitos: Java 17+, Maven
   - Instale dependências:
     ```shell
     ./mvnw clean install
     ```
   - Execute a aplicação:
     ```shell
     ./mvnw spring-boot:run
     ```
   - Acesse H2 Console: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
     - JDBC URL: `jdbc:h2:mem:testdb`
     - Usuário: `sa` | Senha: (em branco)

2. **Frontend**
   - Requisitos: Node.js, npm
   - Instale Angular CLI:
     ```shell
     npm install -g @angular/cli
     ```
   - Instale dependências e rode o dashboard:
     ```shell
     cd dashboard
     npm install
     ng serve
     ```
   - Acesse: [http://localhost:4200](http://localhost:4200)

## Endpoints
- `GET /pagamentos` — Lista todos os pagamentos
- `GET /pagamentos/{id}` — Consulta pagamento por ID
- `POST /pagamentos` — Cria novo pagamento
- `PUT /pagamentos/{id}` — Atualiza pagamento
- `DELETE /pagamentos/{id}` — Remove pagamento

## Autenticação
- **Desenvolvimento:** Todos endpoints `/pagamentos/**` estão públicos.
- **Produção:** Autenticação Basic Auth (configurável em `SecurityConfiguration.java`).

## Testes
- Testes automatizados em `src/test/java/com/mindmatch/pagamento/PagamentoApplicationTests.java`
- Para rodar:
  ```shell
  ./mvnw test
  ```

## Banco de Dados
- Seed de dados em `src/main/resources/data.sql` (ou `import.sql`)
- Configuração em `application.properties`:
  - `spring.datasource.url=jdbc:h2:mem:testdb`
  - `spring.datasource.username=sa`
  - `spring.datasource.password=`
  - `spring.datasource.driver-class-name=org.h2.Driver`
  - `spring.jpa.hibernate.ddl-auto=update`



