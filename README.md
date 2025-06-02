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

1. Certifique-se de ter o Java 17+
2. Execute o comando abaixo para instalacao e configuracao do banco de dados da aplicacao:
```bash
docker run -d \ --name postgres-agendamento \ -p 5433:5432 \ -e POSTGRES_DB=agendamento \ -e POSTGRES_USER=postgres \ -e POSTGRES_PASSWORD=Admin123# \ postgres:latest
```
3. Execute o comando abaixo para instalacao e configuracao do servidor local de e-mail:
```bash
docker run -d -p 1027:1025 -p 8027:8025 mailhog/mailhog
```
4. Execute o comando abaixo para instalacao e configuracao do Message Broker para gerenciamento de filas JMS:
```bash 
docker run -d --name=activemq -p 61616:61616 -p 8161:8161 rmohr/activemq
```
5. Execute o projeto:
```bash
./mvnw spring-boot:run
```
ou
```bash
./gradlew bootRun
```

## Como Acessar

- Aplicação Spring Boot: Acesse a sua aplicação no navegador usando a URL:
```bash
http://localhost:8080
 ```   
- Swagger UI:
```bash
http://localhost:8080/swagger-ui/index.html
```
- MailHog: Acesse a interface web do MailHog no navegador usando a URL:
```bash
http://localhost:8027
```
- ActiveMQ: Acesse a interface web de administração do ActiveMQ no navegador usando a URL:
```bash
http://localhost:8161
```    
    Obs.:Use o nome de usuário e senha padrão  (admin e admin) para fazer login.

## Endpoints
A aplicação expõe endpoints REST para operações de agendamento (consulte a documentação da API ou o código-fonte para detalhes).

Para executar a aplicacao, importe a collection encontrada no diretorio **documentacao/ProjetoJacto.postman_collection.json** no Postman e execute os endpoints de login primeiro e em seguida utilize os demais como exemplo de requisicao.

## Licença
Este projeto é apenas para fins educacionais.