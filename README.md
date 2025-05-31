# Agendamento

Este projeto é um sistema de agendamento desenvolvido em Java com Spring Boot. Ele utiliza PostgreSQL como banco de dados, ActiveMQ para mensageria e integrações externas como Nominatim para geolocalização.

## Estrutura do Projeto

- **src/main/java/**: Código-fonte principal da aplicação.
- **src/main/resources/application.properties**: Configurações da aplicação (banco de dados, e-mail, JWT, ActiveMQ, etc).
- **README.md**: Documentação do projeto.

## Principais Tecnologias

- Java
- Spring Boot
- Spring Data JPA
- PostgreSQL
- ActiveMQ
- JWT (JSON Web Token)
- Nominatim API

## Configuração

Edite o arquivo `src/main/resources/application.properties` conforme necessário:

- Banco de dados PostgreSQL (url, usuário, senha)
- Configurações de e-mail (host, porta, usuário, senha)
- JWT secret
- ActiveMQ (broker-url, usuário, senha)
- Nominatim API URL

## Como Executar

1. Certifique-se de ter o Java 17+ e o PostgreSQL instalados.
2. Configure o banco de dados conforme as propriedades.
3. Execute o projeto:

```bash
./mvnw spring-boot:run
```
ou
```bash
./gradlew bootRun
```

## Endpoints
A aplicação expõe endpoints REST para operações de agendamento (consulte a documentação da API ou o código-fonte para detalhes).

## Licença
Este projeto é apenas para fins educacionais.