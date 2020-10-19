# kitkat-authorization-service

Authorization Service using JWTs to secure subsequent api calls

Technologies:
* Postgres
* Spring Boot
* Spring JPA
* Spring security using OAauth2
* Java JWT -> JAVA Json Web Tokens implementation
* Flyway for database migration scripts

What you will need before starting the service:
1) Install JAVA 14 using sdk man from sdkman.io
2) Install Docker 

In order to run the service:
1) Run docker-compose up --build
2) Subsequent runs do not have to contain the --build flag