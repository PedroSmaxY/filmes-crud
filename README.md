# Filmes CRUD

API REST simples para gerenciar filmes, clientes, funcionários e aluguéis.

Projeto desenvolvido para a matéria de Projeto de Extensão em Engenharia de Software.

- Você pode cadastrar, listar, atualizar e remover filmes.
- Gerenciar clientes e funcionários.
- Realizar aluguéis e devoluções com controle de cópias disponíveis.

Documentação da API (Swagger):
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI: `http://localhost:8080/api-docs`

## Como rodar

Pré-requisitos: JDK 21+ (Maven Wrapper já incluso no projeto)

- Executar em modo desenvolvimento:

```bash
./mvnw spring-boot:run
```

- Build do JAR e execução:

```bash
./mvnw clean package
java -jar target/filmes-0.0.1-SNAPSHOT.jar
```

A aplicação inicia na porta 8080.

## Colaboradores
- [Pedro Henrique](https://github.com/PedroSmaxY)
- [Deborah Goulart](http://github.com/debhgoulart)
- [Victor Jacques](https://github.com/Victor-Jacques)
-
