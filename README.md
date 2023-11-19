# FriendZone

Recentemente, diversas plataformas de redes sociais anônimas têm optado por se distanciar das grandes redes consolidadas. As motivações para essa mudança são variadas, desde a evolução no modelo de interação dos usuários até as preocupações com a privacidade e a busca por alternativas mais independentes.

Entretanto, em 2023, simplesmente não é prático retroceder aos métodos convencionais de interação online. Foi por isso que decidimos desenvolver o "FriendZone".

Com o Spring Boot, o FriendZOne busca criar uma experiência de usuário única e personalizada, permitindo que os usuários compartilhem seus pensamentos de forma anônima, obtenham conselhos da comunidade e construam conexões significativas sem as amarras das grandes redes tradicionais. Estamos empolgados em colaborar nesse desenvolvimento e criar uma plataforma que respeite a privacidade dos usuários enquanto promove uma comunidade vibrante e solidária.

## Estrutura básica

- Um projeto: FriendZone;
- Controllers que implementam os endpoints da API Rest (VersionController, UsuarioV1Controller, PublicacaoV1Controller e ComentarioV1Controller).
- Uso de WebSocket para tráfego de comentários entre as publicações dos usuarios.
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

## Execução da Aplicação

### Construção do projeto FriendZone usando o comando Gradle:
- ./gradlew clean build
### Execução da aplicação Spring para o arquivo Jar gerado:
- java -jar build/libs/friendzone-0.0.1-SNAPSHOT.jar
### Se preferir
- ./gradlew bootRun --args="--server.port=9000"
### Requisições HTTP local
- go run RunApiRest.go

## Testes

<code>> mvn clean test </code>

## Contato e Dúvidas

levi.pereira.junior@ccc.ufcg.edu.br
