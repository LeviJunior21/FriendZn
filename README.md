# FriendZn
### Construa o projeto FriendZone usando o comando Gradle:
- ./gradlew clean build
### Execute a aplicação Spring para o arquivo Jar gerado:
- java -jar build/libs/friendzone-0.0.1-SNAPSHOT.jar
### Se preferir
- ./gradlew bootRun --args="--server.port=9000"
### Requisições HTTP
- go run RunApiRest.go

## Estrutura básica

- Um projeto: FriendZone;
- Controllers que implementam os endpoints da API Rest (VersionController, UsuarioV1Controller, PublicacaoV1Controller e ComentarioV1Controller).
- Quatro repositórios são utilizados: UsuarioRepository, PublicacaoRepository e ComentarioRepository, que são responsáveis por manipular as entidades Usuario, Publicação e Comentario em um banco de dados em memória;
- O modelo é composto pelas classes Usuario.java, Publicacao.java e Comentario.java, que podem ser encontradas no pacote model;
- O pacote exceptions guarda as classes de exceções que podem ser levantadas dentro do sistema;
- Há implementação de frontend (aqui: [FriendZoneApp](https://github.com/LeviJunior21/FriendZnApp)) e o projeto fornece uma interface de acesso à API via swagger.

## Tecnologias
Código base gerado via [start.sprint.io](https://start.spring.io/#!type=maven-project&language=java&platformVersion=2.3.3.RELEASE&packaging=jar&jvmVersion=1.8&groupId=com.example&artifactId=EstoqueFacil&name=EstoqueFacil&description=Projeto%20Estoque%20Facil&packageName=com.example.EstoqueFacil&dependencies=web,actuator,devtools,data-jpa,h2) com as seguintes dependências:

- Spring Web
- Spring Security
- Spring WebSocket
- Spring Validation
- Spring Actuator
- Spring Boot DevTools
- Spring Data JPA
- H2 Database
- Cucumber

