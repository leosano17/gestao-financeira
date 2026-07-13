# Gestão Financeira 💰

Sistema de gestão financeira pessoal desenvolvido com Java e Spring Boot.

🚀 Tecnologias

- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- Lombok
- Maven

⚙️ Funcionalidades

- Cadastro de categorias financeiras
- Lançamento de receitas e despesas
- Consulta de saldo atual
- Histórico de transações

🛠️ Como rodar

Pré-requisitos
- Java 17
- MySQL 8.0

Configuração
1. Clone o repositório
2. Crie o banco de dados:
sql
   CREATE DATABASE gestao_financeira;

3. Configure o `application.properties` com sua senha do MySQL
4. Rode o projeto pelo IntelliJ

Endpoints

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | /categorias | Lista todas as categorias |
| POST | /categorias | Cria uma categoria |
| DELETE | /categorias/{id} | Remove uma categoria |
| GET | /transacoes | Lista todas as transações |
| POST | /transacoes | Cria uma transação |
| DELETE | /transacoes/{id} | Remove uma transação |
| GET | /transacoes/saldo | Consulta saldo atual |

👨‍💻 Autor

Léo Sano - [GitHub](https://github.com/leosano17)