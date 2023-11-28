# Api para cadastro de contatos de profissionais

---
## Objetivo

Esse projeto foi desenvolvido para mostrar um exemplo de como podem ser cadastrados os contatos de profissionais de uma
determinada empresa.

## Tecnologias

- ``Java 17``
- ``Maven``
- ``Spring Boot``
- ``PostgreSql``
- ``JPA``
- ``Lombok``
- ``MapStruct``
- ``Swagger``
- ``Docker``

## Funcionalidades

- CRUD para contatos;
- CRUD para profissionais;
- Busca de contatos e profissionais por string parcial que pode estar contida em qualquer um de seus campos;
- Possibilidade de escolher quais informações deseja obter dos contatos ou dos profissionais.

## Como usar

Este projeto pode ser usado através do Docker, estando na pasta raiz do projeto basta seguir os passos abaixo:

1) Gerar o arquivo *.jar*: `mvn clean package`
2) Executar o comando: `docker compose up -d`
3) Abrir o swagger: http://localhost:8080/swagger-ui/index.html

